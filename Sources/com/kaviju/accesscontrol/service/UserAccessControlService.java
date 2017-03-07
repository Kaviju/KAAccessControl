package com.kaviju.accesscontrol.service;

import com.kaviju.accesscontrol.model.*;
import com.webobjects.appserver.*;
import com.webobjects.foundation.*;

import er.extensions.foundation.*;

public class UserAccessControlService<U extends KAUser> {

	protected static final String UserAccessControlServiceThreadStorageKey = "UserAccessControlServiceThreadStorageKey";
	private U realUser;
	private KAUserProfile currentUserProfile;
	private boolean profileSelectedByUser;
	private NSMutableArray<UserStackEntry<U>> userStack = new NSMutableArray<UserStackEntry<U>>();
	private final WOSession session;

	private UserAccessControlService() {
		this.session = null;
    	ERXThreadStorage.takeValueForKey(this, UserAccessControlServiceThreadStorageKey);
	}

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

	@SuppressWarnings("unchecked")
	static public <U extends KAUser> UserAccessControlService<U> currentService() {
    	UserAccessControlService<U> service;
    	service = (UserAccessControlService<U>) ERXThreadStorage.valueForKey(UserAccessControlServiceThreadStorageKey);
    	if (service == null) {
    		service = new UserAccessControlService<U>();
    	}
    	return service;
    }

	public U realUser() {
		return realUser;
	}

	@SuppressWarnings("unchecked")
	public U currentUser() {
		if (currentUserProfile == null) {
			return null;
		}
		return (U) currentUserProfile.user();
	}

	@SuppressWarnings("unchecked")
	public <T extends KAUser> T currentUser(Class<T> userClass) {
		return (T) currentUser();
	}
	
	@SuppressWarnings("unchecked")
	public <T extends KAUserProfile> T currentUserProfile() {
		return (T) currentUserProfile;
	}
	
	@SuppressWarnings("unchecked")
	public <T extends KAUserProfile> T currentUserProfile(Class<T> userProfileClass) {
		return (T) currentUserProfile;
	}

	public void setCurrentUserProfile(KAUserProfile profile) {
		if ( currentUserProfile.user().equals(profile.user()) == false ) {
			throw new IllegalArgumentException("Cannot set a profile from another user or editing context as current profile.");
		}
		currentUserProfile = profile;
	}
	
	public boolean profileSelectedByUser() {
		return profileSelectedByUser;
	}

	public boolean currentUserHasRole(String roleCode) {
		if (currentUserProfile() == null) {
			return false;
		}
		return currentUserProfile().hasRole(roleCode);
	}

	public boolean isUserLoggedIn() {
		return currentUserProfile != null;
	}

	public WOComponent logonAsUser(U user) {
		if (user == null) {
			throw new IllegalArgumentException("logonAsUser does not accept null user.");
		}
		if (realUser != null) {
			throw new IllegalStateException("Trying to logonAsUser when already logged.");
		}
		realUser = user;
		currentUserProfile = user.defaultUserProfile();
		if (user.profiles().count() > 1) {
			profileSelectedByUser = false;
		}
		else {
			profileSelectedByUser = true;
		}
		return createHomePage();
	}
	
	public WOComponent createHomePage() {
		if (session instanceof UserLogonDelegate) {
			((UserLogonDelegate)session).userProfileDidLogon(currentUserProfile());
		}
		return currentUserProfile().createHomePage(session.context());
	}

	public WOComponent personifyUser(U user) {
		userStack.add(new UserStackEntry<U>(currentUserProfile, profileSelectedByUser, session.context().page()));
		currentUserProfile = user.defaultUserProfile();
		if (user.profiles().count() > 1) {
			profileSelectedByUser = false;
		}
		else {
			profileSelectedByUser = true;
		}
		return createHomePage();
	}

	public WOComponent personifyUserProfile(KAUserProfile userProfile) {
		userStack.add(new UserStackEntry<U>(currentUserProfile, profileSelectedByUser, session.context().page()));
		currentUserProfile = userProfile;
		profileSelectedByUser = true;
		return createHomePage();
	}

	public boolean isPersonifying() {
		return userStack.count() > 0;
	}

	public WOComponent logout() {
		if (userStack.count() == 0) {
			session.terminate();
			currentUserProfile = null;
			realUser = null;
			WOComponent newPage = createLoggedOutPage();
			return newPage;			
		}
		UserStackEntry<U> lastUserEntry = userStack.removeLastObject();
		currentUserProfile = lastUserEntry.userProfile;
		profileSelectedByUser = lastUserEntry.profileSelectedByUser;
		
		if (session instanceof UserLogonDelegate) {
			((UserLogonDelegate)session).userProfileDidLogon(currentUserProfile());
		}
		return lastUserEntry.page;
	}

	public WOComponent createLoggedOutPage() {
		String logedOutPageName = ERXProperties.stringForKeyWithDefault("ka.accesscontrol.loggedOutPageName", "LoggedOut");
		return WOApplication.application().pageWithName(logedOutPageName, session.context());
	}
	
	static private class UserStackEntry<U extends KAUser> {
		public final KAUserProfile userProfile;
		public final boolean profileSelectedByUser;
		public final WOComponent page;
		
		public UserStackEntry(KAUserProfile userProfile, boolean profileSelectedByUser, WOComponent page) {
			this.userProfile = userProfile;
			this.profileSelectedByUser = profileSelectedByUser;
			this.page = page;
		}
	}
}
