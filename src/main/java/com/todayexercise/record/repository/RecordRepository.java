package com.todayexercise.record.repository;

import com.todayexercise.record.model.Record;
import com.todayexercise.reply.model.Reply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecordRepository extends JpaRepository<Record, Long> {
//    "select o from Order o" +
//            " join fetch o.member m" +
//            " join fetch o.delivery d" m.age = :age
    @Query("select r from Reply r join fetch r.record rr join fetch r.user ru where rr.id =:record_id")
    List<Reply> findAllByRecord(@Param("record_id") Long record_id);
}
