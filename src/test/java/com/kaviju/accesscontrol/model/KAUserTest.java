package com.kaviju.accesscontrol.model;

import static com.wounit.matchers.EOAssert.*;
import static org.junit.Assert.*;

import static org.mockito.Mockito.*;

import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import com.kaviju.accesscontrol.authentication.*;
import com.webobjects.appserver.*;
import com.wounit.annotations.*;
import com.wounit.rules.MockEditingContext;

@RunWith(MockitoJUnitRunner.class)
public class KAUserTest {
	private static final int keyLength = 24;
	private static final int nbIterations = 1000;
	static private final String testPassword = "password";
	static private final String testWrongPassword = "passworD";
	private static final String dummyHashedPassword = "A0A0A0";

	static private Pbkdf2Hasher testHasher;

	@Rule
    public MockEditingContext ec = new MockEditingContext("KAAccessControl", "ModelForTest");
	
	@Before
	public void setPasswordHasher() {
		testHasher = spy(new Pbkdf2Hasher(keyLength, nbIterations));
		PasswordHasher.registerHasher(testHasher);

		KAUser.setCurrentPasswordHasher(testHasher);
	}

	@Dummy KAProfile profile;
	@UnderTest private KAUserForTest testUser;

	@SuppressWarnings("serial")
	static public class KAUserForTest extends KAUser {
		@Override
		public WOComponent createHomePageForUserProfile(WOContext context, KAUserProfile userProfile) {
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
		
		verify(defaultHasher).verifyPasswordWithHash(eq(testPassword), isA(PasswordHash.class));
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
	public void authenticateWithPasswordAndUpgradeHashIfRequiredDoesNothingAndReturnFalseIfIncorrectPassword() {		
		assertFalse(testUser.authenticateWithPasswordAndUpgradeHashIfRequired(testWrongPassword));
		
		assertFalse(testUser.hasKeyChangedFromCommittedSnapshot(KAUser.PASSWORD_HASH_KEY));
		confirm(testUser, hasNotBeenSaved());
	}

	@Test
	public void authenticateWithPasswordAndUpgradeHashIfRequiredUpgradeHashIfPasswordIsValidAndHashNotCurrent() {
		LatinSimpleMD5Hasher otherHasher = spy(LatinSimpleMD5Hasher.class);
		KAUser.setDefaultPasswordHasher(otherHasher);

		testUser.setPasswordHash(otherHasher.hashPassword(testPassword).hashAsHexString());
		testUser.authenticateWithPasswordAndUpgradeHashIfRequired(testPassword);
		
		confirm(testUser, hasBeenSaved());
		verify(testHasher).hashPassword(testPassword);
	}

	@Test
	public void createProfileWithDefaultEntityCreateAKAUserProfileForCurrentUser() {
		KAUserProfile userProfile = testUser.createProfileWithDefaultEntity();
		
		assertEquals(userProfile.user(), testUser);
	}
	
	@Test
	public void defaultUserProfileWithNoProfileCreateANewProfile() {
		KAUserProfile profile = testUser.defaultUserProfile();
		
		assertTrue(profile.isNewObject());
		assertNull(profile.profile());
	}

	@Test
	public void defaultUserProfileWithADefaultProfileReturnDefaultProfile() {
		testUser.createProfileWithDefaultEntity(); // empty not default profile
		KAUserProfile defaultProfile = testUser.createProfileWithDefaultEntity();
		defaultProfile.setIsDefaultProfile(true);
		testUser.createProfileWithDefaultEntity(); // empty not default profile
		
		KAUserProfile profile = testUser.defaultUserProfile();
		assertEquals(defaultProfile, profile);
	}

	@Test
	public void defaultUserProfileWithSomeProfilesButNoDefaultReturnFirstFound() {
		KAUserProfile firstProfile = testUser.createProfileWithDefaultEntity(); // empty not default profile
		testUser.createProfileWithDefaultEntity(); // empty not default profile
		
		KAUserProfile profile = testUser.defaultUserProfile();
		assertEquals(firstProfile, profile);
	}
		
	@Test
	public void createHomePageReturnAComponent() {
		@SuppressWarnings("unused")
		WOComponent homePage = testUser.createHomePageForUserProfile(null, null);
	}

	@Test
	public void toStringReturnaString() {
		assertNotNull(testUser.toString());
	}
	
}
