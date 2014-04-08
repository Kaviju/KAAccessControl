package com.kaviju.accesscontrol.service;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import com.kaviju.accesscontrol.model.*;
import com.webobjects.appserver.*;
import com.webobjects.foundation.NSNotificationCenter;
import com.wounit.annotations.Dummy;
import com.wounit.rules.MockEditingContext;

import er.extensions.foundation.ERXThreadStorage;

@RunWith(MockitoJUnitRunner.class)
public class UserAccessControlServiceTest {
	@Rule
    public MockEditingContext ec = new MockEditingContext("KAAccessControl", "ModelForTest");

	private UserAccessControlService<KAUserTest.KAUserForTest> serviceUnderTest;
		
	@Spy @Dummy private KAUserProfile testUserProfile;
	@Spy @Dummy private KAUserTest.KAUserForTest testUser;
	@Spy @Dummy private KAUserTest.KAUserForTest testPersonifiedUser;

	static private WOComponent loggedOutPage = mock(WOComponent.class);
	private WOApplication app = new AppForTest();

	private WOContext context = mock(WOContext.class);
	private WOSession session = mock(WOSession.class);
	private SessionWithUserDidLogonDelegate sessionWithDelegate = mock(SessionWithUserDidLogonDelegate.class);

	@Before
	public void createService() {
		testUserProfile.setIsDefaultProfile(true);
		testUserProfile.setUser(testUser);
		testUser.addToProfiles(testUserProfile);
		
		when(session.context()).thenReturn(context);
		when(sessionWithDelegate.context()).thenReturn(context);
		
		app.setTimeOut(100);
		serviceUnderTest = new UserAccessControlService<KAUserTest.KAUserForTest>(session);
	}
	
	@Test
	public void staticCurrentServiceCreateNewService() {
		assertThat(UserAccessControlService.currentService(), notNullValue(UserAccessControlService.class));
	}	
	
	@Test
	public void restoreSessionRegisterServiceInThreadStorage() {
		ERXThreadStorage.removeValueForKey(UserAccessControlService.UserAccessControlServiceThreadStorageKey);
		
        NSNotificationCenter.defaultCenter().postNotification(WOSession.SessionDidRestoreNotification, session);

        assertThat(UserAccessControlService.currentService(KAUserTest.KAUserForTest.class), is(serviceUnderTest));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void logonAsUserThrowWithNull() {
		serviceUnderTest.logonAsUser(null);
	}

	@Test(expected=IllegalStateException.class)
	public void logonAsUserAlreadyloggedThrow() {
		serviceUnderTest.logonAsUser(testUser);

		serviceUnderTest.logonAsUser(testUser);
	}

	@Test
	public void logonAsUserSetCurrentAndRealUser() {
		serviceUnderTest.logonAsUser(testUser);
		
		assertEquals("current user", testUser, serviceUnderTest.currentUser());
		assertEquals("real user", testUser, serviceUnderTest.realUser());
	}

	@Test
	public void logonAsUserReturnUserHomePage() {
		serviceUnderTest.logonAsUser(testUser);
		
		verify(testUser).createHomePageForUserProfile(context, testUser.defaultUserProfile());
	}

	@Test
	public void logonAsUserCallUserDidLogonOnSession() {
		UserAccessControlService<KAUser> serviceUnderTestWithSessionDelegate = spy(new UserAccessControlService<KAUser>(sessionWithDelegate));

		serviceUnderTestWithSessionDelegate.logonAsUser(testUser);
		
		verify(sessionWithDelegate).userProfileDidLogon(testUserProfile);
	}

	@Test
	public void isUserLoggedReturnFalseWithoutCurrentUser() {
		assertThat(serviceUnderTest.isUserLoggedIn(), is(false));
	}
	
	@Test
	public void isUserLoggedReturnTrueWithCurrentUser() {
		serviceUnderTest.logonAsUser(testUser);
		
		assertThat(serviceUnderTest.isUserLoggedIn(), is(true));
	}

	@Test
	public void staticCurrentUserReturnLoggedUner() {
		serviceUnderTest.logonAsUser(testUser);
		
		assertThat(UserAccessControlService.currentUser(KAUserTest.KAUserForTest.class), is(testUser));
	}	

	@Test
	public void staticCurrentUserProfileWithoutCurrentServiceReturnNull() {
		ERXThreadStorage.removeValueForKey(UserAccessControlService.UserAccessControlServiceThreadStorageKey);
		
		assertThat(UserAccessControlService.currentUser(KAUserTest.KAUserForTest.class), nullValue());
	}	

	@Test
	public void staticCurrentUserProfileReturnsLoggedUserProfile() {
		serviceUnderTest.logonAsUser(testUser);

		assertThat(UserAccessControlService.currentUser(KAUserTest.KAUserForTest.class), is(testUser));
	}
	
	@Test
	public void staticCurrentUserWithoutCurrentServiceReturnNull() {
		ERXThreadStorage.removeValueForKey(UserAccessControlService.UserAccessControlServiceThreadStorageKey);
		
		assertThat(UserAccessControlService.currentUser(KAUserTest.KAUserForTest.class), nullValue());
	}	

	@Test
	public void staticCurrentUserReturnsLoggedUser() {
		serviceUnderTest.logonAsUser(testUser);
		
		assertThat(UserAccessControlService.currentUser(KAUserTest.KAUserForTest.class), is(testUser));
	}	

	@Test
	public void currentUserProfileReturnTheCurrentUserProfile() {
		serviceUnderTest.logonAsUser(testUser);

		KAUserProfile profile = serviceUnderTest.currentUserProfile();
		
		assertThat(profile, is(testUser.defaultUserProfile()));
	}

	@Test(expected=IllegalArgumentException.class)
	public void setCurrentUserProfileWithExternalProfileFail() {
		serviceUnderTest.logonAsUser(testUser);
		
		KAUserProfile profile = mock(KAUserProfile.class);
		serviceUnderTest.setCurrentUserProfile(profile);
	}

	@Test
	public void setCurrentUserProfile() {
		testUser.defaultUserProfile();
		KAUserProfile profile = testUser.createProfilesRelationship();
		profile.setUserRelationship(testUser);
		serviceUnderTest.logonAsUser(testUser);

		serviceUnderTest.setCurrentUserProfile(profile);
	}

	@Test
	public void personifyUserReturnUserHomePageAndSetCurrentUserProfile() {
		serviceUnderTest.logonAsUser(testUser);
		serviceUnderTest.personifyUser(testPersonifiedUser);
		
		verify(testPersonifiedUser).createHomePageForUserProfile(context, testPersonifiedUser.defaultUserProfile());
		assertEquals("current user", testPersonifiedUser, serviceUnderTest.currentUser());
	}

	@Test
	public void createHomePageReturnUserHomePage() {
		serviceUnderTest.logonAsUser(testUser);

		serviceUnderTest.createHomePage();
		
		verify(testUserProfile, times(2)).createHomePage(context);
	}

	@Test
	public void logoutWhenNotPersonifyingTerminateSessionAndReturnsLogedOutPage() {		
		serviceUnderTest.logonAsUser(testUser);
		WOComponent page = serviceUnderTest.logout();
		
		assertThat(serviceUnderTest.isUserLoggedIn(), is(false));
		assertThat(serviceUnderTest.realUser(), nullValue());
		assertThat(page, is(loggedOutPage));
		verify(session).terminate();
	}

	@Test
	public void logoutWhenPersonifyingSetCurrentUserAndReturnsLastViewedPageForUser() {
		WOComponent lastViewedPage = mock(WOComponent.class);
		when(context.page()).thenReturn(lastViewedPage);
		
		serviceUnderTest.logonAsUser(testUser);
		serviceUnderTest.personifyUser(testPersonifiedUser);
		WOComponent page = serviceUnderTest.logout();
		
		assertThat(serviceUnderTest.currentUser(), is(testUser));
		assertThat(page, is(lastViewedPage));
		verify(session, never()).terminate();
	}
	
	static public class AppForTest extends WOApplication {
		@Override
		public void run() {
			;
		}
		@Override
		public WOComponent pageWithName(String arg0, WOContext arg1) {
			return loggedOutPage;
		}
	}
	
	@SuppressWarnings("serial")
	static public class SessionWithUserDidLogonDelegate extends WOSession implements UserLogonDelegate {
		@Override
		public void userProfileDidLogon(KAUserProfile userProfile) {
		}
	}
}
