package com.hongik.joinhere.domain.announcement.announcement;

import com.hongik.joinhere.domain.announcement.announcement.entity.Announcement;
import com.hongik.joinhere.domain.announcement.announcement.repository.AnnouncementRepository;
import com.hongik.joinhere.domain.announcement.question.entity.AnnouncementQuestion;
import com.hongik.joinhere.domain.announcement.question.repository.AnnouncementQuestionRepository;
import com.hongik.joinhere.domain.auth.security.SecurityUtil;
import com.hongik.joinhere.domain.belong.entity.Belong;
import com.hongik.joinhere.domain.belong.entity.Position;
import com.hongik.joinhere.domain.belong.repository.BelongRepository;
import com.hongik.joinhere.domain.club.repository.ClubRepository;
import com.hongik.joinhere.domain.announcement.announcement.dto.CreateAnnouncementRequest;
import com.hongik.joinhere.domain.announcement.announcement.dto.CreateAnnouncementResponse;
import com.hongik.joinhere.domain.club.entity.Club;
import com.hongik.joinhere.domain.member.entity.Member;
import com.hongik.joinhere.domain.member.repository.MemberRepository;
import com.hongik.joinhere.global.error.ErrorCode;
import com.hongik.joinhere.global.error.exception.BadRequestException;
import com.hongik.joinhere.global.error.exception.ForbiddenException;
import com.hongik.joinhere.infra.s3.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class AnnouncementService {

    private final ClubRepository clubRepository;
    private final MemberRepository memberRepository;
    private final BelongRepository belongRepository;
    private final AnnouncementRepository announcementRepository;
    private final AnnouncementQuestionRepository announcementQuestionRepository;
    private final S3Service s3Service;

    public CreateAnnouncementResponse registerAnnouncementAndQuestion(CreateAnnouncementRequest request, MultipartFile multipartFile, Long clubId) {
        Club club = handleExceptions(clubId);
        Announcement announcement = request.toAnnouncement(club, uploadImageToS3(multipartFile));
        CreateAnnouncementResponse response = CreateAnnouncementResponse.from(announcementRepository.save(announcement));
        List<AnnouncementQuestion> announcementQuestions = request.toAnnouncementQuestion(announcement);
        for (AnnouncementQuestion announcementQuestion : announcementQuestions) {
            announcementQuestionRepository.save(announcementQuestion);
        }
        return response;
    }

    private Club handleExceptions(Long clubId) {
        Club club = handleClubManageAuthorityException(clubId);
        List<Announcement> announcements = announcementRepository.findByClub(club);
        if (!announcements.isEmpty() && !announcements.get(announcements.size() - 1).getInformState()) {
            throw new BadRequestException(ErrorCode.ANNOUNCEMENT_NOT_INFORM);
        }
        return club;
    }

    private Club handleClubManageAuthorityException(Long clubId) {
        Club club = clubRepository.findById(clubId)
                .orElseThrow(() -> new BadRequestException(ErrorCode.CLUB_NOT_FOUND));
        Member member = memberRepository.findById(SecurityUtil.getCurrentMemberId())
                .orElseThrow(() -> new BadRequestException(ErrorCode.MEMBER_NOT_FOUND));
        Belong belong = belongRepository.findByMemberAndClub(member, club)
                .orElseThrow(() -> new BadRequestException(ErrorCode.BELONG_NOT_FOUND));
        if (belong.getPosition() == Position.NORMAL) {
            throw new ForbiddenException(ErrorCode.BELONG_FORBIDDEN_MEMBER);
        }
        return club;
    }

    private String uploadImageToS3(MultipartFile multipartFile) {
        try {
            if (multipartFile != null && !multipartFile.isEmpty()) {
                return s3Service.uploadFiles(multipartFile, "images");
            }
        } catch (Exception e) {
            throw new BadRequestException(ErrorCode.S3_CONNECTION_ERROR);
        }
        return null;
    }

}
