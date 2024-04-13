package com.kaviju.accesscontrol.component;

import com.kaviju.accesscontrol.model.KAProfile;
import com.kaviju.accesscontrol.model.KAUser;
import com.kaviju.accesscontrol.model.KAUserProfile;
import com.webobjects.appserver.*;
import com.webobjects.foundation.NSArray;

import er.ajax.AjaxModalDialog;
import er.extensions.components.*;
import com.webobjects.appserver.WOActionResults;

@SuppressWarnings("serial")
public class UserPermissionsEditor extends ERXNonSynchronizingComponent {
	private KAUser user;
    private NSArray<KAProfile> profiles;
	private KAProfile profile;
	private KAUserProfile userProfile;

	public UserPermissionsEditor(WOContext context) {
        super(context);
    }
	
	@Override
	protected void preAppendToResponse(WOResponse response, WOContext context) {
		super.preAppendToResponse(response, context);
		setUser((KAUser) valueForBinding("user"));
	}

	public KAUser user() {
		return user;
	}

	public void setUser(KAUser user) {
		if (this.user != user) {
			this.user = user;
	        profiles = KAProfile.fetchProfiles(user.editingContext());
	        profiles = KAProfile.LOCALIZED_NAME.ascs().sorted(profiles);
	        setUserProfile(user.defaultUserProfile());
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
		return userProfile;
	}

	public void setUserProfile(KAUserProfile userProfile) {
		this.userProfile = userProfile;
	}

	public String selectProfileString() {
		if (userProfile().profile() != null) {
			return null;  // If already selected, does not allow NULL.
		}
		return localizer().localizedStringForKeyWithDefault("UserPermissionEditor.selectProfile");
	}

	public WOActionResults autosave() {
		if (booleanValueForBinding("autoSave", false)) {
			userProfile.editingContext().saveChanges();
		}
		return null;
	}

	public WOActionResults refresh() {
		if (AjaxModalDialog.isInDialog(context())) {
			AjaxModalDialog.update(context(), null);
		}
		return null;
	}

}