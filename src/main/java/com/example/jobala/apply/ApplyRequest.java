package com.example.jobala.apply;

import com.example.jobala._user.User;
import com.example.jobala.jobopen.Jobopen;
import com.example.jobala.resume.Resume;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class ApplyRequest {

    // TODO: 이름
    // 상태 업데이트
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ApplyStatusUpdateRequestDTO {
        @NotEmpty
        private Integer applyId;
        @Pattern(regexp = "^(열람전|합격|불합격|수락|거절)$", message = "status는 비어있을 수 없습니다")
        @NotEmpty(message = "status는 비어있을 수 없습니다")
        private String status;
    }

    // TODO: 이름
    // 지원하기
    @Data
    @AllArgsConstructor
    public static class ApplyRequestDTO {
        @NotEmpty(message = "status는 비어있을 수 없습니다")
        private Integer resumeId;
        @NotEmpty(message = "status는 비어있을 수 없습니다")
        private Integer jobopenId;

        public Apply toEntity(Resume resume, Jobopen jobopen, User sessionUser) {
            return Apply.builder()
                    .role(sessionUser.getRole())
                    .user(sessionUser)
                    .jobopen(jobopen)
                    .resume(resume)
                    .state("검토중")
                    .build();
        }
    }
}