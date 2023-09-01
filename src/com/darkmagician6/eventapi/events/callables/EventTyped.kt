package com.darkmagician6.eventapi.events.callables

import com.darkmagician6.eventapi.events.Event
import com.darkmagician6.eventapi.events.Typed

/**
 * Abstract example implementation of the Typed interface.
 *
 * @author DarkMagician6
 * @since August 27, 2013
 */
abstract class EventTyped
/**
 * Sets the type of the event when it's called.
 *
 * @param eventType The type ID of the event.
 */ protected constructor(override val type: Byte) : Event, Typed
