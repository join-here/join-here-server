package com.hongik.joinhere.service;

import com.hongik.joinhere.entity.Member;
import com.hongik.joinhere.dto.member.MemberRequest;
import com.hongik.joinhere.dto.member.MemberResponse;
import com.hongik.joinhere.dto.member.LoginMemberRequest;
import com.hongik.joinhere.dto.member.LoginMemberResponse;
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

    public MemberResponse join(MemberRequest request) {
        Member member = request.toEntity();
        return MemberResponse.from(memberRepository.save(member));
    }

    public MemberResponse findMember(String memberId) {
        Member member = memberRepository.findById(memberId);
        return MemberResponse.from(member);
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
