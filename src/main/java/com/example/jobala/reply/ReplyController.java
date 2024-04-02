package com.example.jobala.reply;

import com.example.jobala._core.utill.ApiUtil;
import com.example.jobala._user.User;
import com.example.jobala._user.UserResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequiredArgsConstructor
public class ReplyController {
    private final HttpSession session;
    private final ReplyService replyService;

    //댓글 쓰기
    @PostMapping("/api/replies")
    public ResponseEntity<?> save(@Valid @RequestBody ReplyRequest.SaveDTO reqDTO, Errors errors){
        User sessionUser = (User) session.getAttribute("sessionUser");
        ReplyResponse.ReplyDTO respDTO = replyService.replySave(reqDTO, sessionUser);
        return ResponseEntity.ok(new ApiUtil(respDTO));
    }

    //댓글 삭제
    @DeleteMapping("/api/replies/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id){
        User sessionUser = (User) session.getAttribute("sessionUser");
        replyService.replyDelete(id, sessionUser.getId());
        return ResponseEntity.ok(new ApiUtil(null));
    }
}