Localpoint Android Library - Change Logs
==========

Version 2.5.4 *(Release soon)*
----------------------------
* Used Geofencing API instead of Proximity Alert by default, only use Proximity Alert when Network location is disabled
* Added Activity Recognition to algorithm, use different interval and location provider based on current activity, distance and radius of nearest geofence
* Added "Update Monitor Region", which is half search radius geofence, triggers download geofence when device exit it
* Added alarm service to wakeup SDK to trigger download geofence is the device didn't call home more than a week
* Added ILPLocalNotificationListener, allows customize local notification programmatically.
