package com.example.Spring.dao;

import com.example.Spring.models.Exam;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ExamListDao implements Dao<Exam> {
    public ExamListDao() {
        exams.add(new Exam("Математика", 64, 0));
    }

    private List<Exam> exams = new ArrayList<>();

    @Override
    public int size() {
        return exams.size();
    }

    @Override
    public Optional<Exam> get(long id) {
        return Optional.of(exams.get((int) id));
    }

    @Override
    public List<Exam> getAll() {
        return exams;
    }

    @Override
    public void save(Exam exam) {
        exams.add(exam);
    }

    @Override
    public void delete(Exam exam) {
        exams.remove(exam);
    }

    /*public List<Exam> getExamsByEnrolleeId(int idEnrollee) {
        List<Exam> examList;
        examList = exams.stream().filter(x -> x.getIdEnrollee() == idEnrollee).toList();
        return examList;
    }
    public void update(Exam updatedExam) {
        for (int i = 0; i < exams.size(); i++) {
            Exam existingExam = exams.get(i);
            if (existingExam == updatedExam) {
                exams.set(i, updatedExam);
                return;
            }
        }
    }*/
}
