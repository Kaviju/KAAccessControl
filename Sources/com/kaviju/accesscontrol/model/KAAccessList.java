package com.kaviju.accesscontrol.model;

import org.apache.log4j.Logger;

import com.webobjects.foundation.NSArray;

@SuppressWarnings("serial")
public class KAAccessList extends com.kaviju.accesscontrol.model.base._KAAccessList {
	@SuppressWarnings("unused")
	private static Logger log = Logger.getLogger(KAAccessList.class);

	public void deleteItemWithCode(String code) {
		KAAccessListItem foundItem = itemWithCode(code);
		if (foundItem != null) {
			deleteItemsRelationship(foundItem);
		}
	}

	public void insertItemWithCodeAndName(String code, String name) {
		KAAccessListItem foundItem = itemWithCode(code);
		if (foundItem == null) {
			KAAccessListItem newItem = createItemsRelationship();
			newItem.setCode(code);
			newItem.setName(name);
		}
	}
	
	public KAAccessListItem itemWithCode(String code) {
		NSArray<KAAccessListItem> foundItems = items(KAAccessListItem.CODE.eq(code));
		if (foundItems.count() == 1) {
			return foundItems.objectAtIndex(0);
		}
		return null;
	}
	
}
