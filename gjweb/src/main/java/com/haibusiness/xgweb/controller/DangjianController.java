package com.haibusiness.xgweb.controller;

import com.haibusiness.xgweb.domain.Dangjian;
import com.haibusiness.xgweb.domain.User;
import com.haibusiness.xgweb.service.DangjianService;
import com.haibusiness.xgweb.util.ConstraintViolationExceptionHandler;
import com.haibusiness.xgweb.vo.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.ConstraintViolationException;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/dangjian")

public class DangjianController {
    private static final Logger logger=LoggerFactory.getLogger(DangjianController.class);

    @Autowired
    private DangjianService dangjianService;

    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_USER')")
    @GetMapping("/list")
    public ModelAndView list(
                             @RequestParam(value="async",required=false) boolean async,
                             @RequestParam(value="pageIndex",required=false,defaultValue="0") int pageIndex,
                             @RequestParam(value="pageSize",required=false,defaultValue="10") int pageSize,
                             @RequestParam(value="title",required=false,defaultValue="") String title,
                             Model model) {

        Pageable pageable = new PageRequest(pageIndex, pageSize);
        Page<Dangjian> page = dangjianService.listDangjiansByTitleLike(title,pageable);
        List<Dangjian> list = page.getContent();	// 当前所在页面数据列表

        model.addAttribute("page", page);
        model.addAttribute("dangjianList", list);
        model.addAttribute("title","学院动态");
        return new ModelAndView(async==true?"admin/dangjian/list :: #mainContainerRepleace":"admin/dangjian/list", "dangjianModel", model);
    }
    /**
     * 获取 form 表单页面
     * @param model
     * @return
     */
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_USER')")
    @GetMapping("/add")
    public ModelAndView createForm(Model model) {
        model.addAttribute("dangjian", new Dangjian());
        return new ModelAndView("/admin/dangjian/edit", "dangjianModel", model);
    }

    /**
     * 新建和更新教研工作
     * @param dangjian


     * @return
     */
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_USER')")
    @PostMapping("/update")
    public ResponseEntity<Response> create( Dangjian dangjian) {


        try {
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

           //新建教研工作
            if(dangjian.getId()==null){
                dangjian.setHit(0);
                dangjian.setPublisher(user);
                dangjian.setPublishTime(new Date());
                dangjian.setUpdateTime(new Date());
                dangjian.setUpdateUser(user);

            }else{
                //更新教研工作,对于表单中没有出现的字段要把原来的值付给它们
                Dangjian originalDangjian=dangjianService.getDangjianById(dangjian.getId());
                dangjian.setHit(originalDangjian.getHit());
                dangjian.setPublisher(originalDangjian.getPublisher());
                dangjian.setPublishTime(originalDangjian.getPublishTime());
                dangjian.setUpdateTime(new Date());
                dangjian.setUpdateUser(user);

            }

            dangjianService.saveDangjian(dangjian);
        } catch (ConstraintViolationException e)  {
            return ResponseEntity.ok().body(new Response(false, ConstraintViolationExceptionHandler.getMessage(e)));
        } catch (Exception e) {
            return ResponseEntity.ok().body(new Response(false, e.getMessage()));
        }
        return ResponseEntity.ok().body(new Response(true, "处理成功", null));
    }

    /**
     * 删除教研工作
     * @param id
     * @return
     */
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_USER')")
    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<Response> delete(@PathVariable("id") Long id) {
        try {
            dangjianService.removeDangjian(id);
        } catch (Exception e) {
            return  ResponseEntity.ok().body( new Response(false, e.getMessage()));
        }
        return  ResponseEntity.ok().body( new Response(true, "处理成功"));
    }

    /**
     * 获取修、添加改教研工作的界面及数据
     * @param id,model
     * @return
     */
    @GetMapping(value = "edit/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_USER')")
    public ModelAndView modifyForm(@PathVariable("id") Long id, Model model) {
        Dangjian dangjian = dangjianService.getDangjianById(id);
        model.addAttribute("dangjian", dangjian);

        return new ModelAndView("admin/dangjian/edit", "dangjianModel", model);
    }

    @GetMapping(value = "homeList")
    public ModelAndView homeList(Model model) {
        Pageable pageable = new PageRequest(0, 20);
        Page<Dangjian> page = dangjianService.listDangjiansByTitleLike("", pageable);
        List<Dangjian> list = page.getContent();    // 当前所在页面数据列表

        model.addAttribute("dangjianList",list);
        model.addAttribute("title","党建");
        return new ModelAndView("dangjianList", "dangjianModel", model);
    }
    @GetMapping(value = "view/{id}")
    public ModelAndView homeView(@PathVariable("id") Long id, Model model) {
        Dangjian dangjian = dangjianService.getDangjianById(id);
        dangjian.setHit(dangjian.getHit()+1);
        dangjianService.saveDangjian(dangjian);
        model.addAttribute("dangjian", dangjian);
        model.addAttribute("title","党建");
        return new ModelAndView("dangjianView", "dangjianModel", model);
    }
}
