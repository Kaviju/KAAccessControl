// DO NOT EDIT.  Make changes to com.kaviju.accesscontrol.model.KAAccessListItem.java instead.
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
public abstract class _KAAccessListItem extends  ERXGenericRecord {
  public static final String ENTITY_NAME = "KAAccessListItem";

  // Attribute Keys
  public static final ERXKey<String> CODE = new ERXKey<String>("code");
  public static final ERXKey<String> NAME = new ERXKey<String>("name");
  // Relationship Keys
  public static final ERXKey<com.kaviju.accesscontrol.model.KAAccessList> LIST = new ERXKey<com.kaviju.accesscontrol.model.KAAccessList>("list");
  public static final ERXKey<com.kaviju.accesscontrol.model.KAUserProfileRole> USER_PROFILE_ROLES = new ERXKey<com.kaviju.accesscontrol.model.KAUserProfileRole>("userProfileRoles");

  // Attributes
  public static final String CODE_KEY = "code";
  public static final String NAME_KEY = "name";
  // Relationships
  public static final String LIST_KEY = "list";
  public static final String USER_PROFILE_ROLES_KEY = "userProfileRoles";

  private static Logger LOG = LoggerFactory.getLogger(_KAAccessListItem.class);

  public com.kaviju.accesscontrol.model.KAAccessListItem localInstanceIn(EOEditingContext editingContext) {
    com.kaviju.accesscontrol.model.KAAccessListItem localInstance = (com.kaviju.accesscontrol.model.KAAccessListItem)EOUtilities.localInstanceOfObject(editingContext, this);
    if (localInstance == null) {
      throw new IllegalStateException("You attempted to localInstance " + this + ", which has not yet committed.");
    }
    return localInstance;
  }

  public String code() {
    return (String) storedValueForKey(_KAAccessListItem.CODE_KEY);
  }

  public void setCode(String value) {
    if (_KAAccessListItem.LOG.isDebugEnabled()) {
    	_KAAccessListItem.LOG.debug( "updating code from " + code() + " to " + value);
    }
    takeStoredValueForKey(value, _KAAccessListItem.CODE_KEY);
  }

  public String name() {
    return (String) storedValueForKey(_KAAccessListItem.NAME_KEY);
  }

  public void setName(String value) {
    if (_KAAccessListItem.LOG.isDebugEnabled()) {
    	_KAAccessListItem.LOG.debug( "updating name from " + name() + " to " + value);
    }
    takeStoredValueForKey(value, _KAAccessListItem.NAME_KEY);
  }

  public com.kaviju.accesscontrol.model.KAAccessList list() {
    return (com.kaviju.accesscontrol.model.KAAccessList)storedValueForKey(_KAAccessListItem.LIST_KEY);
  }
  
  public void setList(com.kaviju.accesscontrol.model.KAAccessList value) {
    takeStoredValueForKey(value, _KAAccessListItem.LIST_KEY);
  }

  public void setListRelationship(com.kaviju.accesscontrol.model.KAAccessList value) {
    if (_KAAccessListItem.LOG.isDebugEnabled()) {
      _KAAccessListItem.LOG.debug("updating list from " + list() + " to " + value);
    }
    if (er.extensions.eof.ERXGenericRecord.InverseRelationshipUpdater.updateInverseRelationships()) {
    	setList(value);
    }
    else if (value == null) {
    	com.kaviju.accesscontrol.model.KAAccessList oldValue = list();
    	if (oldValue != null) {
    		removeObjectFromBothSidesOfRelationshipWithKey(oldValue, _KAAccessListItem.LIST_KEY);
      }
    } else {
    	addObjectToBothSidesOfRelationshipWithKey(value, _KAAccessListItem.LIST_KEY);
    }
  }
  
  public NSArray<com.kaviju.accesscontrol.model.KAUserProfileRole> userProfileRoles() {
    return (NSArray<com.kaviju.accesscontrol.model.KAUserProfileRole>)storedValueForKey(_KAAccessListItem.USER_PROFILE_ROLES_KEY);
  }

  public NSArray<com.kaviju.accesscontrol.model.KAUserProfileRole> userProfileRoles(EOQualifier qualifier) {
    return userProfileRoles(qualifier, null);
  }

  public NSArray<com.kaviju.accesscontrol.model.KAUserProfileRole> userProfileRoles(EOQualifier qualifier, NSArray<EOSortOrdering> sortOrderings) {
    NSArray<com.kaviju.accesscontrol.model.KAUserProfileRole> results;
      results = userProfileRoles();
      if (qualifier != null) {
        results = (NSArray<com.kaviju.accesscontrol.model.KAUserProfileRole>)EOQualifier.filteredArrayWithQualifier(results, qualifier);
      }
      if (sortOrderings != null) {
        results = (NSArray<com.kaviju.accesscontrol.model.KAUserProfileRole>)EOSortOrdering.sortedArrayUsingKeyOrderArray(results, sortOrderings);
      }
    return results;
  }
  
  public void addToUserProfileRoles(com.kaviju.accesscontrol.model.KAUserProfileRole object) {
    includeObjectIntoPropertyWithKey(object, _KAAccessListItem.USER_PROFILE_ROLES_KEY);
  }

  public void removeFromUserProfileRoles(com.kaviju.accesscontrol.model.KAUserProfileRole object) {
    excludeObjectFromPropertyWithKey(object, _KAAccessListItem.USER_PROFILE_ROLES_KEY);
  }

  public void addToUserProfileRolesRelationship(com.kaviju.accesscontrol.model.KAUserProfileRole object) {
    if (_KAAccessListItem.LOG.isDebugEnabled()) {
      _KAAccessListItem.LOG.debug("adding " + object + " to userProfileRoles relationship");
    }
    if (er.extensions.eof.ERXGenericRecord.InverseRelationshipUpdater.updateInverseRelationships()) {
    	addToUserProfileRoles(object);
    }
    else {
    	addObjectToBothSidesOfRelationshipWithKey(object, _KAAccessListItem.USER_PROFILE_ROLES_KEY);
    }
  }

  public void removeFromUserProfileRolesRelationship(com.kaviju.accesscontrol.model.KAUserProfileRole object) {
    if (_KAAccessListItem.LOG.isDebugEnabled()) {
      _KAAccessListItem.LOG.debug("removing " + object + " from userProfileRoles relationship");
    }
    if (er.extensions.eof.ERXGenericRecord.InverseRelationshipUpdater.updateInverseRelationships()) {
    	removeFromUserProfileRoles(object);
    }
    else {
    	removeObjectFromBothSidesOfRelationshipWithKey(object, _KAAccessListItem.USER_PROFILE_ROLES_KEY);
    }
  }

  public com.kaviju.accesscontrol.model.KAUserProfileRole createUserProfileRolesRelationship() {
    EOClassDescription eoClassDesc = EOClassDescription.classDescriptionForEntityName( com.kaviju.accesscontrol.model.KAUserProfileRole.ENTITY_NAME );
    EOEnterpriseObject eo = eoClassDesc.createInstanceWithEditingContext(editingContext(), null);
    editingContext().insertObject(eo);
    addObjectToBothSidesOfRelationshipWithKey(eo, _KAAccessListItem.USER_PROFILE_ROLES_KEY);
    return (com.kaviju.accesscontrol.model.KAUserProfileRole) eo;
  }

  public void deleteUserProfileRolesRelationship(com.kaviju.accesscontrol.model.KAUserProfileRole object) {
    removeObjectFromBothSidesOfRelationshipWithKey(object, _KAAccessListItem.USER_PROFILE_ROLES_KEY);
    editingContext().deleteObject(object);
  }

  public void deleteAllUserProfileRolesRelationships() {
    Enumeration<com.kaviju.accesscontrol.model.KAUserProfileRole> objects = userProfileRoles().immutableClone().objectEnumerator();
    while (objects.hasMoreElements()) {
      deleteUserProfileRolesRelationship(objects.nextElement());
    }
  }


public static com.kaviju.accesscontrol.model.KAAccessListItem createKAAccessListItem(EOEditingContext editingContext) {
    com.kaviju.accesscontrol.model.KAAccessListItem eo = (com.kaviju.accesscontrol.model.KAAccessListItem) EOUtilities.createAndInsertInstance(editingContext, _KAAccessListItem.ENTITY_NAME);    return eo;
  }

  public static ERXFetchSpecification<com.kaviju.accesscontrol.model.KAAccessListItem> fetchSpec() {
    return new ERXFetchSpecification<com.kaviju.accesscontrol.model.KAAccessListItem>(_KAAccessListItem.ENTITY_NAME, null, null, false, true, null);
  }

  public static NSArray<com.kaviju.accesscontrol.model.KAAccessListItem> fetchAllKAAccessListItems(EOEditingContext editingContext) {
    return _KAAccessListItem.fetchAllKAAccessListItems(editingContext, null);
  }

  public static NSArray<com.kaviju.accesscontrol.model.KAAccessListItem> fetchAllKAAccessListItems(EOEditingContext editingContext, NSArray<EOSortOrdering> sortOrderings) {
    return _KAAccessListItem.fetchKAAccessListItems(editingContext, null, sortOrderings);
  }

  public static NSArray<com.kaviju.accesscontrol.model.KAAccessListItem> fetchKAAccessListItems(EOEditingContext editingContext, EOQualifier qualifier, NSArray<EOSortOrdering> sortOrderings) {
    ERXFetchSpecification<com.kaviju.accesscontrol.model.KAAccessListItem> fetchSpec = new ERXFetchSpecification<com.kaviju.accesscontrol.model.KAAccessListItem>(_KAAccessListItem.ENTITY_NAME, qualifier, sortOrderings);
    fetchSpec.setIsDeep(true);
    NSArray<com.kaviju.accesscontrol.model.KAAccessListItem> eoObjects = fetchSpec.fetchObjects(editingContext);
    return eoObjects;
  }

  public static com.kaviju.accesscontrol.model.KAAccessListItem fetchKAAccessListItem(EOEditingContext editingContext, String keyName, Object value) {
    return _KAAccessListItem.fetchKAAccessListItem(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static com.kaviju.accesscontrol.model.KAAccessListItem fetchKAAccessListItem(EOEditingContext editingContext, EOQualifier qualifier) {
    NSArray<com.kaviju.accesscontrol.model.KAAccessListItem> eoObjects = _KAAccessListItem.fetchKAAccessListItems(editingContext, qualifier, null);
    com.kaviju.accesscontrol.model.KAAccessListItem eoObject;
    int count = eoObjects.count();
    if (count == 0) {
      eoObject = null;
    }
    else if (count == 1) {
      eoObject = eoObjects.objectAtIndex(0);
    }
    else {
      throw new IllegalStateException("There was more than one KAAccessListItem that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static com.kaviju.accesscontrol.model.KAAccessListItem fetchRequiredKAAccessListItem(EOEditingContext editingContext, String keyName, Object value) {
    return _KAAccessListItem.fetchRequiredKAAccessListItem(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static com.kaviju.accesscontrol.model.KAAccessListItem fetchRequiredKAAccessListItem(EOEditingContext editingContext, EOQualifier qualifier) {
    com.kaviju.accesscontrol.model.KAAccessListItem eoObject = _KAAccessListItem.fetchKAAccessListItem(editingContext, qualifier);
    if (eoObject == null) {
      throw new NoSuchElementException("There was no KAAccessListItem that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static com.kaviju.accesscontrol.model.KAAccessListItem localInstanceIn(EOEditingContext editingContext, com.kaviju.accesscontrol.model.KAAccessListItem eo) {
    com.kaviju.accesscontrol.model.KAAccessListItem localInstance = (eo == null) ? null : ERXEOControlUtilities.localInstanceOfObject(editingContext, eo);
    if (localInstance == null && eo != null) {
      throw new IllegalStateException("You attempted to localInstance " + eo + ", which has not yet committed.");
    }
    return localInstance;
  }
}
