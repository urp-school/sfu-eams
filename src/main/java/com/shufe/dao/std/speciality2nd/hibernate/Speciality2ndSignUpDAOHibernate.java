//$Id: Speciality2ndSignUpDAOHibernate.java,v 1.1 2007-5-7 上午11:05:37 chaostone Exp $
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
 * Name           Date          Description 
 * ============         ============        ============
 *chaostone      2007-5-7         Created
 *  
 ********************************************************************************/

package com.shufe.dao.std.speciality2nd.hibernate;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.hibernate.Query;

import com.ekingstar.commons.collection.transformers.PropertyTransformer;
import com.shufe.dao.BasicHibernateDAO;
import com.shufe.dao.std.speciality2nd.Speciality2ndSignUpDAO;
import com.shufe.model.std.speciality2nd.SignUpSetting;
import com.shufe.util.ListUtils;

public class Speciality2ndSignUpDAOHibernate extends BasicHibernateDAO implements
		Speciality2ndSignUpDAO {

	public void initMatriculate(SignUpSetting setting) {
		// 更新每个报名学生的录取绩点,归零录取志愿,是否调剂录取，录取专业
		String hql = "update SignUpStudent set matriculateGPA=GPA,rank=null,isAdjustMatriculated=null,matriculated=null where setting.id="
				+ setting.getId();
		Query query = getSession().createQuery(hql);
		query.executeUpdate();
		// 归零报名详情中的录取状态
		hql = "update SignUpStudentRecord set status=false where id in(select record.id from SignUpStudentRecord record where record.signUpStd.setting.id="
				+ setting.getId() + ")";
		query = getSession().createQuery(hql);
		query.executeUpdate();
		// 清除学生信息中的专业信息
		hql = "update Student set secondMajor=null,secondAspect=null,isSecondMajorStudy=null where id in(select signUpStd.std.id from SignUpStudent signUpStd  where signUpStd.setting.id="
				+ setting.getId() + ")";
		query = getSession().createQuery(hql);
		query.executeUpdate();
	}

	public void batchMatriculateSignUpStudent(List signUpStds) {
		if (CollectionUtils.isEmpty(signUpStds))
			return;
		utilDao.saveOrUpdate(signUpStds);
		ArrayList recordIds = new ArrayList(CollectionUtils.collect(signUpStds,
				new PropertyTransformer("id")));
		String updateSql = "update Student std set std.isSecondMajorStudy=true,"
				+ "secondMajor=(select s.matriculated.speciality from SignUpStudent s where s.std.id=std.id and s.id in(:ids)),"
				+ "secondAspect=(select s.matriculated.aspect from SignUpStudent s where s.std.id=std.id and s.id in(:ids))"
				+ " where exists (select s.id from SignUpStudent s where s.std.id=std.id and s.id in(:ids))";
		List subIdLists = ListUtils.split(recordIds, 500);
		Query query = getSession().createQuery(updateSql);
		for (Iterator iter = subIdLists.iterator(); iter.hasNext();) {
			List element = (List) iter.next();
			query.setParameterList("ids", element);
			query.executeUpdate();
		}
	}

	public void batchMatriculateSignUpStudentRecords(SignUpSetting setting, List records) {
		if (records.isEmpty())
			return;
		ArrayList recordIds = new ArrayList(CollectionUtils.collect(records,
				new PropertyTransformer("id")));
		String[] categories = new String[3];
		categories[0] = "SignUpStudentRecord";
		categories[1] = "SignUpStudent";
		categories[2] = "Student";
		String[] updateSqls = new String[3];
		updateSqls[0] = "update SignUpStudentRecord set status=true where id in(:ids)";

		updateSqls[1] = "update SignUpStudent signUpStd set signUpStd.isAdjustMatriculated=false,"
				+ "rank=(select r.rank from SignUpStudentRecord r where r.signUpStd.id=signUpStd.id and r.status=true),"
				+ "matriculated=(select r.specialitySetting from SignUpStudentRecord r where r.signUpStd.id=signUpStd.id and r.status=true) "
				+ "where signUpStd.setting.id="
				+ setting.getId()
				+ " and exists(select r.specialitySetting from SignUpStudentRecord r where r.signUpStd.id=signUpStd.id and r.id in(:ids))";

		updateSqls[2] = "update Student std set std.isSecondMajorStudy=true,"
				+ "std.secondMajor=(select s.matriculated.speciality from SignUpStudent s where s.std.id=std.id and s.setting.id="
				+ setting.getId()
				+ "),"
				+ "std.secondAspect=(select s.matriculated.aspect from SignUpStudent s where s.std.id=std.id and s.setting.id="
				+ setting.getId()
				+ ")"
				+ "where exists (select r.id from SignUpStudentRecord r where r.signUpStd.std.id=std.id and r.id in(:ids))";

		List subIdLists = ListUtils.split(recordIds, 200);
		for (int i = 0; i < updateSqls.length; i++) {
			Query query = getSession().createQuery(updateSqls[i]);
			for (Iterator iter = subIdLists.iterator(); iter.hasNext();) {
				List element = (List) iter.next();
				query.setParameterList("ids", element);
				query.executeUpdate();
				logger.info("batch update " + categories[i] + " with id:" + element);
			}
		}
	}

}
