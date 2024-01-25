package com.todayexercise.redis.service;

import com.todayexercise.challenge.service.ChallengeService;
import com.todayexercise.redis.repository.RedisLockRepository;
import org.springframework.stereotype.Service;

@Service
public class LettuceLockFacade {
    private RedisLockRepository redisLockRepository;

    private ChallengeService challengeService;

    public LettuceLockFacade(RedisLockRepository redisLockRepository, ChallengeService challengeService) {
        this.redisLockRepository = redisLockRepository;
        this.challengeService = challengeService;
    }

    public void decrease(Long key, int quantity) throws InterruptedException {
        while (!redisLockRepository.lock(key)) {
            Thread.sleep(100);
        }
        try {
            challengeService.decrease(key, quantity);
        } finally {
            redisLockRepository.unlock(key);
        }
    }
}
