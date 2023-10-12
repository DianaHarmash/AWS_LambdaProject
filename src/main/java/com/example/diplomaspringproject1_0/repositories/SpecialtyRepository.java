package com.example.diplomaspringproject1_0.repositories;

import com.example.diplomaspringproject1_0.entity.Speciality;
import com.example.diplomaspringproject1_0.entity.enums.SpecialityName;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface SpecialtyRepository extends CrudRepository<Speciality, Long> {

    Optional<Speciality> findBySpeciality(SpecialityName specialityName);

    @Query(value = "DELETE FROM speciality WHERE speciality.speciality = :param1", nativeQuery = true)
    void deleteBySpeciality(@Param(value = "param1") String speciality);

}
