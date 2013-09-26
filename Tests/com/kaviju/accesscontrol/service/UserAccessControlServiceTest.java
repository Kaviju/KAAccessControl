package com.kaviju.accesscontrol.service;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import static org.mockito.Mockito.*;

import org.junit.*;

import com.kaviju.accesscontrol.model.KAUser;
import com.kaviju.accesscontrol.service.UserAccessControlService;
import com.webobjects.appserver.*;

public class UserAccessControlServiceTest {

	private UserAccessControlService<KAUser> serviceUnderTest;
	
	private KAUser testUser = mock(KAUser.class);
	private KAUser testPersonifiedUser = mock(KAUser.class);

	private WOContext context = mock(WOContext.class);
	private WOSession session = mock(WOSession.class);

	@Before
	public void createService() {
		when(context.session()).thenReturn(session);
		WOApplication app = new AppForTest();
		app.setTimeOut(100);
		serviceUnderTest = spy(new UserAccessControlService<KAUser>());
	}
	
	
	@Test(expected=IllegalArgumentException.class)
	public void logonAsUserThrowWithNull() {
		serviceUnderTest.logonAsUserInContext(null, context);
	}

	@Test(expected=IllegalStateException.class)
	public void logonAsUserAlreadyloggedThrow() {
		serviceUnderTest.logonAsUserInContext(testUser, context);

		serviceUnderTest.logonAsUserInContext(testUser, context);
	}

	@Test
	public void logonAsUserSetCurrentAndRealUser() {
		serviceUnderTest.logonAsUserInContext(testUser, context);
		
		assertEquals("current user", testUser, serviceUnderTest.currentUser());
		assertEquals("real user", testUser, serviceUnderTest.realUser());
	}

	@Test
	public void logonAsUserReturnUserHomePage() {
		serviceUnderTest.logonAsUserInContext(testUser, context);
		
		verify(testUser).createHomePage(context);
	}
	
	@Test
	public void isUserLoggedReturnFalseWithoutCurrentUser() {
		assertThat(serviceUnderTest.isUserLoggedIn(), is(false));
	}
	
	@Test
	public void isUserLoggedReturnTrueWithCurrentUser() {
		serviceUnderTest.logonAsUserInContext(testUser, context);
		
		assertThat(serviceUnderTest.isUserLoggedIn(), is(true));
	}

	@Test
	public void personifyUserReturnUserHomePageAndSetCurrentUser() {
		serviceUnderTest.logonAsUserInContext(testUser, context);
		serviceUnderTest.personifyUserInContext(testPersonifiedUser, context);
		
		verify(testPersonifiedUser).createHomePage(context);
		assertEquals("current user", testPersonifiedUser, serviceUnderTest.currentUser());
	}

	@Test
	public void createHomePageReturnUserHomePage() {
		serviceUnderTest.logonAsUserInContext(testUser, context);
		WOComponent homePage = mock(WOComponent.class);
		when(testUser.createHomePage(context)).thenReturn(homePage);
		
		WOComponent returnedHomePage = serviceUnderTest.createHomePage(context);
		
		assertThat(returnedHomePage, is(homePage));
	}

	@Test
	public void logoutWhenNotPersonifyingTerminateSessionAndReturnsLogedOutPage() {
		when(serviceUnderTest.createLoggedOutPage(context)).thenReturn(null);
		
		serviceUnderTest.logonAsUserInContext(testUser, context);
		serviceUnderTest.logoutInContext(context);
		
		assertThat(serviceUnderTest.isUserLoggedIn(), is(false));
		assertThat(serviceUnderTest.realUser(), nullValue());
		verify(serviceUnderTest).createLoggedOutPage(context);
		verify(session).terminate();
	}

	@Test
	public void logoutWhenPersonifyingSetCurrentUserAndReturnsPreviousUserHomePage() {
		serviceUnderTest.logonAsUserInContext(testUser, context);
		serviceUnderTest.personifyUserInContext(testPersonifiedUser, context);
		serviceUnderTest.logoutInContext(context);
		
		assertEquals("current user", testUser, serviceUnderTest.currentUser());
		verify(testUser, times(2)).createHomePage(context);
		verify(session, never()).terminate();
	}
	
	static public class AppForTest extends WOApplication {
		@Override
		public void run() {
			;
		}
		@Override
		public WOComponent pageWithName(String arg0, WOContext arg1) {
			return null;
		}
	}
	
}
