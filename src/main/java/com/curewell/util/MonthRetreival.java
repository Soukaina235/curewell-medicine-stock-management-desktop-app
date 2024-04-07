package com.curewell.util;

import java.util.Calendar;

public class MonthRetreival {
    public static int retreiveMonth() {
        Calendar calendar = Calendar.getInstance();
        int currentMonth = calendar.get(Calendar.MONTH) + 1; // January is 0, so add 1
        return currentMonth;
    }

}