package com.example.Spring.service;

import com.example.Spring.dao.Dao;
import com.example.Spring.entity.EnrolleeEntity;
import com.example.Spring.models.Enrollee;

import java.util.List;

public class EnrolleeService {
    private Dao enrolleeDao;

    public EnrolleeService(Dao enrolleeDao) {
        this.enrolleeDao = enrolleeDao;
    }

    public long sizeEnrollees() {
        return enrolleeDao.size();
    }

    public List<Enrollee> getAllEnrolles() {
        return enrolleeDao.getAll().stream().map(x -> new Enrollee((EnrolleeEntity) x)).toList();
    }

    public Enrollee getEnrollee (long id) {
        return new Enrollee((EnrolleeEntity) enrolleeDao.get(id).get());
    }

    public void save(Enrollee enrollee) {
        enrollee.setId(enrolleeDao.size());
        EnrolleeEntity enrolleeEntity = new EnrolleeEntity(enrollee);
        enrolleeDao.save(enrolleeEntity);
    }

}
