package com.kaviju.accesscontrol.component;

import com.kaviju.accesscontrol.model.KAAccessListItem;
import com.kaviju.accesscontrol.model.KARole;
import com.kaviju.accesscontrol.model.KAUserProfile;
import com.webobjects.appserver.WOActionResults;
import com.webobjects.appserver.WOContext;
import com.webobjects.appserver.WOResponse;
import com.webobjects.foundation.NSArray;

import er.ajax.AjaxModalDialog;
import er.ajax.AjaxUpdateContainer;
import er.extensions.components.ERXComponent;
import er.extensions.foundation.ERXArrayUtilities;

@SuppressWarnings("serial")
public class UserPermRoleEditor extends ERXComponent {
    private KAUserProfile userProfile;
	private KARole role;
	private NSArray<KAAccessListItem> items;
	private KAAccessListItem item;
	private KAAccessListItem selectedItem;
	private String updateContainerId;

	public UserPermRoleEditor(WOContext context) {
        super(context);
    }
	
	@Override
	public void appendToResponse(WOResponse aResponse, WOContext aContext) {
		updateContainerId = "UserPermRoleEdit_"+role().primaryKey();
		super.appendToResponse(aResponse, aContext);
	}

	public KAUserProfile userProfile() {
		return userProfile;
	}

	public void setUserProfile(KAUserProfile userProfile) {
		this.userProfile = userProfile;
	}

	public KARole role() {
		return role;
	}

	public void setRole(KARole role) {
		this.role = role;
	}

	public boolean roleDisabled() {
		return userProfile().profile().mandatoryRoles().contains(role());
	}

	public boolean roleChecked() {
		return userProfile().hasRole(role().code());
	}

	public void setRoleChecked(boolean roleChecked) {
		if (roleChecked() && roleChecked == false) {
			userProfile().removeRole(role());
		}
		if (roleChecked() == false && roleChecked == true) {
			userProfile().addRole(role());
		}
	}

	public NSArray<KAAccessListItem> currentItems() {
		return userProfile().listItemsForRole(role());
	}

	public NSArray<KAAccessListItem> items() {
		items = role().list().items();
		items = ERXArrayUtilities.arrayMinusArray(items, userProfile().listItemsForRole(role()));
		items = KAAccessListItem.NAME.asc().sorted(items);
		return items;
	}

	public KAAccessListItem item() {
		return item;
	}

	public void setItem(KAAccessListItem item) {
		this.item = item;
	}

	public KAAccessListItem selectedItem() {
		return selectedItem;
	}

	public void setSelectedItem(KAAccessListItem selectedItem) {
		this.selectedItem = selectedItem;
	}

	public WOActionResults deleteItem() {
		userProfile().removeItemForRole(item(), role());
		if (AjaxModalDialog.isInDialog(context())) {
			AjaxModalDialog.update(context(), null);
		}
		else {
			AjaxUpdateContainer.updateContainerWithID(AjaxUpdateContainer.currentUpdateContainerID(), context());
		}
		return null;
	}
	public WOActionResults addItem() {
		userProfile().addItemForRole(selectedItem(), role());
		if (AjaxModalDialog.isInDialog(context())) {
			AjaxModalDialog.update(context(), null);
		}
		else {
			AjaxUpdateContainer.updateContainerWithID(AjaxUpdateContainer.currentUpdateContainerID(), context());
		}
		setSelectedItem(null);
		return null;
	}

	public String addItemLabel() {
		return localizer().localizedStringForKeyWithDefault("UserPermissionEditor.addItem");
	}

	public String removeItemLabel() {
		return localizer().localizedStringForKeyWithDefault("UserPermissionEditor.removeItem");
	}

	public boolean displayItemList() {
		return roleChecked() && role().list() != null;
	}

	/**
	 * @return the updateContainerId
	 */
	public String updateContainerId() {
		return updateContainerId;
	}
}