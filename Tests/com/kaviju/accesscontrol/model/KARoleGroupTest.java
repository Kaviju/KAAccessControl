package com.kaviju.accesscontrol.model;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.junit.*;

import com.wounit.annotations.UnderTest;
import com.wounit.rules.MockEditingContext;

public class KARoleGroupTest {
	@Rule public MockEditingContext ec = new MockEditingContext("KAAccessControl");

	@UnderTest private KARoleGroup groupUnderTest;
	
	@Test
	public void localizedNameReturnString() {
		assertThat(groupUnderTest.localizedName(), notNullValue());
	}
}
