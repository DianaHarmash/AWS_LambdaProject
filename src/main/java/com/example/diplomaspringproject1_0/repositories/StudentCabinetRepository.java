package com.example.diplomaspringproject1_0.repositories;

import com.example.diplomaspringproject1_0.entity.StudentCabinet;
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
                 + "WHERE su.surname = :param1 and su.name = :param2", nativeQuery = true)
    Optional<StudentCabinet> findBySurnameAndName(@Param(value = "param1") String surname, @Param(value = "param2") String name);

    @Query(value = "UPDATE student_cabinet SET debt_balance = null, group_name = null WHERE speciality_id = ?1", nativeQuery = true)
    Optional<StudentCabinet> removeSpecialityInformationWhenRemovingSpeciality(Long id);
}
