package com.example.jobala.guest;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

public class GuestRequest {

    //TODO: 이름
    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class SearchDTO {
        @Pattern(regexp = "^(신입|경력)$", message = "status는 비어있을 수 없습니다")
        private String career;
        @NotEmpty(message = "주소가 공백일 수 없습니다")
        private String compLocation;
        @Pattern(regexp = "^(고등학교 졸업|대학교 졸업)$", message = "학력은 비어있을 수 없습니다")
        private String edu;
        @Pattern(regexp = "^(협의|3000만원 이상|5000만원 이상)$", message = "연봉은 비어있을 수 없습니다")
        private String salary;
        @Pattern(regexp = "^(백엔드|프론트엔드)$", message = "희망직업은 비어있을 수 없습니다")
        private String hopeJob;
        @Pattern(regexp = "^(계약직|정규직)$", message = "고용형태 비어있을 수 없습니다")
        private String jobType;
    }

    //개인 프로필 업데이트
    @Data
    public static class GuestProfileUpdateDTO {
        @Size(min = 1, max = 3, message = "이름은 1자 이상 3자 이하여야 합니다")
        private String name;
        @NotEmpty(message = "비밀번호는 비어있을 수 없습니다")
        @Size(min = 4, message = "비밀번호는 최소 4자 이상이어야 합니다")
        private String password;
        @Length(min = 13, max = 13, message = "전화번호는 정확히 13자여야 합니다")
        private String phone;
        @Email(message = "올바른 이메일 형식이어야 합니다")
        private String email;
        @NotEmpty(message = "사진제목은 비어있을 수 없습니다")
        private String imgTitle;
        @NotEmpty(message = "사진경로는 비어있을 수 없습니다")
        private String imgFilename;
    }
}