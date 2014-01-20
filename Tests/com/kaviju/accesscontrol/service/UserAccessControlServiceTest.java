package com.kaviju.accesscontrol.service;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import static org.mockito.Mockito.*;

import org.junit.*;

import com.kaviju.accesscontrol.model.*;
import com.kaviju.accesscontrol.service.UserAccessControlService;
import com.webobjects.appserver.*;
import com.webobjects.foundation.NSNotificationCenter;

import er.extensions.foundation.*;

public class UserAccessControlServiceTest {

	private UserAccessControlService<KAUser> serviceUnderTest;
	
	private KAUser testUser = mock(KAUser.class, withSettings().name("testUser"));
	private KAUser testPersonifiedUser = mock(KAUser.class, withSettings().name("testPersonifiedUser"));

	static private WOComponent loggedOutPage = mock(WOComponent.class);
	private WOApplication app = new AppForTest();

	private WOContext context = mock(WOContext.class);
	private WOSession session = mock(WOSession.class);
	private SessionWithUserDidLogonDelegate sessionWithDelegate = mock(SessionWithUserDidLogonDelegate.class);

	@Before
	public void createService() {
		when(session.context()).thenReturn(context);
		when(sessionWithDelegate.context()).thenReturn(context);
		app.setTimeOut(100);
		serviceUnderTest = new UserAccessControlService<KAUser>(session);
	}
	
	@Test
	public void restoreSessionRegisterServiceInThreadStorage() {
		ERXThreadStorage.removeValueForKey(UserAccessControlService.UserAccessControlServiceThreadStorageKey);
		
        NSNotificationCenter.defaultCenter().postNotification(WOSession.SessionDidRestoreNotification, session);

        assertThat(UserAccessControlService.currentService(KAUser.class), is(serviceUnderTest));
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
		
		verify(testUser).createHomePage(context);
	}

	@Test
	public void logonAsUserCallUserDidLogonOnSession() {
		UserAccessControlService<KAUser> serviceUnderTestWithSessionDelegate = spy(new UserAccessControlService<KAUser>(sessionWithDelegate));

		serviceUnderTestWithSessionDelegate.logonAsUser(testUser);
		
		verify(sessionWithDelegate).userDidLogon(testUser);
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
		
		assertThat(UserAccessControlService.currentUser(KAUser.class), is(testUser));
	}	

	@Test
	public void staticCurrentUserWithoutCurrentServiceReturnNull() {
		ERXThreadStorage.removeValueForKey(UserAccessControlService.UserAccessControlServiceThreadStorageKey);
		
		assertThat(UserAccessControlService.currentUser(KAUser.class), nullValue());
	}	

	@Test
	public void currentUserProfileReturnTheCurrentUserProfile() {
		serviceUnderTest.logonAsUser(testUser);

		KAUserProfile profile = serviceUnderTest.currentUserProfile();
		
		assertThat(profile, is(testUser.currentUserProfile()));
	}
	
	@Test
	public void personifyUserReturnUserHomePageAndSetCurrentUser() {
		serviceUnderTest.logonAsUser(testUser);
		serviceUnderTest.personifyUser(testPersonifiedUser);
		
		verify(testPersonifiedUser).createHomePage(context);
		assertEquals("current user", testPersonifiedUser, serviceUnderTest.currentUser());
	}

	@Test
	public void createHomePageReturnUserHomePage() {
		serviceUnderTest.logonAsUser(testUser);

		serviceUnderTest.createHomePage();
		
		verify(testUser, times(2)).createHomePage(context);
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
	static public class SessionWithUserDidLogonDelegate extends WOSession implements UserLogonDelegate<KAUser> {
		@Override
		public void userDidLogon(KAUser user) {
		}
	}
}
