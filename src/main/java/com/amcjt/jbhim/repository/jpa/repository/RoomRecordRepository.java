package com.amcjt.jbhim.repository.jpa.repository;

import com.amcjt.jbhim.repository.jpa.entity.RoomRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;

/**
 * @author jbhim
 */
@Repository
public interface RoomRecordRepository extends JpaRepository<RoomRecord, String> {
    Optional<RoomRecord> findAllByRoomIdAndEndTimeGreaterThan(String roomId, Date date);
}
