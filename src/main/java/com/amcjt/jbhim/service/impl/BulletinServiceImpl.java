package com.amcjt.jbhim.service.impl;

import com.amcjt.jbhim.exception.JbhimException;
import com.amcjt.jbhim.repository.jpa.entity.Bulletin;
import com.amcjt.jbhim.repository.jpa.repository.BulletinRepository;
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
public class BulletinServiceImpl implements BulletinService {
    @Resource
    private BulletinRepository bulletinRepository;

    @Override
    public ResultVO findAll(PaginatedFilter paginatedFilter) {
        Sort sort = Sort.by(Sort.Direction.DESC, "createTime");
        PageRequest of = PageRequest.of(paginatedFilter.getIndex(), paginatedFilter.getSize(), sort);
        String title = paginatedFilter.getFilter("title") == null ? "" : paginatedFilter.getFilter("title");
        Page<Bulletin> allByTitleContaining = bulletinRepository.findAllByTitleContaining(title, of);
        Map<String, Object> map = new HashMap<>();
        map.put("list", allByTitleContaining);
        map.put("count", allByTitleContaining.getTotalElements());
        return ResultVO.success(map);
    }

    @Override
    public void save(Bulletin bulletin) {
        bulletinRepository.save(bulletin);
    }

    @Override
    public Bulletin findById(String id) {
        return bulletinRepository
                .findById(id)
                .orElseThrow(() -> new JbhimException(ResultEnum.NOT_FOUND));
    }

    @Override
    public void delete(String id) {
        bulletinRepository.deleteById(id);
    }

    @Override
    public void publish(String id) {
        Bulletin bulletin = findById(id);
        bulletin.setPublish(!bulletin.isPublish());
        bulletinRepository.save(bulletin);
    }
}
