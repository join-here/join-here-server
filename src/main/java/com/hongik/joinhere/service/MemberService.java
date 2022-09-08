package com.hongik.joinhere.service;

import com.hongik.joinhere.domain.Member;
import com.hongik.joinhere.dto.CreateMemberRequest;
import com.hongik.joinhere.dto.CreateMemberResponse;
import com.hongik.joinhere.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;

    @Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public CreateMemberResponse join(CreateMemberRequest request) {
        Member member = request.toEntity();
        return CreateMemberResponse.from(memberRepository.save(member));
    }
}
