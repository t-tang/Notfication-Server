Real time notifications
=======================

This POC demonstrates an Html5-Ember client for real-time enterprise class applications. The server publishes real time notifications to the client using SSE (i.e. server side push).

A notification contains actions which are rendered as buttons, when a button is clicked, a REST call is triggered to perform the actual action, finally the results of the action are broadcast to all clients.

It is envisaged that notifications would form part of a semi-automated workflow. The workflow would pop up a notification whenever user interaction is needed.

This project is a work in progress.

__Technical Notes__


Optimistic concurrency control is used, objects are automatically assigned a revision id when they are checked in. Race conditions are identified by comparing the revision ids.

The in memory store is a concurrent hash map, this can be easily swapped out for any no-sql database which supports putIfAbsent and replace operations.

The project adopts the Jersey 2 framework for REST services. A limitation of Jersey 2 is that Guice JIT isn't enabled for injection into Jersey components, an injector adapter was created to overcome this limitation and has been submitted for possible inclusion in a future release.

The project demonstrates using Guice's eager singleton to bootstrap the application with test data.
