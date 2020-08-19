package com.shmup.hiscores.services;

import com.shmup.hiscores.models.Mode;
import com.shmup.hiscores.models.Ship;
import com.shmup.hiscores.repositories.ModeRepository;
import com.shmup.hiscores.repositories.ShipRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Deprecated
@Service
@AllArgsConstructor
public class ModeService {

    private ModeRepository modeRepository;

    public Mode findById(Long modeId) {
        return modeRepository.findById(modeId).get();
    }
}
