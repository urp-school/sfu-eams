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
 * chaostone             2006-8-10            Created
 *  
 ********************************************************************************/
package com.shufe.service.degree.subject.impl;

import java.util.Collections;

import net.ekingstar.common.detail.Pagination;
import net.ekingstar.common.detail.Result;

import com.shufe.dao.degree.subject.Level1SubjectDAO;
import com.shufe.model.degree.subject.Level1Subject;
import com.shufe.service.BasicService;
import com.shufe.service.degree.subject.Level1SubjectService;

public class Level1SubjectServiceImpl extends BasicService implements Level1SubjectService {
    
    private Level1SubjectDAO level1SubjectDAO;
    
    public Pagination getLevel1Subject(Level1Subject subject, int pageSize, int pageNo) {
        if (pageSize < 0 || pageNo < 0) {
            return new Pagination(new Result(0, Collections.EMPTY_LIST));
        } else {
            return level1SubjectDAO.getLevel1Subject(subject, pageSize, pageNo);
        }
    }
    
    public void setLevel1SubjectDAO(Level1SubjectDAO level1SubjectDAO) {
        this.level1SubjectDAO = level1SubjectDAO;
    }
    
}
