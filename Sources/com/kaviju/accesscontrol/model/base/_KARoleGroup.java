// DO NOT EDIT.  Make changes to com.kaviju.accesscontrol.model.KARoleGroup.java instead.
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
public abstract class _KARoleGroup extends  ERXGenericRecord {
  public static final String ENTITY_NAME = "KARoleGroup";

  // Attribute Keys
  public static final ERXKey<String> CODE = new ERXKey<String>("code");
  public static final ERXKey<Integer> DISPLAY_ORDER = new ERXKey<Integer>("displayOrder");
  // Relationship Keys
  public static final ERXKey<com.kaviju.accesscontrol.model.KARole> ROLES = new ERXKey<com.kaviju.accesscontrol.model.KARole>("roles");

  // Attributes
  public static final String CODE_KEY = CODE.key();
  public static final String DISPLAY_ORDER_KEY = DISPLAY_ORDER.key();
  // Relationships
  public static final String ROLES_KEY = ROLES.key();

  private static Logger LOG = Logger.getLogger(_KARoleGroup.class);

  public com.kaviju.accesscontrol.model.KARoleGroup localInstanceIn(EOEditingContext editingContext) {
    com.kaviju.accesscontrol.model.KARoleGroup localInstance = (com.kaviju.accesscontrol.model.KARoleGroup)EOUtilities.localInstanceOfObject(editingContext, this);
    if (localInstance == null) {
      throw new IllegalStateException("You attempted to localInstance " + this + ", which has not yet committed.");
    }
    return localInstance;
  }

  public String code() {
    return (String) storedValueForKey(_KARoleGroup.CODE_KEY);
  }

  public void setCode(String value) {
    if (_KARoleGroup.LOG.isDebugEnabled()) {
    	_KARoleGroup.LOG.debug( "updating code from " + code() + " to " + value);
    }
    takeStoredValueForKey(value, _KARoleGroup.CODE_KEY);
  }

  public Integer displayOrder() {
    return (Integer) storedValueForKey(_KARoleGroup.DISPLAY_ORDER_KEY);
  }

  public void setDisplayOrder(Integer value) {
    if (_KARoleGroup.LOG.isDebugEnabled()) {
    	_KARoleGroup.LOG.debug( "updating displayOrder from " + displayOrder() + " to " + value);
    }
    takeStoredValueForKey(value, _KARoleGroup.DISPLAY_ORDER_KEY);
  }

  public NSArray<com.kaviju.accesscontrol.model.KARole> roles() {
    return (NSArray<com.kaviju.accesscontrol.model.KARole>)storedValueForKey(_KARoleGroup.ROLES_KEY);
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
      EOQualifier inverseQualifier = new EOKeyValueQualifier(com.kaviju.accesscontrol.model.KARole.GROUP_KEY, EOQualifier.QualifierOperatorEqual, this);
    	
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
    includeObjectIntoPropertyWithKey(object, _KARoleGroup.ROLES_KEY);
  }

  public void removeFromRoles(com.kaviju.accesscontrol.model.KARole object) {
    excludeObjectFromPropertyWithKey(object, _KARoleGroup.ROLES_KEY);
  }

  public void addToRolesRelationship(com.kaviju.accesscontrol.model.KARole object) {
    if (_KARoleGroup.LOG.isDebugEnabled()) {
      _KARoleGroup.LOG.debug("adding " + object + " to roles relationship");
    }
    if (er.extensions.eof.ERXGenericRecord.InverseRelationshipUpdater.updateInverseRelationships()) {
    	addToRoles(object);
    }
    else {
    	addObjectToBothSidesOfRelationshipWithKey(object, _KARoleGroup.ROLES_KEY);
    }
  }

  public void removeFromRolesRelationship(com.kaviju.accesscontrol.model.KARole object) {
    if (_KARoleGroup.LOG.isDebugEnabled()) {
      _KARoleGroup.LOG.debug("removing " + object + " from roles relationship");
    }
    if (er.extensions.eof.ERXGenericRecord.InverseRelationshipUpdater.updateInverseRelationships()) {
    	removeFromRoles(object);
    }
    else {
    	removeObjectFromBothSidesOfRelationshipWithKey(object, _KARoleGroup.ROLES_KEY);
    }
  }

  public com.kaviju.accesscontrol.model.KARole createRolesRelationship() {
    EOClassDescription eoClassDesc = EOClassDescription.classDescriptionForEntityName( com.kaviju.accesscontrol.model.KARole.ENTITY_NAME );
    EOEnterpriseObject eo = eoClassDesc.createInstanceWithEditingContext(editingContext(), null);
    editingContext().insertObject(eo);
    addObjectToBothSidesOfRelationshipWithKey(eo, _KARoleGroup.ROLES_KEY);
    return (com.kaviju.accesscontrol.model.KARole) eo;
  }

  public void deleteRolesRelationship(com.kaviju.accesscontrol.model.KARole object) {
    removeObjectFromBothSidesOfRelationshipWithKey(object, _KARoleGroup.ROLES_KEY);
    editingContext().deleteObject(object);
  }

  public void deleteAllRolesRelationships() {
    Enumeration<com.kaviju.accesscontrol.model.KARole> objects = roles().immutableClone().objectEnumerator();
    while (objects.hasMoreElements()) {
      deleteRolesRelationship(objects.nextElement());
    }
  }


public static com.kaviju.accesscontrol.model.KARoleGroup createKARoleGroup(EOEditingContext editingContext, String code
, Integer displayOrder
) {
  com.kaviju.accesscontrol.model.KARoleGroup eo = (com.kaviju.accesscontrol.model.KARoleGroup) EOUtilities.createAndInsertInstance(editingContext, _KARoleGroup.ENTITY_NAME);  
		eo.setCode(code);
		eo.setDisplayOrder(displayOrder);
  return eo;
}

public static com.kaviju.accesscontrol.model.KARoleGroup createKARoleGroup(EOEditingContext editingContext) {
    com.kaviju.accesscontrol.model.KARoleGroup eo = (com.kaviju.accesscontrol.model.KARoleGroup) EOUtilities.createAndInsertInstance(editingContext, _KARoleGroup.ENTITY_NAME);    return eo;
  }

  public static ERXFetchSpecification<com.kaviju.accesscontrol.model.KARoleGroup> fetchSpec() {
    return new ERXFetchSpecification<com.kaviju.accesscontrol.model.KARoleGroup>(_KARoleGroup.ENTITY_NAME, null, null, false, true, null);
  }

  public static NSArray<com.kaviju.accesscontrol.model.KARoleGroup> fetchAllKARoleGroups(EOEditingContext editingContext) {
    return _KARoleGroup.fetchAllKARoleGroups(editingContext, null);
  }

  public static NSArray<com.kaviju.accesscontrol.model.KARoleGroup> fetchAllKARoleGroups(EOEditingContext editingContext, NSArray<EOSortOrdering> sortOrderings) {
    return _KARoleGroup.fetchKARoleGroups(editingContext, null, sortOrderings);
  }

  public static NSArray<com.kaviju.accesscontrol.model.KARoleGroup> fetchKARoleGroups(EOEditingContext editingContext, EOQualifier qualifier, NSArray<EOSortOrdering> sortOrderings) {
    ERXFetchSpecification<com.kaviju.accesscontrol.model.KARoleGroup> fetchSpec = new ERXFetchSpecification<com.kaviju.accesscontrol.model.KARoleGroup>(_KARoleGroup.ENTITY_NAME, qualifier, sortOrderings);
    fetchSpec.setIsDeep(true);
    NSArray<com.kaviju.accesscontrol.model.KARoleGroup> eoObjects = fetchSpec.fetchObjects(editingContext);
    return eoObjects;
  }

  public static com.kaviju.accesscontrol.model.KARoleGroup fetchKARoleGroup(EOEditingContext editingContext, String keyName, Object value) {
    return _KARoleGroup.fetchKARoleGroup(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static com.kaviju.accesscontrol.model.KARoleGroup fetchKARoleGroup(EOEditingContext editingContext, EOQualifier qualifier) {
    NSArray<com.kaviju.accesscontrol.model.KARoleGroup> eoObjects = _KARoleGroup.fetchKARoleGroups(editingContext, qualifier, null);
    com.kaviju.accesscontrol.model.KARoleGroup eoObject;
    int count = eoObjects.count();
    if (count == 0) {
      eoObject = null;
    }
    else if (count == 1) {
      eoObject = eoObjects.objectAtIndex(0);
    }
    else {
      throw new IllegalStateException("There was more than one KARoleGroup that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static com.kaviju.accesscontrol.model.KARoleGroup fetchRequiredKARoleGroup(EOEditingContext editingContext, String keyName, Object value) {
    return _KARoleGroup.fetchRequiredKARoleGroup(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static com.kaviju.accesscontrol.model.KARoleGroup fetchRequiredKARoleGroup(EOEditingContext editingContext, EOQualifier qualifier) {
    com.kaviju.accesscontrol.model.KARoleGroup eoObject = _KARoleGroup.fetchKARoleGroup(editingContext, qualifier);
    if (eoObject == null) {
      throw new NoSuchElementException("There was no KARoleGroup that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static com.kaviju.accesscontrol.model.KARoleGroup localInstanceIn(EOEditingContext editingContext, com.kaviju.accesscontrol.model.KARoleGroup eo) {
    com.kaviju.accesscontrol.model.KARoleGroup localInstance = (eo == null) ? null : ERXEOControlUtilities.localInstanceOfObject(editingContext, eo);
    if (localInstance == null && eo != null) {
      throw new IllegalStateException("You attempted to localInstance " + eo + ", which has not yet committed.");
    }
    return localInstance;
  }
}
