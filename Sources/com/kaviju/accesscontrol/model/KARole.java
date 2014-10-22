package com.kaviju.accesscontrol.model;

import org.apache.log4j.Logger;

import com.webobjects.eocontrol.EOEditingContext;

import er.extensions.eof.ERXKey;
import er.extensions.localization.ERXLocalizer;

@SuppressWarnings("serial")
public class KARole extends com.kaviju.accesscontrol.model.base._KARole {
	@SuppressWarnings("unused")
	private static Logger log = Logger.getLogger(KARole.class);
	public static final ERXKey<String> LOCALIZED_NAME = new ERXKey<String>("localizedName");
	
	static public KARole roleWithCode(EOEditingContext ec, String code) {
		return fetchRequiredKARole(ec, CODE.eq(code));
	}
	
	public String localizedName() {
		return ERXLocalizer.currentLocalizer().localizedStringForKeyWithDefault("Role."+code());
	}
}
