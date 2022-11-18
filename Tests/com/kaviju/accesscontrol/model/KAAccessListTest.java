package com.kaviju.accesscontrol.model;

import static org.junit.Assert.*;

import org.junit.*;

import com.webobjects.foundation.NSArray;
import com.wounit.annotations.*;
import com.wounit.rules.MockEditingContext;

public class KAAccessListTest {
	static private final String territory1Code = "T1";
	static private final String territory1Name = "T1Name";
	static private final String territory2Code = "T2";
	static private final String territory3Code = "T3";
	static private final String territory3Name = "T3Name";
	static private final String tooLongName = "1234567890123456789012345678901234567890é1234567890";
	static private final String truncatedName = "1234567890123456789012345678901234567890é12345678";

	@Rule
    public MockEditingContext ec = new MockEditingContext("KAAccessControl");

	@Dummy private KAAccessListItem testTerritory1;
	@Dummy private KAAccessListItem testTerritory2;

	@UnderTest private KAAccessList listUnderTest;
	
	@Before
	public void createDummies() {
		testTerritory1.setCode(territory1Code);
		listUnderTest.addToItems(testTerritory1);
		testTerritory2.setCode(territory2Code);
		listUnderTest.addToItems(testTerritory2);
	}
	
	@Test
	public void itemWithCodeReturnsTheItemWithSpecifiedCode() {		
		assertEquals(testTerritory1, listUnderTest.itemWithCode(territory1Code));
	}

	@Test
	public void itemWithCodeReturnsNullIfItemWithSpecifiedCodeDoesIsNotInList() {		
		assertNull(listUnderTest.itemWithCode(territory3Code));
	}

	@Test
	public void deleteItemWithCode() {
		listUnderTest.deleteItemWithCode(territory1Code);
		
		assertFalse(listUnderTest.items().containsObject(testTerritory1));
		assertTrue(testTerritory1.isDeletedEO());
	}

	@Test
	public void insertItemWithCodeCreateNewItemIfItemDoesNotExists() {
		listUnderTest.insertItemWithCodeAndName(territory3Code, territory3Name);
		
		NSArray<KAAccessListItem> foundItems = listUnderTest.items(KAAccessListItem.CODE.eq(territory3Code));
		assertEquals(1, foundItems.count());
		assertEquals(territory3Name, listUnderTest.itemWithCode(territory3Code).name());
		assertTrue(foundItems.objectAtIndex(0).isNewObject());
	}

	@Test
	public void insertItemWithCodeDoesNothingIfItemAlreadyExists() {
		listUnderTest.insertItemWithCodeAndName(territory1Code, territory1Name);

		assertEquals(2, listUnderTest.items().count());
	}

	@Test
	public void insertItemWithCodeTruncateNameIfRequired() {
		listUnderTest.insertItemWithCodeAndName(territory1Code, tooLongName);
		assertEquals(truncatedName, listUnderTest.itemWithCode(territory1Code).name());
	}

}
