package com.kaviju.accesscontrol.migration;
import com.webobjects.eocontrol.EOEditingContext;
import com.webobjects.foundation.NSArray;

import er.extensions.migration.*;

public class KAAccessControl0 extends ERXMigrationDatabase.Migration {
	@Override
	public NSArray<ERXModelVersion> modelDependencies() {
		return null;
	}
  
	@Override
	public void downgrade(EOEditingContext editingContext, ERXMigrationDatabase database) throws Throwable {
		// DO NOTHING
	}

	@Override
	public void upgrade(EOEditingContext editingContext, ERXMigrationDatabase database) throws Throwable {
		ERXMigrationTable kaAccessListTable = database.newTableNamed("KAAccessList");
		kaAccessListTable.newStringColumn("code", 50, false);
		kaAccessListTable.newIntegerColumn("id", false);

		kaAccessListTable.addUniqueIndex("KAAccessList_UniqueCodeIndex", kaAccessListTable.existingColumnNamed("code"));

		kaAccessListTable.create();
	 	kaAccessListTable.setPrimaryKey("id");

		ERXMigrationTable kaAccessListItemTable = database.newTableNamed("KAAccessListItem");
		kaAccessListItemTable.newIntegerColumn("accessList_id", false);
		kaAccessListItemTable.newStringColumn("code", 50, false);
		kaAccessListItemTable.newIntegerColumn("id", false);
		kaAccessListItemTable.newStringColumn("name", 50, false);

		kaAccessListItemTable.addIndex("code");
		kaAccessListItemTable.addUniqueIndex("KAAccessListItem_UniqueCodeIndex", kaAccessListItemTable.existingColumnNamed("code"), kaAccessListItemTable.existingColumnNamed("accessList_id"));

		kaAccessListItemTable.create();
	 	kaAccessListItemTable.setPrimaryKey("id");

		ERXMigrationTable kaProfileTable = database.newTableNamed("KAProfile");
		kaProfileTable.newStringColumn("code", 50, false);
		kaProfileTable.newIntegerColumn("id", false);

		kaProfileTable.addUniqueIndex("KAProfile_UniqueCodeIndex", kaProfileTable.existingColumnNamed("code"));

		kaProfileTable.create();
	 	kaProfileTable.setPrimaryKey("id");

		ERXMigrationTable kaProfileRoleTable = database.newTableNamed("KAProfileRole");
		kaProfileRoleTable.newIntegerColumn("profile_id", false);
		kaProfileRoleTable.newIntegerColumn("role_id", false);
		kaProfileRoleTable.newIntBooleanColumn("isOptional", false);


		kaProfileRoleTable.create();
	 	kaProfileRoleTable.setPrimaryKey("profile_id", "role_id");

		ERXMigrationTable kaRoleTable = database.newTableNamed("KARole");
		kaRoleTable.newIntegerColumn("accessList_id", true);
		kaRoleTable.newIntBooleanColumn("allowsMultipleItems", false);
		kaRoleTable.newStringColumn("code", 50, false);
		kaRoleTable.newIntegerColumn("displayOrder", false);
		kaRoleTable.newIntegerColumn("id", false);
		kaRoleTable.newIntegerColumn("roleGroup_id", false);

		kaRoleTable.addUniqueIndex("KARole_UniqueCodeIndex", kaRoleTable.existingColumnNamed("code"));

		kaRoleTable.create();
	 	kaRoleTable.setPrimaryKey("id");

		ERXMigrationTable kaRoleGroupTable = database.newTableNamed("KARoleGroup");
		kaRoleGroupTable.newStringColumn("code", 50, false);
		kaRoleGroupTable.newIntegerColumn("displayOrder", false);
		kaRoleGroupTable.newIntegerColumn("id", false);


		kaRoleGroupTable.create();
	 	kaRoleGroupTable.setPrimaryKey("id");


		ERXMigrationTable kaUserProfileTable = database.newTableNamed("KAUserProfile");
		kaUserProfileTable.newIntegerColumn("id", false);
		kaUserProfileTable.newIntBooleanColumn("isDefaultProfile", false);
		kaUserProfileTable.newIntegerColumn("profile_id", false);
		kaUserProfileTable.newIntegerColumn("user_id", false);


		kaUserProfileTable.create();
	 	kaUserProfileTable.setPrimaryKey("id");

		ERXMigrationTable kaUserProfileRoleTable = database.newTableNamed("KAUserProfileRole");
		kaUserProfileRoleTable.newIntegerColumn("id", false);
		kaUserProfileRoleTable.newIntegerColumn("role_id", false);
		kaUserProfileRoleTable.newIntegerColumn("userProfile_id", false);


		kaUserProfileRoleTable.create();
	 	kaUserProfileRoleTable.setPrimaryKey("id");

		ERXMigrationTable kaUserProfileRoleItemTable = database.newTableNamed("KAUserProfileRoleItem");
		kaUserProfileRoleItemTable.newIntegerColumn("permissionListItem_id", false);
		kaUserProfileRoleItemTable.newIntegerColumn("userProfileRole_id", false);


		kaUserProfileRoleItemTable.create();
	 	kaUserProfileRoleItemTable.setPrimaryKey("permissionListItem_id", "userProfileRole_id");

		kaAccessListItemTable.addForeignKey("accessList_id", "KAAccessList", "id");
		kaProfileRoleTable.addForeignKey("profile_id", "KAProfile", "id");
		kaProfileRoleTable.addForeignKey("role_id", "KARole", "id");
		kaRoleTable.addForeignKey("roleGroup_id", "KARoleGroup", "id");
		kaRoleTable.addForeignKey("accessList_id", "KAAccessList", "id");
		kaUserProfileTable.addForeignKey("profile_id", "KAProfile", "id");
		kaUserProfileRoleTable.addForeignKey("role_id", "KARole", "id");
		kaUserProfileRoleTable.addForeignKey("userProfile_id", "KAUserProfile", "id");
		kaUserProfileRoleItemTable.addForeignKey("permissionListItem_id", "KAAccessListItem", "id");
		kaUserProfileRoleItemTable.addForeignKey("userProfileRole_id", "KAUserProfileRole", "id");
	}
}