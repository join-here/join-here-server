package com.hongik.joinhere.service;

import com.hongik.joinhere.dto.club.*;
import com.hongik.joinhere.entity.Announcement;
import com.hongik.joinhere.entity.Belong;
import com.hongik.joinhere.entity.Club;
import com.hongik.joinhere.entity.Member;
import com.hongik.joinhere.repository.AnnouncementRepository;
import com.hongik.joinhere.repository.BelongRepository;
import com.hongik.joinhere.repository.ClubRepository;
import com.hongik.joinhere.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ClubService {

    private final ClubRepository clubRepository;
    private final MemberRepository memberRepository;
    private final AnnouncementRepository announcementRepository;
    private final BelongRepository belongRepository;

    public CreateClubResponse register(CreateClubRequest request) {
        Club club = request.toEntity();
        Member member = memberRepository.findById(request.getId());
        CreateClubResponse response = CreateClubResponse.from(clubRepository.save(club));
        Belong belong = new Belong(null, "pre", member, club);
        belongRepository.save(belong);
        return response;
    }

    public List<ShowClubResponse> findClubs() {
        List<Club> clubs = clubRepository.findAll();
        return mappingShowClubResponse(clubs);
    }

    public List<ShowClubResponse> findClubsByCategory(String category) {
        List<Club> clubs = clubRepository.findByCategory(category);
        return mappingShowClubResponse(clubs);
    }

    public List<ShowClubResponse> findClubsByQuery(String query) {
        List<Club> clubs = clubRepository.findByQuery("%" + query + "%");
        return mappingShowClubResponse(clubs);
    }

    public ShowClubInfoResponse findClubInfo(Long id) {
        Club club = clubRepository.findById(id);
        club.setView(club.getView() + 1L);
        List<Announcement> announcements = announcementRepository.findByClubId(club.getId());
        if (announcements.size() == 0)
            return ShowClubInfoResponse.from(club, null, null, null);
        else
            return ShowClubInfoResponse.from(club, announcements.get(announcements.size() - 1), null, null);
    }

    public void updateClubInfo(UpdateClubRequest request) {
        Club club = clubRepository.findById(request.getClubId());
        club.setName(request.getName());
        club.setCategory(request.getCategory());
        club.setArea(request.getArea());
        club.setImage(request.getImage());
        club.setIntroduction(request.getIntroduction());
    }

    private List<ShowClubResponse> mappingShowClubResponse(List<Club> clubs) {
        List<ShowClubResponse> responses = new ArrayList<>();

        for (Club club : clubs) {
            List<Announcement> announcements = announcementRepository.findByClubId(club.getId());
            if (announcements.size() == 0)
                responses.add(ShowClubResponse.from(club, null));
            else
                responses.add(ShowClubResponse.from(club, announcements.get(announcements.size() - 1).getEndDate()));
        }
        return responses;
    }
}