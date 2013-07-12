package com.kaviju.accesscontrol.model;

import org.apache.log4j.Logger;

import com.webobjects.eocontrol.EOEnterpriseObject;
import com.webobjects.foundation.NSArray;

@SuppressWarnings("serial")
public class KAUserProfile extends com.kaviju.accesscontrol.model.base._KAUserProfile {
	@SuppressWarnings("unused")
	private static Logger log = Logger.getLogger(KAUserProfile.class);

	public boolean hasRole(String roleCode) {
		for (KAUserProfileRole profileRole : roles()) {
			if (profileRole.role().code().equals(roleCode)) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public void setProfile(KAProfile value) {
		deleteAllRolesRelationships();
		for (KARole role : value.roles()) {
			KAUserProfileRole newUserProfileRole = createRolesRelationship();
			newUserProfileRole.setRole(role);
		}
		super.setProfile(value);
	}

	public NSArray<String> itemCodesForRole(String roleCode) {
		KAUserProfileRole foundUserProfileRole = userProfileRoleWithCode(roleCode);
		if (foundUserProfileRole != null) {
			return foundUserProfileRole.itemCodes();
		}
		return new NSArray<String>();
	}

	public <T extends EOEnterpriseObject> NSArray<T> itemsAsObjectsForRole(Class<T> entityClass, String roleCode) {
		KAUserProfileRole foundUserProfileRole = userProfileRoleWithCode(roleCode);
		if (foundUserProfileRole != null) {
			return foundUserProfileRole.itemsAsObjects(entityClass);
		}
		return new NSArray<T>();
	}

	private KAUserProfileRole userProfileRoleWithCode(String roleCode) {
		NSArray<KAUserProfileRole> foundRoles = roles(KAUserProfileRole.ROLE.dot(KARole.CODE).eq(roleCode));
		if (foundRoles.count() > 0) {
			return foundRoles.objectAtIndex(0);
		}
		return null;
	}
}
