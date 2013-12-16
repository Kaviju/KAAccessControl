package com.kaviju.accesscontrol.model;

import org.apache.log4j.Logger;

import com.webobjects.foundation.NSArray;

import er.extensions.eof.ERXKey;
import er.extensions.foundation.ERXArrayUtilities;
import er.extensions.localization.ERXLocalizer;
import er.extensions.qualifiers.ERXKeyValueQualifier;

@SuppressWarnings("serial")
public class KARoleGroup extends com.kaviju.accesscontrol.model.base._KARoleGroup {
	@SuppressWarnings("unused")
	private static Logger log = Logger.getLogger(KARoleGroup.class);
	public static final ERXKey<String> LOCALIZED_NAME = new ERXKey<String>("localizedName");
	
	public String localizedName() {
		return ERXLocalizer.currentLocalizer().localizedStringForKeyWithDefault("RoleGroup."+code());
	}

	public static NSArray<KARoleGroup> fetchGroupsForUserProfile(KAUserProfile userProfile) {
		if (userProfile.profile() == null) {
			return new NSArray<KARoleGroup>();
		}
		ERXKeyValueQualifier qualifier = ROLES.dot(KARole.IN_PROFILE_ONLY).eq(false);
		NSArray<KARoleGroup> groups = fetchKARoleGroups(userProfile.editingContext(), qualifier, null);
		groups = groups.arrayByAddingObjectsFromArray(KARole.GROUP.arrayValueInObject(userProfile.profile().roles()));
		
		groups = ERXArrayUtilities.arrayWithoutDuplicates(groups);	
		return LOCALIZED_NAME.asc().sorted(groups);
	}
	
	public NSArray<KARole> displayedRolesForProfile(KAProfile profile) {
		NSArray<KARole> roles = roles(KARole.IN_PROFILE_ONLY.eq(false));
		roles = roles.arrayByAddingObjectsFromArray(profile.roles(KARole.GROUP.eq(this)));
		
		roles = ERXArrayUtilities.arrayWithoutDuplicates(roles);	
		return KARole.LOCALIZED_NAME.asc().sorted(roles);
	}
}
