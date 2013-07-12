package com.kaviju.accesscontrol;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.*;

import com.kaviju.accesscontrol.model.KAUser;
import com.kaviju.accesscontrol.service.UserAccessControlService;
import com.webobjects.appserver.*;


public class SessionWithUserTest {

	private UserAccessControlService serviceUnderTest;
	
	private KAUser testUser = mock(KAUser.class);
	private KAUser testPersonifiedUser = mock(KAUser.class);

	@Before
	public void createSession() {
		WOApplication app = new AppForTest();
		app.setTimeOut(100);
		
		serviceUnderTest = spy(new UserAccessControlService());
	}
	
	
	@Test(expected=IllegalArgumentException.class)
	public void logonAsUserThrowWithNull() {
		serviceUnderTest.logonAsUserInContext(null, null);
	}

	@Test(expected=IllegalStateException.class)
	public void logonAsUserAlreadyloggedThrow() {
		serviceUnderTest.logonAsUserInContext(testUser, null);

		serviceUnderTest.logonAsUserInContext(testUser, null);
	}

	@Test
	public void logonAsUserSetCurrentAndRealUser() {
		serviceUnderTest.logonAsUserInContext(testUser, null);
		
		assertEquals("current user", testUser, serviceUnderTest.currentUser());
		assertEquals("real user", testUser, serviceUnderTest.realUser());
	}

	@Test
	public void logonAsUserReturnUserHomePage() {
		serviceUnderTest.logonAsUserInContext(testUser, null);
		
		verify(testUser).createHomePage(null);
	}

	@Test
	public void personifyUserReturnUserHomePageAndSetCurrentUser() {
		serviceUnderTest.logonAsUserInContext(testUser, null);
		serviceUnderTest.personifyUserInContext(testPersonifiedUser, null);
		
		verify(testPersonifiedUser).createHomePage(null);
		assertEquals("current user", testPersonifiedUser, serviceUnderTest.currentUser());
	}
	
	@Test
	public void logoutWhenNotPersonifyingTerminateSessionAndReturnsLogedOutPage() {
		when(serviceUnderTest.createLogedOutPage(null)).thenReturn(null);
		
		serviceUnderTest.logonAsUserInContext(testUser, null);
		serviceUnderTest.logoutInContext(null);
		
		verify(serviceUnderTest).createLogedOutPage(null);
	}

	@Test
	public void logoutWhenPersonifyingSetCurrentUserAndReturnsPreviousUserHomePage() {
		serviceUnderTest.logonAsUserInContext(testUser, null);
		serviceUnderTest.personifyUserInContext(testPersonifiedUser, null);
		serviceUnderTest.logoutInContext(null);
		
		assertEquals("current user", testUser, serviceUnderTest.currentUser());
		verify(testUser, times(2)).createHomePage(null);
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
