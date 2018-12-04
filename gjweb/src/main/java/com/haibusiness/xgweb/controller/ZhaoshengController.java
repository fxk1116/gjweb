package com.haibusiness.xgweb.controller;

import com.haibusiness.xgweb.domain.Zhaosheng;
import com.haibusiness.xgweb.domain.User;
import com.haibusiness.xgweb.service.ZhaoshengService;
import com.haibusiness.xgweb.service.JiaoyanService;
import com.haibusiness.xgweb.util.ConstraintViolationExceptionHandler;
import com.haibusiness.xgweb.util.Menu;
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
@RequestMapping("/zhaosheng")

public class ZhaoshengController {
    private static final Logger logger=LoggerFactory.getLogger(ZhaoshengController.class);

    @Autowired
    private ZhaoshengService zhaoshengService;

    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_USER')")
    @GetMapping("/list/{type}")
    public ModelAndView list(@PathVariable("type") String type,
                             @RequestParam(value="async",required=false) boolean async,
                             @RequestParam(value="pageIndex",required=false,defaultValue="0") int pageIndex,
                             @RequestParam(value="pageSize",required=false,defaultValue="10") int pageSize,
                             @RequestParam(value="title",required=false,defaultValue="") String title,
                             Model model) {

        Pageable pageable = new PageRequest(pageIndex, pageSize);
        Page<Zhaosheng> page = zhaoshengService.listZhaoshengsByTypeAndTitleLike(type,title,pageable);
        List<Zhaosheng> list = page.getContent();	// 当前所在页面数据列表

        model.addAttribute("page", page);
        model.addAttribute("zhaoshengList", list);
        model.addAttribute("title","招生信息 》"+Menu.getMenu().get(type));
        model.addAttribute("type",type);
        return new ModelAndView(async==true?"admin/zhaosheng/list :: #mainContainerRepleace":"admin/zhaosheng/list", "zhaoshengModel", model);
    }
    /**
     * 获取 form 表单页面
     * @param model
     * @return
     */
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_USER')")
    @GetMapping("/add/{type}")
    public ModelAndView createForm(@PathVariable("type") String type,Model model) {
        model.addAttribute("zhaosheng", new Zhaosheng());
        model.addAttribute("type", type);
        return new ModelAndView("/admin/zhaosheng/edit", "zhaoshengModel", model);
    }

    /**
     * 新建和更新教研工作
     * @param zhaosheng


     * @return
     */
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_USER')")
    @PostMapping("/update/{type}")
    public ResponseEntity<Response> create(@PathVariable("type") String type, Zhaosheng zhaosheng) {


        try {
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

           //新建教研工作
            if(zhaosheng.getId()==null){
                zhaosheng.setHit(0);
                zhaosheng.setPublisher(user);
                zhaosheng.setPublishTime(new Date());
                zhaosheng.setUpdateTime(new Date());
                zhaosheng.setUpdateUser(user);

            }else{
                //更新教研工作,对于表单中没有出现的字段要把原来的值付给它们
                Zhaosheng originalZhaosheng=zhaoshengService.getZhaoshengById(zhaosheng.getId());
                zhaosheng.setHit(originalZhaosheng.getHit());
                zhaosheng.setPublisher(originalZhaosheng.getPublisher());
                zhaosheng.setPublishTime(originalZhaosheng.getPublishTime());
                zhaosheng.setUpdateTime(new Date());
                zhaosheng.setUpdateUser(user);

            }

           zhaoshengService.saveZhaosheng(zhaosheng);
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
            zhaoshengService.removeZhaosheng(id);
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
        Zhaosheng zhaosheng = zhaoshengService.getZhaoshengById(id);
        model.addAttribute("zhaosheng", zhaosheng);

        return new ModelAndView("admin/zhaosheng/edit", "zhaoshengModel", model);
    }

    @GetMapping(value = "homeList/{type}")
    public ModelAndView homeList(@PathVariable("type") String type, Model model) {
        Pageable pageable = new PageRequest(0, 20);
        Page<Zhaosheng> page = zhaoshengService.listZhaoshengsByTypeAndTitleLike(""+type, "", pageable);
        List<Zhaosheng> list = page.getContent();    // 当前所在页面数据列表

        model.addAttribute("zhaoshengList",list);
        model.addAttribute("type",type);
        model.addAttribute("title","招生信息 》"+Menu.getMenu().get(type));
        return new ModelAndView("zhaoshengList", "zhaoshengModel", model);
    }
    @GetMapping(value = "view/{id}/{type}")
    public ModelAndView homeView(@PathVariable("id") Long id,@PathVariable("type") String type, Model model) {
        Zhaosheng zhaosheng = zhaoshengService.getZhaoshengById(id);
        zhaosheng.setHit(zhaosheng.getHit()+1);
        zhaoshengService.saveZhaosheng(zhaosheng);
        model.addAttribute("zhaosheng", zhaosheng);
        model.addAttribute("title","招生信息 》"+Menu.getMenu().get(type));
        return new ModelAndView("zhaoshengView", "zhaoshengModel", model);
    }
}
