package com.example.diplomaspringproject1_0.repositories;

import com.example.diplomaspringproject1_0.entity.SystemUser;
import com.example.diplomaspringproject1_0.entity.enums.Rights;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface SystemUserRepository extends CrudRepository<SystemUser, Long> {
    Optional<SystemUser> findSystemUserByEmail(String email);

    Optional<SystemUser> findSystemUserByRights(Rights rights);
}
