package com.todayexercise.record.service;

import com.todayexercise.record.model.Record;
import com.todayexercise.record.repository.RecordRepository;
import com.todayexercise.reply.dto.ReplyDTO;
import com.todayexercise.reply.model.Reply;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class RecordService {
    @Autowired
    RecordRepository recordRepository;

    public List<ReplyDTO> getAllReplyByRecordId(Long record_id) {
        List<Reply> replyList = recordRepository.findAllByRecord(record_id);
        List<ReplyDTO> replyDTOList = new ArrayList<>();
        for (Reply reply:replyList){
            ReplyDTO replyDTO = new ReplyDTO();
            replyDTO.setReplyId(reply.getId());
            replyDTO.setContent(reply.getContent());
            replyDTO.setUserId(reply.getUser().getUserId());
            replyDTOList.add(replyDTO);

        }
        return replyDTOList;
    }
}
