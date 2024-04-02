package com.example.jobala.guest;

import com.example.jobala._core.utill.ApiUtil;
import com.example.jobala._user.SessionUser;
import com.example.jobala._user.UserResponse;
import com.example.jobala.resume.ResumeResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class GuestController {

    private final HttpSession session;
    private final GuestService guestService;

    // DEL: mainForm 삭제

    //이력서 관리 페이징
    @GetMapping("/api/guest/mngForm")
    public ResponseEntity<?> mngForm( @RequestParam(defaultValue = "0") int page) {
        SessionUser sessionUser = (SessionUser) session.getAttribute("sessionUser");
        ResumeResponse.MngDTO respDTO = guestService.guestResumesMng(page, sessionUser.getId());
        return ResponseEntity.ok(new ApiUtil(respDTO));
    }

    // 개인 - 마이페이지 - 프로필
    @GetMapping("/api/guest/profileForm")
    public ResponseEntity<?> profileForm(HttpServletRequest req) {
        SessionUser sessionUser = (SessionUser) session.getAttribute("sessionUser");
        UserResponse.GuestProfile respDTO = guestService.guestProgile(sessionUser.getId());
        return ResponseEntity.ok(new ApiUtil(respDTO));
    }

    // 개인 - 마이페이지 - 프로필 업데이트
    @PutMapping("/api/guest/profile")
    public ResponseEntity<?> profileUpdate(@RequestBody GuestRequest.GuestProfileUpdateDTO reqDTO) {
        SessionUser sessionUser = (SessionUser) session.getAttribute("sessionUser");
        UserResponse.GuestProfile respDTO = guestService.guestUpdateProfile(reqDTO, sessionUser);
        return ResponseEntity.ok(new ApiUtil(respDTO));
    }
}