package com.example.diplomaspringproject.service;

import com.example.diplomaspringproject.dto.BalanceDto;
import com.example.diplomaspringproject.entity.Balance;
import com.example.diplomaspringproject.entity.enums.Entities;
import com.example.diplomaspringproject.exceptions.UserException;
import com.example.diplomaspringproject.facades.Validators;
import com.example.diplomaspringproject.mappers.BalanceMapping;
import com.example.diplomaspringproject.repositories.BalanceRepository;
import jakarta.transaction.Transactional;

import java.util.List;

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

    public List<BalanceDto> getBalance() {
        List<Balance> balance = (List<Balance>) balanceRepository.findAll();
        List<BalanceDto> balanceDto = balance.stream()
                                             .map(balanceMapping::balanceToBalanceDto)
                                             .toList();

        return balanceDto;
    }
}
