package com.example.diplomaspringproject.repositories;

import com.example.diplomaspringproject.entity.Balance;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface BalanceRepository extends CrudRepository<Balance, Long> {

    Optional<Balance> findTopByOrderByIdDesc();
}
