package com.todayexercise.challenge.controller;

import com.todayexercise.challenge.dto.CreateChallengeDTO;
import com.todayexercise.challenge.dto.GetChallengeDTO;
import com.todayexercise.challenge.dto.ParticipatedChallengeDTO;
import com.todayexercise.challenge.model.Challenge;
import com.todayexercise.challenge.service.ChallengeService;
import com.todayexercise.dto.CMRespDTO;
import com.todayexercise.record.dto.PostRecordDTO;
import com.todayexercise.record.dto.ResponseRecordDTO;
import com.todayexercise.record.service.RecordService;
import com.todayexercise.reply.dto.CreatedReplyDTO;
import com.todayexercise.reply.dto.ReplyDTO;
import com.todayexercise.reply.dto.WriteReplyDTO;
import com.todayexercise.reply.model.Reply;
import com.todayexercise.user.service.UserService;
import com.todayexercise.util.CustomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
public class ChallengeController {
    @Autowired
    ChallengeService challengeService;

    @Autowired
    RecordService recordService;

    @Autowired
    UserService userService;
    //챌린지 만들기
    @PostMapping("/challenges")
    public CMRespDTO<?> createChallenge(@RequestBody @Valid CreateChallengeDTO createChallengeDTO){
        Challenge createdChallenge = challengeService.createChallenge(createChallengeDTO);
        return new CMRespDTO<>(1,"챌린지 생성 성공", createdChallenge);
    }

    //챌린지 참여
    @PostMapping("/challenges/{challenge_id}")
    public CMRespDTO<?> participateChallenges(HttpServletRequest request, @PathVariable Long challenge_id) throws InterruptedException {
        String userId = new CustomUtil().getUserIdByJWT(request);
        ParticipatedChallengeDTO participatedChallengeDTO = challengeService.participateChallenge(userId, challenge_id);
        return new CMRespDTO<>(1,"챌린지 참여성공", participatedChallengeDTO);
    }

    //내가 참여중인 챌린지 조회
    @GetMapping("/challenges")
    public CMRespDTO<?> getChallenges(HttpServletRequest request){
        String userId = new CustomUtil().getUserIdByJWT(request);
        List<GetChallengeDTO> allChallengesByUser = challengeService.getAllChallengesByUserId(userId);
        return new CMRespDTO<>(1,"유저 참여 챌린지 조회 성공", allChallengesByUser);
    }
    //챌린지 인증
    @PostMapping("/challenges/{challenge_id}/record")
    public CMRespDTO<?> recordChallenge(HttpServletRequest request, @PathVariable Long challenge_id,
                                        @RequestBody PostRecordDTO recordDTO){
        String userId = new CustomUtil().getUserIdByJWT(request);
        ResponseRecordDTO responseRecord = challengeService.createRecord(userId, challenge_id, recordDTO);
        return new CMRespDTO<>(1,"챌린지 인증 성공", responseRecord);
    }
    //챌린지 댓글 쓰기
    @PostMapping("/challenges/records/{record_id}")
    public CMRespDTO<?> writeReply(HttpServletRequest request, @PathVariable Long record_id,
                                   @RequestBody WriteReplyDTO writeReplyDTO){
        String userId = new CustomUtil().getUserIdByJWT(request);
        String replyContent = writeReplyDTO.getContent();
        CreatedReplyDTO createdReplyDTO = challengeService.writeReply(record_id, replyContent, userId);

        return new CMRespDTO<>(1,"챌린지 댓글쓰기 성공", createdReplyDTO);
    }

    //챌린지기준 댓글준조회
    @GetMapping("/challenges/records/{record_id}")
    public CMRespDTO<?> getReplyInRecord(@PathVariable Long record_id){
        List<ReplyDTO> replyList = recordService.getAllReplyByRecordId(record_id);

        return new CMRespDTO<>(1,"챌린지 댓글불러오기 성공", replyList);
    }

}
