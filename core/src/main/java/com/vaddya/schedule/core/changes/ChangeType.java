package com.vaddya.schedule.core.changes;

/**
 * Перечисление изменений в расписании
 *
 * @author vaddya
 */
public enum ChangeType {

    /**
     * Дополнительный урок
     */
    ADD,

    /**
     * Измененный урок
     */
    UPDATE,

    /**
     * Отмененный урок
     */
    REMOVE

}