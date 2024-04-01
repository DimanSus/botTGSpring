package com.example.bottg.repos;

import com.example.bottg.helpers.DoctorEnum;
import com.example.bottg.models.BookModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepo extends JpaRepository<BookModel, Long> {
    List<BookModel> findBookModelByDoctorEnum(DoctorEnum d);
}
