KAAccessControl
======

The KAAccessControl framework .

**Version**: 1.1.0

Requirements
------------

* WebObjects 5
* [Project Wonder](http://wiki.wocommunity.org/display/documentation/Home)
* The Ajax Framework for the provided access right editor components.

Features
--------

**Handle very complex access control problems**: the design support needs of large organizations with complexe structures like:

* **Data segmentation**: each role can be linked to a list used to specify it's access. For example, a saleman is usually assigned to some sales territories, a wharehouse manager to a wharehouse and a regional manager to a region.
* **Support of multiple profiles per user**: A single user requiring differents non compatible access to the system (a sales manager also wharehouse manager for example) can have multiple profiles and switch between then during a single session. 
* **Easy to setup**: add the framework to a project, create the real user entity with KAUser as parent entity and activate migration.
* **Easy to use**: add annotations to your components class to specify access right and override the 

### New in version 1.1
**KAUserProfile** is now abstract with a default implementation. This allows the use of a custom entity with new attributes and relationships. This is useful for multi tenants systems to link the user to it's organization especially if a user may have right to multiple organization.

Documentation
-------------

WOWODC 2014 présentation [Video] (https://itunes.apple.com/podcast/webobjects-podcasts/id270165303?mt=2) and [Slides](http://fr.slideshare.net/wocommunity/ka-access-control)

More to come.

