package com.kaviju.accesscontrol.service;

import com.kaviju.accesscontrol.model.KAUser;
import com.webobjects.appserver.*;
import com.webobjects.foundation.NSMutableArray;

import er.extensions.foundation.ERXProperties;

public class UserAccessControlService {

	private KAUser realUser;
	private KAUser currentUser;
	private NSMutableArray<KAUser> userStack = new NSMutableArray<KAUser>();

	public UserAccessControlService() {
		super();
	}

	public KAUser realUser() {
		return realUser;
	}

	public KAUser currentUser() {
		return currentUser;
	}

	public boolean isUserLoggedIn() {
		return currentUser != null;
	}

	public WOComponent logonAsUserInContext(KAUser user, WOContext context) {
		if (user == null) {
			throw new IllegalArgumentException("logonAsUser does not accept null user.");
		}
		if (realUser != null) {
			throw new IllegalStateException("Trying to logonAsUser when already logged.");
		}
		realUser = user;
		currentUser = user;
		return createHomePage(context);
	}
	
	public WOComponent createHomePage(WOContext context) {
		return currentUser().createHomePage(context);
	}

	public WOComponent personifyUserInContext(KAUser user, WOContext context) {
		userStack.add(currentUser);
		currentUser = user;
		return user.createHomePage(context);
	}

	public WOComponent logoutInContext(WOContext context) {
		if (userStack.count() == 0) {
			WOComponent newPage = createLogedOutPage(context);
			return newPage;			
		}
		currentUser = userStack.removeLastObject();
		return currentUser.createHomePage(context);
	}

	public WOComponent createLogedOutPage(WOContext context) {
		String logedOutPageName = ERXProperties.stringForKeyWithDefault("ka.accesscontrol.logedOutPageName", "LoggedOut");
		return WOApplication.application().pageWithName(logedOutPageName, context);
	}
}
