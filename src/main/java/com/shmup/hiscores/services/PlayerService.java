package com.shmup.hiscores.services;

import com.shmup.hiscores.drawer.MedalsPicture;
import com.shmup.hiscores.models.Player;
import com.shmup.hiscores.models.Score;
import com.shmup.hiscores.models.Versus;
import com.shmup.hiscores.repositories.PlayerCustomRepository;
import com.shmup.hiscores.repositories.PlayerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

@Deprecated
@Service
@AllArgsConstructor
public class PlayerService {

    private PlayerRepository playerRepository;
    private PlayerCustomRepository playerCustomRepository;

    public Player findByShmupUserId(Long shmupUserId) {
        return playerRepository.findByShmupUserId(shmupUserId);
    }

    public Player findOrCreatePlayer(String name, Long shmupUserId) {
        Player player = playerRepository.findByName(name);
        if (player == null) {
            player = new Player(name);
            player.setShmupUserId(shmupUserId);
            player.setVip(false);
            playerRepository.save(player);
        }
        return player;
    }

    public void update(Player player) {
        playerRepository.save(player);
    }

    public Versus getBestVersus(Player player) {
        List<Player> all = playerCustomRepository.findAllJoinScores();
        all.remove(player);
        Versus bestVersus = null;
        for (Player opponent : all) {
            Versus versus = player.getComparisonWith(opponent);
            if (bestVersus == null || bestVersus.loseCount() < versus.loseCount()) {
                bestVersus = versus;
            }
        }
        return bestVersus;
    }

    public byte[] createMedalsFor(Player player) throws IOException {
        byte[] bytes = MedalsPicture.blankImage;
        if (player != null && !player.isHideMedals()) {
            int firstRankCount = playerCustomRepository.getRankCount(player, 1);
            int secondRankCount = playerCustomRepository.getRankCount(player, 2);
            int thirdRankCount = playerCustomRepository.getRankCount(player, 3);
            int oneCreditCount = oneccs(player).size();
            int gameCount = playerCustomRepository.getGameCount(player);
            bytes = MedalsPicture.createMedalsPicture(firstRankCount, secondRankCount, thirdRankCount, oneCreditCount, gameCount);
        }
        return bytes;
    }

    public Collection<Score> oneccs(Player player) {
        return playerCustomRepository.findOneCreditScores(player);
    }

    public Player findOrCreatePlayer(String login) {
        return findOrCreatePlayer(login, null);
    }

    public List<Player> findAll() {
        return this.playerRepository.findAll();
    }

    public long getPlayersCount() {
        return this.playerRepository.count();
    }
}
