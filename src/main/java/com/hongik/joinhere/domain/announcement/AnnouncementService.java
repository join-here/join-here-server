package com.hongik.joinhere.domain.announcement;

import com.hongik.joinhere.domain.announcement.entity.Announcement;
import com.hongik.joinhere.domain.announcement.repository.AnnouncementRepository;
import com.hongik.joinhere.domain.application.question.entity.ApplicationQuestion;
import com.hongik.joinhere.domain.application.question.repository.ApplicationQuestionRepository;
import com.hongik.joinhere.domain.auth.security.SecurityUtil;
import com.hongik.joinhere.domain.belong.entity.Belong;
import com.hongik.joinhere.domain.belong.entity.Position;
import com.hongik.joinhere.domain.belong.repository.BelongRepository;
import com.hongik.joinhere.domain.club.repository.ClubRepository;
import com.hongik.joinhere.domain.announcement.dto.CreateAnnouncementRequest;
import com.hongik.joinhere.domain.announcement.dto.CreateAnnouncementResponse;
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
    private final ApplicationQuestionRepository applicationQuestionRepository;
    private final S3Service s3Service;

    public CreateAnnouncementResponse register(CreateAnnouncementRequest request, MultipartFile multipartFile, Long clubId) {
        String posterUrl = null;

        Club club = clubRepository.findById(clubId)
                .orElseThrow(() -> new BadRequestException(ErrorCode.CLUB_NOT_FOUND));
        Member member = memberRepository.findById(SecurityUtil.getCurrentMemberId())
                .orElseThrow(() -> new BadRequestException(ErrorCode.MEMBER_NOT_FOUND));
        Belong belong = belongRepository.findBelongByMemberAndClub(member, club)
                .orElseThrow(() -> new BadRequestException(ErrorCode.BELONG_NOT_FOUND));
        if (belong.getPosition() == Position.NORMAL)
            throw new ForbiddenException(ErrorCode.BELONG_FORBIDDEN_MEMBER);
        List<Announcement> announcements = announcementRepository.findAnnouncementsByClub(club);
        if (!announcements.isEmpty() && !announcements.get(announcements.size() - 1).getInformState())
            throw new BadRequestException(ErrorCode.ANNOUNCEMENT_NOT_INFORM);
        try {
            if (multipartFile != null && !multipartFile.isEmpty())
                posterUrl = s3Service.uploadFiles(multipartFile, "images");
        } catch (Exception e) {
            throw new BadRequestException(ErrorCode.S3_CONNECTION_ERROR);
        }
        Announcement announcement = request.toAnnouncement(club, posterUrl);
        CreateAnnouncementResponse response = CreateAnnouncementResponse.from(announcementRepository.save(announcement));
        for (String content : request.getQuestion()) {
            ApplicationQuestion applicationQuestion = new ApplicationQuestion(null, content, announcement);
            applicationQuestionRepository.save(applicationQuestion);
        }
        return response;
    }
}
