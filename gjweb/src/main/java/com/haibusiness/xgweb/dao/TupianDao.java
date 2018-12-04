package com.haibusiness.xgweb.dao;

import com.haibusiness.xgweb.domain.Tupian;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TupianDao extends JpaRepository<Tupian,Long> {
    Page<Tupian> findByTitleLikeOrderByPublishTimeDesc(String title, Pageable pageable);
}
