package com.example.diplomaspringproject.repositories;

import com.example.diplomaspringproject.entity.SystemUser;
import com.example.diplomaspringproject.entity.enums.Rights;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface SystemUserRepository extends CrudRepository<SystemUser, Long> {
    Optional<SystemUser> findSystemUserByEmail(String email);

    Optional<SystemUser> findSystemUserByRights(Rights rights);
}
