// DO NOT EDIT.  Make changes to com.kaviju.accesscontrol.model.KAUserProfileRole.java instead.
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
public abstract class _KAUserProfileRole extends  ERXGenericRecord {
  public static final String ENTITY_NAME = "KAUserProfileRole";

  // Attribute Keys
  // Relationship Keys
  public static final ERXKey<com.kaviju.accesscontrol.model.KAAccessListItem> LIST_ITEMS = new ERXKey<com.kaviju.accesscontrol.model.KAAccessListItem>("listItems");
  public static final ERXKey<com.kaviju.accesscontrol.model.KARole> ROLE = new ERXKey<com.kaviju.accesscontrol.model.KARole>("role");
  public static final ERXKey<com.kaviju.accesscontrol.model.KAUserProfile> USER_PROFILE = new ERXKey<com.kaviju.accesscontrol.model.KAUserProfile>("userProfile");

  // Attributes
  // Relationships
  public static final String LIST_ITEMS_KEY = LIST_ITEMS.key();
  public static final String ROLE_KEY = ROLE.key();
  public static final String USER_PROFILE_KEY = USER_PROFILE.key();

  private static Logger LOG = Logger.getLogger(_KAUserProfileRole.class);

  public com.kaviju.accesscontrol.model.KAUserProfileRole localInstanceIn(EOEditingContext editingContext) {
    com.kaviju.accesscontrol.model.KAUserProfileRole localInstance = (com.kaviju.accesscontrol.model.KAUserProfileRole)EOUtilities.localInstanceOfObject(editingContext, this);
    if (localInstance == null) {
      throw new IllegalStateException("You attempted to localInstance " + this + ", which has not yet committed.");
    }
    return localInstance;
  }

  public com.kaviju.accesscontrol.model.KARole role() {
    return (com.kaviju.accesscontrol.model.KARole)storedValueForKey(_KAUserProfileRole.ROLE_KEY);
  }
  
  public void setRole(com.kaviju.accesscontrol.model.KARole value) {
    takeStoredValueForKey(value, _KAUserProfileRole.ROLE_KEY);
  }

  public void setRoleRelationship(com.kaviju.accesscontrol.model.KARole value) {
    if (_KAUserProfileRole.LOG.isDebugEnabled()) {
      _KAUserProfileRole.LOG.debug("updating role from " + role() + " to " + value);
    }
    if (er.extensions.eof.ERXGenericRecord.InverseRelationshipUpdater.updateInverseRelationships()) {
    	setRole(value);
    }
    else if (value == null) {
    	com.kaviju.accesscontrol.model.KARole oldValue = role();
    	if (oldValue != null) {
    		removeObjectFromBothSidesOfRelationshipWithKey(oldValue, _KAUserProfileRole.ROLE_KEY);
      }
    } else {
    	addObjectToBothSidesOfRelationshipWithKey(value, _KAUserProfileRole.ROLE_KEY);
    }
  }
  
  public com.kaviju.accesscontrol.model.KAUserProfile userProfile() {
    return (com.kaviju.accesscontrol.model.KAUserProfile)storedValueForKey(_KAUserProfileRole.USER_PROFILE_KEY);
  }
  
  public void setUserProfile(com.kaviju.accesscontrol.model.KAUserProfile value) {
    takeStoredValueForKey(value, _KAUserProfileRole.USER_PROFILE_KEY);
  }

  public void setUserProfileRelationship(com.kaviju.accesscontrol.model.KAUserProfile value) {
    if (_KAUserProfileRole.LOG.isDebugEnabled()) {
      _KAUserProfileRole.LOG.debug("updating userProfile from " + userProfile() + " to " + value);
    }
    if (er.extensions.eof.ERXGenericRecord.InverseRelationshipUpdater.updateInverseRelationships()) {
    	setUserProfile(value);
    }
    else if (value == null) {
    	com.kaviju.accesscontrol.model.KAUserProfile oldValue = userProfile();
    	if (oldValue != null) {
    		removeObjectFromBothSidesOfRelationshipWithKey(oldValue, _KAUserProfileRole.USER_PROFILE_KEY);
      }
    } else {
    	addObjectToBothSidesOfRelationshipWithKey(value, _KAUserProfileRole.USER_PROFILE_KEY);
    }
  }
  
  public NSArray<com.kaviju.accesscontrol.model.KAAccessListItem> listItems() {
    return (NSArray<com.kaviju.accesscontrol.model.KAAccessListItem>)storedValueForKey(_KAUserProfileRole.LIST_ITEMS_KEY);
  }

  public NSArray<com.kaviju.accesscontrol.model.KAAccessListItem> listItems(EOQualifier qualifier) {
    return listItems(qualifier, null);
  }

  public NSArray<com.kaviju.accesscontrol.model.KAAccessListItem> listItems(EOQualifier qualifier, NSArray<EOSortOrdering> sortOrderings) {
    NSArray<com.kaviju.accesscontrol.model.KAAccessListItem> results;
      results = listItems();
      if (qualifier != null) {
        results = (NSArray<com.kaviju.accesscontrol.model.KAAccessListItem>)EOQualifier.filteredArrayWithQualifier(results, qualifier);
      }
      if (sortOrderings != null) {
        results = (NSArray<com.kaviju.accesscontrol.model.KAAccessListItem>)EOSortOrdering.sortedArrayUsingKeyOrderArray(results, sortOrderings);
      }
    return results;
  }
  
  public void addToListItems(com.kaviju.accesscontrol.model.KAAccessListItem object) {
    includeObjectIntoPropertyWithKey(object, _KAUserProfileRole.LIST_ITEMS_KEY);
  }

  public void removeFromListItems(com.kaviju.accesscontrol.model.KAAccessListItem object) {
    excludeObjectFromPropertyWithKey(object, _KAUserProfileRole.LIST_ITEMS_KEY);
  }

  public void addToListItemsRelationship(com.kaviju.accesscontrol.model.KAAccessListItem object) {
    if (_KAUserProfileRole.LOG.isDebugEnabled()) {
      _KAUserProfileRole.LOG.debug("adding " + object + " to listItems relationship");
    }
    if (er.extensions.eof.ERXGenericRecord.InverseRelationshipUpdater.updateInverseRelationships()) {
    	addToListItems(object);
    }
    else {
    	addObjectToBothSidesOfRelationshipWithKey(object, _KAUserProfileRole.LIST_ITEMS_KEY);
    }
  }

  public void removeFromListItemsRelationship(com.kaviju.accesscontrol.model.KAAccessListItem object) {
    if (_KAUserProfileRole.LOG.isDebugEnabled()) {
      _KAUserProfileRole.LOG.debug("removing " + object + " from listItems relationship");
    }
    if (er.extensions.eof.ERXGenericRecord.InverseRelationshipUpdater.updateInverseRelationships()) {
    	removeFromListItems(object);
    }
    else {
    	removeObjectFromBothSidesOfRelationshipWithKey(object, _KAUserProfileRole.LIST_ITEMS_KEY);
    }
  }

  public com.kaviju.accesscontrol.model.KAAccessListItem createListItemsRelationship() {
    EOClassDescription eoClassDesc = EOClassDescription.classDescriptionForEntityName( com.kaviju.accesscontrol.model.KAAccessListItem.ENTITY_NAME );
    EOEnterpriseObject eo = eoClassDesc.createInstanceWithEditingContext(editingContext(), null);
    editingContext().insertObject(eo);
    addObjectToBothSidesOfRelationshipWithKey(eo, _KAUserProfileRole.LIST_ITEMS_KEY);
    return (com.kaviju.accesscontrol.model.KAAccessListItem) eo;
  }

  public void deleteListItemsRelationship(com.kaviju.accesscontrol.model.KAAccessListItem object) {
    removeObjectFromBothSidesOfRelationshipWithKey(object, _KAUserProfileRole.LIST_ITEMS_KEY);
    editingContext().deleteObject(object);
  }

  public void deleteAllListItemsRelationships() {
    Enumeration<com.kaviju.accesscontrol.model.KAAccessListItem> objects = listItems().immutableClone().objectEnumerator();
    while (objects.hasMoreElements()) {
      deleteListItemsRelationship(objects.nextElement());
    }
  }


public static com.kaviju.accesscontrol.model.KAUserProfileRole createKAUserProfileRole(EOEditingContext editingContext, com.kaviju.accesscontrol.model.KARole role, com.kaviju.accesscontrol.model.KAUserProfile userProfile) {
  com.kaviju.accesscontrol.model.KAUserProfileRole eo = (com.kaviju.accesscontrol.model.KAUserProfileRole) EOUtilities.createAndInsertInstance(editingContext, _KAUserProfileRole.ENTITY_NAME);  
  eo.setRoleRelationship(role);
  eo.setUserProfileRelationship(userProfile);
  return eo;
}

public static com.kaviju.accesscontrol.model.KAUserProfileRole createKAUserProfileRole(EOEditingContext editingContext) {
    com.kaviju.accesscontrol.model.KAUserProfileRole eo = (com.kaviju.accesscontrol.model.KAUserProfileRole) EOUtilities.createAndInsertInstance(editingContext, _KAUserProfileRole.ENTITY_NAME);    return eo;
  }

  public static ERXFetchSpecification<com.kaviju.accesscontrol.model.KAUserProfileRole> fetchSpec() {
    return new ERXFetchSpecification<com.kaviju.accesscontrol.model.KAUserProfileRole>(_KAUserProfileRole.ENTITY_NAME, null, null, false, true, null);
  }

  public static NSArray<com.kaviju.accesscontrol.model.KAUserProfileRole> fetchAllKAUserProfileRoles(EOEditingContext editingContext) {
    return _KAUserProfileRole.fetchAllKAUserProfileRoles(editingContext, null);
  }

  public static NSArray<com.kaviju.accesscontrol.model.KAUserProfileRole> fetchAllKAUserProfileRoles(EOEditingContext editingContext, NSArray<EOSortOrdering> sortOrderings) {
    return _KAUserProfileRole.fetchKAUserProfileRoles(editingContext, null, sortOrderings);
  }

  public static NSArray<com.kaviju.accesscontrol.model.KAUserProfileRole> fetchKAUserProfileRoles(EOEditingContext editingContext, EOQualifier qualifier, NSArray<EOSortOrdering> sortOrderings) {
    ERXFetchSpecification<com.kaviju.accesscontrol.model.KAUserProfileRole> fetchSpec = new ERXFetchSpecification<com.kaviju.accesscontrol.model.KAUserProfileRole>(_KAUserProfileRole.ENTITY_NAME, qualifier, sortOrderings);
    fetchSpec.setIsDeep(true);
    NSArray<com.kaviju.accesscontrol.model.KAUserProfileRole> eoObjects = fetchSpec.fetchObjects(editingContext);
    return eoObjects;
  }

  public static com.kaviju.accesscontrol.model.KAUserProfileRole fetchKAUserProfileRole(EOEditingContext editingContext, String keyName, Object value) {
    return _KAUserProfileRole.fetchKAUserProfileRole(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static com.kaviju.accesscontrol.model.KAUserProfileRole fetchKAUserProfileRole(EOEditingContext editingContext, EOQualifier qualifier) {
    NSArray<com.kaviju.accesscontrol.model.KAUserProfileRole> eoObjects = _KAUserProfileRole.fetchKAUserProfileRoles(editingContext, qualifier, null);
    com.kaviju.accesscontrol.model.KAUserProfileRole eoObject;
    int count = eoObjects.count();
    if (count == 0) {
      eoObject = null;
    }
    else if (count == 1) {
      eoObject = eoObjects.objectAtIndex(0);
    }
    else {
      throw new IllegalStateException("There was more than one KAUserProfileRole that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static com.kaviju.accesscontrol.model.KAUserProfileRole fetchRequiredKAUserProfileRole(EOEditingContext editingContext, String keyName, Object value) {
    return _KAUserProfileRole.fetchRequiredKAUserProfileRole(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static com.kaviju.accesscontrol.model.KAUserProfileRole fetchRequiredKAUserProfileRole(EOEditingContext editingContext, EOQualifier qualifier) {
    com.kaviju.accesscontrol.model.KAUserProfileRole eoObject = _KAUserProfileRole.fetchKAUserProfileRole(editingContext, qualifier);
    if (eoObject == null) {
      throw new NoSuchElementException("There was no KAUserProfileRole that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static com.kaviju.accesscontrol.model.KAUserProfileRole localInstanceIn(EOEditingContext editingContext, com.kaviju.accesscontrol.model.KAUserProfileRole eo) {
    com.kaviju.accesscontrol.model.KAUserProfileRole localInstance = (eo == null) ? null : ERXEOControlUtilities.localInstanceOfObject(editingContext, eo);
    if (localInstance == null && eo != null) {
      throw new IllegalStateException("You attempted to localInstance " + eo + ", which has not yet committed.");
    }
    return localInstance;
  }
}
