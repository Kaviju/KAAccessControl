// DO NOT EDIT.  Make changes to com.kaviju.accesscontrol.model.KAUser.java instead.
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
public abstract class _KAUser extends  ERXGenericRecord {
  public static final String ENTITY_NAME = "KAUser";

  // Attribute Keys
  public static final ERXKey<String> FIRST_NAME = new ERXKey<String>("firstName");
  public static final ERXKey<String> LAST_NAME = new ERXKey<String>("lastName");
  public static final ERXKey<String> PASSWORD_HASH = new ERXKey<String>("passwordHash");
  public static final ERXKey<String> USER_NAME = new ERXKey<String>("userName");
  // Relationship Keys
  public static final ERXKey<com.kaviju.accesscontrol.model.KAUserProfile> PROFILES = new ERXKey<com.kaviju.accesscontrol.model.KAUserProfile>("profiles");

  // Attributes
  public static final String FIRST_NAME_KEY = FIRST_NAME.key();
  public static final String LAST_NAME_KEY = LAST_NAME.key();
  public static final String PASSWORD_HASH_KEY = PASSWORD_HASH.key();
  public static final String USER_NAME_KEY = USER_NAME.key();
  // Relationships
  public static final String PROFILES_KEY = PROFILES.key();

  private static Logger LOG = Logger.getLogger(_KAUser.class);

  public com.kaviju.accesscontrol.model.KAUser localInstanceIn(EOEditingContext editingContext) {
    com.kaviju.accesscontrol.model.KAUser localInstance = (com.kaviju.accesscontrol.model.KAUser)EOUtilities.localInstanceOfObject(editingContext, this);
    if (localInstance == null) {
      throw new IllegalStateException("You attempted to localInstance " + this + ", which has not yet committed.");
    }
    return localInstance;
  }

  public String firstName() {
    return (String) storedValueForKey(_KAUser.FIRST_NAME_KEY);
  }

  public void setFirstName(String value) {
    if (_KAUser.LOG.isDebugEnabled()) {
    	_KAUser.LOG.debug( "updating firstName from " + firstName() + " to " + value);
    }
    takeStoredValueForKey(value, _KAUser.FIRST_NAME_KEY);
  }

  public String lastName() {
    return (String) storedValueForKey(_KAUser.LAST_NAME_KEY);
  }

  public void setLastName(String value) {
    if (_KAUser.LOG.isDebugEnabled()) {
    	_KAUser.LOG.debug( "updating lastName from " + lastName() + " to " + value);
    }
    takeStoredValueForKey(value, _KAUser.LAST_NAME_KEY);
  }

  public String passwordHash() {
    return (String) storedValueForKey(_KAUser.PASSWORD_HASH_KEY);
  }

  public void setPasswordHash(String value) {
    if (_KAUser.LOG.isDebugEnabled()) {
    	_KAUser.LOG.debug( "updating passwordHash from " + passwordHash() + " to " + value);
    }
    takeStoredValueForKey(value, _KAUser.PASSWORD_HASH_KEY);
  }

  public String userName() {
    return (String) storedValueForKey(_KAUser.USER_NAME_KEY);
  }

  public void setUserName(String value) {
    if (_KAUser.LOG.isDebugEnabled()) {
    	_KAUser.LOG.debug( "updating userName from " + userName() + " to " + value);
    }
    takeStoredValueForKey(value, _KAUser.USER_NAME_KEY);
  }

  public NSArray<com.kaviju.accesscontrol.model.KAUserProfile> profiles() {
    return (NSArray<com.kaviju.accesscontrol.model.KAUserProfile>)storedValueForKey(_KAUser.PROFILES_KEY);
  }

  public NSArray<com.kaviju.accesscontrol.model.KAUserProfile> profiles(EOQualifier qualifier) {
    return profiles(qualifier, null, false);
  }

  public NSArray<com.kaviju.accesscontrol.model.KAUserProfile> profiles(EOQualifier qualifier, boolean fetch) {
    return profiles(qualifier, null, fetch);
  }

  public NSArray<com.kaviju.accesscontrol.model.KAUserProfile> profiles(EOQualifier qualifier, NSArray<EOSortOrdering> sortOrderings, boolean fetch) {
    NSArray<com.kaviju.accesscontrol.model.KAUserProfile> results;
    if (fetch) {
      EOQualifier fullQualifier;
      EOQualifier inverseQualifier = new EOKeyValueQualifier(com.kaviju.accesscontrol.model.KAUserProfile.USER_KEY, EOQualifier.QualifierOperatorEqual, this);
    	
      if (qualifier == null) {
        fullQualifier = inverseQualifier;
      }
      else {
        NSMutableArray<EOQualifier> qualifiers = new NSMutableArray<EOQualifier>();
        qualifiers.addObject(qualifier);
        qualifiers.addObject(inverseQualifier);
        fullQualifier = new EOAndQualifier(qualifiers);
      }

      results = com.kaviju.accesscontrol.model.KAUserProfile.fetchKAUserProfiles(editingContext(), fullQualifier, sortOrderings);
    }
    else {
      results = profiles();
      if (qualifier != null) {
        results = (NSArray<com.kaviju.accesscontrol.model.KAUserProfile>)EOQualifier.filteredArrayWithQualifier(results, qualifier);
      }
      if (sortOrderings != null) {
        results = (NSArray<com.kaviju.accesscontrol.model.KAUserProfile>)EOSortOrdering.sortedArrayUsingKeyOrderArray(results, sortOrderings);
      }
    }
    return results;
  }
  
  public void addToProfiles(com.kaviju.accesscontrol.model.KAUserProfile object) {
    includeObjectIntoPropertyWithKey(object, _KAUser.PROFILES_KEY);
  }

  public void removeFromProfiles(com.kaviju.accesscontrol.model.KAUserProfile object) {
    excludeObjectFromPropertyWithKey(object, _KAUser.PROFILES_KEY);
  }

  public void addToProfilesRelationship(com.kaviju.accesscontrol.model.KAUserProfile object) {
    if (_KAUser.LOG.isDebugEnabled()) {
      _KAUser.LOG.debug("adding " + object + " to profiles relationship");
    }
    if (er.extensions.eof.ERXGenericRecord.InverseRelationshipUpdater.updateInverseRelationships()) {
    	addToProfiles(object);
    }
    else {
    	addObjectToBothSidesOfRelationshipWithKey(object, _KAUser.PROFILES_KEY);
    }
  }

  public void removeFromProfilesRelationship(com.kaviju.accesscontrol.model.KAUserProfile object) {
    if (_KAUser.LOG.isDebugEnabled()) {
      _KAUser.LOG.debug("removing " + object + " from profiles relationship");
    }
    if (er.extensions.eof.ERXGenericRecord.InverseRelationshipUpdater.updateInverseRelationships()) {
    	removeFromProfiles(object);
    }
    else {
    	removeObjectFromBothSidesOfRelationshipWithKey(object, _KAUser.PROFILES_KEY);
    }
  }

  public com.kaviju.accesscontrol.model.KAUserProfile createProfilesRelationship() {
    EOClassDescription eoClassDesc = EOClassDescription.classDescriptionForEntityName( com.kaviju.accesscontrol.model.KAUserProfile.ENTITY_NAME );
    EOEnterpriseObject eo = eoClassDesc.createInstanceWithEditingContext(editingContext(), null);
    editingContext().insertObject(eo);
    addObjectToBothSidesOfRelationshipWithKey(eo, _KAUser.PROFILES_KEY);
    return (com.kaviju.accesscontrol.model.KAUserProfile) eo;
  }

  public void deleteProfilesRelationship(com.kaviju.accesscontrol.model.KAUserProfile object) {
    removeObjectFromBothSidesOfRelationshipWithKey(object, _KAUser.PROFILES_KEY);
    editingContext().deleteObject(object);
  }

  public void deleteAllProfilesRelationships() {
    Enumeration<com.kaviju.accesscontrol.model.KAUserProfile> objects = profiles().immutableClone().objectEnumerator();
    while (objects.hasMoreElements()) {
      deleteProfilesRelationship(objects.nextElement());
    }
  }


public static com.kaviju.accesscontrol.model.KAUser createKAUser(EOEditingContext editingContext, String firstName
, String passwordHash
, String userName
) {
  com.kaviju.accesscontrol.model.KAUser eo = (com.kaviju.accesscontrol.model.KAUser) EOUtilities.createAndInsertInstance(editingContext, _KAUser.ENTITY_NAME);  
		eo.setFirstName(firstName);
		eo.setPasswordHash(passwordHash);
		eo.setUserName(userName);
  return eo;
}

public static com.kaviju.accesscontrol.model.KAUser createKAUser(EOEditingContext editingContext) {
    com.kaviju.accesscontrol.model.KAUser eo = (com.kaviju.accesscontrol.model.KAUser) EOUtilities.createAndInsertInstance(editingContext, _KAUser.ENTITY_NAME);    return eo;
  }

  public static ERXFetchSpecification<com.kaviju.accesscontrol.model.KAUser> fetchSpec() {
    return new ERXFetchSpecification<com.kaviju.accesscontrol.model.KAUser>(_KAUser.ENTITY_NAME, null, null, false, true, null);
  }

  public static NSArray<com.kaviju.accesscontrol.model.KAUser> fetchAllKAUsers(EOEditingContext editingContext) {
    return _KAUser.fetchAllKAUsers(editingContext, null);
  }

  public static NSArray<com.kaviju.accesscontrol.model.KAUser> fetchAllKAUsers(EOEditingContext editingContext, NSArray<EOSortOrdering> sortOrderings) {
    return _KAUser.fetchKAUsers(editingContext, null, sortOrderings);
  }

  public static NSArray<com.kaviju.accesscontrol.model.KAUser> fetchKAUsers(EOEditingContext editingContext, EOQualifier qualifier, NSArray<EOSortOrdering> sortOrderings) {
    ERXFetchSpecification<com.kaviju.accesscontrol.model.KAUser> fetchSpec = new ERXFetchSpecification<com.kaviju.accesscontrol.model.KAUser>(_KAUser.ENTITY_NAME, qualifier, sortOrderings);
    fetchSpec.setIsDeep(true);
    NSArray<com.kaviju.accesscontrol.model.KAUser> eoObjects = fetchSpec.fetchObjects(editingContext);
    return eoObjects;
  }

  public static com.kaviju.accesscontrol.model.KAUser fetchKAUser(EOEditingContext editingContext, String keyName, Object value) {
    return _KAUser.fetchKAUser(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static com.kaviju.accesscontrol.model.KAUser fetchKAUser(EOEditingContext editingContext, EOQualifier qualifier) {
    NSArray<com.kaviju.accesscontrol.model.KAUser> eoObjects = _KAUser.fetchKAUsers(editingContext, qualifier, null);
    com.kaviju.accesscontrol.model.KAUser eoObject;
    int count = eoObjects.count();
    if (count == 0) {
      eoObject = null;
    }
    else if (count == 1) {
      eoObject = eoObjects.objectAtIndex(0);
    }
    else {
      throw new IllegalStateException("There was more than one KAUser that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static com.kaviju.accesscontrol.model.KAUser fetchRequiredKAUser(EOEditingContext editingContext, String keyName, Object value) {
    return _KAUser.fetchRequiredKAUser(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static com.kaviju.accesscontrol.model.KAUser fetchRequiredKAUser(EOEditingContext editingContext, EOQualifier qualifier) {
    com.kaviju.accesscontrol.model.KAUser eoObject = _KAUser.fetchKAUser(editingContext, qualifier);
    if (eoObject == null) {
      throw new NoSuchElementException("There was no KAUser that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static com.kaviju.accesscontrol.model.KAUser localInstanceIn(EOEditingContext editingContext, com.kaviju.accesscontrol.model.KAUser eo) {
    com.kaviju.accesscontrol.model.KAUser localInstance = (eo == null) ? null : ERXEOControlUtilities.localInstanceOfObject(editingContext, eo);
    if (localInstance == null && eo != null) {
      throw new IllegalStateException("You attempted to localInstance " + eo + ", which has not yet committed.");
    }
    return localInstance;
  }
}
