package com.haibusiness.xgweb.dao;

import com.haibusiness.xgweb.domain.Gaikuang;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GaikuangDao extends JpaRepository<Gaikuang,Long> {
     Page<Gaikuang> findByTypeAndTitleLikeOrderByPublishTimeDesc(String type,String title,Pageable pageable);
}
