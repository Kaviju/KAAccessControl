package com.kaviju.accesscontrol.component;

import com.kaviju.accesscontrol.model.KARoleGroup;
import com.kaviju.accesscontrol.model.KAUserProfile;
import com.webobjects.appserver.WOContext;
import com.webobjects.appserver.WOResponse;
import com.webobjects.foundation.NSArray;

import er.extensions.components.*;

import com.kaviju.accesscontrol.model.KARole;

@SuppressWarnings("serial")
public class UserPermProfileEditor extends ERXNonSynchronizingComponent {
	private KAUserProfile userProfile;
	private NSArray<KARoleGroup> groups;
	private KARoleGroup group;
	private KARole role;

	public UserPermProfileEditor(WOContext context) {
        super(context);
    }
	
	@Override
	protected void preAppendToResponse(WOResponse response, WOContext context) {
		super.preAppendToResponse(response, context);
		userProfile = (KAUserProfile) valueForBinding("userProfile");
		if (userProfile.profile() == null) {
			groups = new NSArray<KARoleGroup>();
		}
		else {
			groups = userProfile.profile().displayedGroups();
		}
	}

	public KAUserProfile userProfile() {
		return userProfile;
	}

	public NSArray<KARoleGroup> groups() {
		return groups;
	}

	public KARoleGroup group() {
		return group;
	}

	public void setGroup(KARoleGroup group) {
		this.group = group;
	}

	public NSArray<KARole> rolesInGroup() {
		return userProfile().profile().displayedRolesForGroup(group());
	}

	public KARole role() {
		return role;
	}

	public void setRole(KARole role) {
		this.role = role;
	}
}