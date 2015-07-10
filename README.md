# trackmytrain

[![Build Status](https://travis-ci.org/rossbeazley/trackmytrain.svg?branch=master)](https://travis-ci.org/rossbeazley/trackmytrain)


Another experiment, pushing the boundaries on ports and adapters. The network is the port, not the repo as i may have done in the past.




Todo list:

- Wearable can send broadcast message to "find home"
- Mobile can send a "I am base" message for wearables so then can register node id
- Mobile can respond with to "find home" with "i am base"
2nd deliverable Wearable can send a attach service tracking ui message.
2nd deliverable Mobile will store the node id of the wearable that has attached ui
2nd deliverable Wearable can send a detach service tracking ui message.

Mobile can send serialised train view models

+ Mobile can send a tracking started broadcast.
+ Wearable will show UI in response to tracking started.
+ Wearable will hide UI in response to tracking finished.

Very next task is the update view in response to service details update


First deliverable:

host will need to send presentation messages as broadcast -- SERVICE_VIEW_PRESENT
wear activity is always on
activity finishes self when tracking stops


Second deliverable:
point to point comms