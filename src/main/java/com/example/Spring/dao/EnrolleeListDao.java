package com.example.Spring.dao;

import com.example.Spring.models.Enrollee;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class EnrolleeListDao implements Dao<Enrollee>{
    public EnrolleeListDao() {
        enrollees.add(new Enrollee(0, "Одинцов Алексей Александрович", new Date(2003, 11, 19)));
    }

    private List<Enrollee> enrollees = new ArrayList<>();
    @Override
    public int size() {
        return enrollees.size();
    }

    @Override
    public Optional<Enrollee> get(long id) {
        return Optional.of(enrollees.get((int) id));
    }

    @Override
    public List<Enrollee> getAll() {
        return enrollees;
    }

    @Override
    public void save(Enrollee enrollee) {
        enrollees.add(enrollee);
    }

    @Override
    public void delete(Enrollee enrollee) {
        enrollees.remove(enrollee);
    }
}