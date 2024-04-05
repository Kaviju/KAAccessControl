package com.kaviju.accesscontrol.model;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import com.webobjects.foundation.NSArray;

@SuppressWarnings("serial")
public class KAAccessListItem extends com.kaviju.accesscontrol.model.base._KAAccessListItem {
	@SuppressWarnings("unused")
	private static Logger log = LoggerFactory.getLogger(KAAccessListItem.class);
	
	public NSArray<? extends KAUserProfile>fetchUserProfileWithItemForRole(String roleCode) {
		KARole role = KARole.roleWithCode(editingContext(), roleCode);
		
		NSArray<KAUserProfileRole> userProfileRoles = KAUserProfileRole.fetchKAUserProfileRoles(editingContext(), KAUserProfileRole.ROLE.eq(role), null);
		userProfileRoles = KAUserProfileRole.LIST_ITEMS.containsObject(this).filtered(userProfileRoles);
		
		return KAUserProfileRole.USER_PROFILE.arrayValueInObject(userProfileRoles);
	}
}
