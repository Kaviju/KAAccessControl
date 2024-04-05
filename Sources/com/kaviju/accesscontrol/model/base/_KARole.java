// DO NOT EDIT.  Make changes to com.kaviju.accesscontrol.model.KARole.java instead.
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
public abstract class _KARole extends  ERXGenericRecord {
  public static final String ENTITY_NAME = "KARole";

  // Attribute Keys
  public static final ERXKey<Boolean> ALLOWS_MULTIPLE_ITEMS = new ERXKey<Boolean>("allowsMultipleItems");
  public static final ERXKey<String> CODE = new ERXKey<String>("code");
  public static final ERXKey<Integer> DISPLAY_ORDER = new ERXKey<Integer>("displayOrder");
  // Relationship Keys
  public static final ERXKey<com.kaviju.accesscontrol.model.KARoleGroup> GROUP = new ERXKey<com.kaviju.accesscontrol.model.KARoleGroup>("group");
  public static final ERXKey<com.kaviju.accesscontrol.model.KAAccessList> LIST = new ERXKey<com.kaviju.accesscontrol.model.KAAccessList>("list");
  public static final ERXKey<com.kaviju.accesscontrol.model.KAProfileRole> PROFILE_ROLES = new ERXKey<com.kaviju.accesscontrol.model.KAProfileRole>("profileRoles");
  public static final ERXKey<com.kaviju.accesscontrol.model.KAUserProfileRole> USER_PROFILE_ROLES = new ERXKey<com.kaviju.accesscontrol.model.KAUserProfileRole>("userProfileRoles");

  // Attributes
  public static final String ALLOWS_MULTIPLE_ITEMS_KEY = "allowsMultipleItems";
  public static final String CODE_KEY = "code";
  public static final String DISPLAY_ORDER_KEY = "displayOrder";
  // Relationships
  public static final String GROUP_KEY = "group";
  public static final String LIST_KEY = "list";
  public static final String PROFILE_ROLES_KEY = "profileRoles";
  public static final String USER_PROFILE_ROLES_KEY = "userProfileRoles";

  private static Logger LOG = LoggerFactory.getLogger(_KARole.class);

  public com.kaviju.accesscontrol.model.KARole localInstanceIn(EOEditingContext editingContext) {
    com.kaviju.accesscontrol.model.KARole localInstance = (com.kaviju.accesscontrol.model.KARole)EOUtilities.localInstanceOfObject(editingContext, this);
    if (localInstance == null) {
      throw new IllegalStateException("You attempted to localInstance " + this + ", which has not yet committed.");
    }
    return localInstance;
  }

  public Boolean allowsMultipleItems() {
    return (Boolean) storedValueForKey(_KARole.ALLOWS_MULTIPLE_ITEMS_KEY);
  }

  public void setAllowsMultipleItems(Boolean value) {
    if (_KARole.LOG.isDebugEnabled()) {
    	_KARole.LOG.debug( "updating allowsMultipleItems from " + allowsMultipleItems() + " to " + value);
    }
    takeStoredValueForKey(value, _KARole.ALLOWS_MULTIPLE_ITEMS_KEY);
  }

  public String code() {
    return (String) storedValueForKey(_KARole.CODE_KEY);
  }

  public void setCode(String value) {
    if (_KARole.LOG.isDebugEnabled()) {
    	_KARole.LOG.debug( "updating code from " + code() + " to " + value);
    }
    takeStoredValueForKey(value, _KARole.CODE_KEY);
  }

  public Integer displayOrder() {
    return (Integer) storedValueForKey(_KARole.DISPLAY_ORDER_KEY);
  }

  public void setDisplayOrder(Integer value) {
    if (_KARole.LOG.isDebugEnabled()) {
    	_KARole.LOG.debug( "updating displayOrder from " + displayOrder() + " to " + value);
    }
    takeStoredValueForKey(value, _KARole.DISPLAY_ORDER_KEY);
  }

  public com.kaviju.accesscontrol.model.KARoleGroup group() {
    return (com.kaviju.accesscontrol.model.KARoleGroup)storedValueForKey(_KARole.GROUP_KEY);
  }
  
  public void setGroup(com.kaviju.accesscontrol.model.KARoleGroup value) {
    takeStoredValueForKey(value, _KARole.GROUP_KEY);
  }

  public void setGroupRelationship(com.kaviju.accesscontrol.model.KARoleGroup value) {
    if (_KARole.LOG.isDebugEnabled()) {
      _KARole.LOG.debug("updating group from " + group() + " to " + value);
    }
    if (er.extensions.eof.ERXGenericRecord.InverseRelationshipUpdater.updateInverseRelationships()) {
    	setGroup(value);
    }
    else if (value == null) {
    	com.kaviju.accesscontrol.model.KARoleGroup oldValue = group();
    	if (oldValue != null) {
    		removeObjectFromBothSidesOfRelationshipWithKey(oldValue, _KARole.GROUP_KEY);
      }
    } else {
    	addObjectToBothSidesOfRelationshipWithKey(value, _KARole.GROUP_KEY);
    }
  }
  
  public com.kaviju.accesscontrol.model.KAAccessList list() {
    return (com.kaviju.accesscontrol.model.KAAccessList)storedValueForKey(_KARole.LIST_KEY);
  }
  
  public void setList(com.kaviju.accesscontrol.model.KAAccessList value) {
    takeStoredValueForKey(value, _KARole.LIST_KEY);
  }

  public void setListRelationship(com.kaviju.accesscontrol.model.KAAccessList value) {
    if (_KARole.LOG.isDebugEnabled()) {
      _KARole.LOG.debug("updating list from " + list() + " to " + value);
    }
    if (er.extensions.eof.ERXGenericRecord.InverseRelationshipUpdater.updateInverseRelationships()) {
    	setList(value);
    }
    else if (value == null) {
    	com.kaviju.accesscontrol.model.KAAccessList oldValue = list();
    	if (oldValue != null) {
    		removeObjectFromBothSidesOfRelationshipWithKey(oldValue, _KARole.LIST_KEY);
      }
    } else {
    	addObjectToBothSidesOfRelationshipWithKey(value, _KARole.LIST_KEY);
    }
  }
  
  public NSArray<com.kaviju.accesscontrol.model.KAProfileRole> profileRoles() {
    return (NSArray<com.kaviju.accesscontrol.model.KAProfileRole>)storedValueForKey(_KARole.PROFILE_ROLES_KEY);
  }

  public NSArray<com.kaviju.accesscontrol.model.KAProfileRole> profileRoles(EOQualifier qualifier) {
    return profileRoles(qualifier, null, false);
  }

  public NSArray<com.kaviju.accesscontrol.model.KAProfileRole> profileRoles(EOQualifier qualifier, boolean fetch) {
    return profileRoles(qualifier, null, fetch);
  }

  public NSArray<com.kaviju.accesscontrol.model.KAProfileRole> profileRoles(EOQualifier qualifier, NSArray<EOSortOrdering> sortOrderings, boolean fetch) {
    NSArray<com.kaviju.accesscontrol.model.KAProfileRole> results;
    if (fetch) {
      EOQualifier fullQualifier;
      EOQualifier inverseQualifier = new EOKeyValueQualifier(com.kaviju.accesscontrol.model.KAProfileRole.ROLE_KEY, EOQualifier.QualifierOperatorEqual, this);
    	
      if (qualifier == null) {
        fullQualifier = inverseQualifier;
      }
      else {
        NSMutableArray<EOQualifier> qualifiers = new NSMutableArray<EOQualifier>();
        qualifiers.addObject(qualifier);
        qualifiers.addObject(inverseQualifier);
        fullQualifier = new EOAndQualifier(qualifiers);
      }

      results = com.kaviju.accesscontrol.model.KAProfileRole.fetchKAProfileRoles(editingContext(), fullQualifier, sortOrderings);
    }
    else {
      results = profileRoles();
      if (qualifier != null) {
        results = (NSArray<com.kaviju.accesscontrol.model.KAProfileRole>)EOQualifier.filteredArrayWithQualifier(results, qualifier);
      }
      if (sortOrderings != null) {
        results = (NSArray<com.kaviju.accesscontrol.model.KAProfileRole>)EOSortOrdering.sortedArrayUsingKeyOrderArray(results, sortOrderings);
      }
    }
    return results;
  }
  
  public void addToProfileRoles(com.kaviju.accesscontrol.model.KAProfileRole object) {
    includeObjectIntoPropertyWithKey(object, _KARole.PROFILE_ROLES_KEY);
  }

  public void removeFromProfileRoles(com.kaviju.accesscontrol.model.KAProfileRole object) {
    excludeObjectFromPropertyWithKey(object, _KARole.PROFILE_ROLES_KEY);
  }

  public void addToProfileRolesRelationship(com.kaviju.accesscontrol.model.KAProfileRole object) {
    if (_KARole.LOG.isDebugEnabled()) {
      _KARole.LOG.debug("adding " + object + " to profileRoles relationship");
    }
    if (er.extensions.eof.ERXGenericRecord.InverseRelationshipUpdater.updateInverseRelationships()) {
    	addToProfileRoles(object);
    }
    else {
    	addObjectToBothSidesOfRelationshipWithKey(object, _KARole.PROFILE_ROLES_KEY);
    }
  }

  public void removeFromProfileRolesRelationship(com.kaviju.accesscontrol.model.KAProfileRole object) {
    if (_KARole.LOG.isDebugEnabled()) {
      _KARole.LOG.debug("removing " + object + " from profileRoles relationship");
    }
    if (er.extensions.eof.ERXGenericRecord.InverseRelationshipUpdater.updateInverseRelationships()) {
    	removeFromProfileRoles(object);
    }
    else {
    	removeObjectFromBothSidesOfRelationshipWithKey(object, _KARole.PROFILE_ROLES_KEY);
    }
  }

  public com.kaviju.accesscontrol.model.KAProfileRole createProfileRolesRelationship() {
    EOClassDescription eoClassDesc = EOClassDescription.classDescriptionForEntityName( com.kaviju.accesscontrol.model.KAProfileRole.ENTITY_NAME );
    EOEnterpriseObject eo = eoClassDesc.createInstanceWithEditingContext(editingContext(), null);
    editingContext().insertObject(eo);
    addObjectToBothSidesOfRelationshipWithKey(eo, _KARole.PROFILE_ROLES_KEY);
    return (com.kaviju.accesscontrol.model.KAProfileRole) eo;
  }

  public void deleteProfileRolesRelationship(com.kaviju.accesscontrol.model.KAProfileRole object) {
    removeObjectFromBothSidesOfRelationshipWithKey(object, _KARole.PROFILE_ROLES_KEY);
    editingContext().deleteObject(object);
  }

  public void deleteAllProfileRolesRelationships() {
    Enumeration<com.kaviju.accesscontrol.model.KAProfileRole> objects = profileRoles().immutableClone().objectEnumerator();
    while (objects.hasMoreElements()) {
      deleteProfileRolesRelationship(objects.nextElement());
    }
  }

  public NSArray<com.kaviju.accesscontrol.model.KAUserProfileRole> userProfileRoles() {
    return (NSArray<com.kaviju.accesscontrol.model.KAUserProfileRole>)storedValueForKey(_KARole.USER_PROFILE_ROLES_KEY);
  }

  public NSArray<com.kaviju.accesscontrol.model.KAUserProfileRole> userProfileRoles(EOQualifier qualifier) {
    return userProfileRoles(qualifier, null, false);
  }

  public NSArray<com.kaviju.accesscontrol.model.KAUserProfileRole> userProfileRoles(EOQualifier qualifier, boolean fetch) {
    return userProfileRoles(qualifier, null, fetch);
  }

  public NSArray<com.kaviju.accesscontrol.model.KAUserProfileRole> userProfileRoles(EOQualifier qualifier, NSArray<EOSortOrdering> sortOrderings, boolean fetch) {
    NSArray<com.kaviju.accesscontrol.model.KAUserProfileRole> results;
    if (fetch) {
      EOQualifier fullQualifier;
      EOQualifier inverseQualifier = new EOKeyValueQualifier(com.kaviju.accesscontrol.model.KAUserProfileRole.ROLE_KEY, EOQualifier.QualifierOperatorEqual, this);
    	
      if (qualifier == null) {
        fullQualifier = inverseQualifier;
      }
      else {
        NSMutableArray<EOQualifier> qualifiers = new NSMutableArray<EOQualifier>();
        qualifiers.addObject(qualifier);
        qualifiers.addObject(inverseQualifier);
        fullQualifier = new EOAndQualifier(qualifiers);
      }

      results = com.kaviju.accesscontrol.model.KAUserProfileRole.fetchKAUserProfileRoles(editingContext(), fullQualifier, sortOrderings);
    }
    else {
      results = userProfileRoles();
      if (qualifier != null) {
        results = (NSArray<com.kaviju.accesscontrol.model.KAUserProfileRole>)EOQualifier.filteredArrayWithQualifier(results, qualifier);
      }
      if (sortOrderings != null) {
        results = (NSArray<com.kaviju.accesscontrol.model.KAUserProfileRole>)EOSortOrdering.sortedArrayUsingKeyOrderArray(results, sortOrderings);
      }
    }
    return results;
  }
  
  public void addToUserProfileRoles(com.kaviju.accesscontrol.model.KAUserProfileRole object) {
    includeObjectIntoPropertyWithKey(object, _KARole.USER_PROFILE_ROLES_KEY);
  }

  public void removeFromUserProfileRoles(com.kaviju.accesscontrol.model.KAUserProfileRole object) {
    excludeObjectFromPropertyWithKey(object, _KARole.USER_PROFILE_ROLES_KEY);
  }

  public void addToUserProfileRolesRelationship(com.kaviju.accesscontrol.model.KAUserProfileRole object) {
    if (_KARole.LOG.isDebugEnabled()) {
      _KARole.LOG.debug("adding " + object + " to userProfileRoles relationship");
    }
    if (er.extensions.eof.ERXGenericRecord.InverseRelationshipUpdater.updateInverseRelationships()) {
    	addToUserProfileRoles(object);
    }
    else {
    	addObjectToBothSidesOfRelationshipWithKey(object, _KARole.USER_PROFILE_ROLES_KEY);
    }
  }

  public void removeFromUserProfileRolesRelationship(com.kaviju.accesscontrol.model.KAUserProfileRole object) {
    if (_KARole.LOG.isDebugEnabled()) {
      _KARole.LOG.debug("removing " + object + " from userProfileRoles relationship");
    }
    if (er.extensions.eof.ERXGenericRecord.InverseRelationshipUpdater.updateInverseRelationships()) {
    	removeFromUserProfileRoles(object);
    }
    else {
    	removeObjectFromBothSidesOfRelationshipWithKey(object, _KARole.USER_PROFILE_ROLES_KEY);
    }
  }

  public com.kaviju.accesscontrol.model.KAUserProfileRole createUserProfileRolesRelationship() {
    EOClassDescription eoClassDesc = EOClassDescription.classDescriptionForEntityName( com.kaviju.accesscontrol.model.KAUserProfileRole.ENTITY_NAME );
    EOEnterpriseObject eo = eoClassDesc.createInstanceWithEditingContext(editingContext(), null);
    editingContext().insertObject(eo);
    addObjectToBothSidesOfRelationshipWithKey(eo, _KARole.USER_PROFILE_ROLES_KEY);
    return (com.kaviju.accesscontrol.model.KAUserProfileRole) eo;
  }

  public void deleteUserProfileRolesRelationship(com.kaviju.accesscontrol.model.KAUserProfileRole object) {
    removeObjectFromBothSidesOfRelationshipWithKey(object, _KARole.USER_PROFILE_ROLES_KEY);
    editingContext().deleteObject(object);
  }

  public void deleteAllUserProfileRolesRelationships() {
    Enumeration<com.kaviju.accesscontrol.model.KAUserProfileRole> objects = userProfileRoles().immutableClone().objectEnumerator();
    while (objects.hasMoreElements()) {
      deleteUserProfileRolesRelationship(objects.nextElement());
    }
  }


public static com.kaviju.accesscontrol.model.KARole createKARole(EOEditingContext editingContext) {
    com.kaviju.accesscontrol.model.KARole eo = (com.kaviju.accesscontrol.model.KARole) EOUtilities.createAndInsertInstance(editingContext, _KARole.ENTITY_NAME);    return eo;
  }

  public static ERXFetchSpecification<com.kaviju.accesscontrol.model.KARole> fetchSpec() {
    return new ERXFetchSpecification<com.kaviju.accesscontrol.model.KARole>(_KARole.ENTITY_NAME, null, null, false, true, null);
  }

  public static NSArray<com.kaviju.accesscontrol.model.KARole> fetchAllKARoles(EOEditingContext editingContext) {
    return _KARole.fetchAllKARoles(editingContext, null);
  }

  public static NSArray<com.kaviju.accesscontrol.model.KARole> fetchAllKARoles(EOEditingContext editingContext, NSArray<EOSortOrdering> sortOrderings) {
    return _KARole.fetchKARoles(editingContext, null, sortOrderings);
  }

  public static NSArray<com.kaviju.accesscontrol.model.KARole> fetchKARoles(EOEditingContext editingContext, EOQualifier qualifier, NSArray<EOSortOrdering> sortOrderings) {
    ERXFetchSpecification<com.kaviju.accesscontrol.model.KARole> fetchSpec = new ERXFetchSpecification<com.kaviju.accesscontrol.model.KARole>(_KARole.ENTITY_NAME, qualifier, sortOrderings);
    fetchSpec.setIsDeep(true);
    NSArray<com.kaviju.accesscontrol.model.KARole> eoObjects = fetchSpec.fetchObjects(editingContext);
    return eoObjects;
  }

  public static com.kaviju.accesscontrol.model.KARole fetchKARole(EOEditingContext editingContext, String keyName, Object value) {
    return _KARole.fetchKARole(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static com.kaviju.accesscontrol.model.KARole fetchKARole(EOEditingContext editingContext, EOQualifier qualifier) {
    NSArray<com.kaviju.accesscontrol.model.KARole> eoObjects = _KARole.fetchKARoles(editingContext, qualifier, null);
    com.kaviju.accesscontrol.model.KARole eoObject;
    int count = eoObjects.count();
    if (count == 0) {
      eoObject = null;
    }
    else if (count == 1) {
      eoObject = eoObjects.objectAtIndex(0);
    }
    else {
      throw new IllegalStateException("There was more than one KARole that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static com.kaviju.accesscontrol.model.KARole fetchRequiredKARole(EOEditingContext editingContext, String keyName, Object value) {
    return _KARole.fetchRequiredKARole(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static com.kaviju.accesscontrol.model.KARole fetchRequiredKARole(EOEditingContext editingContext, EOQualifier qualifier) {
    com.kaviju.accesscontrol.model.KARole eoObject = _KARole.fetchKARole(editingContext, qualifier);
    if (eoObject == null) {
      throw new NoSuchElementException("There was no KARole that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static com.kaviju.accesscontrol.model.KARole localInstanceIn(EOEditingContext editingContext, com.kaviju.accesscontrol.model.KARole eo) {
    com.kaviju.accesscontrol.model.KARole localInstance = (eo == null) ? null : ERXEOControlUtilities.localInstanceOfObject(editingContext, eo);
    if (localInstance == null && eo != null) {
      throw new IllegalStateException("You attempted to localInstance " + eo + ", which has not yet committed.");
    }
    return localInstance;
  }
}
