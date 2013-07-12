KAAccessControl
======

The KAAccessControl framework .

**Version**: 1.0.0

Requirements
------------

* [KATestJars](http://www.github.com/)
* [Project Wonder](http://wiki.wocommunity.org/display/documentation/Home)
* WebObjects 5

Features
--------

**Handle very complex access control problems**: the design support needs of large organizations with complexe structures like:
* **Data segmentation**: each role can be linked to a list used to specify it's access. For example, a saleman is usually assigned to some sales territories, a wharehouse manager to a wharehouse and a regional manager to a region.
* **Support of multiple profiles per user**: A single user requiring differents non compatible access to the system (a sales manager also wharehouse manager for example) can have multiple profiles and switch between then during a single session. 
* **Easy to setup**: add the framework to a project, create the real user entity with KAUser as parent entity and activate migration.
* **Easy to use**: add annotations to your components class to specify access right and override the 
The rules are responsible for loading eomodels, initializing and cleaning up before/after
test executions.

Installation
------------

Maven users have to add the dependency declaration:

	<dependency>
		<groupId>com.wounit</groupId>
		<artifactId>wounit</artifactId>
		<version>1.2.2</version>
	</dependency>

Non Maven users have to:

1. Download the wounit.jar.
2. Add the wounit library to the build path.

Usage
-----

	import static com.wounit.matchers.EOAssert.*;
	import com.wounit.rules.MockEditingContext;
	import com.wounit.annotations.Dummy;
    import com.wounit.annotations.UnderTest;

	public class MyEntityTest {
		@Rule
		public MockEditingContext ec = new MockEditingContext("MyModel");

		@Dummy
		private Bar dummyBar;

		@UnderTest
		private Foo foo;

		@Test
		public void cantSaveFooWithOnlyOneBar() {
			foo.addToBarRelationship(dummyBar);

			confirm(foo, cannotBeSavedBecause("Foo must have at least 2 bars related to it"));
		}
	}

OR

	import static com.wounit.matchers.EOAssert.*;
	import com.wounit.rules.TemporaryEditingContext;
	import com.wounit.annotations.UnderTest;

	public class MyEntityTest {
		@Rule
		public TemporaryEditingContext ec = new TemporaryEditingContext("MyModel");

		@UnderTest
		private Foo foo;

		@Test
		public void cannotSaveFooIfBarIsNull() {
			foo.setBar(null);

			confirm(foo, cannotBeSavedBecause("The bar property cannot be null"));
		}
	}

Acknowledge
-----------

This project is an evolution of the original [WOUnitTest 2](http://wounittest.sourceforge.net/)
framework and is heavily inspired by it.

About
-----

* **Site**: http://hprange.github.com/wounit
* **E-mail**: hprange at gmail.com
* **Twitter**: @hprange