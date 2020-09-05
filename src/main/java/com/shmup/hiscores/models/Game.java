package com.shmup.hiscores.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.shmup.hiscores.dto.GameSetting;
import lombok.*;
import org.apache.commons.collections.map.MultiKeyMap;
import org.hibernate.annotations.Where;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Comparator.comparing;
import static javax.persistence.CascadeType.MERGE;
import static javax.persistence.CascadeType.PERSIST;

@EqualsAndHashCode(callSuper = true)
@Table(name = "game")
@Entity
@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class Game extends BaseModel<Game> {

    private String thread;

    private String cover;

    private String title;

    @JsonIgnore
    private boolean generalRanking;

    @JsonIgnore
    @OneToMany(mappedBy = "game")
    @Where(clause = "rank > 0")
    private List<Score> scores;

    @JsonIgnore
    @OneToMany(mappedBy = "game")
    private List<Score> allScores;

    @JsonIgnore
    @OneToMany(mappedBy = "game")
    @Where(clause = "onecc = true")
    private List<Score> oneccs;

    @OrderBy("name")
    @OneToMany(mappedBy = "game", cascade = {PERSIST, MERGE})
    private List<Platform> platforms;

    @OrderBy("sortOrder")
    @OneToMany(mappedBy = "game", cascade = {PERSIST, MERGE})
    private List<Difficulty> difficulties;

    @OrderBy("sortOrder")
    @OneToMany(mappedBy = "game", cascade = {PERSIST, MERGE})
    private List<Mode> modes;

    @OrderBy("sortOrder")
    @OneToMany(mappedBy = "game", cascade = {PERSIST, MERGE})
    private List<Ship> ships;

    @OrderBy("sortOrder")
    @OneToMany(mappedBy = "game", cascade = {PERSIST, MERGE})
    private List<Stage> stages;

    @Deprecated
    public String post() {
        return thread.replace("viewtopic.php?", "posting.php?mode=reply&f=20&");
    }

    @Override
    public String toString() {
        return title;
    }

    @Deprecated
    @JsonIgnore
    public String getEscapedTitle() {
        String s = title.replaceAll("[^a-zA-Z0-9]", "_");
        s = s.replaceAll("_(_)*", "_");
        if (s.endsWith("_")) {
            s = s.substring(0, s.length() - 1);
        }
        return s;
    }

    @Deprecated
    @JsonIgnore
    public String getCoverType() {
        if (cover.endsWith("jpg") || cover.endsWith("jpeg")) {
            return "image/jpeg";
        }
        if (cover.endsWith("png")) {
            return "image/png";
        }
        return "image/gif";
    }

    @Deprecated
    @JsonIgnore
    public Collection<Player> getPlayers() {
        Set<Player> players = new HashSet<Player>();
        for (Score score : scores) {
            players.add(score.getPlayer());
        }
        return players;
    }

    @Deprecated
    public boolean hasShip() {
        return ships != null && !ships.isEmpty();
    }

    @Deprecated
    public boolean hasDifficulties() {
        return difficulties != null && !difficulties.isEmpty();
    }

    @Deprecated
    public boolean hasModes() {
        return modes != null && !modes.isEmpty();
    }

    @Deprecated
    @JsonIgnore
    public int getOneCreditCount() {
        Collection<Score> oneCreditScores = allScores.stream().filter(Score::isOnecc).collect(Collectors.toList());
        Set<String> uniqueOneCreditScores = new HashSet<String>();
        for (Score oneCreditScore : oneCreditScores) {
            String player = oneCreditScore.getPlayer().getName();
            String mode = oneCreditScore.modeName();
            String difficulty = oneCreditScore.difficultyName();
            uniqueOneCreditScores.add(player + mode + difficulty);
        }
        return uniqueOneCreditScores.size();
    }

    @Deprecated
    public boolean hasTimerScores() {
        if (this.modes == null || this.modes.isEmpty()) {
            return false;
        }
        for (Mode mode : modes) {
            if (mode.isTimerScore()) {
                return true;
            }
        }
        return false;
    }

    @Deprecated
    @JsonIgnore
    public Collection<Score> getAllOneCCS() {
        MultiKeyMap map = new MultiKeyMap();
        for (Score onecc : this.oneccs) {
            Score score = (Score) map.get(onecc.getPlayer(), onecc.getMode(), onecc.getDifficulty());
            if (score == null) {
                map.put(onecc.getPlayer(), onecc.getMode(), onecc.getDifficulty(), onecc);
            } else if (score.isWorstThan(onecc)) {
                map.put(onecc.getPlayer(), onecc.getMode(), onecc.getDifficulty(), onecc);
            }
        }
        List<Score> scores = new ArrayList<Score>(map.values());
        scores.sort((o1, o2) -> o1.getPlayer().getName().compareToIgnoreCase(o2.getPlayer().getName()));
        return scores;
    }

    public Optional<Mode> getModeById(Long modeId) {
        return this.modes.stream().filter(mode -> mode.id.equals(modeId)).findFirst();
    }

    public void add(Mode mode) {
        this.modes.add(mode);
    }

    public void add(Difficulty difficulty) {
        this.difficulties.add(difficulty);
    }

    public void add(Stage stage) {
        this.stages.add(stage);
    }

    public void add(Ship ship) {
        this.ships.add(ship);
    }

    public void add(Platform platform) {
        this.platforms.add(platform);
    }

    public void addNewMode(GameSetting gameSetting) {
        Mode mode = Mode.builder()
                .game(this)
                .name(gameSetting.getValue())
                .sortOrder(SortableSetting.getNextSortOrder(gameSetting, this.modes))
                .build();
        this.add(mode);
        this.getModes().sort(comparing(Mode::getSortOrder));
    }

    public void addNewDifficulty(GameSetting gameSetting) {
        Difficulty difficulty = Difficulty.builder()
                .game(this)
                .name(gameSetting.getValue())
                .sortOrder(SortableSetting.getNextSortOrder(gameSetting, this.difficulties))
                .build();
        this.add(difficulty);
        this.getDifficulties().sort(comparing(Difficulty::getSortOrder));
    }

    public void addNewShip(GameSetting gameSetting) {
        Ship ship = Ship.builder()
                .game(this)
                .name(gameSetting.getValue())
                .sortOrder(SortableSetting.getNextSortOrder(gameSetting, this.ships))
                .build();
        this.add(ship);
        this.getShips().sort(comparing(Ship::getSortOrder));
    }

    public void addNewStage(GameSetting gameSetting) {
        Stage stage = Stage.builder()
                .game(this)
                .name(gameSetting.getValue())
                .sortOrder(SortableSetting.getNextSortOrder(gameSetting, this.stages))
                .build();
        this.add(stage);
        this.getStages().sort(comparing(Stage::getSortOrder));
    }

    public void addNewPlatforms(String[] platforms) {
        Arrays.stream(platforms)
                .map(platform -> Platform.builder().name(platform).game(this).build())
                .forEach(this::add);
        this.getPlatforms().sort(comparing(Platform::getName));
    }
}
