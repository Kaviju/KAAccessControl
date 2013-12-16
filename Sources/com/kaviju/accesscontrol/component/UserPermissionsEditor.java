package com.kaviju.accesscontrol.component;

import com.kaviju.accesscontrol.model.KAProfile;
import com.kaviju.accesscontrol.model.KAUser;
import com.kaviju.accesscontrol.model.KAUserProfile;
import com.webobjects.appserver.WOContext;
import com.webobjects.foundation.NSArray;

import er.extensions.components.ERXComponent;

@SuppressWarnings("serial")
public class UserPermissionsEditor extends ERXComponent {
	private KAUser user;
    private NSArray<KAProfile> profiles;
	private KAProfile profile;
	private KAUserProfile userProfile;

	public UserPermissionsEditor(WOContext context) {
        super(context);
    }

	public KAUser user() {
		return user;
	}

	public void setUser(KAUser user) {
		if (this.user != user) {
			this.user = user;
	        profiles = KAProfile.fetchProfiles(user.editingContext());
		}
	}

	public NSArray<KAProfile> profiles() {
		return profiles;
	}

	public KAProfile profile() {
		return profile;
	}

	public void setProfile(KAProfile profile) {
		this.profile = profile;
	}

	public String userProfileEditorID() {
		return "userProfileEditor";
	}
	
	public KAUserProfile userProfile() {
		return user.currentUserProfile();
	}

	public void setUserProfile(KAUserProfile userProfile) {
		this.userProfile = userProfile;
	}

	public String selectProfileString() {
		return localizer().localizedStringForKeyWithDefault("UserPermissionEditor.selectProfile");
	}

}