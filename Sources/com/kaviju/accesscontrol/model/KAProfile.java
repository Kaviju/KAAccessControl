package com.kaviju.accesscontrol.model;

import org.apache.log4j.Logger;

import com.webobjects.eocontrol.EOEditingContext;
import com.webobjects.foundation.NSArray;

import er.extensions.eof.ERXKey;
import er.extensions.localization.ERXLocalizer;

@SuppressWarnings("serial")
public class KAProfile extends com.kaviju.accesscontrol.model.base._KAProfile {
	@SuppressWarnings("unused")
	private static Logger log = Logger.getLogger(KAProfile.class);
	public static final ERXKey<String> LOCALIZED_NAME = new ERXKey<String>("localizedName");
	
	static public KAProfile profileWithCode(EOEditingContext ec, String code) {
		return fetchRequiredKAProfile(ec, CODE.eq(code));
	}
	
	public String localizedName() {
		return ERXLocalizer.currentLocalizer().localizedStringForKeyWithDefault("Profile."+code());
	}

	public static NSArray<KAProfile> fetchProfiles(EOEditingContext editingContext) {
		NSArray<KAProfile> profiles = fetchAllKAProfiles(editingContext);
		
		return LOCALIZED_NAME.asc().sorted(profiles);
	}
}
