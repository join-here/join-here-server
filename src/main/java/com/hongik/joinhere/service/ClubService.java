package com.hongik.joinhere.service;

import com.hongik.joinhere.dto.club.CreateClubRequest;
import com.hongik.joinhere.dto.club.CreateClubResponse;
import com.hongik.joinhere.dto.club.ShowClubInfoResponse;
import com.hongik.joinhere.dto.club.ShowClubResponse;
import com.hongik.joinhere.entity.Announcement;
import com.hongik.joinhere.entity.Belong;
import com.hongik.joinhere.entity.Club;
import com.hongik.joinhere.entity.Member;
import com.hongik.joinhere.repository.AnnouncementRepository;
import com.hongik.joinhere.repository.BelongRepository;
import com.hongik.joinhere.repository.ClubRepository;
import com.hongik.joinhere.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ClubService {

    private final ClubRepository clubRepository;
    private final MemberRepository memberRepository;
    private final AnnouncementRepository announcementRepository;
    private final BelongRepository belongRepository;

    @Autowired
    public ClubService(ClubRepository clubRepository, MemberRepository memberRepository, AnnouncementRepository announcementRepository, BelongRepository belongRepository) {
        this.clubRepository = clubRepository;
        this.memberRepository = memberRepository;
        this.announcementRepository = announcementRepository;
        this.belongRepository = belongRepository;
    }

//    public CreateClubResponse register(CreateClubRequest request, String memberId) {
//        Club club = request.toEntity();
//        Member member = memberRepository.findById(memberId);
//        CreateClubResponse response = CreateClubResponse.from(clubRepository.save(club));
//        Belong belong = new Belong(null, "pre", member, club);
//        belongRepository.save(belong);
//        return response;
//    }

    public List<ShowClubResponse> findClubs() {
        List<Club> clubs = clubRepository.findAll();
        List<ShowClubResponse> responses = new ArrayList<>();

        for (Club club : clubs)
            responses.add(ShowClubResponse.from(club));
        return responses;
    }

    public List<ShowClubResponse> findClubsByCategory(String category) {
        List<Club> clubs = clubRepository.findByCategory(category);
        List<ShowClubResponse> responses = new ArrayList<>();

        for (Club club : clubs)
            responses.add(ShowClubResponse.from(club));
        return responses;
    }

    public ShowClubInfoResponse findClubInfo(Long id) {
        Club club = clubRepository.findById(id);
        List<Announcement> announcements = announcementRepository.findByClubId(id);
        if (announcements == null)
            return ShowClubInfoResponse.from(club, null, null, null);
        else
            return ShowClubInfoResponse.from(club, announcements.get(0), null, null);
    }
}