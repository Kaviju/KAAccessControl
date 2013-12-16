package com.kaviju.accesscontrol.service;

import com.kaviju.accesscontrol.model.KAUser;

public interface UserLogonDelegate<U extends KAUser> {
	public void userDidLogon(U user);
}
