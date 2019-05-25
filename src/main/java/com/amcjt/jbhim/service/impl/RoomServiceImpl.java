package com.amcjt.jbhim.service.impl;

import com.amcjt.jbhim.exception.JbhimException;
import com.amcjt.jbhim.repository.jpa.entity.Room;
import com.amcjt.jbhim.repository.jpa.entity.RoomRecord;
import com.amcjt.jbhim.repository.jpa.repository.RoomRecordRepository;
import com.amcjt.jbhim.repository.jpa.repository.RoomRepository;
import com.amcjt.jbhim.service.RoomService;
import com.amcjt.jbhim.utils.PaginatedFilter;
import com.amcjt.jbhim.utils.enums.ResultEnum;
import com.amcjt.jbhim.vo.ResultVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
        String used = paginatedFilter.getFilter("used");

        Page<Room> all;
        if (StringUtils.isBlank(used)) {
            all = roomRepository.findAll(of);
        } else {
            all = roomRepository.findAllByUsed("true".equals(used), of);
        }

        Map<String, Object> map = new HashMap<>();
        List<Room> content = all.getContent();
        content = content.stream().peek(room -> {
            RoomRecord roomRecord = roomRecord(room.getId());
            if (roomRecord != null) {
                room.setUsed(true);
                long start = roomRecord.getStartTime().getTime();
                long end = roomRecord.getEndTime().getTime();
                long time = System.currentTimeMillis();
                long l = (time - start) * 100 / (end - start);
                room.setPercent(String.valueOf(l));
            } else {
                room.setUsed(false);
            }
        }).collect(Collectors.toList());
        map.put("list", content);
        map.put("count", all.getTotalElements());
        return ResultVO.success(map);
    }

    /**
     * 查询正在使用的数据
     *
     * @param roomId
     */
    public RoomRecord roomRecord(String roomId) {
        return roomRecordRepository.findAllByRoomIdAndEndTimeGreaterThan(roomId, new Date()).orElse(null);
    }


    @Override
    public ResultVO show() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:ss");
        List<Room> all = roomRepository.findAll();
        all = all.stream().peek(room -> {
            RoomRecord roomRecord = roomRecord(room.getId());
            if (roomRecord != null) {
                room.setUsed(true);
                long start = roomRecord.getStartTime().getTime();
                long end = roomRecord.getEndTime().getTime();
                long time = System.currentTimeMillis();
                long l = (time - start) * 100 / (end - start);
                room.setPercent(String.valueOf(l));
                room.setStartTime(simpleDateFormat.format(roomRecord.getStartTime()));
                room.setEndTime(simpleDateFormat.format(roomRecord.getEndTime()));
            } else {
                room.setUsed(false);
            }
        }).collect(Collectors.toList());
        return ResultVO.success(all);
    }

    @Override
    public void saveRoomRecord(RoomRecord roomRecord) {
        roomRecordRepository.save(roomRecord);
    }
}
