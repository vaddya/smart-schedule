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
     * Нечетная неделя
     */
    ODD,

    /**
     * Четная неделя
     */
    EVEN,

    /**
     * Обе недели
     */
    BOTH;

    public TypeOfWeek opposite() {
        return equals(ODD) ? EVEN : ODD;
    }

}
