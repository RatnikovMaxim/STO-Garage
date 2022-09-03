package com.example.bonus.manager;

import com.example.bonus.dto.BonusResponseDTO;
import com.example.bonus.repository.BonusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@Transactional
@RequiredArgsConstructor
public class BonusManager {
    private BonusRepository bonusRepository;

    public List<BonusResponseDTO> getAll() {
        return
    }
}