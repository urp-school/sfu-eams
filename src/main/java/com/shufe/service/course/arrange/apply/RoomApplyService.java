// $createAt:2005-12-22-13:59:25-13:59:25 userCreated:dell

package com.shufe.service.course.arrange.apply;

import java.util.List;
import java.util.Set;

import com.ekingstar.security.User;
import com.shufe.model.course.arrange.apply.ApplyTime;
import com.shufe.model.course.arrange.apply.RoomApply;
import com.shufe.model.system.baseinfo.Classroom;

/**
 * @author dell
 */
public interface RoomApplyService {
    
    /**
     * 移居教室申请的时间和教室查询申请记录
     * 
     * @param room TODO
     * @param applyTime
     * @return
     */
    public List getRoomApply(Classroom room, ApplyTime applyTime);
    
    /**
     * 物管批准教室申请<br>
     * 1)将申请的状态改为批准状态<br>
     * 2)记录审核人和审核时间<br>
     * 3)生成申请对应的活动记录<br>
     * 4)如果是营利教室生成费用记录
     * 
     * @param roomApply
     * @param approveBy
     * @param classrooms
     */
    public boolean approve(RoomApply roomApply, User approveBy, Set classrooms);
    
    /**
     * 归口批准教室申请<br>
     * 1)将申请的状态改为批准状态<br>
     * 2)记录审核人和审核时间
     * 
     * @param roomApply
     * @param approveBy
     */
    public void departApprove(RoomApply roomApply, User approveBy);
    
    /**
     * 归口取消批准教室申请<br>
     * 1)将申请的状态改为未通过状态<br>
     * 2)记录审核人和审核时间
     * 
     * @param roomApply
     * @param approveBy
     */
    public void departCancel(RoomApply roomApply, User approveBy);
    
    /**
     * 检测教室申请是否可以批准，即教室是否空闲
     * 
     * @param roomApply
     * @return
     */
    public boolean canApprove(RoomApply roomApply);
}
