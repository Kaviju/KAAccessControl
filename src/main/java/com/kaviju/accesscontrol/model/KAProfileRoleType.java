package com.kaviju.accesscontrol.model;

import com.webobjects.foundation.NSArray;

public enum KAProfileRoleType {
	Mandatory(Constants.MandatoryDbValue), Optional(Constants.OptionalDbValue), ByDefault(Constants.ByDefaultDbValue);
	
	public static final NSArray<KAProfileRoleType> OptionalTypes = new NSArray<>(Optional, ByDefault);

	private int databaseValue;

	private KAProfileRoleType(int databaseValue) {
		this.databaseValue = databaseValue;
	}
	
	public static KAProfileRoleType valueFromDatabase(Integer databaseValue) {
		switch (databaseValue) {
			case Constants.MandatoryDbValue:
				return Mandatory;
			case Constants.OptionalDbValue:	
				return Optional;
			case Constants.ByDefaultDbValue:
				return ByDefault;
			default:
				throw new IllegalArgumentException("KAProfileRoleType.valueFromDatabase unknow for "+ databaseValue);
		}
	}
	
	public int databaseValue() {
		return databaseValue;
	}
	
	private static class Constants {
		private static final int MandatoryDbValue = 0;
		private static final int OptionalDbValue = 1;
		private static final int ByDefaultDbValue = 2;
	}
}
