package com.shufe.dao.course.election.hibernate;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.ekingstar.common.detail.Pagination;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.eams.system.basecode.industry.CourseTakeType;
import com.shufe.dao.BasicHibernateDAO;
import com.shufe.dao.course.election.ElectRecordDAO;
import com.shufe.dao.util.CriterionUtils;
import com.shufe.model.course.arrange.task.CourseTake;
import com.shufe.model.course.election.ElectRecord;
import com.shufe.model.course.election.ElectState;
import com.shufe.model.course.election.StdCreditConstraint;
import com.shufe.model.course.task.TeachTask;
import com.shufe.model.std.Student;

/**
 * @author Administrator
 * 
 */
public class ElectRecordDAOHibernate extends BasicHibernateDAO implements
		ElectRecordDAO {
	/**
	 * @see ElectRecordDAO#getElectRecords(ElectLogRecord)
	 */
	public Pagination getElectRecords(ElectRecord record, int pageNo,
			int pageSize) {
		Criteria criteria = getExampleCriteria(record);
		return dynaSearch(criteria, pageNo, pageSize);
	}

	/**
	 * @see ElectRecordDAO#getElectRecords(ElectLogRecord)
	 */
	public List getElectRecords(ElectRecord record) {
		Criteria criteria = getExampleCriteria(record);
		return criteria.list();
	}

	/**
	 * @param record
	 * @return
	 */
	private Criteria getExampleCriteria(ElectRecord record) {
		Criteria criteria = getSession().createCriteria(ElectRecord.class);
		if (null != record) {
			List criterions = CriterionUtils.getEntityCriterions(record);
			for (Iterator iter = criterions.iterator(); iter.hasNext();) {
				Criterion criterion = (Criterion) iter.next();
				criteria.add(criterion);
			}
		}
		if (StringUtils.isNotEmpty(record.getCourseTakeType().getName())) {
			criteria.createCriteria("courseTakeType", "courseTakeType").add(
					Restrictions.like("name", record.getCourseTakeType()
							.getName(), MatchMode.ANYWHERE));
		}
		criteria.addOrder(Order.asc("electTime")).addOrder(
				Order.asc("student.id"));
		return criteria;
	}

	/**
	 * @see ElectRecordDAO#getLatestElectRecord(TeachTask, Long)
	 */
	public ElectRecord getLatestElectRecord(TeachTask task, Long stdId) {
		Query query = getSession().getNamedQuery("getElectRecords");
		query.setParameter("stdId", stdId);
		query.setParameter("taskId", task.getId());
		List records = query.list();
		if (records.isEmpty())
			return null;
		else {
			return (ElectRecord) records.get(records.size() - 1);
		}
	}

	/**
	 * @see ElectRecordDAO#removeElectRecord(TeachTask, Student)
	 */
	public void removeElectRecord(TeachTask task, Student std) {
		Query deleteQuery = getSession().getNamedQuery("removeElectRecord");
		deleteQuery.setParameter("stdId", std.getId());
		deleteQuery.setParameter("taskId", task.getId());
		deleteQuery.executeUpdate();
	}

	/**
	 * @see ElectRecordDAO#removeElectRecords(TeachTask, Long[])
	 */
	public void removeElectRecords(TeachTask task, Long[] stdIds) {
		Query deleteQuery = getSession().getNamedQuery("removeElectRecords");
		deleteQuery.setParameterList("stdIds", stdIds);
		deleteQuery.setParameter("taskId", task.getId());
		deleteQuery.executeUpdate();
	}

	/**
	 * @see ElectRecordDAO#saveElection(TeachTask, CourseTake, ElectLogRecord,
	 *      StdCreditConstraint, Boolean)
	 */
	public int saveElection(TeachTask task, CourseTakeType courseTakeType,
			ElectState state, boolean checkMaxLimit) {
		int rs = 0;
		Connection con = getSession().connection();
		String strProcedure = "{? = call save_electresult(?,?,?,?,?,?,?,?)}";
		CallableStatement cstmt = null;
		try {
			cstmt = con.prepareCall(strProcedure);
			cstmt.registerOutParameter(1, Types.INTEGER);
			cstmt.setLong(2, task.getId().longValue());
			cstmt.setLong(3, task.getCourseType().getId().longValue());
			cstmt.setLong(4, state.params.getCalendar().getId().longValue());
			cstmt.setLong(5, state.std.getId().longValue());
			cstmt.setInt(6, state.params.getTurn().intValue());
			cstmt.setFloat(7, task.getCourse().getCredits().floatValue());
			cstmt.setLong(8, courseTakeType.getId().longValue());
			cstmt.setInt(9, checkMaxLimit ? 1 : 0);
			cstmt.execute();
			rs = cstmt.getInt(1);
			con.commit();
			cstmt.close();
		} catch (SQLException e) {
			try {
				if (null != cstmt)
					cstmt.close();
				con.rollback();
				throw new RuntimeException(e);
			} catch (Exception e1) {
				info("exec function is failed" + "in save_electresult taskId:"
						+ task.getId() + " std:" + state.std.getId());
				throw new RuntimeException(e);
			}
		}
		return rs;
	}

	public boolean isElected(Student std, TeachTask task, Integer turn) {
		Query query = getSession().getNamedQuery("getCourseElected");
		query.setParameter("std", std);
		query.setParameter("task", task);
		query.setParameter("turn", turn);
		return ((Number) query.uniqueResult()).intValue() > 0;
	}

	/**
	 * @see ElectRecordDAO#removeElection(Student, TeachTask,
	 *      StdCreditConstraint, Boolean)
	 */
	public int removeElection(TeachTask task, ElectState state) {
		int rs = 0;
		Connection con = getSession().connection();
		String strProcedure = "{? = call remove_electresult(?,?,?,?,?,?,?)}";
		CallableStatement cstmt = null;
		try {
			cstmt = con.prepareCall(strProcedure);
			cstmt.registerOutParameter(1, Types.INTEGER);
			cstmt.setLong(2, task.getId().longValue());
			cstmt.setLong(3, state.params.getCalendar().getId().longValue());
			cstmt.setLong(4, state.std.getId().longValue());
			cstmt.setString(5, state.std.getStdNo());
			cstmt.setFloat(6, task.getCourse().getCredits().floatValue());
			cstmt.setInt(7, state.getParams().getTurn().intValue());
			cstmt.setInt(8, Boolean.TRUE.equals(state.getParams()
					.getIsUnderMinAllowed()) ? 0 : 1);
			cstmt.execute();
			rs = cstmt.getInt(1);
			con.commit();
			cstmt.close();
		} catch (SQLException e) {
			try {
				if (null != cstmt)
					cstmt.close();
				con.rollback();
			} catch (Exception e1) {
				throw new RuntimeException(e1.getMessage());
			}
			info("exec function is failed" + "in delete election task:"
					+ task.getId() + " std:" + state.std.getId());
			throw new RuntimeException(e);
		}
		// task.getTeachClass().decStdCount();
		return rs;
	}

	public void updatePitchOn(TeachTask task, Collection stdIds,
			Boolean isPitchOn) {
		if (stdIds.isEmpty())
			return;
		String hql = "update ElectRecord set isPitchOn=:isPitchOn where task=:task and student.id in (:stdIds)";
		Query query = getSession().createQuery(hql);
		query.setParameter("task", task);
		query.setParameterList("stdIds", stdIds);
		query.setParameter("isPitchOn", isPitchOn);
		query.executeUpdate();
	}

	public Set getElectStdIdSet(TeachTask task, Integer turn, Boolean isPitchOn) {
		EntityQuery query = new EntityQuery(ElectRecord.class, "record");
		query.add(new Condition("record.task=:task", task));
		if (null != turn) {
			query.add(new Condition("record.turn=:turn", turn));
		}
		if (null != isPitchOn) {
			query.add(new Condition("record.isPitchOn=:isPitchOn", isPitchOn));
		}
		query.setSelect("select record.student.id ");
		return new HashSet(utilDao.search(query));
	}

	public Map getLastElectTurn(List tasks) {
		String hql = "select distinct task.id,turn from ElectRecord where task in(:tasks) order by turn";
		Query query = getSession().createQuery(hql);
		query.setParameterList("tasks", tasks);
		List rs = query.list();
		Map turnMap = new HashMap();
		for (Iterator iter = rs.iterator(); iter.hasNext();) {
			Object[] pair = (Object[]) iter.next();
			turnMap.put(pair[0], pair[1]);
		}
		return turnMap;
	}

}
