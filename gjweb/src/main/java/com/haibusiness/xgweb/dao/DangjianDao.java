package com.haibusiness.xgweb.dao;

import com.haibusiness.xgweb.domain.Dangjian;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DangjianDao extends JpaRepository<Dangjian,Long> {
    Page<Dangjian> findByTitleLikeOrderByPublishTimeDesc(String title, Pageable pageable);
}
