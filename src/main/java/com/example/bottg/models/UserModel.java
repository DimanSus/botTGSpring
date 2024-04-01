package com.example.bottg.models;

import com.example.bottg.helpers.DoctorEnum;
import jakarta.persistence.*;
import lombok.Data;

@Table(name = "telegram_user")
@Entity
@Data
public class UserModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @Column(name = "username")
    String username;

    @Column(name = "telegram_id")
    String tgId;

    @Column(name = "person_name")
    String person_name;

    @Column(name = "wanted_doc")
    @Enumerated
    DoctorEnum doctorEnum;
}