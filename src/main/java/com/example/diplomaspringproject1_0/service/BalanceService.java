package com.example.diplomaspringproject1_0.service;

import com.example.diplomaspringproject1_0.dto.BalanceDto;
import com.example.diplomaspringproject1_0.entity.Balance;
import com.example.diplomaspringproject1_0.entity.enums.Entities;
import com.example.diplomaspringproject1_0.exceptions.UserException;
import com.example.diplomaspringproject1_0.facades.Validators;
import com.example.diplomaspringproject1_0.mappers.BalanceMapping;
import com.example.diplomaspringproject1_0.repositories.BalanceRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BalanceService {
    private final BalanceMapping balanceMapping;
    private final BalanceRepository balanceRepository;
    private final Validators validators;
    @Autowired
    public BalanceService(BalanceMapping balanceMapping,
                          BalanceRepository balanceRepository,
                          Validators validators) {
        this.balanceMapping = balanceMapping;
        this.balanceRepository = balanceRepository;
        this.validators = validators;
    }
    @Transactional
    public BalanceDto manageBalance(BalanceDto balanceDto) throws UserException {
        Optional<Balance> previousBalanceInDB = balanceRepository.findTopByOrderByIdDesc();

        if (!previousBalanceInDB.isPresent()) {
            validators.getValidators(Entities.BALANCE_FIRST_RECORD).validate(balanceDto);
        } else {
            balanceDto.setBalance(previousBalanceInDB.get().getBalance());
        }

        validators.getValidators(Entities.BALANCE).validate(balanceDto);

        Balance balance = balanceMapping.balanceDtoToBalance(balanceDto);
        Balance savedBalance = balanceRepository.save(balance);
        BalanceDto savedBalanceDto = balanceMapping.balanceToBalanceDto(savedBalance);

        return savedBalanceDto;
    }
}
