package com.example.jobala.jobopen;

import com.example.jobala._user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.sql.Update;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class JobopenRequest {

    @AllArgsConstructor
    @Data
    public static class DeleteDTO {
        private int id;
    }

    @AllArgsConstructor
    @Data
    public static class JobopenDetailDTO {
        private String edu;
        private String jobopenTitle;
        private String career;
        private String jobType;
        private String salary;
        private String hopeJob;
        private String compLocation;
        private String content;
        private Date endTime;
        private List<String> skills = new ArrayList<>(); //내용
    }

    @AllArgsConstructor
    @Data
    public static class UpdateDTO {
        //compname = ? ,jobopenTitle=? , career=?, edu=?, jobType=?,salary=?,compLocation=?,content=? ,skills =?
        private String jobopenTitle; //공고제목
        private String career;// 경력
        private String edu; // 학력
        private String jobType; // 고용형태
        private String salary; //연봉
        private String hopeJob;//희망직무
        private String compLocation; //근무지역
        private Date endTime; //내용
        private List<String> skills = new ArrayList<>();//스킬

    }

    @AllArgsConstructor
    @Data
    public static class SaveDTO {
        private String edu;
        private String jobopenTitle;
        private String career;
        private String jobType;
        private String salary;
        private String hopeJob;
        private String compLocation;
        private Date endTime;
        private List<String> skills = new ArrayList<>(); //내용

        public Jobopen toEntity(User user) {
            return Jobopen.builder()
                    .skills(String.valueOf(skills))
                    .user(user)
                    .role(user.getRole())
                    .edu(edu)
                    .jobopenTitle(jobopenTitle)
                    .career(career)
                    .jobType(jobType)
                    .salary(salary)
                    .hopeJob(hopeJob)
                    .compLocation(compLocation)
                    .endTime(endTime)
                    .build();
        }
    }
}


