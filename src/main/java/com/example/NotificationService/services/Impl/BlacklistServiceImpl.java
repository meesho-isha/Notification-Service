package com.example.NotificationService.services.Impl;

import com.example.NotificationService.entity.sql.BlacklistSql;
import com.example.NotificationService.repository.BlacklistSqlRepository;
import com.example.NotificationService.utils.PhoneNumberValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import com.example.NotificationService.services.BlacklistService;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class BlacklistServiceImpl implements BlacklistService {

    private static final String BLACKLIST_KEY = "blacklist";
    private final BlacklistSqlRepository blacklistSqlRepository;
    private final RedisTemplate<String, String> redisTemplate;
    private final PhoneNumberValidator phoneNumberValidator;

    @Autowired
    public BlacklistServiceImpl(BlacklistSqlRepository blacklistSqlRepository, RedisTemplate<String, String> redisTemplate, PhoneNumberValidator phoneNumberValidator) {
        this.blacklistSqlRepository = blacklistSqlRepository;
        this.redisTemplate = redisTemplate;
        this.phoneNumberValidator = phoneNumberValidator;
    }

    @Override
    public void addToBlacklist(List<String> phoneNumbers) {
        phoneNumberValidator.validateList(phoneNumbers);

        List<BlacklistSql> blacklistSqlList = phoneNumbers.stream().map(BlacklistSql::new).toList();
        blacklistSqlRepository.saveAll(blacklistSqlList);

        redisTemplate.opsForSet().add(BLACKLIST_KEY, phoneNumbers.toArray(new String[0]));
    }

    @Override
    public void removeFromBlacklist(List<String> phoneNumbers) {
        phoneNumberValidator.validateList(phoneNumbers);
        List<BlacklistSql> blacklistSqlList = phoneNumbers.stream().map(BlacklistSql::new).toList();
        blacklistSqlRepository.deleteAll(blacklistSqlList);
        redisTemplate.opsForSet().remove(BLACKLIST_KEY, phoneNumbers.toArray());
    }

    @Override
    public List<String> getBlacklist() {
        Set<String> blacklist = redisTemplate.opsForSet().members(BLACKLIST_KEY);

        if(blacklist != null && !blacklist.isEmpty()) {
            return new ArrayList<>(blacklist);
        }

        List<BlacklistSql> blacklistSqlList = blacklistSqlRepository.findAll();
        return blacklistSqlList.stream().map(BlacklistSql::getPhoneNumber).collect(Collectors.toList());
    }

    @Override
    public boolean isBlacklisted(String phoneNumber) throws IllegalArgumentException {
        if(phoneNumber == null) {
            throw new IllegalArgumentException("Phone number cannot be null");
        }
        return Boolean.TRUE.equals(redisTemplate.opsForSet().isMember(BLACKLIST_KEY, phoneNumber));
    }

}
