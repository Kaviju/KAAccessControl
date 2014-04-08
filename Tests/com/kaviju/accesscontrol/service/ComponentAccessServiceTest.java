package com.kaviju.accesscontrol.service;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import com.kaviju.accesscontrol.annotation.*;
import com.kaviju.accesscontrol.model.*;
import com.webobjects.appserver.WOComponent;
import com.webobjects.foundation.NSSet;
import com.wounit.rules.MockEditingContext;


@RunWith(MockitoJUnitRunner.class)
public class ComponentAccessServiceTest {
	private static final String adminRoleCode = "admin";
	private static final String userRoleCode = "user";
	private static final String remoteUserRoleCode = "remoteUser";
	private static final String[] allRolesCodes = {adminRoleCode, userRoleCode, remoteUserRoleCode};
	private static final NSSet<String> allRolesCodesSet = new NSSet<String>(allRolesCodes);
	private static final String[] allowedRoles = {adminRoleCode, userRoleCode};
	
	@Rule
    public MockEditingContext ec = new MockEditingContext("KAAccessControl");

	ComponentAccessService serviceUnderTest;
	
	@Before
	public void prepareInstanceUnderTest() {
		serviceUnderTest = spy(ComponentAccessService.getInstance());
		serviceUnderTest.setAllRolesCodesForTest(allRolesCodesSet);
	}

	@Test
	public void verifyAllowedForAllAnnotationInClass() {
		assertTrue("Annotation present", serviceUnderTest.verifyAllowedForAllAnnotationInClass(TestComponentWithAllowedForAllAnnotation.class));
		assertFalse("Annotation absent.", serviceUnderTest.verifyAllowedForAllAnnotationInClass(TestComponentWithAllowedForRoleAnnotation.class));
	}

	@Test
	public void readAllowedForRolesAnnotationInClassReturnNullIfAbsent() {
		assertNull(serviceUnderTest.readAllowedForRolesAnnotationInClass(TestComponentWithAllowedForAllAnnotation.class));
	}

	@Test
	public void readAllowedForRolesAnnotationInClassReturnNSSetOfValuesIfPresent() {
		NSSet<String> foundRoleCodes = serviceUnderTest.readAllowedForRolesAnnotationInClass(TestComponentWithAllowedForRoleAnnotation.class);
		
		assertEquals(new NSSet<String>(allowedRoles), foundRoleCodes);
	}

	@Test
	public void readDeniedForRolesAnnotationInClassReturnNullIfAbsent() {
		assertNull(serviceUnderTest.readDeniedForRolesAnnotationInClass(TestComponentWithAllowedForAllAnnotation.class));
	}

	@Test
	public void readDeniedForRolesAnnotationInClassReturnNSSetOfValuesIfPresent() {
		NSSet<String> foundRoleCodes = serviceUnderTest.readDeniedForRolesAnnotationInClass(TestComponentWithDeniedForRoleAnnotation.class);
		
		assertEquals(new NSSet<String>(adminRoleCode), foundRoleCodes);
	}

	@Test(expected=RuntimeException.class)
	public void readAllowedRoleCodesInClassThrowIfTooManyAnnotations1() {
		serviceUnderTest.readAllowedRoleCodesInClass(TestComponentWithTooManyAnnotation1.class);
	}
	@Test(expected=RuntimeException.class)
	public void readAllowedRoleCodesInClassThrowIfTooManyAnnotations2() {
		serviceUnderTest.readAllowedRoleCodesInClass(TestComponentWithTooManyAnnotation2.class);
	}
	@Test(expected=RuntimeException.class)
	public void readAllowedRoleCodesInClassThrowIfTooManyAnnotations3() {
		serviceUnderTest.readAllowedRoleCodesInClass(TestComponentWithTooManyAnnotation3.class);
	}
	@Test(expected=RuntimeException.class)
	public void readAllowedRoleCodesInClassThrowIfTooManyAnnotations4() {
		serviceUnderTest.readAllowedRoleCodesInClass(TestComponentWithTooManyAnnotation4.class);
	}
	
	@Test
	public void readAllowedRoleCodesInClassReturnEmptySetIfNoAnnotations() {
		NSSet<String> rolesCodes = serviceUnderTest.readAllowedRoleCodesInClass(WOComponent.class);
		
		assertEquals(new NSSet<String>(), rolesCodes);
	}

	@Test
	public void readAllowedRoleCodesInClassReturnAllRolesCodesForAllowedForAllAnnotation() {
		NSSet<String> rolesCodes = serviceUnderTest.readAllowedRoleCodesInClass(TestComponentWithAllowedForAllAnnotation.class);
		
		assertEquals(allRolesCodesSet, rolesCodes);
	}

	@Test
	public void readAllowedRoleCodesInClassReturnSpecifiedCodesInAllowedForRoleAnnotation() {
		NSSet<String> rolesCodes = serviceUnderTest.readAllowedRoleCodesInClass(TestComponentWithAllowedForRoleAnnotation.class);
		
		assertEquals(new NSSet<String>(allowedRoles), rolesCodes);
	}

	@Test
	public void readAllowedRoleCodesInClassReturnAllCodesExceptsSpecifiedCodesInDeniedForRoleAnnotation() {
		NSSet<String> rolesCodes = serviceUnderTest.readAllowedRoleCodesInClass(TestComponentWithDeniedForRoleAnnotation.class);
		NSSet<String> expectedRolesCodes = allRolesCodesSet.setBySubtractingSet(new NSSet<String>(adminRoleCode)); 
		
		assertEquals(expectedRolesCodes, rolesCodes);
	}

	@Test(expected=IllegalArgumentException.class)
	public void isComponentAccessibleForUserProfileThrowIfComponentNotFound() {
		serviceUnderTest.isComponentAccessibleForUserProfile("InvalidClassName", null);
	}

	@Test
	public void isComponentAccessibleForUserProfileCallKAUSer_hasAtLeastOneOfTheseRolesWithRolesCodes() {
		KAUserProfile testUserProfile = mock(KAUserProfile.class);

		when(testUserProfile.hasAtLeastOneOfTheseRoles(allRolesCodesSet)).thenReturn(true);
		
		assertTrue(serviceUnderTest.isComponentAccessibleForUserProfile(TestComponentWithAllowedForAllAnnotation.class, testUserProfile));
		verify(serviceUnderTest).readAllowedRoleCodesInClass(TestComponentWithAllowedForAllAnnotation.class);
		verify(testUserProfile).hasAtLeastOneOfTheseRoles(allRolesCodesSet);
	}

	@Test
	public void isComponentNameAccessibleForUserProfileCallKAUSer_hasAtLeastOneOfTheseRolesWithRolesCodes() {
		KAUserProfile testUserProfile = mock(KAUserProfile.class);

		when(testUserProfile.hasAtLeastOneOfTheseRoles(allRolesCodesSet)).thenReturn(true);
		
		assertTrue(serviceUnderTest.isComponentAccessibleForUserProfile(TestComponentWithAllowedForAllAnnotation.class.getName(), testUserProfile));
		verify(serviceUnderTest).readAllowedRoleCodesInClass(TestComponentWithAllowedForAllAnnotation.class);
		verify(testUserProfile).hasAtLeastOneOfTheseRoles(allRolesCodesSet);
	}

	@SuppressWarnings({"serial", "deprecation" })
	public static @AllowedForAll class TestComponentWithAllowedForAllAnnotation extends WOComponent {}
	@SuppressWarnings({ "deprecation", "serial" })
	public static @AllowedForRole({adminRoleCode, userRoleCode}) class TestComponentWithAllowedForRoleAnnotation extends WOComponent {}
	@SuppressWarnings({ "deprecation", "serial" })
	public static @DeniedForRole(adminRoleCode) class TestComponentWithDeniedForRoleAnnotation extends WOComponent {}
	
	@SuppressWarnings({ "deprecation", "serial" })
	public static @AllowedForAll @AllowedForRole(adminRoleCode) class TestComponentWithTooManyAnnotation1 extends WOComponent {}
	@SuppressWarnings({ "deprecation", "serial" })
	public static @AllowedForAll @DeniedForRole(adminRoleCode) class TestComponentWithTooManyAnnotation2 extends WOComponent {}
	@SuppressWarnings({ "deprecation", "serial" })
	public static @AllowedForAll @AllowedForRole(adminRoleCode) @DeniedForRole(adminRoleCode) class TestComponentWithTooManyAnnotation3 extends WOComponent {}
	@SuppressWarnings({ "deprecation", "serial" })
	public static @DeniedForRole(adminRoleCode) @AllowedForRole(adminRoleCode) class TestComponentWithTooManyAnnotation4 extends WOComponent {}
}
