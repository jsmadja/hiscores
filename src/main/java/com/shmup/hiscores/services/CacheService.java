package com.shmup.hiscores.services;

import com.shmup.hiscores.models.Game;
import com.shmup.hiscores.models.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CacheService {

    public static final String MEDALS_PICTURES_CACHE = "medalsPictures";
    public static final String RANKING_PICTURES_CACHE = "rankingPictures";
    public static final String SIGNATURE_PICTURES_CACHE = "signaturePictures";
    public static final String VERSUS_PICTURES_CACHE = "versusPictures";

    @Autowired
    private CacheManager cacheManager;

    public void setRankingPictureOf(Game game, byte[] bytes) {
        getRankingPicturesCache().put(game.getId(), bytes);
    }

    public void setSignaturePictureOf(Player player, byte[] bytes) {
        getSignaturePicturesCache().put(player.getId(), bytes);
    }

    public void setVersusPictureOf(Player player, byte[] bytes) {
        getVersusPicturesCache().put(player.getId(), bytes);
    }

    public void setMedalsPictureOf(Long shmupUserId, byte[] bytes) {
        getMedalsPicturesCache().put(shmupUserId, bytes);
    }

    public Optional<byte[]> getRankingPictureOf(Game game) {
        return toBytes(getRankingPicturesCache().get(game.getId()));
    }

    public Optional<byte[]> getSignaturePictureOf(Player player) {
        return toBytes(getSignaturePicturesCache().get(player.getId()));
    }

    public Optional<byte[]> getMedalsPictureOf(Long shmupUserId) {
        return toBytes(getMedalsPicturesCache().get(shmupUserId));
    }

    public Optional<byte[]> getVersusPictureOf(Player player) {
        return toBytes(getVersusPicturesCache().get(player.getId()));
    }

    public void removeRankingPictureOf(Game game) {
        getRankingPicturesCache().evict(game.getId());
    }

    public void removeSignaturePictureOf(Player player) {
        getSignaturePicturesCache().evict(player.getId());
    }

    public void removeMedalsPictureOf(Long shmupUserId) {
        getMedalsPicturesCache().evict(shmupUserId);
    }

    private Optional<byte[]> toBytes(Cache.ValueWrapper valueWrapper) {
        byte[] bytes = valueWrapper == null ? null : (byte[]) valueWrapper.get();
        return Optional.ofNullable(bytes);
    }

    private Cache getMedalsPicturesCache() {
        return cacheManager.getCache(MEDALS_PICTURES_CACHE);
    }

    private Cache getRankingPicturesCache() {
        return cacheManager.getCache(RANKING_PICTURES_CACHE);
    }

    private Cache getSignaturePicturesCache() {
        return cacheManager.getCache(SIGNATURE_PICTURES_CACHE);
    }

    private Cache getVersusPicturesCache() {
        return cacheManager.getCache(VERSUS_PICTURES_CACHE);
    }

}
