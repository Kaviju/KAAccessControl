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
				boolean inProfileOnly = ERXValueUtilities.booleanValue(groupRole.objectForKey("inProfileOnly"));
				boolean allowsMultipleItems = ERXValueUtilities.booleanValue(groupRole.objectForKey("allowsMultipleItems"));
				KAAccessList listObject = listDict.objectForKey(listCode);
				
				if (roleObject == null) {
					roleObject = KARole.createKARole(ec);
					roleObject.setAllowsMultipleItems(allowsMultipleItems);
					roleObject.setDisplayOrder(subOrder);
					roleObject.setInProfileOnly(inProfileOnly);
					roleObject.setGroup(groupObject);

					roleObject.setList(listObject);
					rolesDict.put(roleCode, roleObject);
				}
				else {
					roleObject.setGroup(groupObject);
					roleObject.setList(listObject);
					roleObject.setDisplayOrder(subOrder);
					roleObject.setInProfileOnly(inProfileOnly);
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
			@SuppressWarnings("unchecked")
			NSArray<String> profileRoles = (NSArray<String>) profile.objectForKey("roles");
			
			KAProfile profileObject = profilesDict.objectForKey(profileCode);
			if (profileObject == null) {
				profileObject = KAProfile.createKAProfile(ec);
				profileObject.setCode(profileCode);
				profilesDict.put(profileCode, profileObject);
			}
			
			// Remove all permissions and add the one specified
			for (KARole roleObject : profileObject.roles().immutableClone()) {
				profileObject.removeFromRoles(roleObject);
			}
			for (String permissionCode : profileRoles) {
				KARole permissionObject = roleDict.objectForKey(permissionCode);
				profileObject.addToRolesRelationship(permissionObject);
			}
		}
	}
}
