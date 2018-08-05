package com.shufe.model.degree.tutorManager;

import java.util.HashSet;
import java.util.Set;

import com.ekingstar.commons.model.pojo.LongIdObject;
import com.shufe.model.std.Student;
import com.shufe.model.system.baseinfo.Tutor;
/**
 * 指定导师
 * @author 塞外狂人
 *
 */
public class ChooseTutor extends LongIdObject {

	private static final long serialVersionUID = 521080543147003786L;
	private Student std = new Student();
	private Set tutorSet = new HashSet();
	
	public Student getStd() {
		return std;
	}
	public void setStd(Student std) {
		this.std = std;
	}
	public Set getTutorSet() {
		return tutorSet;
	}
	public void setTutorSet(Set tutorSet) {
		this.tutorSet = tutorSet;
	}
	public ChooseTutor() {
		super();
	}	
	public ChooseTutor(Student std, Tutor tutor) {
		super();
		this.std = std;
		this.tutorSet.add(tutor);
	}
	
	
}
