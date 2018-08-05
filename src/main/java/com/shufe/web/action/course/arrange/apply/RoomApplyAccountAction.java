
package com.shufe.web.action.course.arrange.apply;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.eams.system.basecode.industry.ActivityType;
import com.shufe.dao.course.arrange.resource.TeachResourceDAO;
import com.shufe.model.system.baseinfo.Classroom;

/**
 * 借用率统计
 * 
 * @author zhouqi
 */
public class RoomApplyAccountAction extends RoomApplyApproveAction {
    
    /** 星期 */
    protected final String[] WEEKS = { "星期一", "星期二", "星期三", "星期四", "星期五", "星期六", "星期日" };
    
    protected TeachResourceDAO teachResourceDAO;
    
    /** 记录星期查询出的情况 */
    private int[] resultWeeks = new int[WEEKS.length];
    
    /**
     * index.ftl<br>
     * 查询结果的字段：week, (isApproved = 1) of count, total_count
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward index(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        request.setAttribute("year", nowYear());
        return forward(request);
    }
    
    /**
     * 获得今年的年份
     * 
     * @return
     */
    protected Integer nowYear() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        return new Integer(calendar.get(Calendar.YEAR));
    }
    
    protected int countDayOfWeeks(int year, int dayOfWeek) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, 0, 1);
        while (calendar.get(Calendar.DAY_OF_WEEK) != dayOfWeek) {
            calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) + 1);
        }
        
        int i = 0;
        while (calendar.get(Calendar.YEAR) == year) {
            i++;
            calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) + 7);
        }
        return i;
    }
    
    /**
     * index.ftl
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward account(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        Object year = request.getParameter("year");
        if (year == null || StringUtils.isEmpty(year.toString())) {
            request.setAttribute("year", year);
        }
        
        request.setAttribute("weeksAccounts", weekAccount(new Integer(year.toString()), request));
        request.setAttribute("placesAccounts", placeAccount(new Integer(year.toString())));
        request.setAttribute("activitiesAccounts", activityAccount(new Integer(year.toString())));
        
        return forward(request);
    }
    
    /**
     * 按星期统计教室借用率
     * 
     * @param request TODO
     * @return
     */
    protected List weekAccount(Integer year, HttpServletRequest request) {
        long usingCounts = 0;
        DecimalFormat bf = new DecimalFormat("0.00");
        if (year == null) {
            return null;
        }
        List weeks = teachResourceDAO.weekAccount(year);
        
        List weeksAccounts = new ArrayList();
        for (Iterator it = weeks.iterator(); it.hasNext();) {
            Object[] obj = (Object[]) it.next();
            if (usingCounts == 0) {
                usingCounts = Long.parseLong(obj[2].toString());
            }
            double rate = (Double.parseDouble(obj[1].toString()) / Double.parseDouble(obj[2]
                    .toString())) * 100;
            Map map = new HashMap();
            map.put(strWeek(obj[0]), bf.format(rate) + "%");
            weeksAccounts.add(map);
        }
        for (int i = 0; i < resultWeeks.length; i++) {
            if (resultWeeks[i] == 0) {
                Map map = new HashMap();
                map.put(WEEKS[i], "0.00%");
                weeksAccounts.add(map);
            }
        }
        
        request.setAttribute("usingCounts", new Long(usingCounts));
        return weeksAccounts;
    }
    
    /**
     * 找到星期几的位置
     * 
     * @param weekValue
     * @return
     */
    protected int whichWeek(Object weekValue) {
        if (weekValue == null) {
            return -1;
        }
        for (int i = 0; i < WEEKS.length; i++) {
            if (String.valueOf(weekValue).equals(WEEKS[i])) {
                return i;
            }
        }
        return -1;
    }
    
    /**
     * 记录查询到的星期
     * 
     * @param obj
     * @return
     */
    protected String strWeek(Object obj) {
        if (obj != null) {
            String week = String.valueOf(obj);
            for (int i = 0; i < WEEKS.length; i++) {
                if (week.equals(WEEKS[i])) {
                    resultWeeks[i] = 1;
                    return week;
                }
            }
        }
        return null;
    }
    
    /**
     * 按地点统计教室借用率
     * 
     * @return
     */
    protected List placeAccount(Integer year) {
        DecimalFormat bf = new DecimalFormat("0.00");
        if (year == null) {
            return null;
        }
        List places = teachResourceDAO.placeAccount(year);
        long[] resultPlaces = new long[places.size()];
        
        List placesAccounts = new ArrayList();
        int k = 0;
        for (Iterator it = places.iterator(); it.hasNext();) {
            Object[] obj = (Object[]) it.next();
            double rate = (Double.parseDouble(obj[2].toString()) / Double.parseDouble(obj[3]
                    .toString())) * 100;
            Map map = new HashMap();
            map.put(obj[1], bf.format(rate) + "%");
            placesAccounts.add(map);
            resultPlaces[k++] = Long.parseLong(obj[0].toString());
        }
        // 处理余下没有查询到的地点，加入列表
        EntityQuery query = new EntityQuery(Classroom.class, "room");
        query.add(new Condition("room.state=true"));
        List classes = (List) utilService.search(query);
        for (Iterator it = classes.iterator(); it.hasNext();) {
            Classroom room = (Classroom) it.next();
            boolean found = false;
            for (int i = 0; i < resultPlaces.length; i++) {
                if (room.getId().longValue() == resultPlaces[i]) {
                    found = true;
                    break;
                }
            }
            if (found == false) {
                Map map = new HashMap();
                map.put(room.getName(), "0.00%");
                placesAccounts.add(map);
            }
        }
        return placesAccounts;
    }
    
    /**
     * 按活动类型统计教室借用率
     * 
     * @return
     */
    protected List activityAccount(Integer year) {
        DecimalFormat bf = new DecimalFormat("0.00");
        if (year == null) {
            return null;
        }
        List activities = teachResourceDAO.activityAccount(year);
        long[] resultActivity = new long[activities.size()];
        
        List placesAccounts = new ArrayList();
        int k = 0;
        for (Iterator it = activities.iterator(); it.hasNext();) {
            Object[] obj = (Object[]) it.next();
            double rate = (Double.parseDouble(obj[2].toString()) / Double.parseDouble(obj[3]
                    .toString())) * 100;
            Map map = new HashMap();
            map.put(obj[1], bf.format(rate) + "%");
            placesAccounts.add(map);
            resultActivity[k++] = Long.parseLong(obj[0].toString());
        }
        // 处理余下没有查询到的活动类型，加入列表
        List activites = baseCodeService.getCodes(ActivityType.class);
        for (Iterator it = activites.iterator(); it.hasNext();) {
            ActivityType type = (ActivityType) it.next();
            boolean found = false;
            for (int i = 0; i < resultActivity.length; i++) {
                if (type.getId().longValue() == resultActivity[i]) {
                    found = true;
                    break;
                }
            }
            if (found == false) {
                Map map = new HashMap();
                map.put(type.getName(), "0.00%");
                placesAccounts.add(map);
            }
        }
        return placesAccounts;
    }
    
    public void setTeachResourceDAO(TeachResourceDAO teachResourceDAO) {
        this.teachResourceDAO = teachResourceDAO;
    }
    
}
