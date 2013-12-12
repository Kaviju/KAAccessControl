// DO NOT EDIT.  Make changes to com.kaviju.accesscontrol.model.KARole.java instead.
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
public abstract class _KARole extends  ERXGenericRecord {
  public static final String ENTITY_NAME = "KARole";

  // Attribute Keys
  public static final ERXKey<Boolean> ALLOWS_MULTIPLE_ITEMS = new ERXKey<Boolean>("allowsMultipleItems");
  public static final ERXKey<String> CODE = new ERXKey<String>("code");
  public static final ERXKey<Integer> DISPLAY_ORDER = new ERXKey<Integer>("displayOrder");
  public static final ERXKey<Boolean> IN_PROFILE_ONLY = new ERXKey<Boolean>("inProfileOnly");
  // Relationship Keys
  public static final ERXKey<com.kaviju.accesscontrol.model.KARoleGroup> GROUP = new ERXKey<com.kaviju.accesscontrol.model.KARoleGroup>("group");
  public static final ERXKey<com.kaviju.accesscontrol.model.KAAccessList> LIST = new ERXKey<com.kaviju.accesscontrol.model.KAAccessList>("list");

  // Attributes
  public static final String ALLOWS_MULTIPLE_ITEMS_KEY = "allowsMultipleItems";
  public static final String CODE_KEY = "code";
  public static final String DISPLAY_ORDER_KEY = "displayOrder";
  public static final String IN_PROFILE_ONLY_KEY = "inProfileOnly";
  // Relationships
  public static final String GROUP_KEY = "group";
  public static final String LIST_KEY = "list";

  private static Logger LOG = Logger.getLogger(_KARole.class);

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

  public Boolean inProfileOnly() {
    return (Boolean) storedValueForKey(_KARole.IN_PROFILE_ONLY_KEY);
  }

  public void setInProfileOnly(Boolean value) {
    if (_KARole.LOG.isDebugEnabled()) {
    	_KARole.LOG.debug( "updating inProfileOnly from " + inProfileOnly() + " to " + value);
    }
    takeStoredValueForKey(value, _KARole.IN_PROFILE_ONLY_KEY);
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
  

public static com.kaviju.accesscontrol.model.KARole createKARole(EOEditingContext editingContext, Boolean allowsMultipleItems
, String code
, Integer displayOrder
, Boolean inProfileOnly
, com.kaviju.accesscontrol.model.KARoleGroup group) {
  com.kaviju.accesscontrol.model.KARole eo = (com.kaviju.accesscontrol.model.KARole) EOUtilities.createAndInsertInstance(editingContext, _KARole.ENTITY_NAME);  
		eo.setAllowsMultipleItems(allowsMultipleItems);
		eo.setCode(code);
		eo.setDisplayOrder(displayOrder);
		eo.setInProfileOnly(inProfileOnly);
  eo.setGroupRelationship(group);
  return eo;
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
