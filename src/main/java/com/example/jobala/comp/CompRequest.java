package com.example.jobala.comp;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

public class CompRequest {

    //TODO: 이름
    @AllArgsConstructor
    @Data
    public static class SearchDTO {
        @Pattern(regexp = "^(신입|경력)$", message = "경력은 비어있을 수 없습니다")
        private String career;

        @Pattern(regexp = "^(고등학교 졸업|대학교 졸업)$", message = "학력은 비어있을 수 없습니다")
        private String edu;

        @Pattern(regexp = "^(백엔드|프론트엔드)$", message = "희망직업은 비어있을 수 없습니다")
        private String hopeJob;
    }

    // DEL: Response에서 ScoutListDTO 만듬

    //TODO: 이름
    @Getter
    @AllArgsConstructor
    public static class CompProfileUpdateDTO {
        @NotEmpty(message = "이름이 공백일 수 없습니다")
        @Size(min = 1, max = 3, message = "이름은 1자 이상 3자 이하여야 합니다")
        private String name;

        @NotEmpty(message = "비밀번호가 공백일 수 없습니다")
        @Size(min = 3, max= 20, message = "비밀번호는 3자 이상 20자 이하여야 합니다")
        private String password;

        @NotEmpty(message = "전화번호가 공백일 수 없습니다")
        @Pattern(regexp = "^\\d{3}-\\d{3,4}-\\d{4}$", message = "전화번호 형식이 올바르지 않습니다")
        private String phone;

        @NotEmpty(message = "이메일이 공백일 수 없습니다")
        @Email(message = "올바른 이메일 형식이어야 합니다")
        private String email;

        @NotEmpty(message = "주소가 공백일 수 없습니다")
        private String address;

        @NotEmpty(message = "사진제목이 공백일 수 없습니다")
        private String imgTitle;

        @NotEmpty(message = "사진경로가 공백일 수 없습니다")
        private String imgFilename;
    }
}
