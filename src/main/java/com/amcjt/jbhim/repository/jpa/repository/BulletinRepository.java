package com.amcjt.jbhim.repository.jpa.repository;

import com.amcjt.jbhim.repository.jpa.entity.Bulletin;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author jbhim
 */
@Repository
public interface BulletinRepository extends JpaRepository<Bulletin, String> {

    Page<Bulletin> findAllByTitleContaining(String title, Pageable pageable);

    Page<Bulletin> findAllByTitleContainingAndPublish(String title, boolean publish, Pageable pageable);

    List<Bulletin> findAllByPublish(boolean publish);


}
