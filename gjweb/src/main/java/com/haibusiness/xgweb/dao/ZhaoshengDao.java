package com.haibusiness.xgweb.dao;

import com.haibusiness.xgweb.domain.Zhaosheng;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ZhaoshengDao extends JpaRepository<Zhaosheng,Long> {
     Page<Zhaosheng> findByTypeAndTitleLikeOrderByPublishTimeDesc(String type,String title,Pageable pageable);
}
