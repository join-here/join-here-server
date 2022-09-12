package com.hongik.joinhere.service;

import com.hongik.joinhere.dto.club.CreateClubRequest;
import com.hongik.joinhere.dto.club.CreateClubResponse;
import com.hongik.joinhere.entity.Club;
import com.hongik.joinhere.repository.ClubRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
