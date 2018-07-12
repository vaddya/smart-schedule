package com.vaddya.schedule.dynamo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class Utils {

    @SafeVarargs
    static <T> List<T> listOf(T... ts) {
        return new ArrayList<T>() {{
            this.addAll(Arrays.asList(ts));
        }};
    }
}
