// DO NOT EDIT.  Make changes to com.kaviju.accesscontrol.model.KAUserProfileDefaultEntity.java instead.
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
public abstract class _KAUserProfileDefaultEntity extends com.kaviju.accesscontrol.model.KAUserProfile {
  public static final String ENTITY_NAME = "KAUserProfileDefaultEntity";

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

  private static Logger LOG = Logger.getLogger(_KAUserProfileDefaultEntity.class);

  public com.kaviju.accesscontrol.model.KAUserProfileDefaultEntity localInstanceIn(EOEditingContext editingContext) {
    com.kaviju.accesscontrol.model.KAUserProfileDefaultEntity localInstance = (com.kaviju.accesscontrol.model.KAUserProfileDefaultEntity)EOUtilities.localInstanceOfObject(editingContext, this);
    if (localInstance == null) {
      throw new IllegalStateException("You attempted to localInstance " + this + ", which has not yet committed.");
    }
    return localInstance;
  }


public static com.kaviju.accesscontrol.model.KAUserProfileDefaultEntity createKAUserProfileDefaultEntity(EOEditingContext editingContext) {
    com.kaviju.accesscontrol.model.KAUserProfileDefaultEntity eo = (com.kaviju.accesscontrol.model.KAUserProfileDefaultEntity) EOUtilities.createAndInsertInstance(editingContext, _KAUserProfileDefaultEntity.ENTITY_NAME);    return eo;
  }

  public static ERXFetchSpecification<com.kaviju.accesscontrol.model.KAUserProfileDefaultEntity> fetchSpecForKAUserProfileDefaultEntity() {
    return new ERXFetchSpecification<com.kaviju.accesscontrol.model.KAUserProfileDefaultEntity>(_KAUserProfileDefaultEntity.ENTITY_NAME, null, null, false, true, null);
  }

  public static NSArray<com.kaviju.accesscontrol.model.KAUserProfileDefaultEntity> fetchAllKAUserProfileDefaultEntities(EOEditingContext editingContext) {
    return _KAUserProfileDefaultEntity.fetchAllKAUserProfileDefaultEntities(editingContext, null);
  }

  public static NSArray<com.kaviju.accesscontrol.model.KAUserProfileDefaultEntity> fetchAllKAUserProfileDefaultEntities(EOEditingContext editingContext, NSArray<EOSortOrdering> sortOrderings) {
    return _KAUserProfileDefaultEntity.fetchKAUserProfileDefaultEntities(editingContext, null, sortOrderings);
  }

  public static NSArray<com.kaviju.accesscontrol.model.KAUserProfileDefaultEntity> fetchKAUserProfileDefaultEntities(EOEditingContext editingContext, EOQualifier qualifier, NSArray<EOSortOrdering> sortOrderings) {
    ERXFetchSpecification<com.kaviju.accesscontrol.model.KAUserProfileDefaultEntity> fetchSpec = new ERXFetchSpecification<com.kaviju.accesscontrol.model.KAUserProfileDefaultEntity>(_KAUserProfileDefaultEntity.ENTITY_NAME, qualifier, sortOrderings);
    fetchSpec.setIsDeep(true);
    NSArray<com.kaviju.accesscontrol.model.KAUserProfileDefaultEntity> eoObjects = fetchSpec.fetchObjects(editingContext);
    return eoObjects;
  }

  public static com.kaviju.accesscontrol.model.KAUserProfileDefaultEntity fetchKAUserProfileDefaultEntity(EOEditingContext editingContext, String keyName, Object value) {
    return _KAUserProfileDefaultEntity.fetchKAUserProfileDefaultEntity(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static com.kaviju.accesscontrol.model.KAUserProfileDefaultEntity fetchKAUserProfileDefaultEntity(EOEditingContext editingContext, EOQualifier qualifier) {
    NSArray<com.kaviju.accesscontrol.model.KAUserProfileDefaultEntity> eoObjects = _KAUserProfileDefaultEntity.fetchKAUserProfileDefaultEntities(editingContext, qualifier, null);
    com.kaviju.accesscontrol.model.KAUserProfileDefaultEntity eoObject;
    int count = eoObjects.count();
    if (count == 0) {
      eoObject = null;
    }
    else if (count == 1) {
      eoObject = eoObjects.objectAtIndex(0);
    }
    else {
      throw new IllegalStateException("There was more than one KAUserProfileDefaultEntity that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static com.kaviju.accesscontrol.model.KAUserProfileDefaultEntity fetchRequiredKAUserProfileDefaultEntity(EOEditingContext editingContext, String keyName, Object value) {
    return _KAUserProfileDefaultEntity.fetchRequiredKAUserProfileDefaultEntity(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static com.kaviju.accesscontrol.model.KAUserProfileDefaultEntity fetchRequiredKAUserProfileDefaultEntity(EOEditingContext editingContext, EOQualifier qualifier) {
    com.kaviju.accesscontrol.model.KAUserProfileDefaultEntity eoObject = _KAUserProfileDefaultEntity.fetchKAUserProfileDefaultEntity(editingContext, qualifier);
    if (eoObject == null) {
      throw new NoSuchElementException("There was no KAUserProfileDefaultEntity that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static com.kaviju.accesscontrol.model.KAUserProfileDefaultEntity localInstanceIn(EOEditingContext editingContext, com.kaviju.accesscontrol.model.KAUserProfileDefaultEntity eo) {
    com.kaviju.accesscontrol.model.KAUserProfileDefaultEntity localInstance = (eo == null) ? null : ERXEOControlUtilities.localInstanceOfObject(editingContext, eo);
    if (localInstance == null && eo != null) {
      throw new IllegalStateException("You attempted to localInstance " + eo + ", which has not yet committed.");
    }
    return localInstance;
  }
}
