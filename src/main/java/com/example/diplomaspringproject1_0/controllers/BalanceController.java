package com.example.diplomaspringproject1_0.controllers;

import com.example.diplomaspringproject1_0.dto.BalanceDto;
import com.example.diplomaspringproject1_0.exceptions.UserException;
import com.example.diplomaspringproject1_0.service.BalanceService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
