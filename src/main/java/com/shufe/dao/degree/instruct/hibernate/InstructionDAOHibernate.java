package com.shufe.dao.degree.instruct.hibernate;

import java.util.Iterator;
import java.util.List;

import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.eams.system.baseinfo.StudentType;
import com.shufe.dao.BasicHibernateDAO;
import com.shufe.dao.degree.instruct.InstructionDAO;
import com.shufe.model.degree.instruct.Instruction;
import com.shufe.model.system.baseinfo.Teacher;
import com.shufe.model.system.calendar.TeachCalendar;
import com.shufe.model.system.security.DataRealm;
import com.shufe.util.DataRealmUtils;

public class InstructionDAOHibernate extends BasicHibernateDAO implements InstructionDAO {
    
    public List statStdTypeTeacher(TeachCalendar calendar, DataRealm dataRealm,
            Boolean isGraduateInstruction) {
        EntityQuery query = new EntityQuery(Instruction.class, "instruction");
        query.add(Condition.eq("instruction.calendar.id", calendar.getId()));
        DataRealmUtils.addDataRealm(query, new String[] { "instruction.std.type.id",
                "instruction.std.department.id" }, dataRealm);
        query.add(new Condition("instruction.isGraduation=:isGraduation", isGraduateInstruction));
        query.groupBy("instruction.std.type.id").groupBy("instruction.tutor.id");
        query.setSelect("count(*),instruction.std.type.id,instruction.tutor.id");
        List rs = (List) utilDao.search(query);
        for (Iterator iter = rs.iterator(); iter.hasNext();) {
            Object[] data = (Object[]) iter.next();
            data[1] = utilDao.get(StudentType.class, (Long) data[1]);
            data[2] = utilDao.get(Teacher.class, (Long) data[2]);
        }
        return rs;
    }
}
