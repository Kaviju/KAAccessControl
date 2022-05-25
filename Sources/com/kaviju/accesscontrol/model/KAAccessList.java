package com.kaviju.accesscontrol.model;

import org.apache.log4j.Logger;

import com.webobjects.eocontrol.EOEditingContext;
import com.webobjects.foundation.NSArray;

@SuppressWarnings("serial")
public class KAAccessList extends com.kaviju.accesscontrol.model.base._KAAccessList {
	@SuppressWarnings("unused")
	private static Logger log = Logger.getLogger(KAAccessList.class);
	
	public static KAAccessList fetchListWithCode(EOEditingContext editingContext, String listCode) {
		return fetchRequiredKAAccessList(editingContext, CODE.eq(listCode));
	}

	public void deleteItemWithCode(String code) {
		KAAccessListItem foundItem = itemWithCode(code);
		if (foundItem != null) {
			deleteItemsRelationship(foundItem);
		}
	}

	public KAAccessListItem insertItemWithCodeAndName(String code, String name) {
		KAAccessListItem foundItem = itemWithCode(code);
		if (foundItem == null) {
			foundItem = createItemsRelationship();
			foundItem.setCode(code);
		}
		foundItem.setName(name);
		return foundItem;
	}
	
	public KAAccessListItem itemWithCode(String code) {
		NSArray<KAAccessListItem> foundItems = items(KAAccessListItem.CODE.eq(code));
		if (foundItems.count() == 1) {
			return foundItems.objectAtIndex(0);
		}
		return null;
	}
	
}
