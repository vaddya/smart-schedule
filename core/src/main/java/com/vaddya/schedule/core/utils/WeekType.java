package com.vaddya.schedule.core.utils;

import com.vaddya.schedule.core.lessons.StudyWeek;

/**
 * Перечисление типов недели
 *
 * @author vaddya
 * @see StudyWeek
 */
public enum WeekType {

    /**
     * Нечетная неделя
     */
    ODD,

    /**
     * Четная неделя
     */
    EVEN;

    public WeekType opposite() {
        return equals(ODD) ? EVEN : ODD;
    }

}
