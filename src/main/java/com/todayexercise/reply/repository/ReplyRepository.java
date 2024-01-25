package com.todayexercise.reply.repository;

import com.todayexercise.reply.model.Reply;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReplyRepository extends JpaRepository<Reply, Long> {
}
