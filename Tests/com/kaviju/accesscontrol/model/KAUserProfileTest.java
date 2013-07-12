package com.kaviju.accesscontrol.model;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.*;

import com.webobjects.foundation.NSArray;
import com.wounit.annotations.*;
import com.wounit.rules.MockEditingContext;

public class KAUserProfileTest {
	static private final String adminRoleCode = "admin";
	static private final String userRoleCode = "user";
	static private final String adminProfileCode = "adminP";
	static private final String userProfileCode = "userP";

	@Rule
    public MockEditingContext ec = new MockEditingContext("KAAccessControl");

	
	@Dummy	private KARole testAdminRole;
	@Dummy	private KAProfile testAdminProfile;
	@Dummy	private KARole testUserRole;
	@Dummy	private KAProfile testUserProfile;

	@UnderTest private KAUserProfile userProfileUnderTest;
	
	@Before
	public void createDummies() {
		testAdminRole.setCode(adminRoleCode);
		testAdminProfile.setCode(adminProfileCode);
		testAdminProfile.addToRoles(testAdminRole);

		testUserRole.setCode(userRoleCode);
		testUserProfile.setCode(userProfileCode);
		testUserProfile.addToRoles(testUserRole);
	}
	
	@Test
	public void hasRoleCheckInRolesArray() {
		KAUserProfileRole userProfileRole = userProfileUnderTest.createRolesRelationship();
		userProfileRole.setRole(testUserRole);
		
		assertTrue("Role in userProfile found", userProfileUnderTest.hasRole(userRoleCode));
		assertFalse("Role not in userProfile not found", userProfileUnderTest.hasRole(adminRoleCode));
	}
	
	@Test
	public void settingProfileShouldAddRolesFromProfile() {
		userProfileUnderTest.setProfile(testAdminProfile);
		
		assertTrue(userProfileUnderTest.hasRole(adminRoleCode));
	}

	@Test
	public void settingProfileShouldRemoveAllPreviousRolesAndAddRolesFromNewProfile() {
		userProfileUnderTest.setProfile(testAdminProfile);
		userProfileUnderTest.setProfile(testUserProfile);
		
		assertFalse("Role in previous profile not found.", userProfileUnderTest.hasRole(adminRoleCode));
		assertTrue("Role in new profile found.", userProfileUnderTest.hasRole(userRoleCode));
	}

	@Test
	public void itemAsObjectsForRoleAskTheKAUserProfileRole() {
		KAUserProfileRole userProfileRole = spy(KAUserProfileRole.createKAUserProfileRole(ec));
		userProfileRole.setRole(testUserRole);
		userProfileUnderTest.addToRoles(userProfileRole);
		
		userProfileUnderTest.itemsAsObjectsForRole(KARole.class, userRoleCode);
		
		verify(userProfileRole).itemsAsObjects(KARole.class);
	}
	
	@Test
	public void itemCodesForRoleAskTheKAUserProfileRole() {
		KAUserProfileRole userProfileRole = spy(KAUserProfileRole.createKAUserProfileRole(ec));
		userProfileRole.setRole(testUserRole);
		userProfileUnderTest.addToRoles(userProfileRole);
		
		userProfileUnderTest.itemCodesForRole(userRoleCode);
		
		verify(userProfileRole).itemCodes();
	}

	@Test
	public void itemCodesForAbsentRoleReturnEmptyArray() {		
		NSArray<String> items = userProfileUnderTest.itemCodesForRole(userRoleCode);
		
		assertEquals(new NSArray<String>(), items);
	}
}
