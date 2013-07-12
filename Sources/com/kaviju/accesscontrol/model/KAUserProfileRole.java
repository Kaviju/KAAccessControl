package com.kaviju.accesscontrol.model;

import org.apache.log4j.Logger;

import com.webobjects.eocontrol.EOEnterpriseObject;
import com.webobjects.foundation.*;

import er.extensions.eof.ERXEOControlUtilities;

@SuppressWarnings("serial")
public class KAUserProfileRole extends com.kaviju.accesscontrol.model.base._KAUserProfileRole {
	@SuppressWarnings("unused")
	private static Logger log = Logger.getLogger(KAUserProfileRole.class);

	@SuppressWarnings("unchecked")
	public NSArray<String> itemCodes() {
		return (NSArray<String>) valueForKey(LIST_ITEMS.dot(KAAccessListItem.CODE_KEY));	
	}
	
	public <T extends EOEnterpriseObject> NSArray<T> itemsAsObjects(Class<T> entityClass) {
		String entityName = entityClass.getSimpleName();
		NSMutableArray<T> eos = new NSMutableArray<T>();
		
		for (String code : itemCodes()) {
			@SuppressWarnings("unchecked")
			T eo = (T)ERXEOControlUtilities.objectWithPrimaryKeyValue(editingContext(), entityName, code, null);
			eos.addObject(eo);
		}
		return eos.immutableClone();
	}
	
	public boolean isFromProfile() {
		KARole myRole = role();
		return userProfile().profile().roles().contains(myRole);
	}
}
