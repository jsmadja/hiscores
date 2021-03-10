package com.shmup.hiscores.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@Data
@Builder
@NoArgsConstructor
public class PlayerForm {

    @NotBlank(message = "name is mandatory")
    private String name;

    @NotNull(message = "shmupUserId is mandatory")
    private Long shmupUserId;

}
