package com.example.jobala.comp;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

public class CompRequest {

    //TODO: 이름
    @AllArgsConstructor
    @Data
    public static class SearchDTO {
        @Pattern(regexp = "^(신입|경력)$", message = "status는 비어있을 수 없습니다")
        private String career;
        @Pattern(regexp = "^(고등학교 졸업|대학교 졸업)$", message = "status는 비어있을 수 없습니다")
        private String edu;
        @Pattern(regexp = "^(백엔드|프론트엔드)$", message = "status는 비어있을 수 없습니다")
        private String hopeJob;
    }

    // DEL: Response에서 ScoutListDTO 만듬

    //TODO: 이름
    @Getter
    @AllArgsConstructor
    public static class CompProfileUpdateDTO {
        @NotEmpty(message = "이름이 공백일 수 없습니다")
        private String name;
        @NotEmpty(message = "비밀번호가 공백일 수 없습니다")
        private String password;
        @NotEmpty(message = "전화번호가 공백일 수 없습니다")
        private String phone;
        @NotEmpty(message = "이메일이 공백일 수 없습니다")
        private String email;
        @NotEmpty(message = "주소가 공백일 수 없습니다")
        private String address;
        @NotEmpty(message = "사진이 없을 수 없습니다")
        private String imgTitle;
        private String imgFilename;
    }
}
