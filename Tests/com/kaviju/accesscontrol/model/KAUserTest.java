package com.kaviju.accesscontrol.model;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import com.wounit.annotations.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import java.util.*;

import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.runners.MockitoJUnitRunner;

import com.kaviju.accesscontrol.authentication.*;
import com.webobjects.appserver.*;
import com.wounit.annotations.UnderTest;
import com.wounit.rules.MockEditingContext;

@RunWith(MockitoJUnitRunner.class)
public class KAUserTest {
	private static final String roleCode1 = "roleCode1";
	private static final String roleCode2 = "roleCode2";
	private static final int keyLength = 24;
	private static final int nbIterations = 1000;
	static private final String testPassword = "password";
	static private final String testWrongPassword = "passworD";
	private static final String dummyHashedPassword = "A0A0A0";

	static private Pbkdf2Hasher testHasher;

	@Rule
    public MockEditingContext ec = new MockEditingContext("KAAccessControl");

	@BeforeClass
	static public void initPasswordHasher() {
		testHasher = new Pbkdf2Hasher(keyLength, nbIterations);
		PasswordHasher.registerHasher(testHasher);
	}
	
	@Before
	public void setPasswordHasher() {
		KAUser.setCurrentPasswordHasher(testHasher);
	}

	@Dummy KAProfile profile;
	@Spy @UnderTest private KAUserForTest testUser;

	@SuppressWarnings("serial")
	static class KAUserForTest extends KAUser {
		@Override
		public WOComponent createHomePage(WOContext context) {
			return mock(WOComponent.class);
		}
	};

	
	@Test(expected=RuntimeException.class)
	public void changePasswordWithNoPasswordHasherThrow() {
		KAUser.setCurrentPasswordHasher(null);
		testUser.changePassword(testPassword);
	}
	
	@Test
	public void changePasswordSetPasswordHash() {
		testUser.changePassword(testPassword);
		
		assertNotNull(testUser.passwordHash());
	}

	@Test
	public void authenticateAUserWithoutPasswordFail() {		
		assertFalse(testUser.authenticateWithPassword(testPassword));
	}

	@Test
	public void authenticateWithLegacyHashUseDefaultHasher() {
		Pbkdf2Hasher defaultHasher = mock(Pbkdf2Hasher.class);
		KAUser.setDefaultPasswordHasher(defaultHasher);
		testUser.setPasswordHash(dummyHashedPassword);
		
		testUser.authenticateWithPassword(testPassword);
		
		verify(defaultHasher).verifyPasswordWithHash(eq(testPassword), any(PasswordHash.class));
	}

	@Test
	public void authenticateWithWrongPasswordFail() {
		testUser.changePassword(testPassword);
		
		assertFalse(testUser.authenticateWithPassword(testWrongPassword));
	}

	@Test
	public void authenticateWithGoodPasswordSucceed() {
		testUser.changePassword(testPassword);
		
		assertTrue(testUser.authenticateWithPassword(testPassword));
	}

	@Test
	public void currentUserProfileWithNoProfileCreateANewProfile() {
		KAUserProfile profile = testUser.currentUserProfile();
		
		assertTrue(profile.isNewObject());
		assertNull(profile.profile());
	}

	@Test
	public void currentUserProfileWithADefaultProfileReturnDefaultProfile() {
		testUser.createProfilesRelationship(); // empty not default profile
		KAUserProfile defaultProfile = testUser.createProfilesRelationship();
		defaultProfile.setIsDefaultProfile(true);
		testUser.createProfilesRelationship(); // empty not default profile
		
		KAUserProfile profile = testUser.currentUserProfile();
		assertEquals(defaultProfile, profile);
	}

	@Test
	public void currentUserProfileWithSomeProfilesButNoDefaultReturnFirstFound() {
		KAUserProfile firstProfile = testUser.createProfilesRelationship(); // empty not default profile
		testUser.createProfilesRelationship(); // empty not default profile
		
		KAUserProfile profile = testUser.currentUserProfile();
		assertEquals(firstProfile, profile);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void setCurrentUserProfileWithExternalProfileFail() {		
		KAUserProfile profile = KAUserProfile.createKAUserProfile(ec);
		testUser.setCurrentUserProfile(profile);
	}

	@Test
	public void setCurrentUserProfile() {
		testUser.createProfilesRelationship(); // empty not default profile
		KAUserProfile profile = testUser.createProfilesRelationship();
		testUser.createProfilesRelationship(); // empty not default profile

		testUser.setCurrentUserProfile(profile);
	}

	@Test
	public void hasRoleAskCurrentUserProfile() {
		KAUserProfile userProfile = spy(KAUserProfile.createKAUserProfile(ec));
		userProfile.setProfile(profile);
		testUser.addToProfiles(userProfile);
		
		testUser.hasRole(roleCode1);
		
		verify(userProfile).hasRole(roleCode1);
	}

	@Test
	public void hasAtLeastOneOfTheseRolesCheckForAllRolesCodeBeforeReturningFalse() {
		when(testUser.hasRole(any(String.class))).thenReturn(false);
		String[] roles = {roleCode1, roleCode2};
		
		assertFalse(testUser.hasAtLeastOneOfTheseRoles(Arrays.asList(roles)));
		
		verify(testUser).hasRole(roleCode1);
		verify(testUser).hasRole(roleCode2);
	}
	
	@Test
	public void hasAtLeastOneOfTheseRolesReturnTrueIfUserHaveOneOfTheRoles() {
		when(testUser.hasRole(any(String.class))).thenReturn(false);
		when(testUser.hasRole(roleCode2)).thenReturn(true);
		String[] roles = {roleCode1, roleCode2};
		
		assertTrue(testUser.hasAtLeastOneOfTheseRoles(Arrays.asList(roles)));
		
		verify(testUser).hasRole(roleCode2);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Test
	public void hasAtLeastOneOfTheseRolesVarargReturnTrueIfUserHaveOneOfTheRoles() {
		when(testUser.hasRole(any(String.class))).thenReturn(false);
		when(testUser.hasRole(roleCode2)).thenReturn(true);
		
		assertTrue(testUser.hasAtLeastOneOfTheseRoles(roleCode1, roleCode2));
		
		ArgumentCaptor<List> argument = ArgumentCaptor.forClass(List.class);
		verify(testUser).hasAtLeastOneOfTheseRoles(argument.capture());
		assertThat(argument.getValue(), is(Arrays.asList(new String[]{roleCode1, roleCode2})));
	}

	@Test
	public void hasAllTheseRolesCheckForAllRolesCodeBeforeReturningTrue() {
		when(testUser.hasRole(any(String.class))).thenReturn(true);
		String[] roles = {roleCode1, roleCode2};
		
		assertTrue(testUser.hasAllTheseRoles(Arrays.asList(roles)));
		
		verify(testUser).hasRole(roleCode1);
		verify(testUser).hasRole(roleCode2);
	}

	@Test
	public void hasAllTheseRolesReturnFalseIfUserDoesNotHaveOneOfTheRoles() {
		when(testUser.hasRole(any(String.class))).thenReturn(true);
		when(testUser.hasRole(roleCode2)).thenReturn(false);
		String[] roles = {roleCode1, roleCode2};
		
		assertFalse(testUser.hasAllTheseRoles(Arrays.asList(roles)));
	}
	
	@Test
	public void itemCodesForRoleAskTheKAUserProfile() {
		KAUserProfile userProfile = spy(KAUserProfile.createKAUserProfile(ec));
		testUser.addToProfiles(userProfile);
		
		testUser.itemCodesForRole(roleCode1);
		
		verify(userProfile).itemCodesForRole(roleCode1);
	}

	@Test
	public void itemAsObjectsForRoleAskTheKAUserProfile() {
		KAUserProfile userProfile = spy(KAUserProfile.createKAUserProfile(ec));
		testUser.addToProfiles(userProfile);
		
		testUser.itemsAsObjectsForRole(KARole.class, roleCode1);
		
		verify(userProfile).itemsAsObjectsForRole(KARole.class, roleCode1);
	}

	@Test
	public void createHomePageReturnAComponent() {
		@SuppressWarnings("unused")
		WOComponent homePage = testUser.createHomePage(null);
	}

	@Test
	public void toStringReturnaString() {
		assertNotNull(testUser.toString());
	}
	
}
