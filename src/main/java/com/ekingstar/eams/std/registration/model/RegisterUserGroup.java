package com.ekingstar.eams.std.registration.model;

import java.sql.Timestamp;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.ekingstar.commons.model.pojo.LongIdObject;
import com.ekingstar.security.User;
/**
 * 注册用户组
 * @author Administrator
 *
 */
public class RegisterUserGroup extends LongIdObject {

	private static final long serialVersionUID = 2952574014379221676L;
	
	private String name;
	
	private Timestamp beginAt;
	
	private Timestamp endAt;

	private Set users;
	
	public Timestamp getBeginAt() {
		return beginAt;
	}

	public void setBeginAt(Timestamp beginAt) {
		this.beginAt = beginAt;
	}

	public Timestamp getEndAt() {
		return endAt;
	}

	public void setEndAt(Timestamp endAt) {
		this.endAt = endAt;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set getUsers() {
		return users;
	}

	public void setUsers(Set users) {
		this.users = users;
	}
	public void addUser(List users){
		for (Iterator iter = users.iterator(); iter.hasNext();) {
			User element = (User) iter.next();
			if(!this.users.contains(element))
			this.users.add(element);
		}
	}
	public void removeUsers(List users){
		for (Iterator iter = users.iterator(); iter.hasNext();) {
			User element = (User) iter.next();
			if(this.users.contains(element))
			this.users.remove(element);
		}
	}
}
