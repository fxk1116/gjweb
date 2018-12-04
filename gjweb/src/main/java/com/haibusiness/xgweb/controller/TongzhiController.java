package com.haibusiness.xgweb.controller;

import com.haibusiness.xgweb.domain.Tongzhi;
import com.haibusiness.xgweb.domain.User;
import com.haibusiness.xgweb.service.TongzhiService;
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
@RequestMapping("/tongzhi")

public class TongzhiController {
    private static final Logger logger=LoggerFactory.getLogger(TongzhiController.class);

    @Autowired
    private TongzhiService tongzhiService;

    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_USER')")
    @GetMapping("/list")
    public ModelAndView list(
                             @RequestParam(value="async",required=false) boolean async,
                             @RequestParam(value="pageIndex",required=false,defaultValue="0") int pageIndex,
                             @RequestParam(value="pageSize",required=false,defaultValue="10") int pageSize,
                             @RequestParam(value="title",required=false,defaultValue="") String title,
                             Model model) {

        Pageable pageable = new PageRequest(pageIndex, pageSize);
        Page<Tongzhi> page = tongzhiService.listTongzhisByTitleLike(title,pageable);
        List<Tongzhi> list = page.getContent();	// 当前所在页面数据列表

        model.addAttribute("page", page);
        model.addAttribute("tongzhiList", list);
        model.addAttribute("title","通知公告");
        return new ModelAndView(async==true?"admin/tongzhi/list :: #mainContainerRepleace":"admin/tongzhi/list", "tongzhiModel", model);
    }
    /**
     * 获取 form 表单页面
     * @param model
     * @return
     */
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_USER')")
    @GetMapping("/add")
    public ModelAndView createForm(Model model) {
        model.addAttribute("tongzhi", new Tongzhi());
        return new ModelAndView("/admin/tongzhi/edit", "tongzhiModel", model);
    }

    /**
     * 新建和更新教研工作
     * @param tongzhi


     * @return
     */
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_USER')")
    @PostMapping("/update")
    public ResponseEntity<Response> create( Tongzhi tongzhi) {


        try {
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

           //新建教研工作
            if(tongzhi.getId()==null){
                tongzhi.setHit(0);
                tongzhi.setPublisher(user);
                tongzhi.setPublishTime(new Date());
                tongzhi.setUpdateTime(new Date());
                tongzhi.setUpdateUser(user);

            }else{
                //更新教研工作,对于表单中没有出现的字段要把原来的值付给它们
                Tongzhi originalTongzhi=tongzhiService.getTongzhiById(tongzhi.getId());
                tongzhi.setHit(originalTongzhi.getHit());
                tongzhi.setPublisher(originalTongzhi.getPublisher());
                tongzhi.setPublishTime(originalTongzhi.getPublishTime());
                tongzhi.setUpdateTime(new Date());
                tongzhi.setUpdateUser(user);

            }

            tongzhiService.saveTongzhi(tongzhi);
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
            tongzhiService.removeTongzhi(id);
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
        Tongzhi tongzhi = tongzhiService.getTongzhiById(id);
        model.addAttribute("tongzhi", tongzhi);

        return new ModelAndView("admin/tongzhi/edit", "tongzhiModel", model);
    }

    @GetMapping(value = "homeList")
    public ModelAndView homeList(Model model) {
        Pageable pageable = new PageRequest(0, 20);
        Page<Tongzhi> page = tongzhiService.listTongzhisByTitleLike("", pageable);
        List<Tongzhi> list = page.getContent();    // 当前所在页面数据列表

        model.addAttribute("tongzhiList",list);
        model.addAttribute("title","通知公告");
        return new ModelAndView("tongzhiList", "tongzhiModel", model);
    }
    @GetMapping(value = "view/{id}")
    public ModelAndView homeView(@PathVariable("id") Long id, Model model) {
        Tongzhi tongzhi = tongzhiService.getTongzhiById(id);
        tongzhi.setHit(tongzhi.getHit()+1);
        tongzhiService.saveTongzhi(tongzhi);
        model.addAttribute("tongzhi", tongzhi);
        model.addAttribute("title","通知公告");
        return new ModelAndView("tongzhiView", "tongzhiModel", model);
    }
}
