package com.haibusiness.xgweb.service;

import com.haibusiness.xgweb.dao.DangjianDao;
import com.haibusiness.xgweb.domain.Dangjian;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class DangjianServiceImpl implements DangjianService {

    @Autowired
    private DangjianDao dangjianDao;
    @Override
    public Dangjian saveDangjian(Dangjian dangjian) {
        return dangjianDao.save(dangjian);
    }

    @Override
    public void removeDangjian(Long id) {
        dangjianDao.delete(id);
    }

    @Override
    public void removeDangjiansInBatch(List<Dangjian> dangjians) {
        dangjianDao.deleteInBatch(dangjians);
    }

    @Override
    public Dangjian updateDangjian(Dangjian dangjian) {
        return dangjianDao.save(dangjian);
    }

    @Override
    public Dangjian getDangjianById(Long id) {
        return dangjianDao.getOne(id);
    }

    @Override
    public List<Dangjian> listDangjians() {
        return dangjianDao.findAll();
    }

    @Override
    public Page<Dangjian> listDangjiansByTitleLike(String title, Pageable pageable) {

        title = "%" + title + "%";
        Page<Dangjian> dangjians = dangjianDao.findByTitleLikeOrderByPublishTimeDesc(title, pageable);
        return dangjians;
    }
}
