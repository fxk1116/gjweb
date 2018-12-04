package com.haibusiness.xgweb.dao;

import com.haibusiness.xgweb.domain.Tongzhi;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TongzhiDao extends JpaRepository<Tongzhi,Long> {
    Page<Tongzhi> findByTitleLikeOrderByPublishTimeDesc(String title, Pageable pageable);
}
