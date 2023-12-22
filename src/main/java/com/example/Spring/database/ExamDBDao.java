package com.example.Spring.database;

import com.example.Spring.dao.Dao;
import com.example.Spring.entity.ExamEntity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ExamDBDao implements Dao<ExamEntity> {
    H2Connection h2Connection;

    public ExamDBDao() throws SQLException, ClassNotFoundException {
        h2Connection = H2Connection.getH2Connection();
        Statement statement = h2Connection.getConnection().createStatement();

        String s = "CREATE TABLE IF NOT EXISTS EXAM " +
                "(idEnrollee INT NOT NULL, " +
                " score INT NOT NULL, " +
                " subject VARCHAR(60) NOT NULL, " +
                " CONSTRAINT FK_idEnrollee FOREIGN KEY (idEnrollee) REFERENCES ENROLLEE(id));";

        statement.execute(s);
        // Добавление данных
        s = "INSERT INTO EXAM (subject, score, idEnrollee) VALUES ('Математика', 78, 0);";
        statement.execute(s);
        statement.close();
    }


    public int size() {
        try {
            Statement statement = h2Connection.getConnection().createStatement();
            ResultSet resultSet = statement.
                    executeQuery("select * from EXAM");
            int counter = 0;
            while (resultSet.next()) {
                counter++;
            }
            statement.close();
            return counter;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    @Override
    public Optional<ExamEntity> get(long id) {
        try {
            Statement statement = h2Connection.getConnection().createStatement();
            ResultSet resultSet = statement.
                    executeQuery("select * from EXAM");
            while (resultSet.next()) {
                if (resultSet.getLong("id") == id) {
                    ExamEntity examEntity = new ExamEntity();
                    examEntity.setIdEnrollee(resultSet.getInt("idEnrollee"));
                    examEntity.setScore(resultSet.getInt("score"));
                    examEntity.setSubject(resultSet.getString("subject"));
                    statement.close();
                    return Optional.of(examEntity);
                }
            }
        } catch (SQLException thrown) {
            thrown.printStackTrace();
        }
        return null;
    }

    @Override
    public List<ExamEntity> getAll() {
        try {
            List<ExamEntity> examEntities = new ArrayList<>();
            Statement statement = h2Connection.getConnection().createStatement();
            ResultSet resultSet = statement.
                    executeQuery("select * from EXAM");
            while (resultSet.next()) {
                ExamEntity examEntity = new ExamEntity();
                examEntity.setIdEnrollee(resultSet.getInt("idEnrollee"));
                examEntity.setScore(resultSet.getInt("score"));
                examEntity.setSubject(resultSet.getString("subject"));
                examEntities.add(examEntity);
            }
            statement.close();
            return examEntities;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void save(ExamEntity examEntity) {
        try {
            Statement statement = h2Connection.getConnection().createStatement();
            String subject = examEntity.getSubject();
            int score = examEntity.getScore();
            int idEnrollee = examEntity.getIdEnrollee();

            // Проверка существования записи
            ResultSet resultSet = statement.executeQuery(
                    String.format("SELECT * FROM EXAM WHERE subject = '%s' AND idEnrollee = %d",
                            subject, idEnrollee)
            );

            if (resultSet.next()) {
                // Запись уже существует, выполняем обновление
                statement.executeUpdate(String.format("UPDATE EXAM SET score = %d WHERE subject = '%s' AND idEnrollee = %d",
                        score, subject, idEnrollee));
            } else {
                // Запись не существует, выполняем вставку
                String s = String.format("INSERT INTO EXAM (subject, score, idEnrollee) VALUES ('%s', %d, %d)",
                        subject, score, idEnrollee);
                statement.execute(s);
            }

            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(ExamEntity examEntity) {
        try {
            Statement statement = h2Connection.getConnection().createStatement();
            statement.executeQuery(String.format("delete * from EXAM where id = %s", examEntity.getIdEnrollee()));
            statement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}