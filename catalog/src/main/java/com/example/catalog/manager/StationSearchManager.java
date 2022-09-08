package com.example.catalog.manager;


import com.example.catalog.dto.InvalidRequestExceptionDTO;
import com.example.catalog.dto.StationResponseDTO;
import com.example.catalog.dto.StationSearchResponseDTO;
import com.example.catalog.entity.StationEntity;
import com.example.catalog.repository.StationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@Transactional
@RequiredArgsConstructor
@Slf4j
public class StationSearchManager {
    private final StationRepository stationRepository;

    private final Function<StationEntity, StationSearchResponseDTO> stationEntityStationSearchResponseDTOFunction = entity -> new StationSearchResponseDTO(
            entity.getId(),
            entity.getName()
    );
    public  List<StationSearchResponseDTO> search(String query, String language) throws InvalidRequestExceptionDTO {
        log.info("Поиск СТО в каталоге по названию");

            return stationRepository.search(query, language).stream()
                    .map(stationEntityStationSearchResponseDTOFunction)
                    .collect(Collectors.toList());

    }
}
