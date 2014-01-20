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

	@UnderTest private KAProfile profileUnderTest;
		
	@Before
	public void createDummies() {
		userProfile.setCode(userProfileCode);
		adminProfile.setCode(adminProfileCode);
		profileUnderTest.setCode(testProfileCode);
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
}
