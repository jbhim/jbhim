package com.amcjt.jbhim.service.impl;

import com.amcjt.jbhim.exception.JbhimException;
import com.amcjt.jbhim.repository.jpa.entity.Room;
import com.amcjt.jbhim.repository.jpa.repository.RoomRecordRepository;
import com.amcjt.jbhim.repository.jpa.repository.RoomRepository;
import com.amcjt.jbhim.service.RoomService;
import com.amcjt.jbhim.utils.PaginatedFilter;
import com.amcjt.jbhim.utils.enums.ResultEnum;
import com.amcjt.jbhim.vo.ResultVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author jbhim
 * @date 2019/5/21/021.
 */
@Service
public class RoomServiceImpl implements RoomService {
    @Resource
    private RoomRecordRepository roomRecordRepository;
    @Resource
    private RoomRepository roomRepository;

    @Override
    public void save(Room room) {
        roomRepository.save(room);
    }

    @Override
    public void delete(String id) {
        roomRepository.deleteById(id);
    }

    @Override
    public Room findById(String id) {
        return roomRepository.findById(id)
                .orElseThrow(() -> new JbhimException(ResultEnum.NOT_FOUND));
    }

    @Override
    public ResultVO findAll(PaginatedFilter paginatedFilter) {
        Sort sort = Sort.by(Sort.Direction.ASC, "createTime");
        PageRequest of = PageRequest.of(paginatedFilter.getIndex(), paginatedFilter.getSize(), sort);
        Page<Room> all = roomRepository.findAll(of);
        Map<String, Object> map = new HashMap<>();
        map.put("list", all.getContent());
        map.put("count", all.getTotalElements());
        return ResultVO.success(map);
    }
}
