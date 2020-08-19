package com.shmup.hiscores.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Deprecated
@AllArgsConstructor
@Data
public class ScoreForm {
    private String value;
    private Integer minutes;
    private Integer seconds;
    private Integer milliseconds;
    private Long platform;
    private Long game;
    private String comment;
    private String replay;
    private MultipartFile photo;
    private MultipartFile inp;
    private Long difficulty;
    private Long ship;
    private Long mode;
    private Long stage;
}
