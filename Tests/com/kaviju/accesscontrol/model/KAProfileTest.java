package com.kaviju.accesscontrol.model;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

import org.junit.*;

import com.webobjects.foundation.NSArray;
import com.wounit.annotations.*;
import com.wounit.rules.MockEditingContext;

public class KAProfileTest {
	static private final String userProfileCode = "user";
	static private final String adminProfileCode = "admin";
	static private final String testProfileCode = "test";

	@Rule public MockEditingContext ec = new MockEditingContext("KAAccessControl");

	@Dummy private KAProfile userProfile;
	@Dummy private KAProfile adminProfile;

	@Dummy private KARoleGroup roleGroup;
	@Dummy private KARoleGroup roleGroupNotUsed;
	@Dummy private KARole mandatoryRole;
	@Dummy private KARole optionalRole;

	@UnderTest private KAProfile profileUnderTest;
		
	@Before
	public void createDummies() {
		userProfile.setCode(userProfileCode);
		adminProfile.setCode(adminProfileCode);
		profileUnderTest.setCode(testProfileCode);
		
		mandatoryRole.setGroup(roleGroup);
		optionalRole.setGroup(roleGroup);
	}
	
	@Test
	public void profileWithCodeFetchCorrectProfile() {
		KAProfile profile = KAProfile.profileWithCode(ec, adminProfileCode);
		
		assertThat(profile, is(adminProfile));
	}

	@Test
	public void localizedNameReturnString() {
		assertThat(profileUnderTest.localizedName(), notNullValue());
	}

	@Test
	public void fetchProfilesReturnOrderedProfiles() {
		NSArray<KAProfile> profiles = KAProfile.fetchProfiles(ec);
		
		assertThat(profiles.objectAtIndex(0), is(adminProfile));
		assertThat(profiles.objectAtIndex(1), is(profileUnderTest));
		assertThat(profiles.objectAtIndex(2), is(userProfile));
	}
	
	@Test
	public void addMandatoryRoleCreateNewMandatoryKAProfileRole() {
		profileUnderTest.addMandatoryRole(mandatoryRole);
		
		KAProfileRole newProfileRole = profileUnderTest.profileRoles().objectAtIndex(0);
		assertThat(newProfileRole.isOptional(), is(false));
	}

	@Test
	public void addedMandatoryRolesIsFoundInCorrectArrays() {
		profileUnderTest.addMandatoryRole(mandatoryRole);
		
		assertThat(profileUnderTest.allPossibleRoles().containsObject(mandatoryRole), is(true));
		assertThat(profileUnderTest.mandatoryRoles().containsObject(mandatoryRole), is(true));
		assertThat(profileUnderTest.optionalRoles().containsObject(mandatoryRole), is(false));
	}

	@Test
	public void addOptionalRoleCreateNewMandatoryKAProfileRole() {
		profileUnderTest.addOptionalRole(optionalRole);
		
		KAProfileRole newProfileRole = profileUnderTest.profileRoles().objectAtIndex(0);
		assertThat(newProfileRole.isOptional(), is(true));
	}

	@Test
	public void addedOptionalRolesIsFoundInCorrectArrays() {
		profileUnderTest.addOptionalRole(optionalRole);
		
		assertThat(profileUnderTest.allPossibleRoles().containsObject(optionalRole), is(true));
		assertThat(profileUnderTest.mandatoryRoles().containsObject(optionalRole), is(false));
		assertThat(profileUnderTest.optionalRoles().containsObject(optionalRole), is(true));
	}

	@Test
	public void displayedGroupsReturnTheGroupOfRolesAdded() {
		profileUnderTest.addMandatoryRole(mandatoryRole);
		profileUnderTest.addOptionalRole(optionalRole);

		NSArray<KARoleGroup> displayedGroups = profileUnderTest.displayedGroups();
		assertThat(displayedGroups.count(), is(1));
		assertThat(displayedGroups.containsObject(roleGroup), is(true));
	}
	
	@Test
	public void displayedRolesForGroupReturnTheRoles() {
		profileUnderTest.addMandatoryRole(mandatoryRole);

		NSArray<KARole> displayedRoles = profileUnderTest.displayedRolesForGroup(roleGroup);
		assertThat(displayedRoles.count(), is(1));
		assertThat(displayedRoles.containsObject(mandatoryRole), is(true));
	}
	
}
