package com.kaviju.accesscontrol.utils;

import com.kaviju.accesscontrol.model.*;
import com.webobjects.eocontrol.EOEditingContext;
import com.webobjects.foundation.*;

import er.extensions.foundation.*;

public class RolesFileLoader {
	public  static final String rolesFileNamePropertyKey = "ka.accesscontrol.rolesFile";
	public  static final String rolesFileBundlePropertyKey = "ka.accesscontrol.rolesFileBundle";


	@SuppressWarnings("unchecked")
	static public void loadRolesFile(EOEditingContext ec) {
		String permissionFile = ERXProperties.stringForKey(rolesFileNamePropertyKey);

		NSBundle bundle = null;
		if (ERXProperties.hasKey(rolesFileBundlePropertyKey, true)) {
			String bundleName = ERXProperties.stringForKey(rolesFileBundlePropertyKey);
			bundle = NSBundle.bundleForName(bundleName);
		}
		
		@SuppressWarnings("rawtypes")
		NSDictionary permissionInfos = ERXDictionaryUtilities.dictionaryFromPropertyList(permissionFile, bundle);
		if (permissionInfos == null) {
			//System.out.println("KAAccessControl RolesFileLoader unable to load permission file " + permissionFile +".");
			return;
		}
		
		NSArray<String> lists = (NSArray<String>)permissionInfos.objectForKey("lists");
		NSDictionary<String, KAAccessList> listDict = processLists(ec, lists);

		NSArray<NSDictionary<String, Object>> groups = (NSArray<NSDictionary<String, Object>>) permissionInfos.objectForKey("roleGroups");
		NSDictionary<String, KARole> roleDict = processRoles(ec, listDict, groups);
		
		NSArray<NSDictionary<String, Object>> profiles = (NSArray<NSDictionary<String, Object>>) permissionInfos.objectForKey("profiles");
		processProfiles(ec, roleDict, profiles);
		ec.saveChanges();
	}

	private static NSDictionary<String, KAAccessList> processLists(EOEditingContext ec, NSArray<String> lists) {
		NSDictionary<String, KAAccessList> listsInDatabase = ERXArrayUtilities.dictionaryOfObjectsIndexedByKeyPath(KAAccessList.fetchAllKAAccessLists(ec), KAAccessList.CODE_KEY);
		NSMutableDictionary<String, KAAccessList> listDict = listsInDatabase.mutableClone();
		
		for (String listCode : lists) {			
			KAAccessList listObject = listDict.objectForKey(listCode);
			if (listObject == null) {
				listObject = KAAccessList.createKAAccessList(ec);
				listObject.setCode(listCode);
				listDict.put(listCode, listObject);
			}
		}
		return listDict;
	}

	private static NSDictionary<String, KARole> processRoles(EOEditingContext ec, NSDictionary<String, KAAccessList> listDict, NSArray<NSDictionary<String, Object>> groups) {

		NSDictionary<String, KARoleGroup> roleGroupsInDatabase = ERXArrayUtilities.dictionaryOfObjectsIndexedByKeyPath(KARoleGroup.fetchAllKARoleGroups(ec), KARoleGroup.CODE_KEY);
		NSMutableDictionary<String, KARoleGroup> roleGroupsDict = roleGroupsInDatabase.mutableClone();
		NSDictionary<String, KARole> rolesInDatabase = ERXArrayUtilities.dictionaryOfObjectsIndexedByKeyPath(KARole.fetchAllKARoles(ec), KARole.CODE_KEY);
		NSMutableDictionary<String, KARole> rolesDict = rolesInDatabase.mutableClone();
		
		int order = 1;
		for (NSDictionary<String, Object> group : groups) {
			String groupCode = (String) group.objectForKey("code");
			@SuppressWarnings("unchecked")
			NSArray<NSDictionary<String,String>> groupRoles = (NSArray<NSDictionary<String,String>>) group.objectForKey("roles");
			
			KARoleGroup groupObject = roleGroupsDict.objectForKey(groupCode);
			if (groupObject == null) {
				groupObject = KARoleGroup.createKARoleGroup(ec);
				groupObject.setCode(groupCode);
				groupObject.setDisplayOrder(order);
				roleGroupsDict.put(groupCode, groupObject);
			}
			else {
				groupObject.setDisplayOrder(order);
			}
			order++;
			
			int subOrder = 1;
			for (NSDictionary<String,String> groupRole : groupRoles) {
				String roleCode = (String)groupRole.objectForKey("code");
				KARole roleObject = rolesDict.objectForKey(roleCode);
				String listCode = (String)groupRole.objectForKey("listCode");
				boolean allowsMultipleItems = ERXValueUtilities.booleanValue(groupRole.objectForKey("allowsMultipleItems"));
				KAAccessList listObject = listDict.objectForKey(listCode);
				
				if (roleObject == null) {
					roleObject = KARole.createKARole(ec);
					roleObject.setCode(roleCode);
					roleObject.setAllowsMultipleItems(allowsMultipleItems);
					roleObject.setDisplayOrder(subOrder);
					roleObject.setGroupRelationship(groupObject);

					roleObject.setList(listObject);
					rolesDict.put(roleCode, roleObject);
				}
				else {
					roleObject.setGroupRelationship(groupObject);
					roleObject.setList(listObject);
					roleObject.setDisplayOrder(subOrder);
					roleObject.setAllowsMultipleItems(allowsMultipleItems);
				}
				subOrder++;
			}
		}
		return rolesDict;
	}

	private static void processProfiles(EOEditingContext ec, NSDictionary<String, KARole> roleDict, NSArray<NSDictionary<String, Object>> profiles) {
		NSDictionary<String, KAProfile> profilesInDatabase = ERXArrayUtilities.dictionaryOfObjectsIndexedByKeyPath(KAProfile.fetchAllKAProfiles(ec), KAProfile.CODE_KEY);
		NSMutableDictionary<String, KAProfile> profilesDict = profilesInDatabase.mutableClone();
		
		for (NSDictionary<String, Object> profile : profiles) {
			String profileCode = (String) profile.objectForKey("code");
			
			KAProfile profileObject = profilesDict.objectForKey(profileCode);
			if (profileObject == null) {
				profileObject = KAProfile.createKAProfile(ec);
				profileObject.setCode(profileCode);
				profilesDict.put(profileCode, profileObject);
			}
			NSMutableArray<String> obsoleteRoleCodes = KAProfileRole.ROLE.dot(KARole.CODE).arrayValueInObject(profileObject.profileRoles()).mutableClone();

			@SuppressWarnings("unchecked")
			NSArray<String> optionalRoleCodes = (NSArray<String>) profile.objectForKey("optionalRoles");
			if (optionalRoleCodes != null) {
				for (String roleCode : optionalRoleCodes) {
					KARole roleObject = roleDict.objectForKey(roleCode);
					profileObject.addOptionalRole(roleObject);
					obsoleteRoleCodes.remove(roleCode);
				}
			}

			@SuppressWarnings("unchecked")
			NSArray<String> byDefaultRoleCodes = (NSArray<String>) profile.objectForKey("byDefaultRoles");
			if (byDefaultRoleCodes != null) {
				for (String roleCode : byDefaultRoleCodes) {
					KARole roleObject = roleDict.objectForKey(roleCode);
					profileObject.addByDefaultRole(roleObject);
					obsoleteRoleCodes.remove(roleCode);
				}
			}

			@SuppressWarnings("unchecked")
			NSArray<String> roleCodes = (NSArray<String>) profile.objectForKey("roles");
			for (String roleCode : roleCodes) {
				KARole roleObject = roleDict.objectForKey(roleCode);
				profileObject.addMandatoryRole(roleObject);
				obsoleteRoleCodes.remove(roleCode);
			}

			// Remove roles no longer in file
			for (String roleCode : obsoleteRoleCodes) {
				KAProfileRole profileRole = ERXArrayUtilities.firstObject(profileObject.profileRoles(KAProfileRole.ROLE.dot(KARole.CODE).eq(roleCode)));
				profileObject.deleteProfileRolesRelationship(profileRole);
			}
		}
	}
}
