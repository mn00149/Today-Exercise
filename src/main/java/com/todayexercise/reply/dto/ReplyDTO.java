package com.todayexercise.reply.dto;

import com.todayexercise.record.model.Record;
import com.todayexercise.reply.model.Reply;
import com.todayexercise.user.model.User;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Data
@NoArgsConstructor
public class ReplyDTO {
    private Long replyId;

    private String content;

    //private Record record;

    private String userId;

//    public ReplyDTO(Reply reply){
//        this.replyId = reply.getId();
//        this.content = reply.getContent();
//        this.userId = reply.getUser().getUserId();
//        //record = reply.getRecord();
//    }
}
