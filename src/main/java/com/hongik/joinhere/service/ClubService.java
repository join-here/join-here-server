package com.hongik.joinhere.service;

import com.hongik.joinhere.dto.club.CreateClubRequest;
import com.hongik.joinhere.dto.club.CreateClubResponse;
import com.hongik.joinhere.dto.club.ShowClubResponse;
import com.hongik.joinhere.entity.Club;
import com.hongik.joinhere.repository.ClubRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ClubService {

    private final ClubRepository clubRepository;

    @Autowired
    public ClubService(ClubRepository clubRepository) {
        this.clubRepository = clubRepository;
    }

    public CreateClubResponse register(CreateClubRequest request, String memberId) {
        Club club = request.toEntity();
        return CreateClubResponse.from(clubRepository.save(club));
    }

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
}
