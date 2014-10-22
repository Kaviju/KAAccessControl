package com.kaviju.accesscontrol.service;

import com.kaviju.accesscontrol.annotation.*;
import com.kaviju.accesscontrol.model.*;
import com.webobjects.appserver.WOComponent;
import com.webobjects.eocontrol.EOEditingContext;
import com.webobjects.foundation.*;

import er.extensions.eof.ERXEC;

public class ComponentAccessService {
	static ComponentAccessService sharedInstance;
	private NSSet<String> _allRolesCodes;
	
	static public ComponentAccessService getInstance() {
		if (sharedInstance == null) {
			sharedInstance = new ComponentAccessService();
		}
		return sharedInstance;
	}
		
	private ComponentAccessService() {}
	
	public boolean isComponentAccessibleForUserProfile(String componentName, KAUserProfile userProfile) {
		@SuppressWarnings("unchecked")
		Class<WOComponent> componentClass = _NSUtilities.classWithName(componentName);
		if (componentClass == null) {
			throw new IllegalArgumentException("There is no component with name: "+componentName+"");
		}
		NSSet<String> roleCodesThatAllowAccess = readAllowedRoleCodesInClass(componentClass);
		
		return userProfile.hasAtLeastOneOfTheseRoles(roleCodesThatAllowAccess);
	}
	public boolean isComponentAccessibleForUserProfile(Class<? extends WOComponent> componentClass, KAUserProfile userProfile) {
		NSSet<String> roleCodesThatAllowAccess = readAllowedRoleCodesInClass(componentClass);
		
		return userProfile.hasAtLeastOneOfTheseRoles(roleCodesThatAllowAccess);
	}
	
	public NSSet<String> readAllowedRoleCodesInClass(Class<? extends WOComponent> componentClass) {
		boolean isAllowedForAll = verifyAllowedForAllAnnotationInClass(componentClass);
		NSSet<String> allowedRoleCodesAnnotation = readAllowedForRolesAnnotationInClass(componentClass);
		NSSet<String> deniedRoleCodesAnnotation = readDeniedForRolesAnnotationInClass(componentClass);
		
		if (isAllowedForAll) {
			if (allowedRoleCodesAnnotation != null || deniedRoleCodesAnnotation != null) {
				throw new RuntimeException("The class "+componentClass.getName()+" specify an AllowedForAll, an AllowedForRole and a DeniedForRole annotation and only one is allowed.");
			}
			return getAllPossibleRoleCodes();
		}
		
		if (allowedRoleCodesAnnotation != null && deniedRoleCodesAnnotation != null) {
			throw new RuntimeException("The class "+componentClass.getName()+" specify both an AllowedForRole and a DeniedForRole annotation and only one is allowed.");
		}

		if (allowedRoleCodesAnnotation != null) {
			return allowedRoleCodesAnnotation;
		}
		
		if (deniedRoleCodesAnnotation != null) {
			return getAllPossibleRoleCodes().setBySubtractingSet(deniedRoleCodesAnnotation);
		}
		return new NSSet<String>(); // Nothing specified, no access allowed.
	}

	public boolean verifyAllowedForAllAnnotationInClass(Class<? extends WOComponent> componentClass) {
		AllowedForAll annotation = componentClass.getAnnotation(AllowedForAll.class);
		return annotation != null;
	}

	public NSSet<String> readAllowedForRolesAnnotationInClass(Class<? extends WOComponent> componentClass) {
		AllowedForRole annotation = componentClass.getAnnotation(AllowedForRole.class);
		if (annotation != null) {
			return new NSSet<String>(annotation.value());
		}
		return null;
	}
	public NSSet<String> readDeniedForRolesAnnotationInClass(Class<? extends WOComponent> componentClass) {
		DeniedForRole annotation = componentClass.getAnnotation(DeniedForRole.class);
		if (annotation != null) {
			return new NSSet<String>(annotation.value());
		}
		return null;
	}
	
	
	protected NSSet<String> getAllPossibleRoleCodes() {
		if (_allRolesCodes == null) {
			EOEditingContext ec = ERXEC.newEditingContext();
			ec.lock();
			try {
				NSArray<KARole> roles = KARole.fetchAllKARoles(ec);
				_allRolesCodes = new NSSet<String>(KARole.CODE.arrayValueInObject(roles));
			}
			finally {
				ec.unlock();
			}
		}
		return _allRolesCodes;
	}
	
	protected void setAllRolesCodesForTest(NSSet<String> allRolesCodes) {
		_allRolesCodes = allRolesCodes;
	}
	
}
