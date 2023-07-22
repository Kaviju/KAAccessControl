// DO NOT EDIT.  Make changes to com.kaviju.accesscontrol.model.KAUserProfile.java instead.
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
public abstract class _KAUserProfile extends  ERXGenericRecord {
  public static final String ENTITY_NAME = "KAUserProfile";

  // Attribute Keys
  public static final ERXKey<Boolean> IS_DEFAULT_PROFILE = new ERXKey<Boolean>("isDefaultProfile");
  public static final ERXKey<Integer> TYPE = new ERXKey<Integer>("type");
  // Relationship Keys
  public static final ERXKey<com.kaviju.accesscontrol.model.KAProfile> PROFILE = new ERXKey<com.kaviju.accesscontrol.model.KAProfile>("profile");
  public static final ERXKey<com.kaviju.accesscontrol.model.KAUserProfileRole> ROLES = new ERXKey<com.kaviju.accesscontrol.model.KAUserProfileRole>("roles");
  public static final ERXKey<com.kaviju.accesscontrol.model.KAUser> USER = new ERXKey<com.kaviju.accesscontrol.model.KAUser>("user");

  // Attributes
  public static final String IS_DEFAULT_PROFILE_KEY = "isDefaultProfile";
  public static final String TYPE_KEY = "type";
  // Relationships
  public static final String PROFILE_KEY = "profile";
  public static final String ROLES_KEY = "roles";
  public static final String USER_KEY = "user";

  private static Logger LOG = Logger.getLogger(_KAUserProfile.class);

  public com.kaviju.accesscontrol.model.KAUserProfile localInstanceIn(EOEditingContext editingContext) {
    com.kaviju.accesscontrol.model.KAUserProfile localInstance = (com.kaviju.accesscontrol.model.KAUserProfile)EOUtilities.localInstanceOfObject(editingContext, this);
    if (localInstance == null) {
      throw new IllegalStateException("You attempted to localInstance " + this + ", which has not yet committed.");
    }
    return localInstance;
  }

  public Boolean isDefaultProfile() {
    return (Boolean) storedValueForKey(_KAUserProfile.IS_DEFAULT_PROFILE_KEY);
  }

  public void setIsDefaultProfile(Boolean value) {
    if (_KAUserProfile.LOG.isDebugEnabled()) {
    	_KAUserProfile.LOG.debug( "updating isDefaultProfile from " + isDefaultProfile() + " to " + value);
    }
    takeStoredValueForKey(value, _KAUserProfile.IS_DEFAULT_PROFILE_KEY);
  }

  public Integer type() {
    return (Integer) storedValueForKey(_KAUserProfile.TYPE_KEY);
  }

  public void setType(Integer value) {
    if (_KAUserProfile.LOG.isDebugEnabled()) {
    	_KAUserProfile.LOG.debug( "updating type from " + type() + " to " + value);
    }
    takeStoredValueForKey(value, _KAUserProfile.TYPE_KEY);
  }

  public com.kaviju.accesscontrol.model.KAProfile profile() {
    return (com.kaviju.accesscontrol.model.KAProfile)storedValueForKey(_KAUserProfile.PROFILE_KEY);
  }
  
  public void setProfile(com.kaviju.accesscontrol.model.KAProfile value) {
    takeStoredValueForKey(value, _KAUserProfile.PROFILE_KEY);
  }

  public void setProfileRelationship(com.kaviju.accesscontrol.model.KAProfile value) {
    if (_KAUserProfile.LOG.isDebugEnabled()) {
      _KAUserProfile.LOG.debug("updating profile from " + profile() + " to " + value);
    }
    if (er.extensions.eof.ERXGenericRecord.InverseRelationshipUpdater.updateInverseRelationships()) {
    	setProfile(value);
    }
    else if (value == null) {
    	com.kaviju.accesscontrol.model.KAProfile oldValue = profile();
    	if (oldValue != null) {
    		removeObjectFromBothSidesOfRelationshipWithKey(oldValue, _KAUserProfile.PROFILE_KEY);
      }
    } else {
    	addObjectToBothSidesOfRelationshipWithKey(value, _KAUserProfile.PROFILE_KEY);
    }
  }
  
  public com.kaviju.accesscontrol.model.KAUser user() {
    return (com.kaviju.accesscontrol.model.KAUser)storedValueForKey(_KAUserProfile.USER_KEY);
  }
  
  public void setUser(com.kaviju.accesscontrol.model.KAUser value) {
    takeStoredValueForKey(value, _KAUserProfile.USER_KEY);
  }

  public void setUserRelationship(com.kaviju.accesscontrol.model.KAUser value) {
    if (_KAUserProfile.LOG.isDebugEnabled()) {
      _KAUserProfile.LOG.debug("updating user from " + user() + " to " + value);
    }
    if (er.extensions.eof.ERXGenericRecord.InverseRelationshipUpdater.updateInverseRelationships()) {
    	setUser(value);
    }
    else if (value == null) {
    	com.kaviju.accesscontrol.model.KAUser oldValue = user();
    	if (oldValue != null) {
    		removeObjectFromBothSidesOfRelationshipWithKey(oldValue, _KAUserProfile.USER_KEY);
      }
    } else {
    	addObjectToBothSidesOfRelationshipWithKey(value, _KAUserProfile.USER_KEY);
    }
  }
  
  public NSArray<com.kaviju.accesscontrol.model.KAUserProfileRole> roles() {
    return (NSArray<com.kaviju.accesscontrol.model.KAUserProfileRole>)storedValueForKey(_KAUserProfile.ROLES_KEY);
  }

  public NSArray<com.kaviju.accesscontrol.model.KAUserProfileRole> roles(EOQualifier qualifier) {
    return roles(qualifier, null, false);
  }

  public NSArray<com.kaviju.accesscontrol.model.KAUserProfileRole> roles(EOQualifier qualifier, boolean fetch) {
    return roles(qualifier, null, fetch);
  }

  public NSArray<com.kaviju.accesscontrol.model.KAUserProfileRole> roles(EOQualifier qualifier, NSArray<EOSortOrdering> sortOrderings, boolean fetch) {
    NSArray<com.kaviju.accesscontrol.model.KAUserProfileRole> results;
    if (fetch) {
      EOQualifier fullQualifier;
      EOQualifier inverseQualifier = new EOKeyValueQualifier(com.kaviju.accesscontrol.model.KAUserProfileRole.USER_PROFILE_KEY, EOQualifier.QualifierOperatorEqual, this);
    	
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
      results = roles();
      if (qualifier != null) {
        results = (NSArray<com.kaviju.accesscontrol.model.KAUserProfileRole>)EOQualifier.filteredArrayWithQualifier(results, qualifier);
      }
      if (sortOrderings != null) {
        results = (NSArray<com.kaviju.accesscontrol.model.KAUserProfileRole>)EOSortOrdering.sortedArrayUsingKeyOrderArray(results, sortOrderings);
      }
    }
    return results;
  }
  
  public void addToRoles(com.kaviju.accesscontrol.model.KAUserProfileRole object) {
    includeObjectIntoPropertyWithKey(object, _KAUserProfile.ROLES_KEY);
  }

  public void removeFromRoles(com.kaviju.accesscontrol.model.KAUserProfileRole object) {
    excludeObjectFromPropertyWithKey(object, _KAUserProfile.ROLES_KEY);
  }

  public void addToRolesRelationship(com.kaviju.accesscontrol.model.KAUserProfileRole object) {
    if (_KAUserProfile.LOG.isDebugEnabled()) {
      _KAUserProfile.LOG.debug("adding " + object + " to roles relationship");
    }
    if (er.extensions.eof.ERXGenericRecord.InverseRelationshipUpdater.updateInverseRelationships()) {
    	addToRoles(object);
    }
    else {
    	addObjectToBothSidesOfRelationshipWithKey(object, _KAUserProfile.ROLES_KEY);
    }
  }

  public void removeFromRolesRelationship(com.kaviju.accesscontrol.model.KAUserProfileRole object) {
    if (_KAUserProfile.LOG.isDebugEnabled()) {
      _KAUserProfile.LOG.debug("removing " + object + " from roles relationship");
    }
    if (er.extensions.eof.ERXGenericRecord.InverseRelationshipUpdater.updateInverseRelationships()) {
    	removeFromRoles(object);
    }
    else {
    	removeObjectFromBothSidesOfRelationshipWithKey(object, _KAUserProfile.ROLES_KEY);
    }
  }

  public com.kaviju.accesscontrol.model.KAUserProfileRole createRolesRelationship() {
    EOClassDescription eoClassDesc = EOClassDescription.classDescriptionForEntityName( com.kaviju.accesscontrol.model.KAUserProfileRole.ENTITY_NAME );
    EOEnterpriseObject eo = eoClassDesc.createInstanceWithEditingContext(editingContext(), null);
    editingContext().insertObject(eo);
    addObjectToBothSidesOfRelationshipWithKey(eo, _KAUserProfile.ROLES_KEY);
    return (com.kaviju.accesscontrol.model.KAUserProfileRole) eo;
  }

  public void deleteRolesRelationship(com.kaviju.accesscontrol.model.KAUserProfileRole object) {
    removeObjectFromBothSidesOfRelationshipWithKey(object, _KAUserProfile.ROLES_KEY);
    editingContext().deleteObject(object);
  }

  public void deleteAllRolesRelationships() {
    Enumeration<com.kaviju.accesscontrol.model.KAUserProfileRole> objects = roles().immutableClone().objectEnumerator();
    while (objects.hasMoreElements()) {
      deleteRolesRelationship(objects.nextElement());
    }
  }


public static com.kaviju.accesscontrol.model.KAUserProfile createKAUserProfile(EOEditingContext editingContext) {
    com.kaviju.accesscontrol.model.KAUserProfile eo = (com.kaviju.accesscontrol.model.KAUserProfile) EOUtilities.createAndInsertInstance(editingContext, _KAUserProfile.ENTITY_NAME);    return eo;
  }

  public static ERXFetchSpecification<com.kaviju.accesscontrol.model.KAUserProfile> fetchSpec() {
    return new ERXFetchSpecification<com.kaviju.accesscontrol.model.KAUserProfile>(_KAUserProfile.ENTITY_NAME, null, null, false, true, null);
  }

  public static NSArray<com.kaviju.accesscontrol.model.KAUserProfile> fetchAllKAUserProfiles(EOEditingContext editingContext) {
    return _KAUserProfile.fetchAllKAUserProfiles(editingContext, null);
  }

  public static NSArray<com.kaviju.accesscontrol.model.KAUserProfile> fetchAllKAUserProfiles(EOEditingContext editingContext, NSArray<EOSortOrdering> sortOrderings) {
    return _KAUserProfile.fetchKAUserProfiles(editingContext, null, sortOrderings);
  }

  public static NSArray<com.kaviju.accesscontrol.model.KAUserProfile> fetchKAUserProfiles(EOEditingContext editingContext, EOQualifier qualifier, NSArray<EOSortOrdering> sortOrderings) {
    ERXFetchSpecification<com.kaviju.accesscontrol.model.KAUserProfile> fetchSpec = new ERXFetchSpecification<com.kaviju.accesscontrol.model.KAUserProfile>(_KAUserProfile.ENTITY_NAME, qualifier, sortOrderings);
    fetchSpec.setIsDeep(true);
    NSArray<com.kaviju.accesscontrol.model.KAUserProfile> eoObjects = fetchSpec.fetchObjects(editingContext);
    return eoObjects;
  }

  public static com.kaviju.accesscontrol.model.KAUserProfile fetchKAUserProfile(EOEditingContext editingContext, String keyName, Object value) {
    return _KAUserProfile.fetchKAUserProfile(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static com.kaviju.accesscontrol.model.KAUserProfile fetchKAUserProfile(EOEditingContext editingContext, EOQualifier qualifier) {
    NSArray<com.kaviju.accesscontrol.model.KAUserProfile> eoObjects = _KAUserProfile.fetchKAUserProfiles(editingContext, qualifier, null);
    com.kaviju.accesscontrol.model.KAUserProfile eoObject;
    int count = eoObjects.count();
    if (count == 0) {
      eoObject = null;
    }
    else if (count == 1) {
      eoObject = eoObjects.objectAtIndex(0);
    }
    else {
      throw new IllegalStateException("There was more than one KAUserProfile that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static com.kaviju.accesscontrol.model.KAUserProfile fetchRequiredKAUserProfile(EOEditingContext editingContext, String keyName, Object value) {
    return _KAUserProfile.fetchRequiredKAUserProfile(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static com.kaviju.accesscontrol.model.KAUserProfile fetchRequiredKAUserProfile(EOEditingContext editingContext, EOQualifier qualifier) {
    com.kaviju.accesscontrol.model.KAUserProfile eoObject = _KAUserProfile.fetchKAUserProfile(editingContext, qualifier);
    if (eoObject == null) {
      throw new NoSuchElementException("There was no KAUserProfile that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static com.kaviju.accesscontrol.model.KAUserProfile localInstanceIn(EOEditingContext editingContext, com.kaviju.accesscontrol.model.KAUserProfile eo) {
    com.kaviju.accesscontrol.model.KAUserProfile localInstance = (eo == null) ? null : ERXEOControlUtilities.localInstanceOfObject(editingContext, eo);
    if (localInstance == null && eo != null) {
      throw new IllegalStateException("You attempted to localInstance " + eo + ", which has not yet committed.");
    }
    return localInstance;
  }
}
