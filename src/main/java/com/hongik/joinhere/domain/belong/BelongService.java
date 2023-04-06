package com.hongik.joinhere.domain.belong;

import com.hongik.joinhere.domain.auth.security.SecurityUtil;
import com.hongik.joinhere.domain.belong.entity.Belong;
import com.hongik.joinhere.domain.belong.entity.Position;
import com.hongik.joinhere.domain.belong.repository.BelongRepository;
import com.hongik.joinhere.domain.club.repository.ClubRepository;
import com.hongik.joinhere.domain.dto.belong.CreateBelongRequest;
import com.hongik.joinhere.domain.dto.belong.DeleteBelongRequest;
import com.hongik.joinhere.domain.belong.dto.ShowBelongResponse;
import com.hongik.joinhere.domain.dto.belong.UpdateBelongRequest;
import com.hongik.joinhere.domain.dto.belong.ShowMyBelongResponse;
import com.hongik.joinhere.domain.announcement.entity.Announcement;
import com.hongik.joinhere.domain.club.entity.Club;
import com.hongik.joinhere.domain.member.entity.Member;
import com.hongik.joinhere.domain.member.repository.MemberRepository;
import com.hongik.joinhere.global.error.ErrorCode;
import com.hongik.joinhere.global.error.exception.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class BelongService {

    private final ClubRepository clubRepository;
    private final BelongRepository belongRepository;
    private final MemberRepository memberRepository;
    private final AnnouncementRepository announcementRepository;

    public List<ShowBelongResponse> showClubMembers(Long clubId) {
        Member member = memberRepository.findById(SecurityUtil.getCurrentMemberId())
                .orElseThrow(() -> new BadRequestException(ErrorCode.MEMBER_NOT_FOUND));
        Club club = clubRepository.findById(clubId)
                .orElseThrow(() -> new BadRequestException(ErrorCode.CLUB_NOT_FOUND));
        belongRepository.findByMemberAndClub(member, club)
                .orElseThrow(() -> new BadRequestException(ErrorCode.BELONG_NOT_FOUND));
        List<Belong> belongs = belongRepository.findByClub(club);
        return sortedByPosition(belongs);
    }

    public List<ShowBelongResponse> registerByMemberId(CreateBelongRequest request, Long clubId) {
        Member member = memberRepository.findById(request.getMemberId())
                .orElseThrow(() -> new BadRequestException(ErrorCode.MEMBER_NOT_FOUND));
        Club club = clubRepository.findById(clubId)
                .orElseThrow(() -> new BadRequestException(ErrorCode.CLUB_NOT_FOUND));
        Belong belong = Belong.builder()
                        .position(Position.NORMAL)
                        .member(member)
                        .club(club)
                        .build();
        belongRepository.save(belong);
        List<Belong> belongs = belongRepository.findByClub(club);
        return sortedByPosition(belongs);
    }

    public List<ShowMyBelongResponse> findBelongByMemberId(String memberId) {
        Member member = memberRepository.findById(memberId);
        List<Belong> belongs = belongRepository.findByMemberId(member);
        List<ShowMyBelongResponse> responses = new ArrayList<>();

        if (belongs.size() == 0)
            return null;
        for (Belong belong: belongs) {
            Boolean hasAnnouncement = false;
            List<Announcement> announcements = announcementRepository.findByClubId(belong.getClub().getId());
            if (!announcements.isEmpty()) {
                if (announcements.get(announcements.size() - 1).getInformState().equals("n"))
                    hasAnnouncement = true;
            }
            responses.add(ShowMyBelongResponse.from(belong, hasAnnouncement));
        }
        return responses;
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

    private List<ShowBelongResponse> sortedByPosition(List<Belong> belongs) {
        List<ShowBelongResponse> responses = new ArrayList<>();
        List<ShowBelongResponse> presidentResponses = new ArrayList<>();
        List<ShowBelongResponse> managerResponses = new ArrayList<>();
        List<ShowBelongResponse> normalResponses = new ArrayList<>();

        for (Belong belong: belongs) {
            if (belong.getPosition() == Position.PRESIDENT)
                presidentResponses.add(ShowBelongResponse.from(belong));
            if (belong.getPosition() == Position.MANAGER)
                managerResponses.add(ShowBelongResponse.from(belong));
            if (belong.getPosition() == Position.NORMAL)
                normalResponses.add(ShowBelongResponse.from(belong));
        }
        responses.addAll(presidentResponses);
        responses.addAll(managerResponses);
        responses.addAll(normalResponses);
        return responses;
    }
}
