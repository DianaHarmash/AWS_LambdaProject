package com.example.diplomaspringproject1_0.repositories;

import com.example.diplomaspringproject1_0.entity.Speciality;
import com.example.diplomaspringproject1_0.entity.enums.SpecialityName;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface SpecialtyRepository extends CrudRepository<Speciality, Long> {

    Optional<Speciality> findBySpeciality(SpecialityName specialityName);

}
