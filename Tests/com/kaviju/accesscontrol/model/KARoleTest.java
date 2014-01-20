package com.kaviju.accesscontrol.model;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.junit.*;

import com.wounit.annotations.*;
import com.wounit.rules.MockEditingContext;

public class KARoleTest {
	
	@Rule public MockEditingContext ec = new MockEditingContext("KAAccessControl");

	@Dummy private KARole role;
		
	@Test
	public void localizedNameReturnString() {
		assertThat(role.localizedName(), notNullValue());
	}
}
