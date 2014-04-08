package com.kaviju.accesscontrol.utils;

import com.kaviju.accesscontrol.annotation.AutoSyncWithAccessList;
import com.kaviju.accesscontrol.model.KAAccessList;
import com.kaviju.accesscontrol.model.KAAccessListItem;
import com.webobjects.eoaccess.*;
import com.webobjects.eocontrol.*;
import com.webobjects.foundation.*;

import er.extensions.eof.*;
import er.extensions.foundation.ERXSelectorUtilities;

public class ListItemAutoUpdater {
	
	private NSMutableDictionary<String, AutoSyncWithAccessList> entitiesToSync = new NSMutableDictionary<String, AutoSyncWithAccessList>();
	
	public ListItemAutoUpdater(EOEditingContext ec) {
		locateEntitiesToSync(ec);
		syncEntities(ec);
		registerForNotification();
	}

	private void locateEntitiesToSync(EOEditingContext ec) {
        for (EOModel model : ERXEOAccessUtilities.modelGroup(ec).models()) {
			for (EOEntity entity : model.entities()) {
				try {
					Class<?> entityClass = Class.forName(entity.className());
					AutoSyncWithAccessList autoSyncAnnotation = entityClass.getAnnotation(AutoSyncWithAccessList.class);
					if (autoSyncAnnotation != null) {
						entitiesToSync.setObjectForKey(autoSyncAnnotation, entity.name());
					}
				} catch (ClassNotFoundException e) {}
			}
		}
	}
	
	private void syncEntities(EOEditingContext ec) {
		for (String entityName : entitiesToSync.keySet()) {
			AutoSyncWithAccessList annotation = entitiesToSync.objectForKey(entityName);
			KAAccessList list = KAAccessList.fetchListWithCode(ec, annotation.listCode());

			ERXFetchSpecification<ERXEnterpriseObject> fs = new ERXFetchSpecification<ERXEnterpriseObject>(entityName, null, null);
			NSArray<ERXEnterpriseObject> listValues = fs.fetchObjects(ec);
			for (ERXEnterpriseObject value : listValues) {
    			String itemName = value.valueForKey(annotation.nameProperty()).toString();
				list.insertItemWithCodeAndName(value.primaryKey(), itemName);
			}
		}
	}
	
	private void registerForNotification() {
        NSNotificationCenter.defaultCenter().addObserver(this, 
                ERXSelectorUtilities.notificationSelector("editingContextWillSaveChanges"), 
                ERXEC.EditingContextWillSaveChangesNotification, null);
	}
	
    public void editingContextWillSaveChanges(NSNotification n) {
        EOEditingContext ec = (EOEditingContext) n.object();
        ec.processRecentChanges();

        if (ec.hasChanges()) {
        	for (EOEnterpriseObject deletedEo : ec.deletedObjects()) {
        		String entityName = deletedEo.entityName();
        		AutoSyncWithAccessList annotation = entitiesToSync.objectForKey(entityName);
        		
        		if (annotation != null) {
        			KAAccessList list = KAAccessList.fetchListWithCode(ec, annotation.listCode());
        			list.deleteItemWithCode(ERXEOControlUtilities.primaryKeyStringForObject(deletedEo));
        		}
			}

        	for (EOEnterpriseObject updatedEO : ec.updatedObjects()) {
        		String entityName = updatedEO.entityName();
        		AutoSyncWithAccessList annotation = entitiesToSync.objectForKey(entityName);
        		
        		if (annotation != null) {
        			KAAccessList list = KAAccessList.fetchListWithCode(ec, annotation.listCode());
        			KAAccessListItem item = list.itemWithCode(ERXEOControlUtilities.primaryKeyStringForObject(updatedEO));
        			String itemName = updatedEO.valueForKey(annotation.nameProperty()).toString();
        			item.setName(itemName);
        		}
			}

        	for (EOEnterpriseObject insertedEO : ec.insertedObjects()) {
        		String entityName = insertedEO.entityName();
        		AutoSyncWithAccessList annotation = entitiesToSync.objectForKey(entityName);
        		
        		if (annotation != null) {
        			ERXEnterpriseObject insertedErxEO = (ERXEnterpriseObject) insertedEO;
        			KAAccessList list = KAAccessList.fetchListWithCode(ec, annotation.listCode());
        			String itemName = insertedEO.valueForKey(annotation.nameProperty()).toString();
					list.insertItemWithCodeAndName(insertedErxEO.primaryKeyInTransaction(), itemName);
        		}
			}
        }
    }
    
    public void unregisterFromNoticiationCenter() {
    	NSNotificationCenter.defaultCenter().removeObserver(this);
    }
}
