package com.hongik.joinhere.service;

import com.hongik.joinhere.dto.belong.ShowClubBelongResponse;
import com.hongik.joinhere.dto.club.ShowMyClubResponse;
import com.hongik.joinhere.entity.Belong;
import com.hongik.joinhere.entity.Club;
import com.hongik.joinhere.entity.Member;
import com.hongik.joinhere.repository.BelongRepository;
import com.hongik.joinhere.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class BelongService {

    private final BelongRepository belongRepository;
    private final MemberRepository memberRepository;

    public BelongService(BelongRepository belongRepository, MemberRepository memberRepository) {
        this.belongRepository = belongRepository;
        this.memberRepository = memberRepository;
    }

    public List<ShowMyClubResponse> findBelongByMemberId(String memberId) {
        Member member = memberRepository.findById(memberId);
        List<Belong> belongs = belongRepository.findByMemberId(member);
        List<ShowMyClubResponse> responses = new ArrayList<>();

        if (belongs.size() == 0)
            return null;
        for (Belong belong: belongs)
            responses.add(ShowMyClubResponse.from(belong));
        return responses;
    }

    public List<ShowClubBelongResponse> findBelongByClubId(Long clubId) {
        List<Belong> belongs = belongRepository.findByClubId(clubId);
        List<ShowClubBelongResponse> responses = new ArrayList<>();

        if (belongs.size() == 0)
            return null;
        for (Belong belong: belongs)
            responses.add(ShowClubBelongResponse.from(belong));
        return responses;
    }
}
