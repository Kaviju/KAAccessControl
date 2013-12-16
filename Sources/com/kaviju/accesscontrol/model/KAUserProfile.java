package com.kaviju.accesscontrol.model;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;

import com.webobjects.eocontrol.EOEnterpriseObject;
import com.webobjects.foundation.NSArray;

@SuppressWarnings("serial")
public class KAUserProfile extends com.kaviju.accesscontrol.model.base._KAUserProfile {
	@SuppressWarnings("unused")
	private static Logger log = Logger.getLogger(KAUserProfile.class);
	private Set<String> allEffectiveRoles;

	public String profileCode() {
		return profile().code();
	}
	
	public boolean hasRole(String roleCode) {
		return allEffectiveRoles().contains(roleCode);
	}
	
	public Set<String> allEffectiveRoles() {
		if (allEffectiveRoles == null) {
			allEffectiveRoles = new HashSet<>();
			if (profile() != null) {
				allEffectiveRoles.addAll(KARole.CODE.arrayValueInObject(profile().roles()));
			}
			allEffectiveRoles.addAll(KAUserProfileRole.ROLE.dot(KARole.CODE).arrayValueInObject(roles()));
		}
		return Collections.unmodifiableSet(allEffectiveRoles);
	}

	@Override
	public void setProfile(KAProfile value) {
		deleteAllRolesRelationships();
		for (KARole role : value.roles()) {
			addRole(role);
		}
		super.setProfile(value);
		clearAllEffectivesRoles();
	}

	public void addRole(KARole role) {
		KAUserProfileRole newUserProfileRole = createRolesRelationship();
		newUserProfileRole.setRole(role);
		clearAllEffectivesRoles();
	}
	
	public void removeRole(KARole role) {
		for (KAUserProfileRole userProfileRole : roles()) {
			if (userProfileRole.role().equals(role)) {
				deleteRolesRelationship(userProfileRole);
			}
		}
		clearAllEffectivesRoles();
	}

	private void clearAllEffectivesRoles() {
		allEffectiveRoles = null;		
	}

	public void setProfileWithCode(String profileCode) {
		KAProfile profile = KAProfile.profileWithCode(editingContext(), profileCode);
		setProfile(profile);
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

	public NSArray<KAAccessListItem> listItemsForRole(KARole role) {
		return userProfileRoleWithCode(role.code()).listItems();
	}

	public void addItemForRole(KAAccessListItem item, KARole role) {
		userProfileRoleWithCode(role.code()).addToListItems(item);		
	}
	public void removeItemForRole(KAAccessListItem item, KARole role) {
		userProfileRoleWithCode(role.code()).removeFromListItems(item);		
	}
}
