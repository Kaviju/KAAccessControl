package com.kaviju.accesscontrol.utils;

import java.util.Set;

import org.reflections.Reflections;

import com.kaviju.accesscontrol.annotation.AllowedAsItemForRole;
import com.kaviju.accesscontrol.model.*;
import com.webobjects.eocontrol.EOEditingContext;
import com.webobjects.foundation.*;

import er.extensions.eof.ERXKey;
import er.extensions.foundation.ERXArrayUtilities;

public class ListItemFromClassUpdater {
	
	private EOEditingContext ec;
	private NSDictionary<String,NSArray<ItemAnnotation>> annotationsByRoleCode;
	
	static public void updateItemsFromAnnotations(EOEditingContext ec) {
		new ListItemFromClassUpdater(ec).updateItem();
	}
	
	private ListItemFromClassUpdater(EOEditingContext ec) {
		this.ec = ec;
	}
	
	private void updateItem() {
		findAnnotations();
		syncClassItemAnnotations(ec);
	}

	private void findAnnotations() {
		NSMutableArray<ItemAnnotation> annotations = new NSMutableArray<>();
		Reflections reflections = new Reflections();
		Set<Class<?>> classes = reflections.getTypesAnnotatedWith(AllowedAsItemForRole.class);
		for (Class<?> itemClass : classes) {
			AllowedAsItemForRole annotation = (AllowedAsItemForRole) itemClass.getAnnotation(AllowedAsItemForRole.class);
			if (annotation != null) {
				annotations.addObject(new ItemAnnotation(itemClass, annotation));
			}
		}
		annotationsByRoleCode = ERXArrayUtilities.arrayGroupedByKeyPath(annotations, ItemAnnotation.RoleCode);
	}

	private void syncClassItemAnnotations(EOEditingContext ec) {
		NSDictionary<String,KARole> roles = ERXArrayUtilities.dictionaryOfObjectsIndexedByKeyPath(KARole.fetchAllKARoles(ec), KARole.CODE);
		
		for (String roleCode : annotationsByRoleCode.allKeys()) {
			KAAccessList list = roles.objectForKey(roleCode).list();
			NSMutableArray<KAAccessListItem> itemsToDelete = list.items().mutableClone();
					
			for (ItemAnnotation annotation : annotationsByRoleCode.objectForKey(roleCode)) {
				KAAccessListItem item = list.insertItemWithCodeAndName(annotation.targetClassName(), annotation.itemName);
				itemsToDelete.removeObject(item);
			}
			for (KAAccessListItem itemToDelete : itemsToDelete) {
				itemToDelete.delete();
			}
		}
	}
	
	static public class ItemAnnotation {
		public static ERXKey<String> RoleCode = new ERXKey<>("roleCode");

		public final Class<?> targetClass;
		public final String roleCode;
		public final String itemName;
		
		ItemAnnotation(Class<?> targetClass, AllowedAsItemForRole annotation) {
			this.targetClass = targetClass;
			this.roleCode = annotation.roleCode();
			this.itemName = annotation.itemName();
		}
		
		public String targetClassName() {
			return targetClass.getSimpleName();
		}
	}
}
