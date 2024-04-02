package com.example.jobala.guest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class GuestResponse {


    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class SearchDTO {
        private String career;
        private String compLocation;
        private String edu;
        private String salary;
        private String hopeJob;
        private String jobType;
    }


    @Data
    @AllArgsConstructor
    public static class JopOpenApplyDTO {
        private String jobopenTitle;
        private String resumeTitle;
        private String state;
    }


    @Data
    @AllArgsConstructor
    public static class GuestProfileDTO {
        private Integer id;
        private String name;
        private String password;
        private String phone;
        private String email;
        private String imgFilename;
        private String imgTitle;
    }


    @Data
    @AllArgsConstructor
    public static class GProfileUpdateDTO {
        private String name;
        private String password;
        private String phone;
        private String email;
        private Integer id;
        private String imgFilename;
        private String imgTitle;
    }
}

