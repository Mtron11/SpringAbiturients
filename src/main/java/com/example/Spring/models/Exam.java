package com.example.Spring.models;

import com.example.Spring.entity.ExamEntity;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Exam {
    private String subject;
    private Integer score;
    private Integer idEnrollee;
    public Exam(ExamEntity exam) {
        setSubject(exam.getSubject());
        setScore(exam.getScore());
        setIdEnrollee(exam.getIdEnrollee());
    }
    public boolean notNull() {
        return subject != null && score != null;
    }
}
