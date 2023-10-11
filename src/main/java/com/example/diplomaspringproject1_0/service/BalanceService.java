package com.example.diplomaspringproject1_0.service;

import com.example.diplomaspringproject1_0.dto.BalanceDto;
import com.example.diplomaspringproject1_0.entity.Balance;
import com.example.diplomaspringproject1_0.exceptions.UserException;
import com.example.diplomaspringproject1_0.mappers.BalanceMapping;
import com.example.diplomaspringproject1_0.repositories.BalanceRepository;
import com.example.diplomaspringproject1_0.validators.BalanceValidators;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BalanceService {
    private final BalanceMapping balanceMapping;
    private final BalanceRepository balanceRepository;
    private final BalanceValidators balanceValidators;
    @Autowired
    public BalanceService(BalanceMapping balanceMapping,
                          BalanceRepository balanceRepository,
                          BalanceValidators balanceValidators) {
        this.balanceMapping = balanceMapping;
        this.balanceRepository = balanceRepository;
        this.balanceValidators = balanceValidators;
    }

    public BalanceDto manageBalance(Long userId, BalanceDto balanceDto) throws UserException {

        // TODO: add validation for userId

        Optional<Balance> previousBalanceInDB = balanceRepository.findTopByOrderByIdDesc();
        if (!previousBalanceInDB.isPresent()) {
            balanceValidators.validateFirstRecord(balanceDto);
        } else {
            balanceDto.setBalance(previousBalanceInDB.get().getBalance());
        }

        balanceValidators.validate(balanceDto);

        Balance balance = balanceMapping.balanceDtoToBalance(balanceDto);
        Balance savedBalance = balanceRepository.save(balance);
        BalanceDto savedBalanceDto = balanceMapping.balanceToBalanceDto(savedBalance);

        return savedBalanceDto;
    }
}
