package com.kaviju.accesscontrol.component;

import com.kaviju.accesscontrol.model.KARoleGroup;
import com.kaviju.accesscontrol.model.KAUserProfile;
import com.webobjects.appserver.WOContext;
import com.webobjects.appserver.WOResponse;
import com.webobjects.foundation.NSArray;

import er.extensions.components.ERXComponent;
import com.kaviju.accesscontrol.model.KARole;

@SuppressWarnings("serial")
public class UserPermProfileEditor extends ERXComponent {
	private KAUserProfile userProfile;
	private NSArray<KARoleGroup> groups;
	private KARoleGroup group;
	private KARole role;

	public UserPermProfileEditor(WOContext context) {
        super(context);
    }

	public KAUserProfile userProfile() {
		return userProfile;
	}

	public void setUserProfile(KAUserProfile userProfile) {
		this.userProfile = userProfile;
	}
	
	@Override
	public void appendToResponse(WOResponse response, WOContext context) {
		groups = KARoleGroup.fetchGroupsForUserProfile(userProfile);
		super.appendToResponse(response, context);
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
		return group().displayedRolesForProfile(userProfile().profile());
	}

	public KARole role() {
		return role;
	}

	public void setRole(KARole role) {
		this.role = role;
	}
}