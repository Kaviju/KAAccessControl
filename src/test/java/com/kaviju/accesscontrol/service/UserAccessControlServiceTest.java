package com.kaviju.accesscontrol.service;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.kaviju.accesscontrol.model.*;
import com.webobjects.appserver.*;
import com.webobjects.foundation.*;
import com.wounit.rules.MockEditingContext;

import er.extensions.foundation.ERXThreadStorage;

@RunWith(MockitoJUnitRunner.class)
public class UserAccessControlServiceTest {
	@Rule
    public MockEditingContext ec = new MockEditingContext("KAAccessControl", "ModelForTest");

	private UserAccessControlService<KAUserTest.KAUserForTest> serviceUnderTest;
		
	@Mock private KAUserTest.KAUserForTest testUser;
	@Mock private KAUserProfileDefaultEntity testUserProfile;
	
	@Mock private KAUserTest.KAUserForTest testPersonifiedUser;
	@Mock private KAUserProfileDefaultEntity testPersonnifiedUserProfile;

	static private WOComponent loggedOutPage = mock(WOComponent.class);
	private WOApplication app = new AppForTest();

	private WOContext context = mock(WOContext.class);
	private WOSession session = mock(WOSession.class);
	private SessionWithUserDidLogonDelegate sessionWithDelegate = mock(SessionWithUserDidLogonDelegate.class);

	@Before
	public void createService() {
		when(testUser.profiles()).thenReturn(new NSArray<>(testUserProfile));
		when(testUser.defaultUserProfile()).thenReturn(testUserProfile);
		when(testUserProfile.user()).thenReturn(testUser);
		when(testUserProfile.localInstanceOf(testUserProfile)).thenReturn(testUserProfile);

		when(testPersonifiedUser.profiles()).thenReturn(new NSArray<>());
		when(testPersonifiedUser.defaultUserProfile()).thenReturn(testPersonnifiedUserProfile);
		when(testPersonnifiedUserProfile.user()).thenReturn(testPersonifiedUser);

		when(session.context()).thenReturn(context);
		when(sessionWithDelegate.context()).thenReturn(context);
		
		app.setTimeOut(100);
		serviceUnderTest = new UserAccessControlService<KAUserTest.KAUserForTest>(session);
	}
	
	@Test
	public void staticCurrentServiceCreateNewService() {
		assertNotNull(UserAccessControlService.currentService());
	}	
	
	@Test
	public void restoreSessionRegisterServiceInThreadStorage() {
		ERXThreadStorage.removeValueForKey(UserAccessControlService.UserAccessControlServiceThreadStorageKey);
		
        NSNotificationCenter.defaultCenter().postNotification(WOSession.SessionDidRestoreNotification, session);

        assertEquals(UserAccessControlService.currentService(KAUserTest.KAUserForTest.class), serviceUnderTest);
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
		assertFalse(serviceUnderTest.isUserLoggedIn());
	}
	
	@Test
	public void isUserLoggedReturnTrueWithCurrentUser() {
		serviceUnderTest.logonAsUser(testUser);
		
		assertTrue(serviceUnderTest.isUserLoggedIn());
	}

	@Test
	public void currentUserProfileReturnTheCurrentUserProfile() {
		serviceUnderTest.logonAsUser(testUser);

		KAUserProfile profile = serviceUnderTest.currentUserProfile();
		
		assertEquals(profile, testUser.defaultUserProfile());
	}

	@Test(expected=IllegalArgumentException.class)
	public void setCurrentUserProfileWithExternalProfileFail() {
		serviceUnderTest.logonAsUser(testUser);
		
		KAUserProfile profile = mock(KAUserProfile.class);
		when(testUserProfile.localInstanceOf(profile)).thenReturn(profile);
		
		serviceUnderTest.setCurrentUserProfile(profile);
	}

	@Test
	public void setCurrentUserProfile() {
		serviceUnderTest.logonAsUser(testUser);

		serviceUnderTest.setCurrentUserProfile(testUserProfile);
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
		
		assertFalse(serviceUnderTest.isUserLoggedIn());
		assertNull(serviceUnderTest.realUser());
		assertEquals(page, loggedOutPage);
		verify(session).terminate();
	}

	@Test
	public void logoutWhenPersonifyingSetCurrentUserAndReturnsLastViewedPageForUser() {
		WOComponent lastViewedPage = mock(WOComponent.class);
		when(context.page()).thenReturn(lastViewedPage);
		
		serviceUnderTest.logonAsUser(testUser);
		serviceUnderTest.personifyUser(testPersonifiedUser);
		WOComponent page = serviceUnderTest.logout();
		
		assertEquals(serviceUnderTest.currentUser(), testUser);
		assertEquals(page, lastViewedPage);
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
