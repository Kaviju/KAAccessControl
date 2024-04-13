package com.kaviju.accesscontrol.model;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import com.kaviju.accesscontrol.authentication.*;
import com.webobjects.appserver.*;
import com.webobjects.eoaccess.EOEntity;
import com.webobjects.foundation.NSArray;

import er.extensions.eof.ERXEOControlUtilities;

@SuppressWarnings("serial")
public abstract class KAUser extends com.kaviju.accesscontrol.model.base._KAUser {
	@SuppressWarnings("unused")
	private static Logger log = LoggerFactory.getLogger(KAUser.class);
	private static PasswordHasher defaultPasswordHasher;
	private static PasswordHasher currentPasswordHasher;
	
	abstract public WOComponent createHomePageForUserProfile(WOContext context, KAUserProfile userProfile);
	
	public static void setDefaultPasswordHasher(PasswordHasher hasher) {
		defaultPasswordHasher = hasher;
	}

	public static void setCurrentPasswordHasher(PasswordHasher hasher) {
		currentPasswordHasher = hasher;
	}
	
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

	public boolean authenticateWithPasswordAndUpgradeHashIfRequired(String password) {
		boolean result = authenticateWithPassword(password);
		if (result) {
			PasswordHash hash = PasswordHash.parseHashWithDefaultHasher(passwordHash(), defaultPasswordHasher);
			if (currentPasswordHasher.hasCreatedHash(hash) == false) {
				changePassword(password);
				editingContext().saveChanges();
			}
		}
		return result;
	}	

	public KAUserProfile defaultUserProfile() {
		KAUserProfile defaultUserProfile = null;
		if (defaultUserProfile == null) {
			NSArray<KAUserProfile> profiles = profiles(KAUserProfile.IS_DEFAULT_PROFILE.eq(true));
			if (profiles.count() > 0) {
				defaultUserProfile = profiles.objectAtIndex(0);
			}
		}
		if (defaultUserProfile == null) {
			NSArray<KAUserProfile> profiles = profiles();
			if (profiles.count() > 0) {
				defaultUserProfile = profiles.objectAtIndex(0);
			}
		}
		if (defaultUserProfile == null) {
			defaultUserProfile = createProfileWithDefaultEntity();
			defaultUserProfile.setIsDefaultProfile(true);
		}
		return defaultUserProfile;
	}

	public KAUserProfile createProfileWithDefaultEntity() {
		KAUserProfile defaultUserProfile = KAUserProfileDefaultEntity.createKAUserProfileDefaultEntity(editingContext());
		defaultUserProfile.setUserRelationship(this);
		return defaultUserProfile;
	}	
		
	@Override
	public String toString() {
		return "KAUser with id:"+primaryKey();
	}
	
	// We do not know the user table name in the model so we set it with the real entity table name at runtime so the insert is done correctly.
	static boolean externalNameSet = false;
	@Override
	public void willInsert() {
		if (externalNameSet == false) {
			EOEntity rootEntity = ERXEOControlUtilities.rootEntity(this);
			rootEntity.setExternalName(entity().externalName());
			externalNameSet = true;
		}
		super.willInsert();
	}
}
