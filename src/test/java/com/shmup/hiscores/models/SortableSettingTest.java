package com.shmup.hiscores.models;

import com.shmup.hiscores.dto.GameSetting;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static com.shmup.hiscores.models.SortableSetting.INITIAL_SORT_ORDER;
import static com.shmup.hiscores.models.SortableSetting.SORT_ORDER_INCREMENT;
import static org.assertj.core.api.Assertions.assertThat;

class SortableSettingTest {

    @Test
    public void getNextSortOrder_should_return_initial_sort_order_when_after_value_is_null() {
        GameSetting newSetting = new GameSetting("Arcade", null);
        List<Mode> modes = new ArrayList<>();
        Long sortOrder = SortableSetting.getNextSortOrder(newSetting, modes);
        assertThat(sortOrder).isEqualTo(INITIAL_SORT_ORDER);
    }

    @Test
    public void getNextSortOrder_should_return_sort_order_after_last_mode_when_after_value_is_null() {
        GameSetting newSetting = new GameSetting("Arcade", null);
        List<Mode> modes = List.of(Mode.builder().sortOrder(10L).build());
        Long sortOrder = SortableSetting.getNextSortOrder(newSetting, modes);
        assertThat(sortOrder).isEqualTo(10L + SORT_ORDER_INCREMENT);
    }

    @Test
    public void getNextSortOrder_should_return_sort_order_after_wanted_mode() {
        Mode mode1 = Mode.builder().sortOrder(10L).build();
        mode1.setId(3L);

        Mode mode2 = Mode.builder().sortOrder(20L).build();
        mode2.setId(4L);

        GameSetting newSetting = new GameSetting("Arcade", 3L);
        List<Mode> modes = List.of(mode1, mode2);

        Long sortOrder = SortableSetting.getNextSortOrder(newSetting, modes);

        assertThat(sortOrder).isEqualTo(10L + SORT_ORDER_INCREMENT);
    }

    @Test
    public void getNextSortOrder_should_return_sort_order_after_not_found_wanted_mode() {
        Mode mode1 = Mode.builder().sortOrder(10L).build();
        mode1.setId(3L);

        Mode mode2 = Mode.builder().sortOrder(20L).build();
        mode2.setId(4L);

        GameSetting newSetting = new GameSetting("Arcade", 5L);
        List<Mode> modes = List.of(mode1, mode2);

        Long sortOrder = SortableSetting.getNextSortOrder(newSetting, modes);

        assertThat(sortOrder).isEqualTo(20L + SORT_ORDER_INCREMENT);
    }

    @Test
    public void getNextSortOrder_should_return_initial_sort_order_when_after_value_is_set_and_modes_is_empty() {
        GameSetting newSetting = new GameSetting("Arcade", 5L);
        List<Mode> modes = new ArrayList<>();

        Long sortOrder = SortableSetting.getNextSortOrder(newSetting, modes);

        assertThat(sortOrder).isEqualTo(INITIAL_SORT_ORDER);
    }

}