package com.darkmagician6.eventapi.events

/**
 * The most basic form of an stoppable Event.
 * Stoppable events are called seperate from other events and the calling of methods is stopped
 * as soon as the EventStoppable is stopped.
 *
 * @author DarkMagician6
 * @since 26-9-13
 */
abstract class EventStoppable
/**
 * No need for the constructor to be public.
 */
protected constructor() : Event {
    /**
     * Checks the stopped boolean.
     *
     * @return True if the EventStoppable is stopped.
     */
    var isStopped = false
        private set

    /**
     * Sets the stopped state to true.
     */
    fun stop() {
        isStopped = true
    }
}
