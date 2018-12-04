package com.haibusiness.xgweb.controller;

import com.haibusiness.xgweb.domain.Tupian;
import com.haibusiness.xgweb.domain.User;
import com.haibusiness.xgweb.service.TupianService;
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
@RequestMapping("/tupian")

public class TupianController {
    private static final Logger logger=LoggerFactory.getLogger(TupianController.class);

    @Autowired
    private TupianService tupianService;

    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_USER')")
    @GetMapping("/list")
    public ModelAndView list(
                             @RequestParam(value="async",required=false) boolean async,
                             @RequestParam(value="pageIndex",required=false,defaultValue="0") int pageIndex,
                             @RequestParam(value="pageSize",required=false,defaultValue="10") int pageSize,
                             @RequestParam(value="title",required=false,defaultValue="") String title,
                             Model model) {

        Pageable pageable = new PageRequest(pageIndex, pageSize);
        Page<Tupian> page = tupianService.listTupiansByTitleLike(title,pageable);
        List<Tupian> list = page.getContent();	// 当前所在页面数据列表

        model.addAttribute("page", page);
        model.addAttribute("tupianList", list);
        model.addAttribute("title","学院动态");
        return new ModelAndView(async==true?"admin/tupian/list :: #mainContainerRepleace":"admin/tupian/list", "tupianModel", model);
    }
    /**
     * 获取 form 表单页面
     * @param model
     * @return
     */
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_USER')")
    @GetMapping("/add")
    public ModelAndView createForm(Model model) {
        model.addAttribute("tupian", new Tupian());
        return new ModelAndView("/admin/tupian/edit", "tupianModel", model);
    }

    /**
     * 新建和更新教研工作
     * @param tupian


     * @return
     */
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_USER')")
    @PostMapping("/update")
    public ResponseEntity<Response> create( Tupian tupian) {


        try {
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

           //新建教研工作
            if(tupian.getId()==null){
                tupian.setHit(0);
                tupian.setPublisher(user);
                tupian.setPublishTime(new Date());
                tupian.setUpdateTime(new Date());
                tupian.setUpdateUser(user);

            }else{
                //更新教研工作,对于表单中没有出现的字段要把原来的值付给它们
                Tupian originalTupian=tupianService.getTupianById(tupian.getId());
                tupian.setHit(originalTupian.getHit());
                tupian.setPublisher(originalTupian.getPublisher());
                tupian.setPublishTime(originalTupian.getPublishTime());
                tupian.setUpdateTime(new Date());
                tupian.setUpdateUser(user);

            }

            tupianService.saveTupian(tupian);
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
            tupianService.removeTupian(id);
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
        Tupian tupian = tupianService.getTupianById(id);
        model.addAttribute("tupian", tupian);

        return new ModelAndView("admin/tupian/edit", "tupianModel", model);
    }

    @GetMapping(value = "homeList")
    public ModelAndView homeList(Model model) {
        Pageable pageable = new PageRequest(0, 20);
        Page<Tupian> page = tupianService.listTupiansByTitleLike("", pageable);
        List<Tupian> list = page.getContent();    // 当前所在页面数据列表

        model.addAttribute("tupianList",list);
        model.addAttribute("title","学院动态");
        return new ModelAndView("tupianList", "tupianModel", model);
    }
    @GetMapping(value = "view/{id}")
    public ModelAndView homeView(@PathVariable("id") Long id, Model model) {
        Tupian tupian = tupianService.getTupianById(id);
        tupian.setHit(tupian.getHit()+1);
        tupianService.saveTupian(tupian);
        model.addAttribute("tupian", tupian);
        model.addAttribute("title","学院动态");
        return new ModelAndView("tupianView", "tupianModel", model);
    }
}
