package com.shmup.hiscores.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Deprecated
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
public class Event extends BaseModel<Event> {

    private String name;
    private String description;

    @JsonIgnore
    @JoinColumn(name = "game_id")
    @OneToOne(cascade = CascadeType.ALL)
    private Game game;
}
