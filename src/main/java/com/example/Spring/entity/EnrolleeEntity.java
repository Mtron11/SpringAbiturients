package com.example.Spring.entity;

import com.example.Spring.models.Enrollee;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "ENROLLEE")
public class EnrolleeEntity {
    @Id
    @GeneratedValue
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    @Column(name = "ID", nullable = false)
    private Integer id;

    @Column(name = "FULLNAME", length = 64, nullable = false)
    private String fullName;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @Column(name = "BIRTHDAY", nullable = false)
    private Date birthday;

    private String stringBirthday;

    public EnrolleeEntity(Enrollee enrollee) {
        setId(enrollee.getId());
        setBirthday(transformDate(enrollee.getBirthday()));
        setFullName(enrollee.getFullName());
    }

    private Date transformDate(java.util.Date date) {
        return new Date(date.getYear(), date.getMonth(), date.getDay());
    }
}
