package com.hongik.joinhere.domain.belong;

import com.hongik.joinhere.domain.announcement.announcement.repository.AnnouncementRepository;
import com.hongik.joinhere.domain.auth.security.SecurityUtil;
import com.hongik.joinhere.domain.belong.dto.*;
import com.hongik.joinhere.domain.belong.entity.Belong;
import com.hongik.joinhere.domain.belong.entity.Position;
import com.hongik.joinhere.domain.belong.repository.BelongRepository;
import com.hongik.joinhere.domain.club.repository.ClubRepository;
import com.hongik.joinhere.domain.announcement.announcement.entity.Announcement;
import com.hongik.joinhere.domain.club.entity.Club;
import com.hongik.joinhere.domain.member.entity.Member;
import com.hongik.joinhere.domain.member.repository.MemberRepository;
import com.hongik.joinhere.global.error.ErrorCode;
import com.hongik.joinhere.global.error.exception.BadRequestException;
import com.hongik.joinhere.global.error.exception.ForbiddenException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
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

    public List<BelongResponse> showClubMembers(Long clubId) {
        Belong belong = handleCommonExceptions(clubId);
        return sortedByPosition(belongRepository.findByClub(belong.getClub()));
    }

    public List<BelongResponse> registerBelongByMemberUsername(CreateBelongRequest request, Long clubId) {
        Belong belong = handleClubManageAuthorityException(clubId);
        Member findMember = memberRepository.findByUsername(request.getMemberUsername())
                .orElseThrow(() -> new BadRequestException(ErrorCode.MEMBER_NOT_FOUND));
        if (belongRepository.existsByMemberAndClub(findMember, belong.getClub())) {
            throw new BadRequestException(ErrorCode.DUPLICATE_BELONG);
        }
        Belong newBelong = Belong.builder()
                        .position(Position.NORMAL)
                        .member(findMember)
                        .club(belong.getClub())
                        .build();
        belongRepository.save(newBelong);
        return sortedByPosition(belongRepository.findByClub(belong.getClub()));
    }

    public List<ShowMyBelongResponse> findMyBelongs() {
        Member member = memberRepository.findById(SecurityUtil.getCurrentMemberId())
                .orElseThrow(() -> new BadRequestException(ErrorCode.MEMBER_NOT_FOUND));
        List<Belong> belongs = belongRepository.findByMember(member);
        List<ShowMyBelongResponse> responses = new ArrayList<>();
        for (Belong belong: belongs) {
            Boolean hasAnnouncement = false;
            List<Announcement> announcements = announcementRepository.findByClub(belong.getClub());
            if (!announcements.isEmpty()) {
                if (!announcements.get(announcements.size() - 1).getInformState()) {
                    hasAnnouncement = true;
                }
            }
            responses.add(ShowMyBelongResponse.from(belong, hasAnnouncement));
        }
        return responses;
    }

    public List<BelongResponse> updatePosition(UpdateBelongRequest request, Long clubId) {
        Belong belong = handleClubManageAuthorityException(clubId);
        Belong updatedBelong = belongRepository.findById(request.getBelongId())
                        .orElseThrow(() -> new BadRequestException(ErrorCode.BELONG_NOT_FOUND));
        updatedBelong.updatePosition(request.getPosition());
        return sortedByPosition(belongRepository.findByClub(belong.getClub()));
    }

    public List<BelongResponse> deleteBelong(DeleteBelongRequest request, Long clubId) {
        Belong belong = handleClubManageAuthorityException(clubId);
        Belong willDeleteBelong = belongRepository.findById(request.getBelongId())
                .orElseThrow(() -> new BadRequestException(ErrorCode.BELONG_NOT_FOUND));
        belongRepository.delete(willDeleteBelong);
        return sortedByPosition(belongRepository.findByClub(belong.getClub()));
    }

    public List<ReviewResponse> registerReview(CreateReviewRequest request, Long clubId) {
        Belong belong = handleCommonExceptions(clubId);
        LocalDateTime now = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
        belong.updateReview(request.getReviewContent(), now);
        return mappingReviewResponse(belongRepository.findByClub(belong.getClub()));
    }

    public List<ReviewResponse> deleteReview(DeleteReviewRequest request, Long clubId) {
        Belong belong = handleCommonExceptions(clubId);
        if (belong.getId() != request.getBelongId() || belong.getMember().getId() != SecurityUtil.getCurrentMemberId()) {
            throw new ForbiddenException(ErrorCode.REVIEW_FORBIDDEN_MEMBER);
        }
        belong.deleteReview();
        return mappingReviewResponse(belongRepository.findByClub(belong.getClub()));
    }

    private Belong handleCommonExceptions(Long clubId) {
        Member member = memberRepository.findById(SecurityUtil.getCurrentMemberId())
                .orElseThrow(() -> new BadRequestException(ErrorCode.MEMBER_NOT_FOUND));
        Club club = clubRepository.findById(clubId)
                .orElseThrow(() -> new BadRequestException(ErrorCode.CLUB_NOT_FOUND));
        Belong belong = belongRepository.findByMemberAndClub(member, club)
                .orElseThrow(() -> new BadRequestException(ErrorCode.BELONG_NOT_FOUND));
        return belong;
    }

    private Belong handleClubManageAuthorityException(Long clubId) {
        Belong belong = handleCommonExceptions(clubId);
        if (belong.getPosition() == Position.NORMAL) {
            throw new ForbiddenException(ErrorCode.BELONG_FORBIDDEN_MEMBER);
        }
        return belong;
    }

    private List<BelongResponse> sortedByPosition(List<Belong> belongs) {
        List<BelongResponse> responses = new ArrayList<>();
        List<BelongResponse> presidentResponses = new ArrayList<>();
        List<BelongResponse> managerResponses = new ArrayList<>();
        List<BelongResponse> normalResponses = new ArrayList<>();

        for (Belong belong: belongs) {
            if (belong.getPosition() == Position.PRESIDENT)
                presidentResponses.add(BelongResponse.from(belong));
            if (belong.getPosition() == Position.MANAGER)
                managerResponses.add(BelongResponse.from(belong));
            if (belong.getPosition() == Position.NORMAL)
                normalResponses.add(BelongResponse.from(belong));
        }
        responses.addAll(presidentResponses);
        responses.addAll(managerResponses);
        responses.addAll(normalResponses);
        return responses;
    }

    private List<ReviewResponse> mappingReviewResponse(List<Belong> belongs) {
        List<ReviewResponse> responses = new ArrayList<>();

        for (Belong belong : belongs) {
            if (!(belong.getReview() == null)) {
                responses.add(ReviewResponse.from(belong));
            }
        }
        return responses;
    }
}
