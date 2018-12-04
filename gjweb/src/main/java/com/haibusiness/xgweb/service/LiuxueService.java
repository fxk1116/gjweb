package com.haibusiness.xgweb.service;

import com.haibusiness.xgweb.domain.Liuxue;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface LiuxueService {
    Liuxue saveLiuxue(Liuxue liuxue);


    void removeLiuxue(Long id);


    void removeLiuxuesInBatch(List<Liuxue> liuxues);

    Liuxue updateLiuxue(Liuxue liuxue);

    Liuxue getLiuxueById(Long id);


    List<Liuxue> listLiuxues();


    Page<Liuxue> listLiuxuesByTypeAndNameLike(String type,String name, Pageable pageable);
}
