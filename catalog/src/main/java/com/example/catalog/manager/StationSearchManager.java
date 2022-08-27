package com.example.catalog.manager;


import com.example.catalog.dto.StationResponseDTO;
import com.example.catalog.dto.StationSearchResponseDTO;
import com.example.catalog.entity.StationEntity;
import com.example.catalog.repository.StationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@Transactional
@RequiredArgsConstructor
public class StationSearchManager {
    private final StationRepository stationRepository;

    private final Function<StationEntity, StationSearchResponseDTO> stationEntityStationSearchResponseDTOFunction = entity -> new StationSearchResponseDTO(
            entity.getId(),
            entity.getName()
            //entity.getServices().stream().map(o -> new StationSearchResponseDTO.(
               //     o.getId(),
                //    o.getName())).collect(Collectors.toList())

    );
    public List<StationSearchResponseDTO> search(String query, String language) {
        return stationRepository.search(query,language).stream()
                .map(stationEntityStationSearchResponseDTOFunction)
                .collect(Collectors.toList());


    }
}
