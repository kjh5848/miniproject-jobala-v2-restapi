package com.example.jobala.apply;

import com.example.jobala._core.errors.exception.Exception404;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.example.jobala._user.User;
import com.example.jobala.jobopen.Jobopen;
import com.example.jobala.jobopen.JobopenJPARepository;
import com.example.jobala.resume.Resume;
import com.example.jobala.resume.ResumeJPARepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ApplyService {

    private final ApplyJPARepository applyJPARepository;
    private final JobopenJPARepository jobopenJPARepository;
    private final ResumeJPARepository resumeJPARepository;
    private final ApplyQueryRepository applyQueryRepository;

    // 기업 사용자를 위한 메서드
    public ApplyResponse.ApplyStatusDTO getCompanyApplyStatus(int userId) {
        List<ApplyResponse.ApplyDTO> appliedPositions = applyQueryRepository.findApplyCompByUserId(userId);
        // 기업 사용자에게 필요한 추가적인 데이터 조회 로직

        // 필요한 데이터를 기반으로 응답 객체 생성
        return new ApplyResponse.ApplyStatusDTO(appliedPositions, null, null, null);
    }

    // 개인 사용자를 위한 메서드
    public ApplyResponse.ApplyStatusDTO getGuestApplyStatus(int userId) {
        List<ApplyResponse.ApplyDTO> appliedPositions = applyQueryRepository.findByUserId(userId);
        List<ApplyResponse.ApplyDTO2> receivedOffers = applyQueryRepository.findJopOpenByUserId(userId);
        // 개인 사용자에게 필요한 추가적인 데이터 조회 로직

        // 필요한 데이터를 기반으로 응답 객체 생성
        return new ApplyResponse.ApplyStatusDTO(appliedPositions, receivedOffers, null, null);
    }

    @Transactional
    public void 상태수정(Integer applyId, String status) {
        Apply apply = applyJPARepository.findById(applyId).orElseThrow(() ->
                new RuntimeException("해당 ID로 조회된 지원정보가 없습니다 : " + applyId));

        apply.setState(status);
    }

    @Transactional
    public void 지원후저장(ApplyRequest.ApplyRequestDTO reqDTO, User sessionUser) {
        Jobopen jobopen = jobopenJPARepository.findById(reqDTO.getJobopenId())
                .orElseThrow(() -> new Exception404("공고를 찾을 수 없습니다"));
        Resume resume = resumeJPARepository.findById(reqDTO.getResumeId())
                .orElseThrow(() -> new Exception404("이력서를 찾을 수 없습니다"));

        applyJPARepository.save(reqDTO.toEntity(resume,jobopen,sessionUser));
    }

}
