package com.example.jobala.jobopen;

import com.example.jobala.resume.Resume;
import com.example.jobala.resume.ResumeResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface JobopenJPARepository extends JpaRepository<Jobopen, Integer> {

    @Query("select j from Jobopen j JOIN FETCH j.user")
    List<Jobopen> main();

    //공고 찾아오기
    @Query("select j from Jobopen j where j.user.id=:userId")
    List<Jobopen> findJobopenById(@Param("userId")int userId);

    @Query("SELECT new com.example.jobala.jobopen.JobopenResponse$ScrapDTO(j) FROM Jobopen j JOIN j.scraps s WHERE s.user.id = :userId")
    List<JobopenResponse.ScrapDTO> findByUserIdJoinScrap(@Param("userId") int userId);

    // 사용자 ID를 이용하여 채용공고 정보와 함께 사용자 정보를 가져오는 쿼리
    @Query("SELECT j FROM Jobopen j JOIN FETCH j.user WHERE j.id = :jobopenId")
    Optional<Jobopen> findByJobopenIdWithUser(@Param("jobopenId") int jobopenId);

}