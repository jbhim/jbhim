package com.amcjt.jbhim.repository.jpa.repository;

import com.amcjt.jbhim.repository.jpa.entity.Bulletin;
import com.amcjt.jbhim.repository.jpa.entity.Room;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author jbhim
 */
@Repository
public interface RoomRepository extends JpaRepository<Room, String> {

}
