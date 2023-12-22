package com.example.Spring.entity;

import com.example.Spring.models.Exam;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "ENROLLEE")
public class ExamEntity {
    @Autowired
    @Column(name = "SUBJECT", length = 64, nullable = false)
    private String subject;

    @Column(name = "SCORE", nullable = false)
    private Integer score;

    @Id
    @GeneratedValue
    @Column(name = "IDENROLLEE", nullable = false)
    private Integer idEnrollee;

    public ExamEntity(Exam exam) {
        setSubject(exam.getSubject());
        setScore(exam.getScore());
        setIdEnrollee(exam.getIdEnrollee());
    }
}
