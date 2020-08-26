package com.shmup.hiscores.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Mode extends BaseModel<Mode> implements SortableSetting {

    private String name;

    private Long sortOrder;

    @JsonIgnore
    @ManyToOne
    private Game game;

    private String scoreType;

    @JsonIgnore
    public boolean isTimerScore() {
        return "timer".equals(scoreType);
    }

}
