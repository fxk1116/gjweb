package com.haibusiness.xgweb.service;

import com.haibusiness.xgweb.dao.TupianDao;
import com.haibusiness.xgweb.domain.Tupian;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class TupianServiceImpl implements TupianService {

    @Autowired
    private TupianDao tupianDao;
    @Override
    public Tupian saveTupian(Tupian tupian) {
        return tupianDao.save(tupian);
    }

    @Override
    public void removeTupian(Long id) {
        tupianDao.delete(id);
    }

    @Override
    public void removeTupiansInBatch(List<Tupian> tupians) {
        tupianDao.deleteInBatch(tupians);
    }

    @Override
    public Tupian updateTupian(Tupian tupian) {
        return tupianDao.save(tupian);
    }

    @Override
    public Tupian getTupianById(Long id) {
        return tupianDao.getOne(id);
    }

    @Override
    public List<Tupian> listTupians() {
        return tupianDao.findAll();
    }

    @Override
    public Page<Tupian> listTupiansByTitleLike(String title, Pageable pageable) {

        title = "%" + title + "%";
        Page<Tupian> tupians = tupianDao.findByTitleLikeOrderByPublishTimeDesc(title, pageable);
        return tupians;
    }
}
