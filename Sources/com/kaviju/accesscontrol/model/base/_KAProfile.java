// DO NOT EDIT.  Make changes to com.kaviju.accesscontrol.model.KAProfile.java instead.
package com.kaviju.accesscontrol.model.base;

import com.webobjects.eoaccess.*;
import com.webobjects.eocontrol.*;
import com.webobjects.foundation.*;
import java.math.*;
import java.util.*;
import org.apache.log4j.Logger;

import er.extensions.eof.*;
import er.extensions.foundation.*;

@SuppressWarnings("all")
public abstract class _KAProfile extends  ERXGenericRecord {
  public static final String ENTITY_NAME = "KAProfile";

  // Attribute Keys
  public static final ERXKey<String> CODE = new ERXKey<String>("code");
  // Relationship Keys
  public static final ERXKey<com.kaviju.accesscontrol.model.KARole> ROLES = new ERXKey<com.kaviju.accesscontrol.model.KARole>("roles");
  public static final ERXKey<com.kaviju.accesscontrol.model.KAUser> USERS = new ERXKey<com.kaviju.accesscontrol.model.KAUser>("users");

  // Attributes
  public static final String CODE_KEY = "code";
  // Relationships
  public static final String ROLES_KEY = "roles";
  public static final String USERS_KEY = "users";

  private static Logger LOG = Logger.getLogger(_KAProfile.class);

  public com.kaviju.accesscontrol.model.KAProfile localInstanceIn(EOEditingContext editingContext) {
    com.kaviju.accesscontrol.model.KAProfile localInstance = (com.kaviju.accesscontrol.model.KAProfile)EOUtilities.localInstanceOfObject(editingContext, this);
    if (localInstance == null) {
      throw new IllegalStateException("You attempted to localInstance " + this + ", which has not yet committed.");
    }
    return localInstance;
  }

  public String code() {
    return (String) storedValueForKey(_KAProfile.CODE_KEY);
  }

  public void setCode(String value) {
    if (_KAProfile.LOG.isDebugEnabled()) {
    	_KAProfile.LOG.debug( "updating code from " + code() + " to " + value);
    }
    takeStoredValueForKey(value, _KAProfile.CODE_KEY);
  }

  public NSArray<com.kaviju.accesscontrol.model.KARole> roles() {
    return (NSArray<com.kaviju.accesscontrol.model.KARole>)storedValueForKey(_KAProfile.ROLES_KEY);
  }

  public NSArray<com.kaviju.accesscontrol.model.KARole> roles(EOQualifier qualifier) {
    return roles(qualifier, null);
  }

  public NSArray<com.kaviju.accesscontrol.model.KARole> roles(EOQualifier qualifier, NSArray<EOSortOrdering> sortOrderings) {
    NSArray<com.kaviju.accesscontrol.model.KARole> results;
      results = roles();
      if (qualifier != null) {
        results = (NSArray<com.kaviju.accesscontrol.model.KARole>)EOQualifier.filteredArrayWithQualifier(results, qualifier);
      }
      if (sortOrderings != null) {
        results = (NSArray<com.kaviju.accesscontrol.model.KARole>)EOSortOrdering.sortedArrayUsingKeyOrderArray(results, sortOrderings);
      }
    return results;
  }
  
  public void addToRoles(com.kaviju.accesscontrol.model.KARole object) {
    includeObjectIntoPropertyWithKey(object, _KAProfile.ROLES_KEY);
  }

  public void removeFromRoles(com.kaviju.accesscontrol.model.KARole object) {
    excludeObjectFromPropertyWithKey(object, _KAProfile.ROLES_KEY);
  }

  public void addToRolesRelationship(com.kaviju.accesscontrol.model.KARole object) {
    if (_KAProfile.LOG.isDebugEnabled()) {
      _KAProfile.LOG.debug("adding " + object + " to roles relationship");
    }
    if (er.extensions.eof.ERXGenericRecord.InverseRelationshipUpdater.updateInverseRelationships()) {
    	addToRoles(object);
    }
    else {
    	addObjectToBothSidesOfRelationshipWithKey(object, _KAProfile.ROLES_KEY);
    }
  }

  public void removeFromRolesRelationship(com.kaviju.accesscontrol.model.KARole object) {
    if (_KAProfile.LOG.isDebugEnabled()) {
      _KAProfile.LOG.debug("removing " + object + " from roles relationship");
    }
    if (er.extensions.eof.ERXGenericRecord.InverseRelationshipUpdater.updateInverseRelationships()) {
    	removeFromRoles(object);
    }
    else {
    	removeObjectFromBothSidesOfRelationshipWithKey(object, _KAProfile.ROLES_KEY);
    }
  }

  public com.kaviju.accesscontrol.model.KARole createRolesRelationship() {
    EOClassDescription eoClassDesc = EOClassDescription.classDescriptionForEntityName( com.kaviju.accesscontrol.model.KARole.ENTITY_NAME );
    EOEnterpriseObject eo = eoClassDesc.createInstanceWithEditingContext(editingContext(), null);
    editingContext().insertObject(eo);
    addObjectToBothSidesOfRelationshipWithKey(eo, _KAProfile.ROLES_KEY);
    return (com.kaviju.accesscontrol.model.KARole) eo;
  }

  public void deleteRolesRelationship(com.kaviju.accesscontrol.model.KARole object) {
    removeObjectFromBothSidesOfRelationshipWithKey(object, _KAProfile.ROLES_KEY);
    editingContext().deleteObject(object);
  }

  public void deleteAllRolesRelationships() {
    Enumeration<com.kaviju.accesscontrol.model.KARole> objects = roles().immutableClone().objectEnumerator();
    while (objects.hasMoreElements()) {
      deleteRolesRelationship(objects.nextElement());
    }
  }

  public NSArray<com.kaviju.accesscontrol.model.KAUser> users() {
    return (NSArray<com.kaviju.accesscontrol.model.KAUser>)storedValueForKey(_KAProfile.USERS_KEY);
  }

  public NSArray<com.kaviju.accesscontrol.model.KAUser> users(EOQualifier qualifier) {
    return users(qualifier, null);
  }

  public NSArray<com.kaviju.accesscontrol.model.KAUser> users(EOQualifier qualifier, NSArray<EOSortOrdering> sortOrderings) {
    NSArray<com.kaviju.accesscontrol.model.KAUser> results;
      results = users();
      if (qualifier != null) {
        results = (NSArray<com.kaviju.accesscontrol.model.KAUser>)EOQualifier.filteredArrayWithQualifier(results, qualifier);
      }
      if (sortOrderings != null) {
        results = (NSArray<com.kaviju.accesscontrol.model.KAUser>)EOSortOrdering.sortedArrayUsingKeyOrderArray(results, sortOrderings);
      }
    return results;
  }
  
  public void addToUsers(com.kaviju.accesscontrol.model.KAUser object) {
    includeObjectIntoPropertyWithKey(object, _KAProfile.USERS_KEY);
  }

  public void removeFromUsers(com.kaviju.accesscontrol.model.KAUser object) {
    excludeObjectFromPropertyWithKey(object, _KAProfile.USERS_KEY);
  }

  public void addToUsersRelationship(com.kaviju.accesscontrol.model.KAUser object) {
    if (_KAProfile.LOG.isDebugEnabled()) {
      _KAProfile.LOG.debug("adding " + object + " to users relationship");
    }
    if (er.extensions.eof.ERXGenericRecord.InverseRelationshipUpdater.updateInverseRelationships()) {
    	addToUsers(object);
    }
    else {
    	addObjectToBothSidesOfRelationshipWithKey(object, _KAProfile.USERS_KEY);
    }
  }

  public void removeFromUsersRelationship(com.kaviju.accesscontrol.model.KAUser object) {
    if (_KAProfile.LOG.isDebugEnabled()) {
      _KAProfile.LOG.debug("removing " + object + " from users relationship");
    }
    if (er.extensions.eof.ERXGenericRecord.InverseRelationshipUpdater.updateInverseRelationships()) {
    	removeFromUsers(object);
    }
    else {
    	removeObjectFromBothSidesOfRelationshipWithKey(object, _KAProfile.USERS_KEY);
    }
  }

  public com.kaviju.accesscontrol.model.KAUser createUsersRelationship() {
    EOClassDescription eoClassDesc = EOClassDescription.classDescriptionForEntityName( com.kaviju.accesscontrol.model.KAUser.ENTITY_NAME );
    EOEnterpriseObject eo = eoClassDesc.createInstanceWithEditingContext(editingContext(), null);
    editingContext().insertObject(eo);
    addObjectToBothSidesOfRelationshipWithKey(eo, _KAProfile.USERS_KEY);
    return (com.kaviju.accesscontrol.model.KAUser) eo;
  }

  public void deleteUsersRelationship(com.kaviju.accesscontrol.model.KAUser object) {
    removeObjectFromBothSidesOfRelationshipWithKey(object, _KAProfile.USERS_KEY);
    editingContext().deleteObject(object);
  }

  public void deleteAllUsersRelationships() {
    Enumeration<com.kaviju.accesscontrol.model.KAUser> objects = users().immutableClone().objectEnumerator();
    while (objects.hasMoreElements()) {
      deleteUsersRelationship(objects.nextElement());
    }
  }


public static com.kaviju.accesscontrol.model.KAProfile createKAProfile(EOEditingContext editingContext) {
    com.kaviju.accesscontrol.model.KAProfile eo = (com.kaviju.accesscontrol.model.KAProfile) EOUtilities.createAndInsertInstance(editingContext, _KAProfile.ENTITY_NAME);    return eo;
  }

  public static ERXFetchSpecification<com.kaviju.accesscontrol.model.KAProfile> fetchSpec() {
    return new ERXFetchSpecification<com.kaviju.accesscontrol.model.KAProfile>(_KAProfile.ENTITY_NAME, null, null, false, true, null);
  }

  public static NSArray<com.kaviju.accesscontrol.model.KAProfile> fetchAllKAProfiles(EOEditingContext editingContext) {
    return _KAProfile.fetchAllKAProfiles(editingContext, null);
  }

  public static NSArray<com.kaviju.accesscontrol.model.KAProfile> fetchAllKAProfiles(EOEditingContext editingContext, NSArray<EOSortOrdering> sortOrderings) {
    return _KAProfile.fetchKAProfiles(editingContext, null, sortOrderings);
  }

  public static NSArray<com.kaviju.accesscontrol.model.KAProfile> fetchKAProfiles(EOEditingContext editingContext, EOQualifier qualifier, NSArray<EOSortOrdering> sortOrderings) {
    ERXFetchSpecification<com.kaviju.accesscontrol.model.KAProfile> fetchSpec = new ERXFetchSpecification<com.kaviju.accesscontrol.model.KAProfile>(_KAProfile.ENTITY_NAME, qualifier, sortOrderings);
    fetchSpec.setIsDeep(true);
    NSArray<com.kaviju.accesscontrol.model.KAProfile> eoObjects = fetchSpec.fetchObjects(editingContext);
    return eoObjects;
  }

  public static com.kaviju.accesscontrol.model.KAProfile fetchKAProfile(EOEditingContext editingContext, String keyName, Object value) {
    return _KAProfile.fetchKAProfile(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static com.kaviju.accesscontrol.model.KAProfile fetchKAProfile(EOEditingContext editingContext, EOQualifier qualifier) {
    NSArray<com.kaviju.accesscontrol.model.KAProfile> eoObjects = _KAProfile.fetchKAProfiles(editingContext, qualifier, null);
    com.kaviju.accesscontrol.model.KAProfile eoObject;
    int count = eoObjects.count();
    if (count == 0) {
      eoObject = null;
    }
    else if (count == 1) {
      eoObject = eoObjects.objectAtIndex(0);
    }
    else {
      throw new IllegalStateException("There was more than one KAProfile that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static com.kaviju.accesscontrol.model.KAProfile fetchRequiredKAProfile(EOEditingContext editingContext, String keyName, Object value) {
    return _KAProfile.fetchRequiredKAProfile(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static com.kaviju.accesscontrol.model.KAProfile fetchRequiredKAProfile(EOEditingContext editingContext, EOQualifier qualifier) {
    com.kaviju.accesscontrol.model.KAProfile eoObject = _KAProfile.fetchKAProfile(editingContext, qualifier);
    if (eoObject == null) {
      throw new NoSuchElementException("There was no KAProfile that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static com.kaviju.accesscontrol.model.KAProfile localInstanceIn(EOEditingContext editingContext, com.kaviju.accesscontrol.model.KAProfile eo) {
    com.kaviju.accesscontrol.model.KAProfile localInstance = (eo == null) ? null : ERXEOControlUtilities.localInstanceOfObject(editingContext, eo);
    if (localInstance == null && eo != null) {
      throw new IllegalStateException("You attempted to localInstance " + eo + ", which has not yet committed.");
    }
    return localInstance;
  }
}
