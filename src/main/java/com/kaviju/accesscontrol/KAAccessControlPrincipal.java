package com.kaviju.accesscontrol;

import com.kaviju.accesscontrol.utils.*;
import com.webobjects.eocontrol.EOEditingContext;

import er.extensions.*;
import er.extensions.eof.ERXEC;

public class KAAccessControlPrincipal extends ERXFrameworkPrincipal {
	@SuppressWarnings("rawtypes")
    public final static Class[] REQUIRES = new Class[] { ERXExtensions.class };
	@SuppressWarnings("unused")
	private static ListItemAutoUpdater listAutoUpdater;

	static {
		setUpFrameworkPrincipalClass(KAAccessControlPrincipal.class);
	}



	@Override
	public void finishInitialization() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void didFinishInitialization() {
		new Thread(() -> {
			EOEditingContext ec = ERXEC.newEditingContext();
			ec.lock();
			try {
				RolesFileLoader.loadRolesFile(ec);
				ec.saveChanges();
				// Wait few seconds before starting the scan, we want the app to respond as soon as possible
				Thread.sleep(7500);
				
				listAutoUpdater = new ListItemAutoUpdater(ec);
				ListItemFromClassUpdater.updateItemsFromAnnotations(ec);
				ec.saveChanges();
			}
			catch (Exception e) {
				throw new RuntimeException(e);
			}
			finally {
				ec.unlock();
			}
		}, "ListItemFromClassUpdater").start();
	}
	
}