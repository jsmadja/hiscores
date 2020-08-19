package com.shmup.hiscores.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Deprecated
@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class Platform extends BaseModel<Platform> {

    private String name;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "game_id")
    private Game game;

}
