package com.kaviju.accesscontrol.model;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
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
	public void profileCodeReturnTheProfileCode() {
		userProfileUnderTest.setProfile(testAdminProfile);

		String code = userProfileUnderTest.profileCode();
		
		assertThat(code, is(adminProfileCode));
	}
	
	@Test
	public void addRoleAddItToEffectiveRolesNow() {
		userProfileUnderTest.addRole(testAdminRole);
		
		assertThat(userProfileUnderTest.allEffectiveRoles().contains(testAdminRole.code()), is(true));
	}

	@Test
	public void removeRoleRemoveItItFromEffectiveRolesNow() {
		userProfileUnderTest.addRole(testAdminRole);
		userProfileUnderTest.addRole(testUserRole);

		userProfileUnderTest.removeRole(testAdminRole);

		assertThat(userProfileUnderTest.allEffectiveRoles().contains(testAdminRole.code()), is(false));
	}

	@Test
	public void hasRoleCheckInRolesArray() {
		userProfileUnderTest.addRole(testUserRole);
		
		assertThat("Role in userProfile found", userProfileUnderTest.hasRole(userRoleCode), is(true));
		assertThat("Role not in userProfile not found", userProfileUnderTest.hasRole(adminRoleCode), is(false));
	}
	
	@Test
	public void settingProfileShouldAddRolesFromProfile() {
		userProfileUnderTest.setProfile(testAdminProfile);
		
		assertThat(userProfileUnderTest.hasRole(adminRoleCode), is(true));
	}

	@Test
	public void settingProfileWithCodeShouldSetProfile() {
		userProfileUnderTest.setProfileWithCode(adminProfileCode);
		
		assertThat(userProfileUnderTest.profile(), is(testAdminProfile));
	}

	@Test
	public void settingProfileShouldRemoveAllPreviousRolesAndAddRolesFromNewProfile() {
		userProfileUnderTest.setProfile(testAdminProfile);
		userProfileUnderTest.setProfile(testUserProfile);
		
		assertThat("Role in previous profile not found.", userProfileUnderTest.hasRole(adminRoleCode), is(false));
		assertThat("Role in new profile found.", userProfileUnderTest.hasRole(userRoleCode), is(true));
	}

	@Test
	public void itemAsObjectsForRoleAskTheKAUserProfileRole() {
		KAUserProfileRole userProfileRole = createUserProfileRoleMock();
		
		userProfileUnderTest.itemsAsObjectsForRole(KARole.class, userRoleCode);
		
		verify(userProfileRole).itemsAsObjects(KARole.class);
	}

	private KAUserProfileRole createUserProfileRoleMock() {
		KAUserProfileRole userProfileRole = mock(KAUserProfileRole.class);
		when(userProfileRole.role()).thenReturn(testUserRole);

		userProfileUnderTest.addToRoles(userProfileRole);
		return userProfileRole;
	}
	
	@Test
	public void itemCodesForRoleAskTheKAUserProfileRole() {
		KAUserProfileRole userProfileRole = createUserProfileRoleMock();
		
		userProfileUnderTest.itemCodesForRole(userRoleCode);
		
		verify(userProfileRole).itemCodes();
	}

	@Test
	public void listItemsForRoleAskTheKAUserProfileRole() {
		KAUserProfileRole userProfileRole = createUserProfileRoleMock();
		
		userProfileUnderTest.listItemsForRole(testUserRole);
		
		verify(userProfileRole).listItems();
	}

	@Test
	public void addItemForRoleAskTheKAUserProfileRole() {
		KAUserProfileRole userProfileRole = createUserProfileRoleMock();
		KAAccessListItem listItem = KAAccessListItem.createKAAccessListItem(ec);
		
		userProfileUnderTest.addItemForRole(listItem, testUserRole);
		
		verify(userProfileRole).addToListItems(listItem);
	}
	
	@Test
	public void removeItemForRoleAskTheKAUserProfileRole() {
		KAUserProfileRole userProfileRole = createUserProfileRoleMock();
		KAAccessListItem listItem = KAAccessListItem.createKAAccessListItem(ec);
		
		userProfileUnderTest.removeItemForRole(listItem, testUserRole);
		
		verify(userProfileRole).removeFromListItems(listItem);
	}

	
	@Test
	public void itemCodesForAbsentRoleReturnEmptyArray() {		
		NSArray<String> items = userProfileUnderTest.itemCodesForRole(userRoleCode);
		
		assertThat(new NSArray<String>(), is(items));
	}

	@Test
	public void toStringReturnString() {
		assertThat(userProfileUnderTest.toString(), notNullValue());
	}
}
