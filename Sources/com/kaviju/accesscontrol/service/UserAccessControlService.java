package com.kaviju.accesscontrol.service;

import com.kaviju.accesscontrol.model.KAUser;
import com.kaviju.accesscontrol.model.KAUserProfile;
import com.webobjects.appserver.*;
import com.webobjects.foundation.NSMutableArray;

import er.extensions.foundation.ERXProperties;

public class UserAccessControlService<U extends KAUser> {

	private U realUser;
	private U currentUser;
	private NSMutableArray<U> userStack = new NSMutableArray<U>();

	public UserAccessControlService() {
		super();
	}

	public U realUser() {
		return realUser;
	}

	public U currentUser() {
		return currentUser;
	}

	public KAUserProfile currentUserProfile() {
		return currentUser.currentUserProfile();
	}

	public boolean isUserLoggedIn() {
		return currentUser != null;
	}

	public WOComponent logonAsUserInContext(U user, WOContext context) {
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

	public WOComponent personifyUserInContext(U user, WOContext context) {
		userStack.add(currentUser);
		currentUser = user;
		return user.createHomePage(context);
	}

	public WOComponent logoutInContext(WOContext context) {
		if (userStack.count() == 0) {
			context.session().terminate();
			currentUser = null;
			realUser = null;
			WOComponent newPage = createLoggedOutPage(context);
			return newPage;			
		}
		currentUser = userStack.removeLastObject();
		return currentUser.createHomePage(context);
	}

	public WOComponent createLoggedOutPage(WOContext context) {
		String logedOutPageName = ERXProperties.stringForKeyWithDefault("ka.accesscontrol.loggedOutPageName", "LoggedOut");
		return WOApplication.application().pageWithName(logedOutPageName, context);
	}
}
