package com.shmup.hiscores.services;

import com.shmup.hiscores.models.Mode;
import com.shmup.hiscores.models.Player;
import com.shmup.hiscores.repositories.ModeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Deprecated
@Service
@AllArgsConstructor
public class ModeService {

    private final ModeRepository modeRepository;

    public Mode findById(Long modeId) {
        return modeRepository.findById(modeId).get();
    }

    public List<Mode> getUnplayedModesBy(Player player) {
        return modeRepository.findByPlayerNot(player.getId());
    }
}
