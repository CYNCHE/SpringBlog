package com.cyn.service;

import com.cyn.po.User;

public interface UserService {
	User checkUser(String username, String password);
	
}
