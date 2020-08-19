package com.shmup.hiscores.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Deprecated
@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class Difficulty extends BaseModel<Difficulty> {

    private String name;

    @JsonIgnore
    private Long sortOrder;

    @JsonIgnore
    @ManyToOne
    private Game game;

}
