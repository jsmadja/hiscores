package com.shmup.hiscores.services;

import com.shmup.hiscores.models.Platform;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PlatformService {

    private PlatformRepository platformRepository;

    public Platform findById(Long platformId) {
        return platformRepository.findById(platformId).get();
    }

    public List<String> findAll() {
        return platformRepository.findDistinctByName();
    }
}
