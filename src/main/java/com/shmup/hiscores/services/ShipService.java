package com.shmup.hiscores.services;

import com.shmup.hiscores.models.Ship;
import com.shmup.hiscores.repositories.ShipRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Deprecated
@Service
@AllArgsConstructor
public class ShipService {

    private ShipRepository shipRepository;

    public Ship findById(Long shipId) {
        return shipRepository.findById(shipId).get();
    }
}
