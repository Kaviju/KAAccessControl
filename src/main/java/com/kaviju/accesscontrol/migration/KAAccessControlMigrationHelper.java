package com.kaviju.accesscontrol.migration;
import er.extensions.migration.*;

public class KAAccessControlMigrationHelper {

	static public void addForeignKeyAndIndex(ERXMigrationDatabase database, String realUserTableName) throws Throwable {
		ERXMigrationTable kaUserProfileTable = database.existingTableNamed("KAUserProfile");
		kaUserProfileTable.addForeignKey("user_id", realUserTableName, "id");

	}
}