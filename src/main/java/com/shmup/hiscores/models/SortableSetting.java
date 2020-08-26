package com.shmup.hiscores.models;

import com.shmup.hiscores.dto.GameSetting;

import java.util.List;

public interface SortableSetting {
    long SORT_ORDER_INCREMENT = 10L;
    long INITIAL_SORT_ORDER = 1L;

    static Long getNextSortOrder(GameSetting newSetting, List<? extends SortableSetting> settings) {
        if (settings.isEmpty()) {
            return INITIAL_SORT_ORDER;
        }
        if (newSetting.getAfterValue() == null) {
            return getNextSortOrderOf(getLastOf(settings));
        }
        return settings.stream()
                .filter(setting -> setting.getId().equals(newSetting.getAfterValue()))
                .findFirst()
                .map(SortableSetting::getNextSortOrderOf)
                .orElseGet(() -> getNextSortOrderOf(getLastOf(settings)));
    }

    static long getNextSortOrderOf(SortableSetting lastOf) {
        return lastOf.getSortOrder() + SORT_ORDER_INCREMENT;
    }

    static SortableSetting getLastOf(List<? extends SortableSetting> settings) {
        return settings.get(settings.size() - 1);
    }

    Long getSortOrder();

    Long getId();
}
