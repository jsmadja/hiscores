package com.shmup.hiscores.services;

import com.shmup.hiscores.dto.PlatformWithGameCount;
import com.shmup.hiscores.models.Game;
import com.shmup.hiscores.models.Platform;
import com.shmup.hiscores.repositories.PlatformCustomRepository;
import com.shmup.hiscores.repositories.PlatformRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PlatformService {

    private PlatformRepository platformRepository;
    private PlatformCustomRepository platformCustomRepository;

    @Deprecated
    public Platform findById(Long platformId) {
        return platformRepository.findById(platformId).get();
    }

    public List<PlatformWithGameCount> findPlatformsWithGameCount() {
        return platformRepository.findPlatformsWithGameCount();
    }

    public List<Game> findGamesByPlatform(String platformTitle) {
        return platformCustomRepository.findGamesByPlatform(platformTitle);
    }
}
