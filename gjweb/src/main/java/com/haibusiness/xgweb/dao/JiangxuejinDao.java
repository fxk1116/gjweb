package com.haibusiness.xgweb.dao;


import com.haibusiness.xgweb.domain.Jiangxuejin;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JiangxuejinDao extends JpaRepository<Jiangxuejin,Long> {
     Page<Jiangxuejin> findByTypeAndTitleLikeOrderByPublishTimeDesc(String type,String title,Pageable pageable);
}
