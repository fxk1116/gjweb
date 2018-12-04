package com.haibusiness.xgweb.dao;

import com.haibusiness.xgweb.domain.Liuxue;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LiuxueDao extends JpaRepository<Liuxue,Long> {
     Page<Liuxue> findByTypeAndTitleLikeOrderByPublishTimeDesc(String type,String title,Pageable pageable);
}
