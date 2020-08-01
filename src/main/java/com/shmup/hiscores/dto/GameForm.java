package com.shmup.hiscores.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class GameForm {
    private String title;
    private String cover;
    private String thread;
    private String[] platforms;
    private String[] ships;
    private String[] modes;
    private String[] difficulties;
    private String[] stages;
}
