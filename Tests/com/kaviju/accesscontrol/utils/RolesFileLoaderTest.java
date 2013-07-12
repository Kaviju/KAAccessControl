package com.kaviju.accesscontrol.utils;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

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
		
		assertThat(foundGroups.count(), is(3));
		
		KARoleGroup.fetchRequiredKARoleGroup(ec, KARoleGroup.CODE_KEY, "Admin");
	}

	@Test
	public void rolesGroupsAdminHas2Roles() {
		KARoleGroup adminGroup = KARoleGroup.fetchRequiredKARoleGroup(ec, KARoleGroup.CODE_KEY, "Admin");
		
		assertEquals(2, adminGroup.roles().count());
	}

	@Test
	public void aTotalOf2ListsWhereCreated() {
		assertThat(KAAccessList.fetchAllKAAccessLists(ec).count(), is(2));
	}

	@Test
	public void aTotalOf5RolesWhereCreated() {
		assertThat(KARole.fetchAllKARoles(ec).count(), is(5));
	}

	@Test
	public void roleSeeGrossProfitsIsLinkedToNoListsDoesNotAllowsMultipleItems() {
		KARole role = KARole.fetchRequiredKARole(ec, KARole.CODE.eq(SeeGrossProfitsRoleCode));
		
		assertThat(role.list(), nullValue());
		assertThat("SeeGrossProfits Role does not allows multiple items", role.allowsMultipleItems(), is(false));
	}

	@Test
	public void roleCustomerSalesReportIsLinkedToListTerritoryAndAllowsMultipleItems() {
		KARole role = KARole.fetchRequiredKARole(ec, KARole.CODE.eq(CustomerSalesReportRoleCode));
		KAAccessList territoryList = KAAccessList.fetchRequiredKAAccessList(ec, KAAccessList.CODE.eq(territoryListCode));
		
		assertThat(role.list(), is(territoryList));
		assertThat("CustomerSalesReport Role allows multiple items", role.allowsMultipleItems(), is(true));
	}

	@Test
	public void roleWharehouseSalesReportIsLinkedToListWharehouseDoesNotAllowsMultipleItems() {
		KARole role = KARole.fetchRequiredKARole(ec, KARole.CODE.eq(WharehouseSalesReportRoleCode));
		KAAccessList territoryList = KAAccessList.fetchRequiredKAAccessList(ec, KAAccessList.CODE.eq(WharehouseListCode));
		
		assertThat(role.list(), is(territoryList));
		assertThat("WharehouseSalesReport Role does allows multiple items", role.allowsMultipleItems(), is(false));
	}

	@Test
	public void aTotalOf3ProfilesWhereCreated() {
		assertThat(KAProfile.fetchAllKAProfiles(ec).count(), is(3));
	}

	@Test
	public void profileSalesmanContainsOnlyCustomerSalesReportRole() {
		KAProfile profile = KAProfile.fetchRequiredKAProfile(ec, KAProfile.CODE.eq(SalesmanProfileCode));
		KARole role = KARole.fetchRequiredKARole(ec, KARole.CODE.eq(CustomerSalesReportRoleCode));

		assertThat(profile.roles().count(), is(1));
		assertThat(profile.roles().objectAtIndex(0), is(role));
	}

}
