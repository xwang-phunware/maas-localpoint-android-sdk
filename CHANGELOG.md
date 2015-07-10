Localpoint Android Library - Change Logs
==========

Version 2.6.3.1 *(07/10/2015)*
----------------------------
* As device registration response payload won't return inside geofences and messages any more. SDK does the calculation to check if it's necessary to send the geofence entry event after device registration responds.
* Fix the issue that the notifications without promotion can't be all displayed when they are received at the same time.
* Download geofence sometimes doesn’t work when you move enough long distance.

Version 2.6.3 *(05/01/2015)*
----------------------------
* Change location update method to use significant location update instead of standard location update.
* Change the geofence monitoring algorithm to remove the secondary monitoring regions.
* Change the LP server request payload add add some nodes like inside, entries and exit.
* Move the geofence breach algorithm from server side to client side.

Version 2.6.2 *(11/21/2014)*
----------------------------
* Bug fixes

Version 2.6.1 *(10/03/2014)*
----------------------------
* Bug fixes

Version 2.6.0 *(09/18/2014)*
----------------------------
* Improved battery usage & geofencing accuracy by using Fused location provider, Geofencing APIs and Activity recognition
* Added ability to customize location notifications programmatically. Customizations include overriding the local notification title and message body or supressing the local notification entirely.

