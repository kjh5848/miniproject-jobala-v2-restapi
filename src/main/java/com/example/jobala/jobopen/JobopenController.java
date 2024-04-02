package com.example.jobala.jobopen;

import com.example.jobala._core.utill.ApiUtil;
import com.example.jobala._user.User;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class JobopenController {

    private final HttpSession session;
    private final JobopenService jobopenService;

    //공고 삭제
    @DeleteMapping("/api/comp/jobopens/{id}")  // 주소 수정 필요
    public ResponseEntity<?> delete(@PathVariable int id) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        jobopenService.jobopenDelete(id, sessionUser.getId());
        return ResponseEntity.ok(new ApiUtil(null));
    }

    //공고 수정
    @PutMapping("/api/comp/jobopens/{id}")  // 주소 수정 필요
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody JobopenRequest.UpdateDTO reqDTO) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        JobopenResponse.UpdateDTO respDTO = jobopenService.jobopenUpdate(id, sessionUser, reqDTO);
        return ResponseEntity.ok(new ApiUtil<>(respDTO));
    }


    //공고 등록
    @PostMapping("/api/comp/jobopens")  // 주소 수정 필요
    public ResponseEntity<?> jobopenSave(@RequestBody JobopenRequest.SaveDTO reqDTO) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        JobopenResponse.SaveDTO respDTO = jobopenService.jobopenSave(reqDTO, sessionUser);
        return ResponseEntity.ok(new ApiUtil(respDTO));
    }

    //공고 보기
    @GetMapping("/api/comp/jobopens/{id}")
    public ResponseEntity<?> detailForm(@PathVariable int id) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        // 채용공고 정보 가져오기
        JobopenResponse.DetailDTO respDTO = jobopenService.findJobopenById(id, sessionUser);
        return ResponseEntity.ok(new ApiUtil(respDTO));
    }

}