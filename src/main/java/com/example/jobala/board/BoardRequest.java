package com.example.jobala.board;

import com.example.jobala._user.User;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

public class BoardRequest {

    // TODO: 이름
    @Data
    public static class SaveDTO {
        @NotEmpty(message = "제목은 공백일 수 없습니다")
        @Size(min = 1, max = 100, message = "제목은 1자 이상 100자 이하여야 합니다")
        private String title;

        @NotEmpty(message = "내용은 공백일 수 없습니다")
        @Size(min = 1, message = "내용은 1자 이상여야 합니다")
        private String content;

        public Board toEntity(User sessionUser) {
            Board board = Board.builder()
                    .title(title)
                    .content(content)
                    //유저 객체 받아오기
                    .user(sessionUser)
                    .build();
            return board;
        }
    }

    // TODO: 이름
    @Data
    public static class UpdateDTO {
        @NotEmpty(message = "제목은 공백일 수 없습니다")
        @Size(min = 1, max = 100, message = "제목은 1자 이상 100자 이하여야 합니다")
        private String title;

        @NotEmpty(message = "내용은 공백일 수 없습니다")
        @Size(min = 1, message = "내용은 1자 이상여야 합니다")
        private String content;
    }
}