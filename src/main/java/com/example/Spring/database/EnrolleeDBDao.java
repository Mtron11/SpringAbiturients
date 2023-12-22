package com.example.Spring.database;

import com.example.Spring.dao.Dao;
import com.example.Spring.entity.EnrolleeEntity;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EnrolleeDBDao implements Dao<EnrolleeEntity> {
    private H2Connection h2Connection;

    public EnrolleeDBDao() throws SQLException, ClassNotFoundException {
        h2Connection = H2Connection.getH2Connection();
        Statement statement = h2Connection.getConnection().createStatement();
        String s = "CREATE TABLE IF NOT EXISTS ENROLLEE" +
                "(id number primary key not null," +
                " birthday date not null, " +
                " fullName varchar(60) not null );" +
                "INSERT INTO ENROLLEE (id, birthday, fullName)\n" +
                "VALUES (0, '2003-11-19', 'Одинцов Алексей Александрович');";
        statement.execute(s);
        statement.close();
    }

    public int size() {
        try {
            Statement statement = h2Connection.getConnection().createStatement();
            ResultSet resultSet = statement.
                    executeQuery("select * from ENROLLEE");
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
    public Optional<EnrolleeEntity> get(long id) {
        try {
            Statement statement = h2Connection.getConnection().createStatement();
            ResultSet resultSet = statement.
                    executeQuery("select * from ENROLLEE");
            while (resultSet.next()) {
                if (resultSet.getLong("id") == id) {
                    EnrolleeEntity enrolleeEntity = new EnrolleeEntity();
                    enrolleeEntity.setId(resultSet.getInt("id"));
                    enrolleeEntity.setBirthday(resultSet.getDate("birthday"));
                    enrolleeEntity.setFullName(resultSet.getString("fullName"));
                    statement.close();
                    return Optional.of(enrolleeEntity);
                }
            }
        } catch (SQLException thrown) {
            thrown.printStackTrace();
        }
        return null;
    }

    @Override
    public List<EnrolleeEntity> getAll() {
        try {
            List<EnrolleeEntity> enrolleeEntities = new ArrayList<>();
            Statement statement = h2Connection.getConnection().createStatement();
            ResultSet resultSet = statement.
                    executeQuery("select * from ENROLLEE");
            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String fullName = resultSet.getString("fullName");
                Date birthday = resultSet.getDate("birthday");
                EnrolleeEntity enrolleeEntity = new EnrolleeEntity();
                enrolleeEntity.setId(id.intValue());
                enrolleeEntity.setFullName(fullName);
                enrolleeEntity.setBirthday(birthday);
                enrolleeEntities.add(enrolleeEntity);
            }
            statement.close();
            return enrolleeEntities;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void save(EnrolleeEntity enrollee) {
        try {
            Statement statement = h2Connection.getConnection().createStatement();
            String s = String.format("insert into ENROLLEE values (%s, '%s', '%s')",
                    enrollee.getId(), enrollee.getBirthday(), enrollee.getFullName());
            statement.execute(s);
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(EnrolleeEntity enrollee) {
        try {
            Statement statement = h2Connection.getConnection().createStatement();
            statement.executeQuery(String.format("delete * from ENROLLEE where id = %s", enrollee.getId()));
            statement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
