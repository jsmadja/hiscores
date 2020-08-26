package com.shmup.hiscores.models;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ModeTest {

    @Test
    void isTimerScore_is_true_when_mode_has_timer_score_type() {
        Mode mode = Mode.builder().scoreType("timer").build();
        assertThat(mode.isTimerScore()).isTrue();
    }

    @Test
    void isTimerScore_is_false_when_mode_is_not_timer_score_type() {
        Mode mode = Mode.builder().build();
        assertThat(mode.isTimerScore()).isFalse();
    }
}