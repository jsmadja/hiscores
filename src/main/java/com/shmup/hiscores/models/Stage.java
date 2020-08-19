package com.shmup.hiscores.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.LongNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Deprecated
@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class Stage extends BaseModel<Stage> {

    private String name;

    @JsonIgnore
    private Long sortOrder;

    @JsonIgnore
    @ManyToOne
    private Game game;

}
