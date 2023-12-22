package com.example.Spring.models;

import com.example.Spring.entity.EnrolleeEntity;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.jetbrains.annotations.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
@Data
@NotNull
@AllArgsConstructor
@NoArgsConstructor
@Size(min = 2, max = 50)

public class Enrollee {
    private int id;
    private String fullName;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthday;

    public Enrollee(EnrolleeEntity enrollee) {
        setId(enrollee.getId());
        setBirthday(enrollee.getBirthday());
        setFullName(enrollee.getFullName());
    }
    public String getDrString() {
        return birthday.getDay() + "." + birthday.getMonth() + "." + birthday.getYear();
    }

    public boolean notNull() {
        return fullName != null && birthday != null;
    }
}
