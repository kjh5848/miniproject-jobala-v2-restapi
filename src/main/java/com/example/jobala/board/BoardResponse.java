package com.example.jobala.board;

import com.example.jobala._user.User;
import com.example.jobala.reply.Reply;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class BoardResponse {
    // 글 수정
    @Data
    public class UpdateDTO {
        private Integer id;
        private String title;
        private String content;
        private UserDTO user;

        public UpdateDTO(Board board) {
            this.id = board.getId();
            this.title = board.getTitle();
            this.content = board.getContent();
            this.user = new UserDTO(board.getUser());
        }
    }

    @Data
    public class UserDTO {
        private int id;

        public UserDTO(User user) {
            this.id = user.getId();
        }
    }

    // 글쓰기
    @Data
    public static class SaveDTO {
        private int id;
        private String title;
        private String content;

        public SaveDTO(Board board) {
            this.id = board.getId();
            this.title = board.getTitle();
            this.content = board.getContent();
        }
    }

    @Data
    public static class DetailDTO {
        private int id;
        private String title;
        private String content;
        private int userId;
        private String username; // 게시글 작성자 이름
        private boolean isOwner;
        private List<ReplyDTO> replies = new ArrayList<>();

        public DetailDTO(Board board, User sessionUser) {
            this.id = board.getId();
            this.title = board.getTitle();
            this.content = board.getContent();
            this.userId = board.getUser().getId();
            this.username = board.getUser().getUsername(); // join 해서 가져왔음
            this.isOwner = false;
            if(sessionUser != null){
                if(sessionUser.getId() == userId) isOwner = true;
            }

            this.replies = board.getReplies().stream().map(reply -> new ReplyDTO(reply, sessionUser)).toList();
        }

        @Data
        public class ReplyDTO {
            private int id;
            private String comment;
            private int userId; // 댓글 작성자 아이디
            private String username; // 댓글 작성자 이름
            private boolean isOwner;

            public ReplyDTO(Reply reply, User sessionUser) {
                this.id = reply.getId(); // lazy loading 발동
                this.comment = reply.getComment();
                this.userId = reply.getUser().getId();
                this.username = reply.getUser().getUsername(); // lazy loading 발동 (in query)
                this.isOwner = false;
                if(sessionUser != null){
                    if(sessionUser.getId() == userId) isOwner = true;
                }
            }
        }
    }
    //    자유게시판 메인 목록 DTO
    @Data
    public static class MainDetailDTO {
        private Integer id;
        private String title;
        private Timestamp createdAt;
        private UserDTO user;

        @Data
        public class UserDTO {
            private int id;
            private String username;

            public UserDTO(User user) {
                this.id = user.getId();
                this.username = user.getUsername();
            }
        }

        public MainDetailDTO(Board board) {
            this.id = board.getId();
            this.title = board.getTitle();
            this.createdAt= board.getCreatedAt();
            this.user = new UserDTO(board.getUser());
        }
    }

    // 상세보기
    @Data
    public static class BoardDetailDTO {
        private Integer id;
        private String title;
        private String content;
        private Integer userId;
        private String username;
        private Boolean boardOwner;

        // qlrm 맵핑은 순서를 맞춰주고 조인하는 컬럼만 생성자를 만들어서 초기화를 시켜준다.
        public BoardDetailDTO(Integer id, String title, String content, Integer userId, String username) {
            this.id = id;
            this.title = title;
            this.content = content;
            this.userId = userId;
            this.username = username;
        }

        // 보더의 주인여부 확인
        public void isOwner(User sessionUser) {
            if (sessionUser == null) boardOwner = false;
            else boardOwner = sessionUser.getId() == userId;
        }
    }
}