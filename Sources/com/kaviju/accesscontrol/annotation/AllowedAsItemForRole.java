package com.kaviju.accesscontrol.annotation;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface AllowedAsItemForRole {
	public String roleCode();
	public String itemName();
}
