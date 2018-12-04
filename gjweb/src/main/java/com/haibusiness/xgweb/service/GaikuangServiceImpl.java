/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.haibusiness.xgweb.service;

import com.haibusiness.xgweb.dao.GaikuangDao;
import com.haibusiness.xgweb.domain.Gaikuang;
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

public class GaikuangServiceImpl implements GaikuangService{

    @Autowired
    private GaikuangDao gaikuangDao;
    
    @Override
    public Gaikuang saveGaikuang(Gaikuang gaikuang) {
        return gaikuangDao.save(gaikuang); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void removeGaikuang(Long id) {
        gaikuangDao.delete(id); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void removeGaikuangsInBatch(List<Gaikuang> gaikuangs) {
        gaikuangDao.deleteInBatch(gaikuangs); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Gaikuang updateGaikuang(Gaikuang gaikuang) {
        return gaikuangDao.save(gaikuang); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Gaikuang getGaikuangById(Long id) {
        return gaikuangDao.getOne(id); //To change body of generated methods, choose Tools | Templates.
    }
    
   

    @Override
    public List<Gaikuang> listGaikuangs() {
        return gaikuangDao.findAll(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Page<Gaikuang> listGaikuangsByTypeAndTitleLike(String type,String title, Pageable pageable) {
        title = "%" + title + "%";
        Page<Gaikuang> gaikuangs = gaikuangDao.findByTypeAndTitleLikeOrderByPublishTimeDesc(type, title, pageable);
        return gaikuangs; //To change body of generated methods, choose Tools | Templates.
    }
    
}
