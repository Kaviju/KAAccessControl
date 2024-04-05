package com.kaviju.accesscontrol.model;

import org.apache.log4j.Logger;

import com.webobjects.eocontrol.EOEditingContext;

import er.extensions.eof.ERXKey;

@SuppressWarnings("serial")
public class KAProfileRole extends com.kaviju.accesscontrol.model.base._KAProfileRole {
	@SuppressWarnings("unused")
	private static Logger log = Logger.getLogger(KAProfileRole.class);
	public static final ERXKey<Boolean> IS_OPTIONAL = new ERXKey<>("isOptional");
	public static final ERXKey<Boolean> IS_MANDATORY = new ERXKey<>("isMandatory");
	public static final ERXKey<Boolean> IS_BY_DEFAULT = new ERXKey<>("isByDefault");
	
	@Override
	public void awakeFromInsertion(EOEditingContext editingContext) {
		super.awakeFromInsertion(editingContext);
		setType(KAProfileRoleType.Optional);
	}
	
	public boolean isOptional() {
		return  type() == KAProfileRoleType.Optional;
	}

	public boolean isMandatory() {
		return  type() == KAProfileRoleType.Mandatory;
	}

	public boolean isByDefault() {
		return  type() == KAProfileRoleType.ByDefault;
	}

}
