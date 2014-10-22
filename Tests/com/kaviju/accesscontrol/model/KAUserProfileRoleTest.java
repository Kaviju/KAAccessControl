package com.kaviju.accesscontrol.model;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.*;

import org.junit.*;

import com.webobjects.foundation.NSArray;
import com.wounit.annotations.*;
import com.wounit.rules.MockEditingContext;

public class KAUserProfileRoleTest {
	static private final String userRoleCode = "user";
	static private final String territory1Code = "T1";
	static private final String territory2Code = "T2";

	@Rule public MockEditingContext ec = new MockEditingContext("KAAccessControl");
	
	@Dummy private KAProfile testProfile;
	@Dummy private KAUserProfileDefaultEntity testUserProfile;
	@Dummy private KARole testUserRole;
	@Dummy private KARole testAdminRole;

	@Dummy private KAAccessList testTerritoryList;
	@Dummy private KAAccessListItem testTerritory1;
	@Dummy private KAAccessListItem testTerritory2;
	@Dummy private KAAccessListItem testTerritoryAssociatedWithUserRole;

	@UnderTest private KAUserProfileRole userProfileRoleUnderTest;
	
	@Before
	public void createDummies() {
		testUserRole.setCode(userRoleCode);
		
		testTerritory1.setCode(territory1Code);
		testTerritoryList.addToItems(testTerritory1);
		testTerritory2.setCode(territory2Code);
		testTerritoryList.addToItems(testTerritory2);
		testTerritoryList.addToItems(testTerritoryAssociatedWithUserRole);
		
		testProfile.addMandatoryRole(testUserRole);
		testUserProfile.setProfile(testProfile);
	}
	
	@Test
	public void listItemCodesForRoleReturnsTheArrayOfListItemCodesOfThisRole() {
		userProfileRoleUnderTest.addToListItems(testTerritory1);
		userProfileRoleUnderTest.addToListItems(testTerritory2);
		
		NSArray<String> itemCodes = userProfileRoleUnderTest.itemCodes();
		
		assertTrue(itemCodes.contains(territory1Code));
		assertTrue(itemCodes.contains(territory2Code));
	}
	
	@Test
	public void isFromProfileReturnsTrueIfRoleInCurrentProfile() {
		userProfileRoleUnderTest.setUserProfile(testUserProfile);
		userProfileRoleUnderTest.setRole(testUserRole);
		
		assertTrue(userProfileRoleUnderTest.isMandatory());
	}
	
	@Test
	public void itemAsObjectsReturnEO() {
		testTerritoryAssociatedWithUserRole.setCode(testUserRole.primaryKey());
		userProfileRoleUnderTest.addToListItems(testTerritoryAssociatedWithUserRole);

		assertEquals(new NSArray<KARole>(testUserRole), userProfileRoleUnderTest.itemsAsObjects(KARole.class));
	}
	
	@Test
	public void isFromProfileReturnsFalseIfRoleNotInCurrentProfile() {
		userProfileRoleUnderTest.setUserProfile(testUserProfile);
		userProfileRoleUnderTest.setRole(testAdminRole);
		
		assertFalse(userProfileRoleUnderTest.isMandatory());
	}

	@Test
	public void toStringReturnString() {
		userProfileRoleUnderTest.setRole(testAdminRole);

		assertThat(userProfileRoleUnderTest.toString(), notNullValue());
	}
}
