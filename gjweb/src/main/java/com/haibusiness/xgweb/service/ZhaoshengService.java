package com.haibusiness.xgweb.service;

import com.haibusiness.xgweb.domain.Zhaosheng;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ZhaoshengService {
    Zhaosheng saveZhaosheng(Zhaosheng zhaosheng);


    void removeZhaosheng(Long id);


    void removeZhaoshengsInBatch(List<Zhaosheng> zhaoshengs);

    Zhaosheng updateZhaosheng(Zhaosheng zhaosheng);

    Zhaosheng getZhaoshengById(Long id);
    
    


    List<Zhaosheng> listZhaoshengs();


    Page<Zhaosheng> listZhaoshengsByTypeAndTitleLike(String type,String title, Pageable pageable);
}
