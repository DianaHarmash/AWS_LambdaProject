package com.example.diplomaspringproject1_0.controllers;

import com.example.diplomaspringproject1_0.dto.BalanceDto;
import com.example.diplomaspringproject1_0.exceptions.UserException;
import com.example.diplomaspringproject1_0.service.BalanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/{userId}/balance")
public class BalanceController {

    private final BalanceService balanceService;
    @PostMapping()
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<BalanceDto> manageBalance(@PathVariable Long userId,
                                                    @RequestBody BalanceDto balanceDto) throws UserException {
        return new ResponseEntity<>(balanceService.manageBalance(userId, balanceDto),
                                    HttpStatus.OK);
    }
}
