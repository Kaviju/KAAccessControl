package com.kaviju.accesscontrol.annotation;

import java.lang.annotation.*;

import com.webobjects.appserver.WOComponent;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface SameAccessAsComponent {
	Class<? extends WOComponent> value();
}
