package com.darkmagician6.eventapi.events.callables

import com.darkmagician6.eventapi.events.Cancellable
import com.darkmagician6.eventapi.events.Event

/**
 * Abstract example implementation of the Cancellable interface.
 *
 * @author DarkMagician6
 * @since August 27, 2013
 */
abstract class EventCancellable protected constructor() : Event, Cancellable {
    override var isCancelled = false
}
