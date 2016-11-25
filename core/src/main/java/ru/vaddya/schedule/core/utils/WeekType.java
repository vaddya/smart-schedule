package ru.vaddya.schedule.core.utils;

import ru.vaddya.schedule.core.lessons.StudyWeek;

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

    public WeekType next() {
        return equals(ODD) ? EVEN : ODD;
    }

}
