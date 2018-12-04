package com.haibusiness.xgweb.controller;

import com.haibusiness.xgweb.domain.*;
import com.haibusiness.xgweb.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

@Controller

public class MainController {
    private static final Long ROLE_USER_AUTHORITY_ID = 3L;
    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    private UserService userService;

    @Autowired
    private AuthorityService authorityService;

    @Autowired
    private JiaoyanService jiaoyanService;
    @Autowired
    private DongtaiService dongtaiService;
     @Autowired
    private TongzhiService tongzhiService;
   
    @Autowired
    private BiyeshengService biyeshengService;

    @GetMapping("/")
    public String root() {
        return "redirect:/index";
    }


    @GetMapping("/index")
    public String index(Model model) {
        model.addAttribute("jiaoxueList", this.getJiaoxueList());
        model.addAttribute("dongtaiList", this.getDongtaiList());
         model.addAttribute("tongzhiList", this.getTongzhiList());
        model.addAttribute("biyeshengList", this.getBiyeshengList());
        return "index";
    }

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("hidenLogin", "false");

        return "index";
    }

    @GetMapping("/login-error")
    public String loginError(Model model) {
        model.addAttribute("loginError", true);
        model.addAttribute("errorMsg", "登陆失败，账号或者密码错误！");
        return "redirect:/login";
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("hidenRegister", "false");
        return "index";
    }

    @GetMapping("/error")
    public String error(Model model) {
        model.addAttribute("errorMsg", "出错了！");
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(User user) {
        List<Authority> authorities = new ArrayList<>();
        authorities.add(authorityService.getAuthorityById(ROLE_USER_AUTHORITY_ID));
        user.setAuthorities(authorities);
        user.setEncodePassword(user.getPassword());
        userService.saveUser(user);
        return "redirect:/login";
    }

    @GetMapping("/logout/page")
    public String logoutPage() {

        return "redirect:/login";
    }



    private List<Jiaoyan> getJiaoxueList() {
        Pageable pageable = new PageRequest(0, 12);
        Page<Jiaoyan> page = jiaoyanService.listJiaoyansByTypeAndTitleLike("jiaoxue", "", pageable);
        List<Jiaoyan> list = page.getContent();    // 当前所在页面数据列表

        for (Jiaoyan jiaoxue : list) {
            entityManager.detach(jiaoxue);
            jiaoxue.setTitle("<i class=\"fa fa-chevron-circle-right fa-fw\"></i>" + jiaoxue.getTitle());
        }
        return list;
    }
    private List<Dongtai> getDongtaiList() {
        Pageable pageable = new PageRequest(0, 12);
        Page<Dongtai> page = dongtaiService.listDongtaisByTitleLike("", pageable);
        List<Dongtai> list = page.getContent();    // 当前所在页面数据列表
        for (Dongtai dongtai : list) {
            entityManager.detach(dongtai);
            dongtai.setTitle("<i class=\"fa fa-chevron-circle-right fa-fw\"></i>" + dongtai.getTitle());
        }
        return list;
    }
        private List<Tongzhi> getTongzhiList() {
        Pageable pageable = new PageRequest(0, 12);
        Page<Tongzhi> page = tongzhiService.listTongzhisByTitleLike("", pageable);
        List<Tongzhi> list = page.getContent();    // 当前所在页面数据列表
        for (Tongzhi tongzhi : list) {
            entityManager.detach(tongzhi);
            tongzhi.setTitle("<i class=\"fa fa-chevron-circle-right fa-fw\"></i>" + tongzhi.getTitle());
        }
        return list;
    }
    private List<Biyesheng> getBiyeshengList() {
        Pageable pageable = new PageRequest(0, 12);
        Page<Biyesheng> page = biyeshengService.listBiyeshengsByTitleLike("", pageable);
        List<Biyesheng> list = page.getContent();    // 当前所在页面数据列表

        return list;
    }
}
