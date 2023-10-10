package com.example.diplomaspringproject1_0.repositories;

import com.example.diplomaspringproject1_0.entity.Balance;
import com.example.diplomaspringproject1_0.entity.StudentCabinet;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface BalanceRepository extends CrudRepository<Balance, Long> {

    Optional<Balance> findTopByOrderByIdDesc();
}
