package com.kaviju.accesscontrol.model;

import org.apache.log4j.Logger;

import com.kaviju.accesscontrol.authentication.*;
import com.webobjects.appserver.*;
import com.webobjects.eoaccess.EOEntity;
import com.webobjects.foundation.NSArray;

import er.extensions.eof.ERXEOControlUtilities;

@SuppressWarnings("serial")
public abstract class KAUser extends com.kaviju.accesscontrol.model.base._KAUser {
	@SuppressWarnings("unused")
	private static Logger log = Logger.getLogger(KAUser.class);
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
			defaultUserProfile = createProfilesRelationship();
			defaultUserProfile.setUserRelationship(this);
			defaultUserProfile.setIsDefaultProfile(true);
		}
		return defaultUserProfile;
	}	
		
	@Override
	public String toString() {
		return "KAUser with id:"+primaryKey();
	}
	
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
