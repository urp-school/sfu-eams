package com.shufe.web.action.course.arrange.resource;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.commons.query.OrderUtils;
import com.ekingstar.eams.system.basecode.industry.ClassroomType;
import com.ekingstar.eams.system.baseinfo.Building;
import com.ekingstar.eams.system.baseinfo.SchoolDistrict;
import com.ekingstar.eams.system.baseinfo.model.StudentType;
import com.ekingstar.eams.system.time.CourseUnit;
import com.ekingstar.eams.system.time.WeekInfo;
import com.shufe.model.Constants;
import com.shufe.model.course.arrange.Activity;
import com.shufe.model.course.arrange.TaskActivity;
import com.shufe.model.course.arrange.apply.RoomApply;
import com.shufe.model.system.baseinfo.Classroom;
import com.shufe.model.system.calendar.TeachCalendar;

public class RoomResourceStatAction extends RoomResourceAction {
    
    /**
     * 
     * 教学楼查询的主界面
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     * 
     */
    public ActionForward index(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        setCalendarDataRealm(request, hasStdType);
        Long id = new Long(5);
        setCalendar(request, new StudentType(id));
        TeachCalendar calendar = (TeachCalendar) request.getAttribute(Constants.CALENDAR);
        Integer weekNum = calendar.getWeeks();
        int currentWeek = 0;
        if (calendar.contains(new Date(System.currentTimeMillis()))) {
            currentWeek = calendar.getWeekIndex(new Date(System.currentTimeMillis()));
        }
        initBaseInfos(request, "districtList", SchoolDistrict.class);
        initBaseCodes(request, "configTypes", ClassroomType.class);
        request.setAttribute("weekNum", weekNum);
        request.setAttribute("currentWeek", new Integer(currentWeek));
        request.setAttribute("maxUnits", new Integer(TeachCalendar.MAXUNITS));
        addCollection(request, "weeks", WeekInfo.WEEKS);
        return forward(request);
    }
    
    /**
     * 
     * 教学楼统计
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     * 
     */
    public ActionForward stat(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        /** get real year and weekNum */
        int week = Integer.parseInt(request.getParameter("classroom.week"));
        Long calendarId = getLong(request, "calendar.id");
        TeachCalendar calendar = (TeachCalendar) utilService.get(TeachCalendar.class, calendarId);
        int startWeek = calendar.getWeekStart().intValue();
        int nf, weekNum;
        if (startWeek + week - 1 > 53) {
            nf = calendar.getStartYear() + 1;
            weekNum = startWeek + week - 1 - 53;
        }/** so far not exist */
        else {
            nf = calendar.getStartYear();
            weekNum = startWeek + week - 1;
        }
        /** transfer to valid week format */
        char[] w = new char[53];
        for (int i = 0; i < 53; i++) {
            w[i] = '0';
        }
        w[weekNum - 1] = '1';
        String wn = String.valueOf(w);
        Long wnl = Long.valueOf(wn, 2);
        /** get building information */
        Long buildingId = getLong(request, "classroom.building.id");
        /** get all classroom of building */
        EntityQuery roomQuery = new EntityQuery(Classroom.class, "classroom");
        populateConditions(request, roomQuery);
        roomQuery.addOrder(OrderUtils.parser("classroom.name"));
        List classroomList = (List) utilService.search(roomQuery);
        
        Map unitMap = new HashMap();
        String units[] = StringUtils.split(get(request, "units"), ";");
        for (int i = 0; i < units.length; i++) {
            unitMap.put(units[i], new Object());
        }
        int occupyCount = 0;
        /** get activity */
        List occupyTables = new ArrayList();
        for (Iterator iter = classroomList.iterator(); iter.hasNext();) {
            Classroom classroom = (Classroom) iter.next();
            Map occupyTable = new HashMap();
            occupyTable.put("classroom", classroom);
            List activities = new ArrayList();
            Long classroomId = (Long) PropertyUtils.getProperty(classroom, classroom.key());
            for (int i = 0; i < WeekInfo.MAX; i++) {
                for (int j = CourseUnit.MINUNITS - 1; j < TeachCalendar.MAXUNITS; j++) {
                    if (CollectionUtils.isNotEmpty(unitMap.keySet())
                            && !unitMap.containsKey((i + 1) + "," + (j + 1))) {
                        continue;
                    }
                    Map detail = new HashMap();
                    detail.put("unit", new Integer(i * TeachCalendar.MAXUNITS + j));
                    /** get relative activity */
                    EntityQuery activityQuery = new EntityQuery(Activity.class, "activity");
                    activityQuery.add(new Condition("activity.time.year=:nf", new Integer(nf)));
                    activityQuery.add(new Condition("activity.room.id=:classroomId", classroomId));
                    activityQuery.add(new Condition("BITAND(activity.time.validWeeksNum,:wnl)>0",
                            wnl));
                    activityQuery.add(new Condition("activity.time.weekId=:weekId", new Integer(
                            i + 1)));
                    activityQuery.add(new Condition("activity.time.startUnit<=:unit", new Integer(
                            j + 1)));
                    activityQuery.add(new Condition("activity.time.endUnit>=:unit", new Integer(
                            j + 1)));
                    Iterator it = utilService.search(activityQuery).iterator();
                    if (it.hasNext()) {
                        occupyCount++;
                        Activity a = (Activity) it.next();
                        if (a instanceof TaskActivity) {
                            TaskActivity ta = (TaskActivity) a;
                            String taskResult = ta.getTask().getSeqNo() + " "
                                    + ta.getTask().getArrangeInfo().getTeacherNames() + " "
                                    + ta.getTask().getCourse().getName();
                            detail.put("detail", taskResult);
                            detail.put("isTask", Boolean.TRUE);
                            activities.add(detail);
                        } else {
                            /** get relative room application */
                            EntityQuery applyQuery = new EntityQuery(RoomApply.class, "roomApply");
                            applyQuery.join("right", "roomApply.activities", "activity");
                            applyQuery
                                    .setSelect("roomApply.activityName,roomApply.activityType.name,roomApply.borrower.user.userName");
                            Long activityId = a.getId();
                            applyQuery.add(new Condition("activity.id=:activityId", activityId));
                            List appList = (List) utilService.search(applyQuery);
                            Iterator applyIter = appList.iterator();
                            Object[] data = (Object[]) applyIter.next();
                            String applyResult = (String) data[0] + " " + (String) data[1] + " "
                                    + (String) data[2];
                            detail.put("detail", applyResult);
                            detail.put("isTask", Boolean.FALSE);
                            activities.add(detail);
                        }
                    }
                }
            }
            occupyTable.put("activities", activities);
            occupyTables.add(occupyTable);
        }
        request.setAttribute("building", utilService.get(Building.class, buildingId));
        request.setAttribute("occupyTables", occupyTables);
        request.setAttribute("weekList", WeekInfo.WEEKS);
        request.setAttribute("unitMap", unitMap);
        request.setAttribute("occupyCount", new Integer(occupyCount));
        request.setAttribute(Constants.CALENDAR, calendar);
        return forward(request);
    }
}
