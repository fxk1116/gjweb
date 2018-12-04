package com.haibusiness.xgweb.service;

import com.haibusiness.xgweb.domain.Jiangxuejin;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface JiangxuejinService {
    Jiangxuejin saveJiangxuejin(Jiangxuejin dongtai);


    void removeJiangxuejin(Long id);


    void removeJiangxuejinsInBatch(List<Jiangxuejin> jiangxuejins);

    Jiangxuejin updateJiangxuejin(Jiangxuejin jiangxuejin);

    Jiangxuejin getJiangxuejinById(Long id);


    List<Jiangxuejin> listJiangxuejins();


    Page<Jiangxuejin> listJiangxuejinsByTypeAndTitleLike(String type,String title, Pageable pageable);
}
