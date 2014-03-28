// DO NOT EDIT.  Make changes to com.kaviju.accesscontrol.model.KAProfileRole.java instead.
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
public abstract class _KAProfileRole extends  ERXGenericRecord {
  public static final String ENTITY_NAME = "KAProfileRole";

  // Attribute Keys
  public static final ERXKey<Boolean> IS_OPTIONAL = new ERXKey<Boolean>("isOptional");
  // Relationship Keys
  public static final ERXKey<com.kaviju.accesscontrol.model.KAProfile> PROFILE = new ERXKey<com.kaviju.accesscontrol.model.KAProfile>("profile");
  public static final ERXKey<com.kaviju.accesscontrol.model.KARole> ROLE = new ERXKey<com.kaviju.accesscontrol.model.KARole>("role");

  // Attributes
  public static final String IS_OPTIONAL_KEY = "isOptional";
  // Relationships
  public static final String PROFILE_KEY = "profile";
  public static final String ROLE_KEY = "role";

  private static Logger LOG = Logger.getLogger(_KAProfileRole.class);

  public com.kaviju.accesscontrol.model.KAProfileRole localInstanceIn(EOEditingContext editingContext) {
    com.kaviju.accesscontrol.model.KAProfileRole localInstance = (com.kaviju.accesscontrol.model.KAProfileRole)EOUtilities.localInstanceOfObject(editingContext, this);
    if (localInstance == null) {
      throw new IllegalStateException("You attempted to localInstance " + this + ", which has not yet committed.");
    }
    return localInstance;
  }

  public Boolean isOptional() {
    return (Boolean) storedValueForKey(_KAProfileRole.IS_OPTIONAL_KEY);
  }

  public void setIsOptional(Boolean value) {
    if (_KAProfileRole.LOG.isDebugEnabled()) {
    	_KAProfileRole.LOG.debug( "updating isOptional from " + isOptional() + " to " + value);
    }
    takeStoredValueForKey(value, _KAProfileRole.IS_OPTIONAL_KEY);
  }

  public com.kaviju.accesscontrol.model.KAProfile profile() {
    return (com.kaviju.accesscontrol.model.KAProfile)storedValueForKey(_KAProfileRole.PROFILE_KEY);
  }
  
  public void setProfile(com.kaviju.accesscontrol.model.KAProfile value) {
    takeStoredValueForKey(value, _KAProfileRole.PROFILE_KEY);
  }

  public void setProfileRelationship(com.kaviju.accesscontrol.model.KAProfile value) {
    if (_KAProfileRole.LOG.isDebugEnabled()) {
      _KAProfileRole.LOG.debug("updating profile from " + profile() + " to " + value);
    }
    if (er.extensions.eof.ERXGenericRecord.InverseRelationshipUpdater.updateInverseRelationships()) {
    	setProfile(value);
    }
    else if (value == null) {
    	com.kaviju.accesscontrol.model.KAProfile oldValue = profile();
    	if (oldValue != null) {
    		removeObjectFromBothSidesOfRelationshipWithKey(oldValue, _KAProfileRole.PROFILE_KEY);
      }
    } else {
    	addObjectToBothSidesOfRelationshipWithKey(value, _KAProfileRole.PROFILE_KEY);
    }
  }
  
  public com.kaviju.accesscontrol.model.KARole role() {
    return (com.kaviju.accesscontrol.model.KARole)storedValueForKey(_KAProfileRole.ROLE_KEY);
  }
  
  public void setRole(com.kaviju.accesscontrol.model.KARole value) {
    takeStoredValueForKey(value, _KAProfileRole.ROLE_KEY);
  }

  public void setRoleRelationship(com.kaviju.accesscontrol.model.KARole value) {
    if (_KAProfileRole.LOG.isDebugEnabled()) {
      _KAProfileRole.LOG.debug("updating role from " + role() + " to " + value);
    }
    if (er.extensions.eof.ERXGenericRecord.InverseRelationshipUpdater.updateInverseRelationships()) {
    	setRole(value);
    }
    else if (value == null) {
    	com.kaviju.accesscontrol.model.KARole oldValue = role();
    	if (oldValue != null) {
    		removeObjectFromBothSidesOfRelationshipWithKey(oldValue, _KAProfileRole.ROLE_KEY);
      }
    } else {
    	addObjectToBothSidesOfRelationshipWithKey(value, _KAProfileRole.ROLE_KEY);
    }
  }
  

public static com.kaviju.accesscontrol.model.KAProfileRole createKAProfileRole(EOEditingContext editingContext) {
    com.kaviju.accesscontrol.model.KAProfileRole eo = (com.kaviju.accesscontrol.model.KAProfileRole) EOUtilities.createAndInsertInstance(editingContext, _KAProfileRole.ENTITY_NAME);    return eo;
  }

  public static ERXFetchSpecification<com.kaviju.accesscontrol.model.KAProfileRole> fetchSpec() {
    return new ERXFetchSpecification<com.kaviju.accesscontrol.model.KAProfileRole>(_KAProfileRole.ENTITY_NAME, null, null, false, true, null);
  }

  public static NSArray<com.kaviju.accesscontrol.model.KAProfileRole> fetchAllKAProfileRoles(EOEditingContext editingContext) {
    return _KAProfileRole.fetchAllKAProfileRoles(editingContext, null);
  }

  public static NSArray<com.kaviju.accesscontrol.model.KAProfileRole> fetchAllKAProfileRoles(EOEditingContext editingContext, NSArray<EOSortOrdering> sortOrderings) {
    return _KAProfileRole.fetchKAProfileRoles(editingContext, null, sortOrderings);
  }

  public static NSArray<com.kaviju.accesscontrol.model.KAProfileRole> fetchKAProfileRoles(EOEditingContext editingContext, EOQualifier qualifier, NSArray<EOSortOrdering> sortOrderings) {
    ERXFetchSpecification<com.kaviju.accesscontrol.model.KAProfileRole> fetchSpec = new ERXFetchSpecification<com.kaviju.accesscontrol.model.KAProfileRole>(_KAProfileRole.ENTITY_NAME, qualifier, sortOrderings);
    fetchSpec.setIsDeep(true);
    NSArray<com.kaviju.accesscontrol.model.KAProfileRole> eoObjects = fetchSpec.fetchObjects(editingContext);
    return eoObjects;
  }

  public static com.kaviju.accesscontrol.model.KAProfileRole fetchKAProfileRole(EOEditingContext editingContext, String keyName, Object value) {
    return _KAProfileRole.fetchKAProfileRole(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static com.kaviju.accesscontrol.model.KAProfileRole fetchKAProfileRole(EOEditingContext editingContext, EOQualifier qualifier) {
    NSArray<com.kaviju.accesscontrol.model.KAProfileRole> eoObjects = _KAProfileRole.fetchKAProfileRoles(editingContext, qualifier, null);
    com.kaviju.accesscontrol.model.KAProfileRole eoObject;
    int count = eoObjects.count();
    if (count == 0) {
      eoObject = null;
    }
    else if (count == 1) {
      eoObject = eoObjects.objectAtIndex(0);
    }
    else {
      throw new IllegalStateException("There was more than one KAProfileRole that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static com.kaviju.accesscontrol.model.KAProfileRole fetchRequiredKAProfileRole(EOEditingContext editingContext, String keyName, Object value) {
    return _KAProfileRole.fetchRequiredKAProfileRole(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static com.kaviju.accesscontrol.model.KAProfileRole fetchRequiredKAProfileRole(EOEditingContext editingContext, EOQualifier qualifier) {
    com.kaviju.accesscontrol.model.KAProfileRole eoObject = _KAProfileRole.fetchKAProfileRole(editingContext, qualifier);
    if (eoObject == null) {
      throw new NoSuchElementException("There was no KAProfileRole that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static com.kaviju.accesscontrol.model.KAProfileRole localInstanceIn(EOEditingContext editingContext, com.kaviju.accesscontrol.model.KAProfileRole eo) {
    com.kaviju.accesscontrol.model.KAProfileRole localInstance = (eo == null) ? null : ERXEOControlUtilities.localInstanceOfObject(editingContext, eo);
    if (localInstance == null && eo != null) {
      throw new IllegalStateException("You attempted to localInstance " + eo + ", which has not yet committed.");
    }
    return localInstance;
  }
}
