package com.vaddya.schedule.core.utils;

import com.vaddya.schedule.core.schedule.ScheduleWeek;

/**
 * Перечисление типов недели
 *
 * @author vaddya
 * @see ScheduleWeek
 */
public enum TypeOfWeek {

    /**
     * Обе недели
     */
    BOTH,

    /**
     * Нечетная неделя
     */
    ODD,

    /**
     * Четная неделя
     */
    EVEN;

    public TypeOfWeek opposite() {
        return equals(ODD) ? EVEN : ODD;
    }

}
