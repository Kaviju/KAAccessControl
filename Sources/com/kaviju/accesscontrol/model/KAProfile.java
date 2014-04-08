package com.kaviju.accesscontrol.model;

import org.apache.log4j.Logger;

import com.webobjects.eocontrol.EOEditingContext;
import com.webobjects.foundation.NSArray;

import er.extensions.eof.ERXKey;
import er.extensions.foundation.ERXArrayUtilities;
import er.extensions.localization.ERXLocalizer;

@SuppressWarnings("serial")
public class KAProfile extends com.kaviju.accesscontrol.model.base._KAProfile {
	@SuppressWarnings("unused")
	private static Logger log = Logger.getLogger(KAProfile.class);
	public static final ERXKey<String> LOCALIZED_NAME = new ERXKey<String>("localizedName");
	
	static public KAProfile profileWithCode(EOEditingContext ec, String code) {
		return fetchRequiredKAProfile(ec, CODE.eq(code));
	}
	
	public String localizedName() {
		return ERXLocalizer.currentLocalizer().localizedStringForKeyWithDefault("Profile."+code());
	}

	public static NSArray<KAProfile> fetchProfiles(EOEditingContext editingContext) {
		NSArray<KAProfile> profiles = fetchAllKAProfiles(editingContext);
		
		return LOCALIZED_NAME.asc().sorted(profiles);
	}
	
	public NSArray<KARole> allPossibleRoles() {
		return KAProfileRole.ROLE.arrayValueInObject(profileRoles());
	}

	public NSArray<KARole> mandatoryRoles() {
		return KAProfileRole.ROLE.arrayValueInObject(profileRoles(KAProfileRole.IS_OPTIONAL.eq(false)));
	}

	public NSArray<KARole>optionalRoles() {
		return KAProfileRole.ROLE.arrayValueInObject(profileRoles(KAProfileRole.IS_OPTIONAL.eq(true)));
	}

	public void addMandatoryRole(KARole role) {
		KAProfileRole profileRole = profileRoleForRole(role);
		profileRole.setIsOptional(false);
	}
	
	public void addOptionalRole(KARole role) {
		KAProfileRole profileRole = profileRoleForRole(role);
		profileRole.setIsOptional(true);
	}

	private KAProfileRole profileRoleForRole(KARole role) {
		for (KAProfileRole profileRole : profileRoles()) {
			if (role.equals(profileRole.role())) {
				return profileRole;
			}
		}
		KAProfileRole profileRole = createProfileRolesRelationship();
		profileRole.setRole(role);
		return profileRole;
	}
	
	public NSArray<KARoleGroup> displayedGroups() {
		NSArray<KARoleGroup> groups = KARole.GROUP.arrayValueInObject(allPossibleRoles());
		
		groups = ERXArrayUtilities.arrayWithoutDuplicates(groups);	
		return KARoleGroup.LOCALIZED_NAME.asc().sorted(groups);
	}

	public NSArray<KARole> displayedRolesForGroup(KARoleGroup group) {
		NSArray<KARole> roles = KARole.GROUP.eq(group).filtered(allPossibleRoles());
		
		roles = ERXArrayUtilities.arrayWithoutDuplicates(roles);
		return KARole.LOCALIZED_NAME.asc().sorted(roles);
	}

}
