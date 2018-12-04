/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.haibusiness.xgweb.service;

import com.haibusiness.xgweb.dao.ZhaoshengDao;
import com.haibusiness.xgweb.domain.Zhaosheng;
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

public class ZhaoshengServiceImpl implements ZhaoshengService{

    @Autowired
    private ZhaoshengDao zhaoshengDao;
    
    @Override
    public Zhaosheng saveZhaosheng(Zhaosheng zhaosheng) {
        return zhaoshengDao.save(zhaosheng); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void removeZhaosheng(Long id) {
        zhaoshengDao.delete(id); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void removeZhaoshengsInBatch(List<Zhaosheng> zhaoshengs) {
        zhaoshengDao.deleteInBatch(zhaoshengs); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Zhaosheng updateZhaosheng(Zhaosheng zhaosheng) {
        return zhaoshengDao.save(zhaosheng); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Zhaosheng getZhaoshengById(Long id) {
        return zhaoshengDao.getOne(id); //To change body of generated methods, choose Tools | Templates.
    }
    
   

    @Override
    public List<Zhaosheng> listZhaoshengs() {
        return zhaoshengDao.findAll(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Page<Zhaosheng> listZhaoshengsByTypeAndTitleLike(String type,String title, Pageable pageable) {
        title = "%" + title + "%";
        Page<Zhaosheng> zhaoshengs = zhaoshengDao.findByTypeAndTitleLikeOrderByPublishTimeDesc(type, title, pageable);
        return zhaoshengs; //To change body of generated methods, choose Tools | Templates.
    }
    
}
