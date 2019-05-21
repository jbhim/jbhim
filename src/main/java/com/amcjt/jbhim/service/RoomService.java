package com.amcjt.jbhim.service;

import com.amcjt.jbhim.repository.jpa.entity.Bulletin;
import com.amcjt.jbhim.repository.jpa.entity.Room;
import com.amcjt.jbhim.utils.PaginatedFilter;
import com.amcjt.jbhim.vo.ResultVO;

/**
 * @author jbhim
 * @date 2019/5/21/021.
 */
public interface RoomService {
    void save(Room room);

    void delete(String id);

    Room findById(String id);

    ResultVO findAll(PaginatedFilter paginatedFilter);
}
