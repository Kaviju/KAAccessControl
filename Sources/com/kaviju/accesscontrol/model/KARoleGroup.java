package com.kaviju.accesscontrol.model;

import org.apache.log4j.Logger;

import er.extensions.eof.ERXKey;
import er.extensions.localization.ERXLocalizer;

@SuppressWarnings("serial")
public class KARoleGroup extends com.kaviju.accesscontrol.model.base._KARoleGroup {
	@SuppressWarnings("unused")
	private static Logger log = Logger.getLogger(KARoleGroup.class);
	public static final ERXKey<String> LOCALIZED_NAME = new ERXKey<String>("localizedName");
	
	public String localizedName() {
		return ERXLocalizer.currentLocalizer().localizedStringForKeyWithDefault("RoleGroup."+code());
	}
}
