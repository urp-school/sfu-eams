//$Id: ClassroomServiceImpl.java,v 1.2 2006/09/06 20:39:47 cwx Exp $
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
 * chaostone             2005-9-15         Created
 *  
 ********************************************************************************/

package com.shufe.service.system.baseinfo.impl;

import java.sql.Date;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import net.ekingstar.common.detail.Pagination;
import net.ekingstar.common.detail.Result;

import org.apache.commons.lang.StringUtils;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.ObjectRetrievalFailureException;

import com.ekingstar.commons.lang.SeqStringUtil;
import com.ekingstar.commons.model.pojo.PojoExistException;
import com.ekingstar.commons.model.pojo.PojoNotExistException;
import com.shufe.dao.system.baseinfo.ClassroomDAO;
import com.shufe.model.system.baseinfo.Classroom;
import com.shufe.service.BasicService;
import com.shufe.service.system.baseinfo.ClassroomService;

public class ClassroomServiceImpl extends BasicService implements
		ClassroomService {
	private ClassroomDAO classroomDAO = null;

	/**
	 * @see com.shufe.service.system.baseinfo.ClassroomService#setClassroomDAO(com.shufe.dao.system.baseinfo.ClassroomDAO)
	 */
	public void setClassroomDAO(ClassroomDAO classroomDAO) {
		this.classroomDAO = classroomDAO;
	}

	/**
	 * @see com.shufe.service.system.baseinfo.ClassroomService#getClassroom(java.lang.Long)
	 */
	public Classroom getClassroom(Long id) {
		try {
			return classroomDAO.getClassroom(id);
		} catch (ObjectRetrievalFailureException e) {
			return null;
		}
	}

	/**
	 * @see com.shufe.service.system.baseinfo.ClassroomService#getClassrooms()
	 */
	public List getClassrooms() {
		return classroomDAO.getClassrooms();
	}

	/**
	 * @see com.shufe.service.system.baseinfo.ClassroomService#getClassrooms(com.shufe.model.system.baseinfo.Classroom,
	 *      int, int)
	 */
	public Pagination getClassrooms(Classroom classroom, int pageNo,
			int pageSize) {
		return classroomDAO.getClassrooms(classroom, pageNo, pageSize);
	}

	/**
	 * @see com.shufe.service.system.baseinfo.ClassroomService#getClassrooms(com.shufe.model.system.baseinfo.Classroom)
	 */
	public List getClassrooms(Classroom classroom) {
		return classroomDAO.getClassrooms(classroom);
	}

	/**
	 * @see com.shufe.service.system.baseinfo.ClassroomService#getClassrooms(com.shufe.model.system.baseinfo.Classroom,
	 *      java.lang.String, int, int)
	 */
	public Pagination getClassrooms(Classroom classroom, String departIdSeq,
			int pageNo, int pageSize) {
		if (pageNo > 0 && pageSize > 0)
			return classroomDAO.getClassrooms(classroom, SeqStringUtil
					.transformToLong(departIdSeq), pageNo, pageSize);
		else
			return new Pagination(new Result(0, Collections.EMPTY_LIST));
	}

	/**
	 * @see com.shufe.service.system.baseinfo.ClassroomService#getAllClassrooms(com.shufe.model.system.baseinfo.Classroom,
	 *      java.lang.String, int, int)
	 */
	public Pagination getAllClassrooms(Classroom classroom, String departIdSeq,
			int pageNo, int pageSize) {
		if (pageNo > 0 && pageSize > 0)
			return classroomDAO.getAllClassrooms(classroom, SeqStringUtil
					.transformToLong(departIdSeq), pageNo, pageSize);
		else
			return new Pagination(new Result(0, Collections.EMPTY_LIST));
	}

	/**
	 * @see com.shufe.service.system.baseinfo.ClassroomService#getClassrooms(com.shufe.model.system.baseinfo.Classroom,
	 *      java.lang.String)
	 */
	public List getClassrooms(Classroom classroom, String departIdSeq) {
		if (StringUtils.isNotEmpty(departIdSeq))
			return classroomDAO.getClassrooms(classroom, SeqStringUtil
					.transformToLong(departIdSeq));
		return Collections.EMPTY_LIST;
	}

	/**
	 * @see com.shufe.service.system.baseinfo.ClassroomService#getClassrooms(java.util.Collection,
	 *      java.lang.String)
	 */
	public List getClassrooms(Collection configTypeList, String departIdSeq) {
		if (StringUtils.isNotEmpty(departIdSeq))
			return classroomDAO.getClassrooms(configTypeList, SeqStringUtil
					.transformToLong(departIdSeq));
		return Collections.EMPTY_LIST;
	}

	/**
	 * @see com.shufe.service.system.baseinfo.ClassroomService#getClassrooms(String)
	 */
	public List getClassrooms(String departIdSeq) {
		return StringUtils.isNotEmpty(departIdSeq) ? classroomDAO
				.getClassrooms(SeqStringUtil.transformToLong(departIdSeq))
				: Collections.EMPTY_LIST;
	}

	/**
	 * @see com.shufe.service.system.baseinfo.ClassroomService#getClassrooms(java.util.Collection)
	 */
	public List getClassrooms(Collection roomIds) {
		if (!roomIds.isEmpty())
			return classroomDAO.getClassrooms(roomIds);
		else
			return Collections.EMPTY_LIST;
	}

	/**
	 * @see com.shufe.service.system.baseinfo.ClassroomService#removeClassroom(java.lang.String)
	 */
	public void removeClassroom(Long id) {
		if (null == id)
			return;
		try {
			classroomDAO.removeClassroom(id);
		} catch (PojoNotExistException e) {
			return;
		}
	}

	/**
	 * @see com.shufe.service.system.baseinfo.ClassroomService#saveClassroom(com.shufe.model.system.baseinfo.Classroom)
	 */
	public void saveOrUpdate(Classroom classroom) throws PojoExistException {
		try {
			if (!classroom.isPO()) {
				classroom.setCreateAt(new Date(System.currentTimeMillis()));
			}
			classroom.setModifyAt(new Date(System.currentTimeMillis()));
			classroomDAO.saveOrUpdate(classroom);
		} catch (DataIntegrityViolationException e) {
			throw new PojoExistException("classroom already exits:"
					+ classroom);
		} catch (ConstraintViolationException e) {
			throw new PojoExistException("classroom already exits:"
					+ classroom);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.shufe.service.system.baseinfo.ClassroomService#getClassroomIdsByClassroom(com.shufe.model.system.baseinfo.Classroom,
	 *      java.lang.String)
	 */
	public List getClassroomIdsByClassroom(Classroom classroom, String departIds) {
		return classroomDAO.getClassroomIdsByClassroom(classroom, departIds);
	}
}
