package com.vaddya.schedule.core;

import com.vaddya.schedule.core.utils.TypeOfWeek;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * com.vaddya.schedule.core at smart-schedule
 *
 * @author vaddya
 * @since April 11, 2017
 */
public class TypeOfWeekTest {

    @Test
    public void testOpposite() throws Exception {
        TypeOfWeek source = TypeOfWeek.ODD;
        TypeOfWeek expected = TypeOfWeek.EVEN;
        assertEquals(expected, source.opposite());
    }

}