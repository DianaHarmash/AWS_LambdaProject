package com.example.diplomaspringproject.controllers;

import com.example.diplomaspringproject.dto.BalanceDto;
import com.example.diplomaspringproject.exceptions.UserException;
import com.example.diplomaspringproject.service.BalanceService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/balance")
public class BalanceController {

    private final BalanceService balanceService;
    @PostMapping()
    public ResponseEntity<BalanceDto> manageBalance(@RequestBody BalanceDto balanceDto) throws UserException {
        return new ResponseEntity<>(balanceService.manageBalance(balanceDto),
                                    HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<List<BalanceDto>> getBalance() {
        return new ResponseEntity<>(balanceService.getBalance(), HttpStatus.OK);
    }
}
