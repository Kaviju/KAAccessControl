package com.kaviju.accesscontrol.service;

import com.kaviju.accesscontrol.model.KAUser;
import com.kaviju.accesscontrol.model.KAUserProfile;
import com.webobjects.appserver.WOApplication;
import com.webobjects.appserver.WOComponent;
import com.webobjects.appserver.WOSession;
import com.webobjects.foundation.NSMutableArray;
import com.webobjects.foundation.NSNotification;
import com.webobjects.foundation.NSNotificationCenter;

import er.extensions.foundation.ERXProperties;
import er.extensions.foundation.ERXSelectorUtilities;
import er.extensions.foundation.ERXThreadStorage;

public class UserAccessControlService<U extends KAUser> {

	protected static final String UserAccessControlServiceThreadStorageKey = "UserAccessControlServiceThreadStorageKey";
	private U realUser;
	private U currentUser;
	private NSMutableArray<UserStackEntry<U>> userStack = new NSMutableArray<UserStackEntry<U>>();
	private final WOSession session;

	public UserAccessControlService(WOSession session) {
		this.session = session;
		registerForNotification();
    	ERXThreadStorage.takeValueForKey(this, UserAccessControlServiceThreadStorageKey);
	}
	
	private void registerForNotification() {
        NSNotificationCenter.defaultCenter().addObserver(this, 
                ERXSelectorUtilities.notificationSelector("sessionDidRestore"), 
                WOSession.SessionDidRestoreNotification, session);
	}

    public void sessionDidRestore(NSNotification n) {
    	ERXThreadStorage.takeValueForKey(this, UserAccessControlServiceThreadStorageKey);
    }
    
    @SuppressWarnings("unchecked")
	static public <T extends KAUser> UserAccessControlService<T> currentService(Class<T> userClass) {
    	return (UserAccessControlService<T>) ERXThreadStorage.valueForKey(UserAccessControlServiceThreadStorageKey);
    }

	static public <T extends KAUser> T currentUser(Class<T> userClass) {
		UserAccessControlService<T> service = currentService(userClass);
		if (service == null) {
			return null;
		}
    	return service.currentUser();
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

	public WOComponent logonAsUser(U user) {
		if (user == null) {
			throw new IllegalArgumentException("logonAsUser does not accept null user.");
		}
		if (realUser != null) {
			throw new IllegalStateException("Trying to logonAsUser when already logged.");
		}
		realUser = user;
		currentUser = user;
		return createHomePage();
	}
	
	@SuppressWarnings("unchecked")
	public WOComponent createHomePage() {
		if (session instanceof UserLogonDelegate) {
			((UserLogonDelegate<U>)session).userDidLogon(currentUser());
		}
		return currentUser().createHomePage(session.context());
	}

	public WOComponent personifyUser(U user) {
		userStack.add(new UserStackEntry<U>(currentUser, session.context().page()));
		currentUser = user;
		return createHomePage();
	}

	@SuppressWarnings("unchecked")
	public WOComponent logout() {
		if (userStack.count() == 0) {
			session.terminate();
			currentUser = null;
			realUser = null;
			WOComponent newPage = createLoggedOutPage();
			return newPage;			
		}
		UserStackEntry<U> lastUserEntry = userStack.removeLastObject();
		currentUser = lastUserEntry.user;
		if (session instanceof UserLogonDelegate) {
			((UserLogonDelegate<U>)session).userDidLogon(currentUser());
		}
		return lastUserEntry.page;
	}

	public WOComponent createLoggedOutPage() {
		String logedOutPageName = ERXProperties.stringForKeyWithDefault("ka.accesscontrol.loggedOutPageName", "LoggedOut");
		return WOApplication.application().pageWithName(logedOutPageName, session.context());
	}
	
	static private class UserStackEntry<U> {
		public final U user;
		public final WOComponent page;
		
		public UserStackEntry(U user, WOComponent page) {
			this.user = user;
			this.page = page;
		}
	}
}
