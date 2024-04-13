package com.kaviju.accesscontrol.model;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import com.webobjects.eocontrol.EOEnterpriseObject;
import com.webobjects.foundation.*;

import er.extensions.eof.ERXEOControlUtilities;

@SuppressWarnings("serial")
public class KAUserProfileRole extends com.kaviju.accesscontrol.model.base._KAUserProfileRole {
	private static Logger log = LoggerFactory.getLogger(KAUserProfileRole.class);

	public NSArray<String> itemCodes() {
		NSArray<KAAccessListItem> listItems = listItems(null, KAAccessListItem.CODE.ascs());
		return KAAccessListItem.CODE.arrayValueInObject(listItems);
	}

	public NSArray<String> itemNames() {
		NSArray<KAAccessListItem> listItems = listItems(null, KAAccessListItem.NAME.ascs());
		return KAAccessListItem.NAME.arrayValueInObject(listItems);
	}

	public <T extends EOEnterpriseObject> NSArray<T> itemsAsObjects(Class<T> entityClass) {
		String entityName = entityClass.getSimpleName();
		NSMutableArray<T> eos = new NSMutableArray<T>();
		
		for (KAAccessListItem item : listItems()) {
			@SuppressWarnings("unchecked")
			T eo = (T)ERXEOControlUtilities.objectWithPrimaryKeyValue(editingContext(), entityName, Integer.parseInt(item.code()), null, false);
			if (eo == null) {
				log.warn("Object from entity "+entityName+" not found for item with code "+item.code()+" in list "+item.list().code()+" for user "+userProfile().user());
			}
			else {
				eos.addObject(eo);
			}
		}
		return eos.immutableClone();
	}
	
	public boolean isMandatory() {
		KARole myRole = role();
		return userProfile().profile().mandatoryRoles().contains(myRole);
	}
	
	@Override
	public String toString() {
		return "<KAUserProfileRole roleCode="+role().code()+">";
	}
}
