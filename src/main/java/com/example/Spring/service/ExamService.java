package com.example.Spring.service;

import com.example.Spring.dao.Dao;
import com.example.Spring.entity.ExamEntity;
import com.example.Spring.models.Exam;

import java.util.List;

public class ExamService {
    private Dao examDao;

    public ExamService(Dao examDao) {
        this.examDao = examDao;
    }

    public long sizeExams() {
        return examDao.size();
    }

    public List<Exam> getAllExams() {
        return examDao.getAll().stream().map(x -> new Exam((ExamEntity) x)).toList();
    }

    public List<Exam> getExamsByEnrolleeId(int idEnrollee) {
        List<Exam> examList;
        examList = getAllExams().stream().filter(x -> x.getIdEnrollee() == idEnrollee).toList();
        return examList;
    }

    public void save(Exam exam) {
        exam.setIdEnrollee(exam.getIdEnrollee());
        ExamEntity examEntity = new ExamEntity(exam);
        examDao.save(examEntity);
    }

}