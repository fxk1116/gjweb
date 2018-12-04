package com.haibusiness.xgweb.controller;

import com.haibusiness.xgweb.domain.Gaikuang;
import com.haibusiness.xgweb.domain.User;
import com.haibusiness.xgweb.service.GaikuangService;
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
@RequestMapping("/gaikuang")

public class GaikuangController {
    private static final Logger logger=LoggerFactory.getLogger(GaikuangController.class);

    @Autowired
    private GaikuangService gaikuangService;

    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_USER')")
    @GetMapping("/list/{type}")
    public ModelAndView list(@PathVariable("type") String type,
                             @RequestParam(value="async",required=false) boolean async,
                             @RequestParam(value="pageIndex",required=false,defaultValue="0") int pageIndex,
                             @RequestParam(value="pageSize",required=false,defaultValue="10") int pageSize,
                             @RequestParam(value="title",required=false,defaultValue="") String title,
                             Model model) {

        Pageable pageable = new PageRequest(pageIndex, pageSize);
        Page<Gaikuang> page = gaikuangService.listGaikuangsByTypeAndTitleLike(type,title,pageable);
        List<Gaikuang> list = page.getContent();	// 当前所在页面数据列表

        model.addAttribute("page", page);
        model.addAttribute("gaikuangList", list);
        model.addAttribute("title","学院概况 》"+Menu.getMenu().get(type));
        model.addAttribute("type",type);
        return new ModelAndView(async==true?"admin/gaikuang/list :: #mainContainerRepleace":"admin/gaikuang/list", "gaikuangModel", model);
    }
    /**
     * 获取 form 表单页面
     * @param model
     * @return
     */
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_USER')")
    @GetMapping("/add/{type}")
    public ModelAndView createForm(@PathVariable("type") String type,Model model) {
        model.addAttribute("gaikuang", new Gaikuang());
        model.addAttribute("type", type);
        return new ModelAndView("/admin/gaikuang/edit", "gaikuangModel", model);
    }

    /**
     * 新建和更新教研工作
     * @param gaikuang


     * @return
     */
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_USER')")
    @PostMapping("/update/{type}")
    public ResponseEntity<Response> create(@PathVariable("type") String type, Gaikuang gaikuang) {


        try {
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

           //新建教研工作
            if(gaikuang.getId()==null){
                gaikuang.setHit(0);
                gaikuang.setPublisher(user);
                gaikuang.setPublishTime(new Date());
                gaikuang.setUpdateTime(new Date());
                gaikuang.setUpdateUser(user);

            }else{
                //更新教研工作,对于表单中没有出现的字段要把原来的值付给它们
                Gaikuang originalGaikuang=gaikuangService.getGaikuangById(gaikuang.getId());
                gaikuang.setHit(originalGaikuang.getHit());
                gaikuang.setPublisher(originalGaikuang.getPublisher());
                gaikuang.setPublishTime(originalGaikuang.getPublishTime());
                gaikuang.setUpdateTime(new Date());
                gaikuang.setUpdateUser(user);

            }

           gaikuangService.saveGaikuang(gaikuang);
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
            gaikuangService.removeGaikuang(id);
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
        Gaikuang gaikuang = gaikuangService.getGaikuangById(id);
        model.addAttribute("gaikuang", gaikuang);

        return new ModelAndView("admin/gaikuang/edit", "gaikuangModel", model);
    }

    @GetMapping(value = "homeList/{type}")
    public ModelAndView homeList(@PathVariable("type") String type, Model model) {
        Pageable pageable = new PageRequest(0, 20);
        Page<Gaikuang> page = gaikuangService.listGaikuangsByTypeAndTitleLike(""+type, "", pageable);
        List<Gaikuang> list = page.getContent();    // 当前所在页面数据列表

        model.addAttribute("gaikuangList",list);
        model.addAttribute("type",type);
        model.addAttribute("title","学院概况 》"+Menu.getMenu().get(type));
        return new ModelAndView("gaikuangList", "gaikuangModel", model);
    }
    @GetMapping(value = "view/{id}/{type}")
    public ModelAndView homeView(@PathVariable("id") Long id,@PathVariable("type") String type, Model model) {
        Gaikuang gaikuang = gaikuangService.getGaikuangById(id);
        gaikuang.setHit(gaikuang.getHit()+1);
        gaikuangService.saveGaikuang(gaikuang);
        model.addAttribute("gaikuang", gaikuang);
        model.addAttribute("title","学院概况 》"+Menu.getMenu().get(type));
        return new ModelAndView("gaikuangView", "gaikuangModel", model);
    }
}
