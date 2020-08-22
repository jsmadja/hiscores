package com.shmup.hiscores.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@AllArgsConstructor
@Data
@Builder
@NoArgsConstructor
public class GameForm {

    @NotBlank(message = "title is mandatory")
    private String title;

    @NotBlank(message = "cover is mandatory")
    private String cover;

    @NotBlank(message = "thread is mandatory")
    private String thread;

    @NotEmpty(message = "platforms is mandatory")
    private String[] platforms;

    private String[] ships;

    private String[] modes;

    private String[] difficulties;

    private String[] stages;

}
