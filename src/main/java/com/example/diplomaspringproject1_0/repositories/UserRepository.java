package com.example.diplomaspringproject1_0.repositories;

import com.example.diplomaspringproject1_0.entity.SystemUser;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<SystemUser, Long> {
}
