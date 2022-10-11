package com.hongik.joinhere.service;

import com.hongik.joinhere.dto.belong.CreateBelongRequest;
import com.hongik.joinhere.dto.belong.DeleteBelongRequest;
import com.hongik.joinhere.dto.belong.ShowBelongResponse;
import com.hongik.joinhere.dto.belong.UpdateBelongRequest;
import com.hongik.joinhere.dto.club.ShowMyClubResponse;
import com.hongik.joinhere.entity.Belong;
import com.hongik.joinhere.entity.Club;
import com.hongik.joinhere.entity.Member;
import com.hongik.joinhere.repository.BelongRepository;
import com.hongik.joinhere.repository.ClubRepository;
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
    private final ClubRepository clubRepository;

    public BelongService(BelongRepository belongRepository, MemberRepository memberRepository, ClubRepository clubRepository) {
        this.belongRepository = belongRepository;
        this.memberRepository = memberRepository;
        this.clubRepository = clubRepository;
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

    public List<ShowBelongResponse> findBelongByClub(Long clubId) {
        List<Belong> belongs = belongRepository.findByClubId(clubId);
        return mappingResponse(belongs);
    }

    public List<ShowBelongResponse> register(CreateBelongRequest request, Long clubId) {
        Member member = memberRepository.findById(request.getMemberId());
        Club club = clubRepository.findById(clubId);
        Belong belong = new Belong(null, "nor", member, club);
        belongRepository.save(belong);
        List<Belong> belongs = belongRepository.findByClubId(clubId);
        return mappingResponse(belongs);
    }

    public List<ShowBelongResponse> updatePosition(UpdateBelongRequest request, Long clubId) {
        Belong belong = belongRepository.findById(request.getBelongId());
        belong.setPosition(request.getPosition());
        List<Belong> belongs = belongRepository.findByClubId(clubId);
        return mappingResponse(belongs);
    }

    public List<ShowBelongResponse> delete(DeleteBelongRequest request, Long clubId) {
        Belong belong = belongRepository.findById(request.getBelongId());
        belongRepository.delete(belong);
        List<Belong> belongs = belongRepository.findByClubId(clubId);
        return mappingResponse(belongs);
    }

    private List<ShowBelongResponse> mappingResponse(List<Belong> belongs) {
        List<ShowBelongResponse> responses = new ArrayList<>();

        if (belongs.size() == 0)
            return null;
        for (Belong belong: belongs)
            responses.add(ShowBelongResponse.from(belong));
        return responses;
    }
}
