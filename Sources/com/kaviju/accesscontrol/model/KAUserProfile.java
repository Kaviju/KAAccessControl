package com.kaviju.accesscontrol.model;

import java.util.*;

import org.apache.log4j.Logger;

import com.webobjects.appserver.*;
import com.webobjects.eocontrol.EOEnterpriseObject;
import com.webobjects.foundation.NSArray;

@SuppressWarnings("serial")
public class KAUserProfile extends com.kaviju.accesscontrol.model.base._KAUserProfile {
	@SuppressWarnings("unused")
	private static Logger log = Logger.getLogger(KAUserProfile.class);
	private Set<String> allEffectiveRoles;

	@SuppressWarnings("unchecked")
	public <U extends KAUser> U user(Class<U> userClass) {
		return (U) user();
	}
	
	public String profileCode() {
		return profile().code();
	}
	
	public boolean hasRole(String roleCode) {
		return allEffectiveRoles().contains(roleCode);
	}

	public boolean hasAtLeastOneOfTheseRoles(String ...roleCodes) {
		return hasAtLeastOneOfTheseRoles(Arrays.asList(roleCodes));
	}
	
	public boolean hasAtLeastOneOfTheseRoles(Collection<String> rolesCodes) {
		for (String roleCode : rolesCodes) {
			if (hasRole(roleCode)) {
				return true;
			}
		}
		return false;
	}

	public boolean hasAllTheseRoles(Collection<String> requiredRoleCodes) {
		for (String roleCode : requiredRoleCodes) {
			if ( !hasRole(roleCode)) {
				return false;
			}
		}
		return true;
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
		for (KAUserProfileRole userProfileRole : roles().immutableClone()) {
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
		
	public NSArray<KAAccessListItem> listItemsForRole(KARole role) {
		return userProfileRoleWithCode(role.code()).listItems();
	}

	public void addItemForRole(KAAccessListItem item, KARole role) {
		userProfileRoleWithCode(role.code()).addToListItems(item);		
	}
	public void removeItemForRole(KAAccessListItem item, KARole role) {
		userProfileRoleWithCode(role.code()).removeFromListItems(item);		
	}

	private KAUserProfileRole userProfileRoleWithCode(String roleCode) {
		for (KAUserProfileRole userProfileRole : roles()) {
			if (userProfileRole.role().code().equals(roleCode)) {
				return userProfileRole;
			}
		}
		return null;
	}

	public WOComponent createHomePage(WOContext context) {
		return user().createHomePageForUserProfile(context, this);
	}
}
