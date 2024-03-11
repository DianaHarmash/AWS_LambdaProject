package com.example.diplomaspringproject.repositories;

import com.example.diplomaspringproject.entity.StudentCabinet;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface StudentCabinetRepository extends CrudRepository<StudentCabinet, Long> {

    @Query(value = "SELECT * FROM student_cabinet WHERE user_id = ?1", nativeQuery = true)
    Optional<StudentCabinet> findByUserId(Long userId);

    @Query(value = "SELECT student_cabinet.id, "
                 + "student_cabinet.group_name, "
                 + "student_cabinet.debt_balance, "
                 + "student_cabinet.year, "
                 + "student_cabinet.speciality_id, "
                 + "student_cabinet.user_id "
                 + "FROM student_cabinet "
                 + "JOIN system_user su on su.id = student_cabinet.user_id "
                 + "WHERE su.email = :email", nativeQuery = true)
    Optional<StudentCabinet> findByEmail(@Param(value = "email") String email);

    @Query(value = "UPDATE student_cabinet SET debt_balance = null, group_name = null WHERE speciality_id = ?1", nativeQuery = true)
    Optional<StudentCabinet> removeSpecialityInformationWhenRemovingSpeciality(Long id);
}
