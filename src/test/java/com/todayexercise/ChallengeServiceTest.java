package com.todayexercise;

import com.todayexercise.challenge.model.Challenge;
import com.todayexercise.challenge.repository.ChallengeRepository;
import com.todayexercise.challenge.service.ChallengeService;
import com.todayexercise.redis.service.LettuceLockFacade;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class ChallengeServiceTest {
    @Autowired
    private LettuceLockFacade lettuceLockStockFacade;


    @Autowired
    private ChallengeRepository challengeRepository;

    @BeforeEach
    public void insert() {
        Challenge challenge = new Challenge();
        challenge.setPersonnel(5);
        challenge.setChallengeName("Test");
        challenge.setId(1L);
        challengeRepository.saveAndFlush(challenge);
    }

    @AfterEach
    public void delete() {
        challengeRepository.deleteAll();
    }

//    @Test
//    public void decrease_test() {
//        challengeService.decrease(1L, 1);
//
//        Challenge challenge = challengeRepository.findById(1L).orElseThrow();
//        // 100 - 1 = 99
//
//        assertEquals(99, challenge.getPersonnel());
//
//    }

    @Test
    public void 동시에_5명이_참여() throws InterruptedException {
        int threadCount = 5;
        ExecutorService executorService = Executors.newFixedThreadPool(32);
        CountDownLatch latch = new CountDownLatch(threadCount);

        for (int i = 0; i < threadCount; i++) {
            executorService.submit(() -> {
                try {
                    lettuceLockStockFacade.decrease(1L, 1);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } finally {
                    latch.countDown();
                }
            });
        }
        latch.await();
        Challenge challenge = challengeRepository.findById(1L).orElseThrow();

        // 100 - (100 * 1) = 0
        assertEquals(0, challenge.getPersonnel());
    }
}
