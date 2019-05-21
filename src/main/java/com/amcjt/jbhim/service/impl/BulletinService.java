package com.amcjt.jbhim.service.impl;

import com.amcjt.jbhim.repository.jpa.entity.Bulletin;
import com.amcjt.jbhim.utils.PaginatedFilter;
import com.amcjt.jbhim.vo.ResultVO;

public interface BulletinService {

    ResultVO findAll(PaginatedFilter paginatedFilter);

    void save(Bulletin bulletin);

    Bulletin findById(String id);

    void delete(String id);

    void publish(String id);
}
