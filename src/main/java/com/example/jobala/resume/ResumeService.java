package com.example.jobala.resume;

import com.example.jobala._core.errors.apiException.ApiException403;
import com.example.jobala._core.errors.exception.Exception403;

import com.example.jobala._user.User;
import com.example.jobala.jobopen.Jobopen;
import com.example.jobala.jobopen.JobopenJPARepository;
import com.example.jobala.jobopen.JobopenQueryRepository;
import com.example.jobala.scrap.Scrap;
import com.example.jobala.scrap.ScrapJPARepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ResumeService {
    private final ResumeJPARepository resumeJPARepository;
    private final JobopenJPARepository jobopenJPARepository;
    private final JobopenQueryRepository jobopenQueryRepository;
    private final ScrapJPARepository scrapJPARepository;

    // 이력서등록
    @Transactional
    public ResumeResponse.ASaveDTO resumeSave(ResumeRequest.SaveDTO reqDTO, User sessionUser) {
       Resume resume =  resumeJPARepository.save(reqDTO.toEntity(sessionUser));
        return new ResumeResponse.ASaveDTO(resume,sessionUser);
    }

    // 이력서삭제
    @Transactional
    public Resume resumeDelete(int resumeId, Integer sessionUserId) {
        Resume resume = resumeJPARepository.findById(sessionUserId)
                .orElseThrow(() -> new ApiException403("이력서를 찾을 수 없습니다"));
        if (sessionUserId != resume.getUser().getId()) {
            throw new ApiException403("이력서를 삭제할 권한이 없습니다");
        }
        resumeJPARepository.deleteById(resumeId);
        return resume;
    }

    // 이력서수정
    @Transactional
    public ResumeResponse.UpdateDTO resumeUpdate(Integer resumeId, ResumeRequest.UpdateDTO reqDTO, Integer sessionUserId) {
        Resume resume = resumeJPARepository.findById(resumeId)
                .orElseThrow(() -> new ApiException403("이력서 정보를 찾을 수 없습니다."));
        if (sessionUserId != resume.getUser().getId()) {
            throw new ApiException403("이력서를 수정할 권한이 없습니다.");
        }
        resume.setResumeUpdateDTO(reqDTO);
        return new ResumeResponse.UpdateDTO(resume);
    }

    // 이력서보기
    public ResumeResponse.DetailDTO resumeFindById(Integer resumeId, User sessionUser) {
        Resume resume = resumeJPARepository.findByIdWithUser(resumeId)
                .orElseThrow(() -> new ApiException403("이력서를 찾을 수 없습니다."));

        //모달 공고목록 조회
        List<Jobopen> jobopen = jobopenJPARepository.findJobopenById(sessionUser.getId());

        List<String> skills = Arrays.stream(resume.getSkills().replaceAll("[\\[\\]\"]", "").split(",")).toList();
        String skillsString = String.join(", ", skills);

        // isScrap
        Optional<Scrap> scrap = scrapJPARepository.findCompScrapByResumeIdAndUserId(resumeId, sessionUser.getId());
        ResumeResponse.DetailDTO respDTO = new ResumeResponse.DetailDTO(resume, sessionUser, jobopen);
        respDTO.setScrap(scrap.isPresent());
        respDTO.setSkills(skillsString);
        return respDTO;
    }

    public ResumeResponse.CheckBoxDTO getCheckedSkills(Integer id) {
        Resume resume = resumeJPARepository.findById(id).orElseThrow(() -> new ApiException403("이력서를 찾을 수 없습니다."));
        String skillsStr = resume.getSkills();
        skillsStr = skillsStr.substring(1, skillsStr.length() - 1);
        List<String> skills = Arrays.asList(skillsStr.split(","));
        return new ResumeResponse.CheckBoxDTO(skills);
    }
}
