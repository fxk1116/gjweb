/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.haibusiness.xgweb.service;

import com.haibusiness.xgweb.domain.Liuxue;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.haibusiness.xgweb.dao.LiuxueDao;

/**
 *
 * @author Lenovo
 */

@Service
@Transactional
public class LiuxueServiceImpl implements LiuxueService{

    @Autowired
    private LiuxueDao liuxueDao;
    @Override
    public Liuxue saveLiuxue(Liuxue liuxue) {
        return liuxueDao.save(liuxue); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void removeLiuxue(Long id) {
        liuxueDao.delete(id); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void removeLiuxuesInBatch(List<Liuxue> liuxues) {
        liuxueDao.deleteInBatch(liuxues); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Liuxue updateLiuxue(Liuxue liuxue) {
        return liuxueDao.save(liuxue); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Liuxue getLiuxueById(Long id) {
        return liuxueDao.getOne(id); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Liuxue> listLiuxues() {
        return liuxueDao.findAll(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Page<Liuxue> listLiuxuesByTypeAndNameLike(String type,String name, Pageable pageable) {
        name = "%" + name + "%";
        Page<Liuxue> liuxues = liuxueDao.findByTypeAndTitleLikeOrderByPublishTimeDesc(type, name, pageable);
        return liuxues; //To change body of generated methods, choose Tools | Templates.
    }
    
}
