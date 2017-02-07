package org.p1.dao;

import java.util.List;

public interface IAuthorizationDAO {
	
	List<User> getUserHavingRole(String roleName);
	
	List<User> getUserRoles(String loginName);
}
