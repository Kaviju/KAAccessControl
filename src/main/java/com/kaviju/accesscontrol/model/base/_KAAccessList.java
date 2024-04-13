// DO NOT EDIT.  Make changes to com.kaviju.accesscontrol.model.KAAccessList.java instead.
package com.kaviju.accesscontrol.model.base;

import com.webobjects.eoaccess.*;
import com.webobjects.eocontrol.*;
import com.webobjects.foundation.*;
import java.math.*;
import java.util.*;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import er.extensions.eof.*;
import er.extensions.foundation.*;

@SuppressWarnings("all")
public abstract class _KAAccessList extends  ERXGenericRecord {
  public static final String ENTITY_NAME = "KAAccessList";

  // Attribute Keys
  public static final ERXKey<String> CODE = new ERXKey<String>("code");
  // Relationship Keys
  public static final ERXKey<com.kaviju.accesscontrol.model.KAAccessListItem> ITEMS = new ERXKey<com.kaviju.accesscontrol.model.KAAccessListItem>("items");
  public static final ERXKey<com.kaviju.accesscontrol.model.KARole> ROLES = new ERXKey<com.kaviju.accesscontrol.model.KARole>("roles");

  // Attributes
  public static final String CODE_KEY = "code";
  // Relationships
  public static final String ITEMS_KEY = "items";
  public static final String ROLES_KEY = "roles";

  private static Logger LOG = LoggerFactory.getLogger(_KAAccessList.class);

  public com.kaviju.accesscontrol.model.KAAccessList localInstanceIn(EOEditingContext editingContext) {
    com.kaviju.accesscontrol.model.KAAccessList localInstance = (com.kaviju.accesscontrol.model.KAAccessList)EOUtilities.localInstanceOfObject(editingContext, this);
    if (localInstance == null) {
      throw new IllegalStateException("You attempted to localInstance " + this + ", which has not yet committed.");
    }
    return localInstance;
  }

  public String code() {
    return (String) storedValueForKey(_KAAccessList.CODE_KEY);
  }

  public void setCode(String value) {
    if (_KAAccessList.LOG.isDebugEnabled()) {
    	_KAAccessList.LOG.debug( "updating code from " + code() + " to " + value);
    }
    takeStoredValueForKey(value, _KAAccessList.CODE_KEY);
  }

  public NSArray<com.kaviju.accesscontrol.model.KAAccessListItem> items() {
    return (NSArray<com.kaviju.accesscontrol.model.KAAccessListItem>)storedValueForKey(_KAAccessList.ITEMS_KEY);
  }

  public NSArray<com.kaviju.accesscontrol.model.KAAccessListItem> items(EOQualifier qualifier) {
    return items(qualifier, null, false);
  }

  public NSArray<com.kaviju.accesscontrol.model.KAAccessListItem> items(EOQualifier qualifier, boolean fetch) {
    return items(qualifier, null, fetch);
  }

  public NSArray<com.kaviju.accesscontrol.model.KAAccessListItem> items(EOQualifier qualifier, NSArray<EOSortOrdering> sortOrderings, boolean fetch) {
    NSArray<com.kaviju.accesscontrol.model.KAAccessListItem> results;
    if (fetch) {
      EOQualifier fullQualifier;
      EOQualifier inverseQualifier = new EOKeyValueQualifier(com.kaviju.accesscontrol.model.KAAccessListItem.LIST_KEY, EOQualifier.QualifierOperatorEqual, this);
    	
      if (qualifier == null) {
        fullQualifier = inverseQualifier;
      }
      else {
        NSMutableArray<EOQualifier> qualifiers = new NSMutableArray<EOQualifier>();
        qualifiers.addObject(qualifier);
        qualifiers.addObject(inverseQualifier);
        fullQualifier = new EOAndQualifier(qualifiers);
      }

      results = com.kaviju.accesscontrol.model.KAAccessListItem.fetchKAAccessListItems(editingContext(), fullQualifier, sortOrderings);
    }
    else {
      results = items();
      if (qualifier != null) {
        results = (NSArray<com.kaviju.accesscontrol.model.KAAccessListItem>)EOQualifier.filteredArrayWithQualifier(results, qualifier);
      }
      if (sortOrderings != null) {
        results = (NSArray<com.kaviju.accesscontrol.model.KAAccessListItem>)EOSortOrdering.sortedArrayUsingKeyOrderArray(results, sortOrderings);
      }
    }
    return results;
  }
  
  public void addToItems(com.kaviju.accesscontrol.model.KAAccessListItem object) {
    includeObjectIntoPropertyWithKey(object, _KAAccessList.ITEMS_KEY);
  }

  public void removeFromItems(com.kaviju.accesscontrol.model.KAAccessListItem object) {
    excludeObjectFromPropertyWithKey(object, _KAAccessList.ITEMS_KEY);
  }

  public void addToItemsRelationship(com.kaviju.accesscontrol.model.KAAccessListItem object) {
    if (_KAAccessList.LOG.isDebugEnabled()) {
      _KAAccessList.LOG.debug("adding " + object + " to items relationship");
    }
    if (er.extensions.eof.ERXGenericRecord.InverseRelationshipUpdater.updateInverseRelationships()) {
    	addToItems(object);
    }
    else {
    	addObjectToBothSidesOfRelationshipWithKey(object, _KAAccessList.ITEMS_KEY);
    }
  }

  public void removeFromItemsRelationship(com.kaviju.accesscontrol.model.KAAccessListItem object) {
    if (_KAAccessList.LOG.isDebugEnabled()) {
      _KAAccessList.LOG.debug("removing " + object + " from items relationship");
    }
    if (er.extensions.eof.ERXGenericRecord.InverseRelationshipUpdater.updateInverseRelationships()) {
    	removeFromItems(object);
    }
    else {
    	removeObjectFromBothSidesOfRelationshipWithKey(object, _KAAccessList.ITEMS_KEY);
    }
  }

  public com.kaviju.accesscontrol.model.KAAccessListItem createItemsRelationship() {
    EOClassDescription eoClassDesc = EOClassDescription.classDescriptionForEntityName( com.kaviju.accesscontrol.model.KAAccessListItem.ENTITY_NAME );
    EOEnterpriseObject eo = eoClassDesc.createInstanceWithEditingContext(editingContext(), null);
    editingContext().insertObject(eo);
    addObjectToBothSidesOfRelationshipWithKey(eo, _KAAccessList.ITEMS_KEY);
    return (com.kaviju.accesscontrol.model.KAAccessListItem) eo;
  }

  public void deleteItemsRelationship(com.kaviju.accesscontrol.model.KAAccessListItem object) {
    removeObjectFromBothSidesOfRelationshipWithKey(object, _KAAccessList.ITEMS_KEY);
    editingContext().deleteObject(object);
  }

  public void deleteAllItemsRelationships() {
    Enumeration<com.kaviju.accesscontrol.model.KAAccessListItem> objects = items().immutableClone().objectEnumerator();
    while (objects.hasMoreElements()) {
      deleteItemsRelationship(objects.nextElement());
    }
  }

  public NSArray<com.kaviju.accesscontrol.model.KARole> roles() {
    return (NSArray<com.kaviju.accesscontrol.model.KARole>)storedValueForKey(_KAAccessList.ROLES_KEY);
  }

  public NSArray<com.kaviju.accesscontrol.model.KARole> roles(EOQualifier qualifier) {
    return roles(qualifier, null, false);
  }

  public NSArray<com.kaviju.accesscontrol.model.KARole> roles(EOQualifier qualifier, boolean fetch) {
    return roles(qualifier, null, fetch);
  }

  public NSArray<com.kaviju.accesscontrol.model.KARole> roles(EOQualifier qualifier, NSArray<EOSortOrdering> sortOrderings, boolean fetch) {
    NSArray<com.kaviju.accesscontrol.model.KARole> results;
    if (fetch) {
      EOQualifier fullQualifier;
      EOQualifier inverseQualifier = new EOKeyValueQualifier(com.kaviju.accesscontrol.model.KARole.LIST_KEY, EOQualifier.QualifierOperatorEqual, this);
    	
      if (qualifier == null) {
        fullQualifier = inverseQualifier;
      }
      else {
        NSMutableArray<EOQualifier> qualifiers = new NSMutableArray<EOQualifier>();
        qualifiers.addObject(qualifier);
        qualifiers.addObject(inverseQualifier);
        fullQualifier = new EOAndQualifier(qualifiers);
      }

      results = com.kaviju.accesscontrol.model.KARole.fetchKARoles(editingContext(), fullQualifier, sortOrderings);
    }
    else {
      results = roles();
      if (qualifier != null) {
        results = (NSArray<com.kaviju.accesscontrol.model.KARole>)EOQualifier.filteredArrayWithQualifier(results, qualifier);
      }
      if (sortOrderings != null) {
        results = (NSArray<com.kaviju.accesscontrol.model.KARole>)EOSortOrdering.sortedArrayUsingKeyOrderArray(results, sortOrderings);
      }
    }
    return results;
  }
  
  public void addToRoles(com.kaviju.accesscontrol.model.KARole object) {
    includeObjectIntoPropertyWithKey(object, _KAAccessList.ROLES_KEY);
  }

  public void removeFromRoles(com.kaviju.accesscontrol.model.KARole object) {
    excludeObjectFromPropertyWithKey(object, _KAAccessList.ROLES_KEY);
  }

  public void addToRolesRelationship(com.kaviju.accesscontrol.model.KARole object) {
    if (_KAAccessList.LOG.isDebugEnabled()) {
      _KAAccessList.LOG.debug("adding " + object + " to roles relationship");
    }
    if (er.extensions.eof.ERXGenericRecord.InverseRelationshipUpdater.updateInverseRelationships()) {
    	addToRoles(object);
    }
    else {
    	addObjectToBothSidesOfRelationshipWithKey(object, _KAAccessList.ROLES_KEY);
    }
  }

  public void removeFromRolesRelationship(com.kaviju.accesscontrol.model.KARole object) {
    if (_KAAccessList.LOG.isDebugEnabled()) {
      _KAAccessList.LOG.debug("removing " + object + " from roles relationship");
    }
    if (er.extensions.eof.ERXGenericRecord.InverseRelationshipUpdater.updateInverseRelationships()) {
    	removeFromRoles(object);
    }
    else {
    	removeObjectFromBothSidesOfRelationshipWithKey(object, _KAAccessList.ROLES_KEY);
    }
  }

  public com.kaviju.accesscontrol.model.KARole createRolesRelationship() {
    EOClassDescription eoClassDesc = EOClassDescription.classDescriptionForEntityName( com.kaviju.accesscontrol.model.KARole.ENTITY_NAME );
    EOEnterpriseObject eo = eoClassDesc.createInstanceWithEditingContext(editingContext(), null);
    editingContext().insertObject(eo);
    addObjectToBothSidesOfRelationshipWithKey(eo, _KAAccessList.ROLES_KEY);
    return (com.kaviju.accesscontrol.model.KARole) eo;
  }

  public void deleteRolesRelationship(com.kaviju.accesscontrol.model.KARole object) {
    removeObjectFromBothSidesOfRelationshipWithKey(object, _KAAccessList.ROLES_KEY);
    editingContext().deleteObject(object);
  }

  public void deleteAllRolesRelationships() {
    Enumeration<com.kaviju.accesscontrol.model.KARole> objects = roles().immutableClone().objectEnumerator();
    while (objects.hasMoreElements()) {
      deleteRolesRelationship(objects.nextElement());
    }
  }


public static com.kaviju.accesscontrol.model.KAAccessList createKAAccessList(EOEditingContext editingContext) {
    com.kaviju.accesscontrol.model.KAAccessList eo = (com.kaviju.accesscontrol.model.KAAccessList) EOUtilities.createAndInsertInstance(editingContext, _KAAccessList.ENTITY_NAME);    return eo;
  }

  public static ERXFetchSpecification<com.kaviju.accesscontrol.model.KAAccessList> fetchSpec() {
    return new ERXFetchSpecification<com.kaviju.accesscontrol.model.KAAccessList>(_KAAccessList.ENTITY_NAME, null, null, false, true, null);
  }

  public static NSArray<com.kaviju.accesscontrol.model.KAAccessList> fetchAllKAAccessLists(EOEditingContext editingContext) {
    return _KAAccessList.fetchAllKAAccessLists(editingContext, null);
  }

  public static NSArray<com.kaviju.accesscontrol.model.KAAccessList> fetchAllKAAccessLists(EOEditingContext editingContext, NSArray<EOSortOrdering> sortOrderings) {
    return _KAAccessList.fetchKAAccessLists(editingContext, null, sortOrderings);
  }

  public static NSArray<com.kaviju.accesscontrol.model.KAAccessList> fetchKAAccessLists(EOEditingContext editingContext, EOQualifier qualifier, NSArray<EOSortOrdering> sortOrderings) {
    ERXFetchSpecification<com.kaviju.accesscontrol.model.KAAccessList> fetchSpec = new ERXFetchSpecification<com.kaviju.accesscontrol.model.KAAccessList>(_KAAccessList.ENTITY_NAME, qualifier, sortOrderings);
    fetchSpec.setIsDeep(true);
    NSArray<com.kaviju.accesscontrol.model.KAAccessList> eoObjects = fetchSpec.fetchObjects(editingContext);
    return eoObjects;
  }

  public static com.kaviju.accesscontrol.model.KAAccessList fetchKAAccessList(EOEditingContext editingContext, String keyName, Object value) {
    return _KAAccessList.fetchKAAccessList(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static com.kaviju.accesscontrol.model.KAAccessList fetchKAAccessList(EOEditingContext editingContext, EOQualifier qualifier) {
    NSArray<com.kaviju.accesscontrol.model.KAAccessList> eoObjects = _KAAccessList.fetchKAAccessLists(editingContext, qualifier, null);
    com.kaviju.accesscontrol.model.KAAccessList eoObject;
    int count = eoObjects.count();
    if (count == 0) {
      eoObject = null;
    }
    else if (count == 1) {
      eoObject = eoObjects.objectAtIndex(0);
    }
    else {
      throw new IllegalStateException("There was more than one KAAccessList that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static com.kaviju.accesscontrol.model.KAAccessList fetchRequiredKAAccessList(EOEditingContext editingContext, String keyName, Object value) {
    return _KAAccessList.fetchRequiredKAAccessList(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static com.kaviju.accesscontrol.model.KAAccessList fetchRequiredKAAccessList(EOEditingContext editingContext, EOQualifier qualifier) {
    com.kaviju.accesscontrol.model.KAAccessList eoObject = _KAAccessList.fetchKAAccessList(editingContext, qualifier);
    if (eoObject == null) {
      throw new NoSuchElementException("There was no KAAccessList that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static com.kaviju.accesscontrol.model.KAAccessList localInstanceIn(EOEditingContext editingContext, com.kaviju.accesscontrol.model.KAAccessList eo) {
    com.kaviju.accesscontrol.model.KAAccessList localInstance = (eo == null) ? null : ERXEOControlUtilities.localInstanceOfObject(editingContext, eo);
    if (localInstance == null && eo != null) {
      throw new IllegalStateException("You attempted to localInstance " + eo + ", which has not yet committed.");
    }
    return localInstance;
  }
}
