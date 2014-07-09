Real time notifications
=======================

This POC demonstrates an Html5-Ember client for real-time enterprise class applications. The server publishes real time notifications to an Html5-Ember client using SSE (server side push).

A notification contains actions which are rendered as buttons, when a button is clicked, a REST call is triggered to perform the actual action. 

It is envisaged that notifications would form part of a semi-automated workflow. The workflow would pop up a notification whenever user interaction is needed.

__Notes__

This project is a work in progress.

_Optimistic Concurrency Control_
