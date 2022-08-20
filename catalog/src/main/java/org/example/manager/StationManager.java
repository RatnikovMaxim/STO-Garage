package org.example.manager;

import lombok.RequiredArgsConstructor;
import org.example.repository.ServiceRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
@RequiredArgsConstructor
public class StationManager {
    private final ServiceRepository serviceRepository;


}
