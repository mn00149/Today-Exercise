package com.todayexercise.challenge.service;

import com.todayexercise.category.Category;
import com.todayexercise.category.CategoryRepository;
import com.todayexercise.challenge.dto.CreateChallengeDTO;
import com.todayexercise.challenge.dto.GetChallengeDTO;
import com.todayexercise.challenge.dto.ParticipatedChallengeDTO;
import com.todayexercise.challenge.model.Challenge;
import com.todayexercise.challenge.repository.ChallengeRepository;
import com.todayexercise.challengeCategory.model.ChallengeCategory;
import com.todayexercise.challengeCategory.repository.ChallengeCategoryRepository;
import com.todayexercise.record.dto.PostRecordDTO;
import com.todayexercise.record.dto.ResponseRecordDTO;
import com.todayexercise.record.model.Record;
import com.todayexercise.record.repository.RecordRepository;
import com.todayexercise.reply.dto.CreatedReplyDTO;
import com.todayexercise.reply.model.Reply;
import com.todayexercise.reply.repository.ReplyRepository;
import com.todayexercise.user.model.User;
import com.todayexercise.user.service.UserService;
import com.todayexercise.userChallenge.model.UserChallenge;
import com.todayexercise.userChallenge.repository.UserChallengeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ChallengeService {
    @Autowired
    ChallengeRepository challengeRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    ChallengeCategoryRepository challengeCategoryRepository;
    @Autowired
    RecordRepository recordRepository;
    @Autowired
    UserService userService;
    @Autowired
    UserChallengeRepository userChallengeRepository;
    @Autowired
    ReplyRepository replyRepository;

    @Transactional
    public Challenge createChallenge(CreateChallengeDTO createChallengeDTO) {
        List<Long> categoryIdList = createChallengeDTO.getCategoryIdList();

        Long user_id = createChallengeDTO.getUserId();
        User user = userService.findById(user_id);
        Challenge challenge = createChallengeDTO.toEntity();
        Challenge createdChallenge = challengeRepository.save(challenge);
        UserChallenge userChallenge = new UserChallenge();
        userChallenge.setUser(user);
        userChallenge.setChallenge(challenge);
        userChallenge.setIsOwner(createChallengeDTO.getIsOwner());
        userChallengeRepository.save(userChallenge);
        for(long categoryId: categoryIdList){
            Category category = categoryRepository.findById(categoryId);
            ChallengeCategory challengeCategory = new ChallengeCategory();
            challengeCategory.setCategory(category);
            challengeCategory.setChallenge(challenge);
            challengeCategoryRepository.save(challengeCategory);
        }


        return createdChallenge;
    };

    @Transactional
    public ParticipatedChallengeDTO participateChallenge(String userId, Long challenge_id) {
        User user = userService.findByUserId(userId);
        int num = 1;
        //에러처리 하자!!
        Challenge challenge = challengeRepository.findById(challenge_id).get();
        int personnel = challenge.getPersonnel();
        if(personnel>0) {
            challenge.decrease(1);
        }else return null;
        UserChallenge userChallenge = new UserChallenge();
        userChallenge.setUser(user);
        userChallenge.setChallenge(challenge);
        userChallenge.setIsOwner("N");
        userChallengeRepository.save(userChallenge);
        ParticipatedChallengeDTO participatedChallengeDTO = new ParticipatedChallengeDTO();
        participatedChallengeDTO.setChallenge_id(challenge.getId());
        participatedChallengeDTO.setChallengeName(challenge.getChallengeName());
        return participatedChallengeDTO;
    }
    @Transactional
    public ResponseRecordDTO createRecord(String userId, Long challenge_id, PostRecordDTO postRecordDTO) {
        User user = userService.findByUserId(userId);
        Challenge challenge = challengeRepository.findById(challenge_id).get();
        Record record = new Record();
        record.setTitle(postRecordDTO.getTitle());
        record.setContent(postRecordDTO.getContent());
        record.setChallenge(challenge);
        record.setUser(user);
        Record createdRecord = recordRepository.save(record);
        ResponseRecordDTO responseRecordDTO = new ResponseRecordDTO();
        responseRecordDTO.setRecordId(createdRecord.getId());
        responseRecordDTO.setTitle(createdRecord.getTitle());

        return responseRecordDTO;
    }
    @Transactional
    public CreatedReplyDTO writeReply(Long record_id, String replyContent, String userId) {
        User user = userService.findByUserId(userId);
        Record record = recordRepository.findById(record_id).get();
        Reply reply = new Reply();
        reply.setRecord(record);
        reply.setUser(user);
        reply.setContent(replyContent);
        Reply createdReply = replyRepository.save(reply);
        CreatedReplyDTO createdReplyDTO = new CreatedReplyDTO();
        createdReplyDTO.setReply_id(createdReply.getId());
        createdReplyDTO.setContent(createdReply.getContent());
        return createdReplyDTO;
    }
    //참여챌린지 조회 로직 개선전
//    @Transactional
//    public List<GetChallengeDTO> getAllChallengesByUserId(String userId) {
//        User user = userService.findByUserId(userId);
//        List<UserChallenge> userChallenges = user.getUserChallenges();
//
//        List<GetChallengeDTO> challenges = new ArrayList<>();
//        for(UserChallenge userChallenge:userChallenges){
//            GetChallengeDTO challengeDTO = new GetChallengeDTO();
//            challengeDTO.setId(userChallenge.getChallenge().getId());
//            challengeDTO.setChallengeName(userChallenge.getChallenge().getChallengeName());
//            challenges.add(challengeDTO);
//        }
//        return challenges;
//    }

//참여챌린지 조회 로직 개선 후
@Transactional
public List<GetChallengeDTO> getAllChallengesByUserId(String userId) {
        List<Challenge> challenges = challengeRepository.findAllByUserId(userId);

    List<GetChallengeDTO> getChallengeDTOList = new ArrayList<>();
    for(Challenge challenge:challenges){
        GetChallengeDTO getChallengeDTO = new GetChallengeDTO();
        getChallengeDTO.setId(challenge.getId());
        getChallengeDTO.setChallengeName(challenge.getChallengeName());
        getChallengeDTOList.add(getChallengeDTO);
    }
    return getChallengeDTOList;
}

    @Transactional
    public void decrease(Long testId, int personnel) {
        Challenge challenge = challengeRepository.findById(testId).orElseThrow();

        challenge.decrease(personnel);

        challengeRepository.saveAndFlush(challenge);
    }
}
