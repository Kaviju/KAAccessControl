package com.kaviju.accesscontrol.model;

import java.util.Collection;

import org.apache.log4j.Logger;

import com.kaviju.accesscontrol.authentication.*;
import com.webobjects.appserver.*;
import com.webobjects.eoaccess.EOEntity;
import com.webobjects.eocontrol.EOEnterpriseObject;
import com.webobjects.foundation.NSArray;

import er.extensions.eof.ERXEOControlUtilities;

@SuppressWarnings("serial")
public abstract class KAUser extends com.kaviju.accesscontrol.model.base._KAUser {
	@SuppressWarnings("unused")
	private static Logger log = Logger.getLogger(KAUser.class);
	private static PasswordHasher defaultPasswordHasher;
	private static PasswordHasher currentPasswordHasher;
	
	abstract public WOComponent createHomePage(WOContext context);
	
	public static void setDefaultPasswordHasher(PasswordHasher hasher) {
		defaultPasswordHasher = hasher;
	}

	public static void setCurrentPasswordHasher(PasswordHasher hasher) {
		currentPasswordHasher = hasher;
	}

	private KAUserProfile currentUserProfile;
	
	public void changePassword(String newPassword) {
		if (currentPasswordHasher == null) {
			throw new RuntimeException("KAUser current password haser not set, use KAUser.setCurrentPasswordHasher(hasher) in your application init to set it.");
		}
		PasswordHash newHash = currentPasswordHasher.hashPassword(newPassword);
		setPasswordHash(newHash.toString());
	}

	public boolean authenticateWithPassword(String password) {
		String hashString = passwordHash();
		if (hashString == null) {
			return false;
		}
		PasswordHash hash = PasswordHash.parseHashWithDefaultHasher(hashString, defaultPasswordHasher);
		return hash.verifyPassword(password);
	}	
	
	public KAUserProfile currentUserProfile() {
		if (currentUserProfile == null) {
			NSArray<KAUserProfile> profiles = profiles(KAUserProfile.IS_DEFAULT_PROFILE.eq(true));
			if (profiles.count() > 0) {
				currentUserProfile = profiles.objectAtIndex(0);
			}
		}
		if (currentUserProfile == null) {
			NSArray<KAUserProfile> profiles = profiles();
			if (profiles.count() > 0) {
				currentUserProfile = profiles.objectAtIndex(0);
			}
		}
		if (currentUserProfile == null) {
			currentUserProfile = createProfilesRelationship();
			currentUserProfile.setIsDefaultProfile(true);
		}
		return currentUserProfile;
	}
	
	public void setCurrentUserProfile(KAUserProfile profile) {
		if (profiles().containsObject(profile)) {
			currentUserProfile = profile;
		}
		else {
			throw new IllegalArgumentException("Cannot set a profile from another user or editing context as current profile.");
		}
	}
		
	@Override
	public String toString() {
		return "KAUser with id:"+primaryKey()+" username: "+userName();
	}

	public boolean hasRole(String roleCode) {
		return currentUserProfile().hasRole(roleCode);
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
	
	public NSArray<String> itemCodesForRole(String roleCode) {
		return currentUserProfile().itemCodesForRole(roleCode);
	}

	public <T extends EOEnterpriseObject> NSArray<T> itemsAsObjectsForRole(Class<T> entityClass, String roleCode) {
		return currentUserProfile().itemsAsObjectsForRole(entityClass, roleCode);
	}
	
	@Override
	public void willInsert() {
		EOEntity rootEntity = ERXEOControlUtilities.rootEntity(this);
		rootEntity.setExternalName(entity().externalName());
		super.willInsert();
	}

}
