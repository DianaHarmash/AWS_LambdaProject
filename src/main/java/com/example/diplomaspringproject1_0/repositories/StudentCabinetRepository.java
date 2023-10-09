package com.example.diplomaspringproject1_0.repositories;

import com.example.diplomaspringproject1_0.entity.StudentCabinet;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface StudentCabinetRepository extends CrudRepository<StudentCabinet, Long> {

    @Query(value = "SELECT * FROM student_cabinet WHERE user_id = ?1", nativeQuery = true)
    Optional<StudentCabinet> findByUserId(Long userId);
}
