package com.kaviju.accesscontrol.service;

import com.kaviju.accesscontrol.model.KAUserProfile;

public interface UserLogonDelegate {
	public void userProfileDidLogon(KAUserProfile userProfile);
}
