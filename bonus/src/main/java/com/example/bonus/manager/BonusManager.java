package com.example.bonus.manager;

import com.example.bonus.repository.BonusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
@RequiredArgsConstructor
public class BonusManager {
    private BonusRepository bonusRepository;


}
