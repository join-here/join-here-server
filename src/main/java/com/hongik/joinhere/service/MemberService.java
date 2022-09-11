package com.hongik.joinhere.service;

import com.hongik.joinhere.domain.Member;
import com.hongik.joinhere.dto.member.CreateMemberRequest;
import com.hongik.joinhere.dto.member.CreateMemberResponse;
import com.hongik.joinhere.dto.member.LoginMemberRequest;
import com.hongik.joinhere.dto.member.LoginMemberResponse;
import com.hongik.joinhere.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

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

    public LoginMemberResponse login(LoginMemberRequest request) {
        Member member = memberRepository.findById(request.getId());
        if (member == null)
            return null;
        if (member.getPassword().equals(request.getPassword()))
            return LoginMemberResponse.from(member);
        return null;
    }
}
