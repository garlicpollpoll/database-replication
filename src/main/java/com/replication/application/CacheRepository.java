package com.replication.application;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CacheRepository {

    private final RedisTemplate<String, Object> redisTemplate;
    private final MemberRepository memberRepository;

    public void addMember(Member member) {
        String key = KeyGenerator.memberKeyGenerate(member.getName());

        redisTemplate.opsForValue().set(key, member);
    }

    public Member findMemberAtCache(String name) {
        String key = KeyGenerator.memberKeyGenerate(name);
        Member findMemberAtCache = (Member) redisTemplate.opsForValue().get(key);
        Member capsule = findMemberAtCache;

        if (name != null && findMemberAtCache == null) {
            Member findMember = memberRepository.findByName(name);
            addMember(findMember);
            capsule = findMember;
        }

        return capsule;
    }
}
