//$Id: CreditAwardCriteriaDAOHibernate.java,v 1.1 2006/08/02 00:53:11 duanth Exp $
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

package com.shufe.dao.course.election.hibernate;

import java.util.List;

import com.shufe.dao.BasicHibernateDAO;
import com.shufe.dao.course.election.CreditAwardCriteriaDAO;
import com.shufe.model.course.election.CreditAwardCriteria;

public class CreditAwardCriteriaDAOHibernate extends BasicHibernateDAO
        implements CreditAwardCriteriaDAO {

    /**
     * @see com.shufe.dao.course.election.CreditAwardCriteriaDAO#getAwardCredit(float)
     */
    public Float getAwardCredit(Float avgGrade) {
        List awardCritrias = find("getCreditAwardCriteria",
                new Object[] { avgGrade,avgGrade });
        if (awardCritrias.isEmpty())
            return new Float(0);
        else
            return ((CreditAwardCriteria) awardCritrias.iterator().next())
                    .getAwardCredits();
    }

}
