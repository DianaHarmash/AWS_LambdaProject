package com.example.diplomaspringproject1_0.service;

import com.example.diplomaspringproject1_0.dto.BalanceDto;
import com.example.diplomaspringproject1_0.entity.Balance;
import com.example.diplomaspringproject1_0.exceptions.UserException;
import com.example.diplomaspringproject1_0.mappers.BalanceMapping;
import com.example.diplomaspringproject1_0.repositories.BalanceRepository;
import com.example.diplomaspringproject1_0.validators.BalanceValidators;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BalanceService {
    private final BalanceMapping balanceMapping;
    private final BalanceRepository balanceRepository;
    private final BalanceValidators balanceValidators;
//    private final UserService userService;
    @Autowired
    public BalanceService(BalanceMapping balanceMapping,
                          BalanceRepository balanceRepository,
                          BalanceValidators balanceValidators
                          /*UserService userService*/) {
        this.balanceMapping = balanceMapping;
        this.balanceRepository = balanceRepository;
        this.balanceValidators = balanceValidators;
//        this.userService = userService;
    }

    @Transactional
    public BalanceDto manageBalance(BalanceDto balanceDto) throws UserException {

//        userService.checkAdminRights(userId);

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
