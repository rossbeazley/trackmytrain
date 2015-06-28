# trackmytrain

[![Build Status](https://travis-ci.org/rossbeazley/trackmytrain.svg?branch=master)](https://travis-ci.org/rossbeazley/trackmytrain)


Another experiment, pushing the boundaries on ports and adapters. The network is the port, not the repo as i may have done in the past.




Todo list:

Wearable can send broadcast message to "find home"
Mobile can send a "I am base" message for wearables so then can register node id
Mobile can respond with to "find home" with "i am base"
Wearable can send a attach service tracking ui message.
Mobile will store the node id of the wearable that has attached ui
Wearable can send a detach service tracking ui message.

Mobile can send serialised train view models

Mobile can send a tracking started broadcast.
Wearable will show UI in response to tracking started.
Wearable will hide UI in response to tracking finished.



First deliverable:
host will need to broadcast id -- I_AM_BASE_MESSAGE
host can broadcast this when tracking starts

manually start activity, it will attach view to app
attached presenter sends message to host -- ATTACH_SERVICE_TRACKING
host will need to register node


host will need to send presentation messages to registered node -- SERVICE_VIEW_PRESENT
wear activity is always on






Second deliverable:
activity will deregister observer when closed
activity will auto start when tracking starts
activity will close when tracking stops
