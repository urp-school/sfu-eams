package com.ekingstar.eams.system.time;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Vector;
import org.apache.commons.lang.StringUtils;
import com.ekingstar.eams.system.i18n.MessageResources;

public class TimeUnitUtil {
    
    public TimeUnitUtil() {
        
    }
    
    public static Vector digests(String weekOccupyStr, int from, int startWeek, int endWeek) {
        if (null == weekOccupyStr || weekOccupyStr.indexOf('1') == -1) {
            return null;
        }
        Vector occupyWeeks = new Vector();
        int initLength = weekOccupyStr.length();
        StringBuffer weekOccupy = new StringBuffer();
        if (from > 1) {
            String before = weekOccupyStr.substring(0, from - 1);
            weekOccupyStr = weekOccupyStr + before;
        }
        weekOccupy.append(StringUtils.repeat("0", (from + startWeek) - 2));
        weekOccupy.append(weekOccupyStr.substring((from + startWeek) - 2, (from + endWeek) - 1));
        weekOccupy.append(StringUtils.repeat("0", initLength - weekOccupy.length()));
        weekOccupy.append("000");
        if (weekOccupy.indexOf("1") == -1) {
            return occupyWeeks;
        }
        int start;
        for (start = 0; '1' != weekOccupy.charAt(start); start++) {
            ;
        }
        for (int i = start + 1; i < weekOccupy.length(); i = start) {
            char post = weekOccupy.charAt(start + 1);
            if (post == '0') {
                start = digestOdd(occupyWeeks, weekOccupy, from, start);
            }
            if (post == '1') {
                start = digestContinue(occupyWeeks, weekOccupy, from, start);
            }
            for (; start < weekOccupy.length() && '1' != weekOccupy.charAt(start); start++) {
                ;
            }
        }
        
        return occupyWeeks;
        
    }
    
    private static int digestOdd(Vector occupyWeeks, StringBuffer weekOccupy, int from, int start) {
        int cycle = 0;
        if (((start - from) + 2) % 2 == 0) {
            cycle = 3;
        } else {
            
            cycle = 2;
        }
        int i;
        for (i = start + 2; i < weekOccupy.length(); i += 2) {
            if (weekOccupy.charAt(i) == '1') {
                if (weekOccupy.charAt(i + 1) == '1') {
                    occupyWeeks.add(new WeekUnit(cycle, (start - from) + 2, (i - 2 - from) + 2));
                    return i;
                }
            } else {
                if (i - 2 == start)
                    cycle = 1;
                occupyWeeks.add(new WeekUnit(cycle, (start - from) + 2, (i - 2 - from) + 2));
                return i + 1;
            }
        }
        return i;
        
    }
    
    private static int digestContinue(Vector occupyWeeks, StringBuffer weekOccupy, int from,
            int start) {
        int cycle = 1;
        int i;
        for (i = start + 2; i < weekOccupy.length(); i += 2)
            if (weekOccupy.charAt(i) == '1') {
                if (weekOccupy.charAt(i + 1) != '1') {
                    occupyWeeks.add(new WeekUnit(cycle, (start - from) + 2, (i - from) + 2));
                    return i + 2;
                }
            } else {
                occupyWeeks.add(new WeekUnit(cycle, (start - from) + 2, (i - 1 - from) + 2));
                return i + 1;
            }
        return i;
    }
    
    public static String digest(String weekOccupyStr, int from, int startWeek, int endWeek,
            MessageResources resourses, Locale locale, String format) {
        Vector weekUnitVector = digests(weekOccupyStr, from, startWeek, endWeek);
        boolean needI18N = false;
        String weekRegular[] = { "", "", "\u5355", "\u53CC", "" };
        String weekRegularKeys[] = { "", "week.continuely", "week.odd", "week.even", "week.random" };
        if (null != resourses && null != locale) {
            needI18N = true;
        }
        if (null != weekUnitVector && !weekUnitVector.isEmpty()) {
            StringBuffer weekUnits = new StringBuffer();
            for (Iterator iter = weekUnitVector.iterator(); iter.hasNext(); weekUnits.append(format.charAt(3))) {
                WeekUnit weekUnit = (WeekUnit) iter.next();
                if (weekUnit.getStart() == weekUnit.getEnd()) {
                    weekUnits.append(weekUnit.getStart());
                    continue;
                }
                if (needI18N && null == weekRegular[weekUnit.getCycle()])
                    weekRegular[weekUnit.getCycle()] = resourses.getMessage(locale,
                        weekRegularKeys[weekUnit.getCycle()]);
                weekUnits.append(weekRegular[weekUnit.getCycle()]);
                weekUnits.append(format.charAt(0)).append(weekUnit.getStart()).append(
                    format.charAt(1)).append(weekUnit.getEnd()).append(format.charAt(2));
            }
            
            if (weekUnits.lastIndexOf(format.charAt(3) + "") == weekUnits.length() - 1) {
                return weekUnits.substring(0, weekUnits.length() - 1);
            } else {
                return weekUnits.toString();
            }
        } else {
            return "";
        }
        
    }
    
    public static String digest(String weekOccupyStr, int from, int startWeek,

    int endWeek, MessageResources resourses, Locale locale) {
        
        return digest(weekOccupyStr, from, startWeek, endWeek, resourses,

        locale, "[-] ");
        
    }
    
    public static TimeUnit[] buildTimeUnits(int year, int from, int startWeek, int endWeek,
            int cycle) {
        String LastDay = year + "-12-31";
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        gregorianCalendar.setTime(Date.valueOf(LastDay));
        boolean endAtSat = false;
        if (gregorianCalendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
            endAtSat = true;
        }
        StringBuffer sb = new StringBuffer(StringUtils.repeat("0", (from + startWeek) - 2));
        switch (cycle) {
            case 1: // '\001'
                sb.append(StringUtils.repeat("1", (endWeek - startWeek) + 1));
                break;
            case 2: // '\002'
                sb.append(StringUtils.repeat("10", ((endWeek - startWeek) + 2) / 2));
                break;
            case 3: // '\003'
                sb.append(StringUtils.repeat("01", ((endWeek - startWeek) + 1) / 2));
                break;
        }
        sb.append(StringUtils.repeat("0", 106 - sb.length()));
        if (!endAtSat) {
            sb.insert(53, "0");
        }
        List unitList = new ArrayList(2);
        if (sb.substring(0, 53).indexOf("1") != -1) {
            TimeUnit unit = new TimeUnit();
            unit.setYear(new Integer(year));
            unit.setValidWeeks(sb.substring(0, 53));
            unitList.add(unit);
        }
        if (sb.substring(53, 106).indexOf("1") != -1) {
            TimeUnit unit = new TimeUnit();
            unit.setYear(new Integer(year + 1));
            unit.setValidWeeks(sb.substring(53, 106));
            unitList.add(unit);
        }
        return (TimeUnit[]) (TimeUnit[]) unitList.toArray(new TimeUnit[unitList.size()]);
    }
    
    public static TimeUnit[] constructTimes(java.util.Date beginDate, java.util.Date endDate,
            Integer weeks[], Integer units[], Integer cycle) {
        Calendar start = new GregorianCalendar();
        start.setTime(beginDate);
        Calendar end = new GregorianCalendar();
        end.setTime(endDate);
        if (start.get(1) != end.get(1)) {
            return null;
        }
        if (weeks.length < 1 || units.length < 1) {
            return null;
        }
        if (cycle.intValue() > 3 || cycle.intValue() < 0) {
            return null;
        }
        List concreteUnits = new ArrayList();
        Arrays.sort(units);
        for (int i = 0; i < units.length; i++) {
            List continueUnits = new ArrayList();
            continueUnits.add(units[i]);
            int j = i + 1;
            do {
                if (j >= units.length) {
                    break;
                }
                if (units[j].intValue() == units[j - 1].intValue() + 1) {
                    continueUnits.add(units[j]);
                } else {
                    i = j - 1;
                    break;
                }
                j++;
            } while (true);
            concreteUnits.add(continueUnits);
        }
        TimeUnit timeUnits[] = buildTimeUnits(start.get(1), start.get(3), cycle.intValue(),
            (end.get(3) - start.get(3)) + 1, 1);
        List times = new ArrayList();
        for (int i = 0; i < weeks.length; i++) {
            for (int j = 0; j < concreteUnits.size(); j++) {
                for (int k = 0; k < units.length; k++) {
                    TimeUnit unit = new TimeUnit();
                    unit.setValidWeeks(timeUnits[k].getValidWeeks());
                    unit.setYear(timeUnits[k].getYear());
                    unit.setWeekId(weeks[i]);
                    List unitList = (List) concreteUnits.get(j);
                    unit.setStartUnit((Integer) unitList.get(0));
                    unit.setEndUnit((Integer) unitList.get(unitList.size() - 1));
                    
                }
            }
        }
        
        return (TimeUnit[]) (TimeUnit[]) times.toArray(new TimeUnit[times.size()]);
        
    }
    
    public static TimeUnit constructTime(java.util.Date beginDate, java.util.Date endDate,
            Integer week, Integer startUnit, Integer unitCount, Integer cycle) {
        Calendar start = new GregorianCalendar();
        start.setTime(beginDate);
        Calendar end = new GregorianCalendar();
        end.setTime(endDate);
        if (start.get(1) != end.get(1)) {
            return null;
        } else {
            TimeUnit time = new TimeUnit();
            time.setWeekId(week);
            time.setStartUnit(startUnit);
            time.setEndUnit(new Integer((startUnit.intValue() + unitCount.intValue()) - 1));
            time.setYear(new Integer(start.get(1)));
            throw new RuntimeException("not completed .duantihua 2006-6-17");
        }
    }
}
