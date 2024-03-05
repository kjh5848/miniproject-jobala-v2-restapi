package com.example.jobala.apply;

import com.example.jobala.jobopen.Jobopen;
import com.example.jobala.resume.Resume;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "apply_tb")

public class Apply {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer resumeId;
    private Integer userId;
    private Integer jobopenId;
    private String state;
    private Integer role; // 0 -> guest, 1 -> comp
    private LocalDateTime createdAt;


    public void ifPresent(Object o) {
    }
}
