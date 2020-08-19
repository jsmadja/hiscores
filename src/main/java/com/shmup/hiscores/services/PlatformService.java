package com.shmup.hiscores.services;

import com.shmup.hiscores.dto.PlatformWithGameCount;
import com.shmup.hiscores.models.Platform;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Deprecated
@Service
@AllArgsConstructor
public class PlatformService {

    private PlatformRepository platformRepository;

    @Deprecated
    public Platform findById(Long platformId) {
        return platformRepository.findById(platformId).get();
    }

    public List<PlatformWithGameCount> findPlatformsWithGameCount() {
        return platformRepository.findPlatformsWithGameCount();
    }
}
