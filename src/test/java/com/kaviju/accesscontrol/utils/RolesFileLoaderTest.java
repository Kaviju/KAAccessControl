package com.kaviju.accesscontrol.utils;

import static org.junit.Assert.*;

import org.junit.*;

import com.kaviju.accesscontrol.model.*;
import com.webobjects.foundation.NSArray;
import com.wounit.rules.MockEditingContext;

import er.extensions.foundation.ERXProperties;

public class RolesFileLoaderTest {
	
	private static final String SalesmanProfileCode = "Salesman";
	private static final String WharehouseSalesReportRoleCode = "WharehouseSalesReport";
	private static final String WharehouseListCode = "Wharehouse";
	private static final String SeeGrossProfitsRoleCode = "SeeGrossProfits";
	private static final String territoryListCode = "Territory";
	private static final String CustomerSalesReportRoleCode = "CustomerSalesReport";
	
	@Rule public MockEditingContext ec = new MockEditingContext("KAAccessControl");
		
	@Before
	public void loadPermissions() {
		ERXProperties.setStringForKey("SampleRolesFile", RolesFileLoader.rolesFileNamePropertyKey);
		RolesFileLoader.loadRolesFile(ec);
	}

	@Test
	public void testReloadPermissions() {
		ERXProperties.setStringForKey("SampleRolesFile", RolesFileLoader.rolesFileNamePropertyKey);
		RolesFileLoader.loadRolesFile(ec);
	}

	@Test
	public void testLoadPermissionsWithNoFileDoesNothing() {
		ERXProperties.removeKey(RolesFileLoader.rolesFileNamePropertyKey);
		RolesFileLoader.loadRolesFile(ec);
	}

	@Test
	public void rolesGroupsAreCreated() {
		NSArray<KARoleGroup> foundGroups = KARoleGroup.fetchAllKARoleGroups(ec);
		
		assertEquals(foundGroups.count(), 3);
		
		KARoleGroup.fetchRequiredKARoleGroup(ec, KARoleGroup.CODE_KEY, "Admin");
	}

	@Test
	public void rolesGroupsAdminHas2Roles() {
		KARoleGroup adminGroup = KARoleGroup.fetchRequiredKARoleGroup(ec, KARoleGroup.CODE_KEY, "Admin");
		
		assertEquals(2, adminGroup.roles().count());
	}

	@Test
	public void aTotalOf2ListsWhereCreated() {
		assertEquals(KAAccessList.fetchAllKAAccessLists(ec).count(), 2);
	}

	@Test
	public void aTotalOf5RolesWhereCreated() {
		assertEquals(KARole.fetchAllKARoles(ec).count(), 5);
	}

	@Test
	public void roleSeeGrossProfitsIsLinkedToNoListsDoesNotAllowsMultipleItems() {
		KARole role = KARole.fetchRequiredKARole(ec, KARole.CODE.eq(SeeGrossProfitsRoleCode));
		
		assertNull(role.list());
		assertFalse("SeeGrossProfits Role does not allows multiple items", role.allowsMultipleItems());
	}

	@Test
	public void roleCustomerSalesReportIsLinkedToListTerritoryAndAllowsMultipleItems() {
		KARole role = KARole.fetchRequiredKARole(ec, KARole.CODE.eq(CustomerSalesReportRoleCode));
		KAAccessList territoryList = KAAccessList.fetchListWithCode(ec, territoryListCode);
		
		assertEquals(role.list(), territoryList);
		assertTrue("CustomerSalesReport Role allows multiple items", role.allowsMultipleItems());
	}

	@Test
	public void roleWharehouseSalesReportIsLinkedToListWharehouseDoesNotAllowsMultipleItems() {
		KARole role = KARole.fetchRequiredKARole(ec, KARole.CODE.eq(WharehouseSalesReportRoleCode));
		KAAccessList territoryList = KAAccessList.fetchListWithCode(ec, WharehouseListCode);
		
		assertEquals(role.list(), territoryList);
		assertFalse("WharehouseSalesReport Role does allows multiple items", role.allowsMultipleItems());
	}

	@Test
	public void aTotalOf3ProfilesWhereCreated() {
		assertEquals(KAProfile.fetchAllKAProfiles(ec).count(), 3);
	}

	@Test
	public void profileSalesmanContainsOnlyCustomerSalesReportRole() {
		KAProfile profile = KAProfile.fetchRequiredKAProfile(ec, KAProfile.CODE.eq(SalesmanProfileCode));
		KARole role = KARole.fetchRequiredKARole(ec, KARole.CODE.eq(CustomerSalesReportRoleCode));

		assertEquals(profile.mandatoryRoles().count(), 1);
		assertEquals(profile.mandatoryRoles().objectAtIndex(0), role);
	}

	@Test
	public void profileSalesmanContainsOptionalSeeGrossProfitsRole() {
		KAProfile profile = KAProfile.fetchRequiredKAProfile(ec, KAProfile.CODE.eq(SalesmanProfileCode));
		KARole role = KARole.fetchRequiredKARole(ec, KARole.CODE.eq(SeeGrossProfitsRoleCode));

		assertEquals(profile.optionalRoles().count(), 1);
		assertEquals(profile.optionalRoles().objectAtIndex(0), role);
	}

}
