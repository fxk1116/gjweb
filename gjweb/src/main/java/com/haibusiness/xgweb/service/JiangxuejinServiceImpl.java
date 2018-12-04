/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.haibusiness.xgweb.service;

import com.haibusiness.xgweb.dao.JiangxuejinDao;
import com.haibusiness.xgweb.domain.Jiangxuejin;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 *
 * @author Lenovo
 */
@Service
@Transactional

public class JiangxuejinServiceImpl implements JiangxuejinService{

    @Autowired
    private JiangxuejinDao jiangxuejinDao;
    
    @Override
    public Jiangxuejin saveJiangxuejin(Jiangxuejin jiangxuejin) {
        return jiangxuejinDao.save(jiangxuejin); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void removeJiangxuejin(Long id) {
        jiangxuejinDao.delete(id); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void removeJiangxuejinsInBatch(List<Jiangxuejin> jiangxuejins) {
        jiangxuejinDao.deleteInBatch(jiangxuejins); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Jiangxuejin updateJiangxuejin(Jiangxuejin jiangxuejin) {
        return jiangxuejinDao.save(jiangxuejin); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Jiangxuejin getJiangxuejinById(Long id) {
        return jiangxuejinDao.getOne(id); //To change body of generated methods, choose Tools | Templates.
    }
    
   

    @Override
    public List<Jiangxuejin> listJiangxuejins() {
        return jiangxuejinDao.findAll(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Page<Jiangxuejin> listJiangxuejinsByTypeAndTitleLike(String type,String title, Pageable pageable) {
        title = "%" + title + "%";
        Page<Jiangxuejin> jiangxuejins = jiangxuejinDao.findByTypeAndTitleLikeOrderByPublishTimeDesc(type, title, pageable);
        return jiangxuejins; //To change body of generated methods, choose Tools | Templates.
    }
    
}
