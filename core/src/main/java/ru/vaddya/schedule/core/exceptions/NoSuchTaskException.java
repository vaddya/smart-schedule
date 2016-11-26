package ru.vaddya.schedule.core.exceptions;

import java.util.NoSuchElementException;

/**
 * Бросается при отсутствии запрашиваемой задачи
 *
 * @author vaddya
 */
public class NoSuchTaskException extends NoSuchElementException {
    public NoSuchTaskException(String s) {
        super(s);
    }
}
