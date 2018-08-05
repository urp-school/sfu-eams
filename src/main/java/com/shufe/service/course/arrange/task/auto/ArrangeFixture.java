//$Id: ArrangeFixture.java,v 1.2 2006/11/11 06:14:07 duanth Exp $
/*
 *
 * KINGSTAR MEDIA SOLUTIONS Co.,LTD. Copyright c 2005-2006. All rights reserved.
 * 
 * This source code is the property of KINGSTAR MEDIA SOLUTIONS LTD. It is intended 
 * only for the use of KINGSTAR MEDIA application development. Reengineering, reproduction
 * arose from modification of the original source, or other redistribution of this source 
 * is not permitted without written permission of the KINGSTAR MEDIA SOLUTIONS LTD.
 * 
 */
/********************************************************************************
 * @author chaostone
 * 
 * MODIFICATION DESCRIPTION
 * 
 * Name                 Date                Description 
 * ============         ============        ============
 * chaostone             2005-11-8         Created
 *  
 ********************************************************************************/

package com.shufe.service.course.arrange.task.auto;

import com.shufe.dao.system.baseinfo.TeacherDAO;
import com.shufe.model.course.arrange.task.ArrangeParams;

/**
 * 自动排课使用的环境和脚手架<br>
 * 1.负责资源的分配者管理
 * 2.排课参数.
 * （参数、教室、时间分配、规则等）
 * @author chaostone
 * 
 */
public class ArrangeFixture {
    /**
     * 教室分配者
     */
    private RoomAllocator roomAlloc;

    /**
     * 时间分配者
     */
    private TimeAllocator timeAlloc;

    /**
     * 排课参数
     */
    private ArrangeParams params = new ArrangeParams();
    /**
     * 教师信息存取对象
     */
    private  TeacherDAO teacherDAO;
    /**
     * @return Returns the params.
     */
    public ArrangeParams getParams() {
        return params;
    }

    /**
     * @param params The params to set.
     */
    public void setParams(ArrangeParams params) {        
        this.params = params;
        this.timeAlloc.setTimeParams(params);
    }

    /**
     * @return Returns the roomAlloc.
     */
    public RoomAllocator getRoomAlloc() {
        return roomAlloc;
    }

    /**
     * @param roomAlloc The roomAlloc to set.
     */
    public void setRoomAlloc(RoomAllocator roomAlloc) {
        this.roomAlloc = roomAlloc;
    }

    /**
     * @return Returns the timeAlloc.
     */
    public TimeAllocator getTimeAlloc() {
        return timeAlloc;
    }

    /**
     * @param timeAlloc The timeAlloc to set.
     */
    public void setTimeAlloc(TimeAllocator timeAlloc) {
        this.timeAlloc = timeAlloc;
    }

    /**
     * @return Returns the teacherDAO.
     */
    public TeacherDAO getTeacherDAO() {
        return teacherDAO;
    }

    /**
     * @param teacherDAO The teacherDAO to set.
     */
    public void setTeacherDAO(TeacherDAO teacherDAO) {
        this.teacherDAO = teacherDAO;
    }    
}
