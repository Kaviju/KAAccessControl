package com.kaviju.accesscontrol;

import com.kaviju.accesscontrol.utils.*;
import com.webobjects.eocontrol.EOEditingContext;

import er.extensions.*;
import er.extensions.eof.ERXEC;

public class KAAccessControlPrincipal extends ERXFrameworkPrincipal {
	@SuppressWarnings("rawtypes")
    public final static Class[] REQUIRES = new Class[] { ERXExtensions.class };

	static {
		setUpFrameworkPrincipalClass(KAAccessControlPrincipal.class);
	}


	@Override
	public void finishInitialization() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void didFinishInitialization() {
		EOEditingContext ec = ERXEC.newEditingContext();
		ec.lock();
		try {
        	RolesFileLoader.loadRolesFile(ec);
			ec.saveChanges();
			
			new ListItemAutoUpdater(ec);
			ec.saveChanges();
		}
		finally {
			ec.unlock();
		}
	}
	
}