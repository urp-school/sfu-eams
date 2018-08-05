//$Id: CreditAwardCriteriaDAO.java,v 1.1 2006/08/02 00:52:48 duanth Exp $
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
 * chaostone             2005-12-14         Created
 *  
 ********************************************************************************/

package com.shufe.dao.course.election;

import com.shufe.dao.BasicDAO;

public interface CreditAwardCriteriaDAO extends BasicDAO {
    /**
     * 查找平均绩点对应的奖励学分分值，没有则返回0
     * @param avgGrade
     * @return
     */
    public Float getAwardCredit(Float avgGrade);
}
