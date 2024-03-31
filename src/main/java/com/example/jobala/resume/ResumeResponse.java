package com.example.jobala.resume;

import com.example.jobala._user.User;
import com.example.jobala.jobopen.Jobopen;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;


public class ResumeResponse {

    @Data//개인 - 마이페이지 이력서 관리 DTO
    public static class MngDTO {
        private Integer id;
        private List<ResumeDTO> resumeDTO = new ArrayList<>();

        public MngDTO(Integer userId, List<Resume> resumeList) {
            this.id = userId;
            this.resumeDTO = resumeList.stream().map(ResumeDTO::new).toList();
        }

        @Data
        public class ResumeDTO {
            private Integer id;
            private String resumeTitle; //공고제목

            public ResumeDTO(Resume resume) {
                this.id = resume.getId();
                this.resumeTitle = resume.getResumeTitle();
            }
        }
    }

    @AllArgsConstructor
    @Data
    public static class ScrapDTO {
        private int id;
        private String name;
        private String resumeTitle;
        private String career;
        private String edu;

        public ScrapDTO(Resume resume) {
            this.id = resume.getId();
            this.name = resume.getUser().getName();
            this.resumeTitle = resume.getResumeTitle();
            this.career = resume.getCareer();
            this.edu = resume.getEdu();
        }
    }

    // update시 체크되도록하는 DTO
    @Data
    @AllArgsConstructor
    public static class CheckBoxDTO {
        private Boolean java;
        private Boolean JavaScript;
        private Boolean spring;
        private Boolean html;
        private Boolean jquery;
        private Boolean mysql;

        public CheckBoxDTO(List<String> skillList) {
            this.java = skillList.contains("Java");
            this.JavaScript = skillList.contains(" JavaScript");
            this.spring = skillList.contains(" Spring");
            this.html = skillList.contains(" HTML");
            this.jquery = skillList.contains(" jQuery");
            this.mysql = skillList.contains(" MySQL");
        }
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class ScoutListDTO {
        private Integer id; //이력서 아이디
        private String resumeTitle; //이력서제목
        private String edu; //학력
        private String career; //경력
        private User user;

        public ScoutListDTO(User user, Resume resume) {
            this.id = resume.getId();
            this.resumeTitle = resume.getResumeTitle();
            this.edu = resume.getEdu();
            this.career = resume.getCareer();
            this.user = new User(user);
        }


        public class User {
            private Integer id; //유저아이디
            private String imgFilename; // 유저이미지
            private String name; // 유저이름

            public User(User user) {
                this.id = user.id;
                this.imgFilename = user.imgFilename;
                this.name = user.name;
            }
        }
    }


    @AllArgsConstructor
    @Data
    public static class DetailDTO {
        private Integer id;
        private String resumeTitle;
        private String hopeJob;
        private String career;
        private String license;
        private String content;
        private String edu;
        private String skills;
        private boolean isScrap;
        private boolean isGuestScrap;
        private UserDTO userDTO;
        private List<JobopenDTO> applyJobopenList = new ArrayList<>();

        public DetailDTO(Resume resume, User sessionUser, List<Jobopen> jobopenList) {
            this.id = resume.getId();
            this.resumeTitle = resume.getResumeTitle();
            this.hopeJob = resume.getHopeJob();
            this.career = resume.getCareer();
            this.license = resume.getLicense();
            this.content = resume.getContent();
            this.edu = resume.getEdu();
            this.skills = resume.getSkills();
            this.isGuestScrap = false;
            this.isScrap = false;

            this.userDTO = new UserDTO(resume.getUser());

            //기업 제안하기 - 모달창 공고 목록
            this.applyJobopenList = jobopenList.stream().map(j -> new JobopenDTO(j)).toList();

            // 회사만 이력서를 스크랩 할 수 있다.
            if (sessionUser != null) {
                if (sessionUser.getRole() == 1) {
                    this.isGuestScrap = true;
                }
            }
        }

        @Data
        public class JobopenDTO {
            private String jobopenTitle;
            private String hopeJob;
            private String career;

            public JobopenDTO(Jobopen jobopen) {
                this.jobopenTitle = jobopen.getJobopenTitle();
                this.hopeJob = jobopen.getHopeJob();
                this.career = jobopen.getCareer();
            }
        }

        @Data
        public class UserDTO {
            private Integer userId;
            private String imgFilename;
            private String name;
            private Date age;
            private String email;
            private String address;

            public UserDTO(User user) {
                this.userId = user.getId();
                this.imgFilename = user.getImgFilename();
                this.name = user.getName();
                this.age = user.getAge();
                this.email = user.getEmail();
                this.address = user.getAddress();
            }
        }
    }
}
