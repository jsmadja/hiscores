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
public class Difficulty extends BaseModel<Difficulty> {

    private String name;

    @JsonIgnore
    private Long sortOrder;

    @JsonIgnore
    @ManyToOne
    private Game game;

}
