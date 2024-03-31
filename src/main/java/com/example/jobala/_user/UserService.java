package com.example.jobala._user;

import com.example.jobala._core.errors.apiException.ApiException401;
import com.example.jobala._core.errors.exception.Exception400;
import com.example.jobala._core.errors.exception.Exception404;
import com.example.jobala._core.utill.Paging;
import com.example.jobala.jobopen.JobopenJPARepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final JobopenJPARepository jobopenJPARepository;
    private final UserJPARepository userJPARepository;
    private final Paging paging;

    //메인 공고 목록조회
    public List<UserResponse.MainDTO> mainJobopenList(Integer page, User sessionUser) {
        return jobopenJPARepository.findAll(paging.boardPaging(page)).
                stream().map(jobopen -> new UserResponse.MainDTO(jobopen, sessionUser)).toList();
    }

    // 로그인
    public UserResponse.LoginResponseDTO login(UserRequest.LoginDTO reqDTO) {
        try {
            User user = userJPARepository.findByUsernameAndPassword(reqDTO.getUsername(), reqDTO.getPassword())
                    .orElseThrow(() -> new ApiException401("인증되지 않았습니다."));
            Boolean isCheck = user.getRole() == 0;
            return new UserResponse.LoginResponseDTO(user, isCheck);
        } catch (EmptyResultDataAccessException e) {
            throw new ApiException401("아이디,비밀번호가 틀렸어요");
        }
    }

    // 회원가입
    @Transactional
    public UserResponse.JoinDTO join(UserRequest.JoinDTO reqDTO) {
        Optional<User> userOP = userJPARepository.findByUsername(reqDTO.getUsername());
        if (userOP.isPresent()) {
            throw new Exception400("중복된 유저네임입니다.");
        }
        User user = null;
        if (reqDTO.getRole() == 1) {
            user = userJPARepository.save(reqDTO.toCompEntity());
        } else if (reqDTO.getRole() == 0) {
            user = userJPARepository.save(reqDTO.toGuestEntity());
        }
        return new UserResponse.JoinDTO(
                new UserResponse.JoinDTO.GuestDTO(user),
                new UserResponse.JoinDTO.CompDTO(user)
        );
    }

    public User guestInfo(Integer id) {
        return userJPARepository.findById(id).orElseThrow(() -> new Exception404("유저의 정보를 찾을 수 없습니다."));
    }

}