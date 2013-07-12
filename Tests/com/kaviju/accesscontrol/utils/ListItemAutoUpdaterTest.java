package com.kaviju.accesscontrol.utils;

import static org.junit.Assert.*;

import org.junit.*;

import com.kaviju.accesscontrol.annotation.AutoSyncWithAccessList;
import com.kaviju.accesscontrol.model.*;
import com.webobjects.eoaccess.EOEntity;
import com.wounit.annotations.*;
import com.wounit.rules.MockEditingContext;

import er.extensions.eof.ERXEOAccessUtilities;

public class ListItemAutoUpdaterTest {
	static private final String roleListCode = "Role";

	static private final String newRoleCode = "admin";
	static private final String existingRoleCode = "user";

	@Rule
    public MockEditingContext ec = new MockEditingContext("KAAccessControl");

	@UnderTest private KAAccessList listUnderTest;
	
	@Dummy KARoleGroup roleGroup;
	
	@Before
	public void setKARoleEntityClassForAnnotatedVersion() {
		EOEntity roleEntity = ERXEOAccessUtilities.entityNamed(ec, KARole.ENTITY_NAME);
		roleEntity.setClassName(KARoleWithAnnotation.class.getName());
		
		listUnderTest.setCode(roleListCode);
	}
	
	@Test
	public void initializingListItemAutoUpdaterSyncRegisteredEntities() {
		KARole newRole = createExistingRole();
		ec.saveChanges();

		ListItemAutoUpdater updater = new ListItemAutoUpdater(ec);
		ec.saveChanges();
		updater.unregisterFromNoticiationCenter();

		assertEquals(existingRoleCode, listUnderTest.itemWithCode(newRole.primaryKey()).name());
	}

	@Test
	public void createNewRoleAddAListItemWithTheNewID() {
		ListItemAutoUpdater updater = new ListItemAutoUpdater(ec);
		KARole newRole = roleGroup.createRolesRelationship();
		newRole.setCode(newRoleCode);
		newRole.setAllowsMultipleItems(false);
		newRole.setDisplayOrder(0);
		newRole.setInProfileOnly(false);
		// primaryKeyInTransaction() does not works with the MemoryAdaptor, we hint a value.
		newRole._setValueForPrimaryKey(1, "id"); 

		ec.saveChanges();
		updater.unregisterFromNoticiationCenter();
		
		// The memory adaptor set a primaryKey even with our setted value, we cannot it it to verify, we use the name.
		assertEquals(newRoleCode, listUnderTest.items().objectAtIndex(0).name());
	}

	@Test
	public void deleteExistingRoleUpdateList() {
		KARole newRole = createExistingRole();
		ec.saveChanges();
		ListItemAutoUpdater updater = new ListItemAutoUpdater(ec);
		ec.saveChanges();

		newRole.delete();
		ec.saveChanges();
		updater.unregisterFromNoticiationCenter();

		assertNull(listUnderTest.itemWithCode(newRole.primaryKey()));
	}

	private KARole createExistingRole() {
		KARole newRole = roleGroup.createRolesRelationship();
		newRole.setCode(existingRoleCode);
		newRole.setAllowsMultipleItems(false);
		newRole.setDisplayOrder(0);
		newRole.setInProfileOnly(false);
		return newRole;
	}

	
	@SuppressWarnings("serial")
	@AutoSyncWithAccessList(listCode=roleListCode,nameProperty="code")
	static public class KARoleWithAnnotation extends KARole {}

}
