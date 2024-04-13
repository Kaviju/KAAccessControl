package com.kaviju.accesscontrol.annotation;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface AutoSyncWithAccessList {
	public String listCode();
	public String nameProperty();
}
