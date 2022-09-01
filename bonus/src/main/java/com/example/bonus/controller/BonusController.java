package com.example.bonus.controller;

import com.example.bonus.dto.BonusResponseDTO;
import com.example.bonus.manager.BonusManager;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequestMapping("/bonus")
@RequiredArgsConstructor
public class BonusController {
    private BonusManager manager;

    @GetMapping
    public List<BonusResponseDTO> getAll(
            @RequestAttribute final Authentication authentication,
            @RequestParam final long stationId, final long start, final long finish
    ) {
        final List<BonusResponseDTO> responseDTO = manager.getAll(authentication, stationId, start, finish);
        return responseDTO;
    }

}
