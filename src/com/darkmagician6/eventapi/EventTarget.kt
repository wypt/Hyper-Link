package com.darkmagician6.eventapi

import com.darkmagician6.eventapi.types.Priority

/**
 * Marks a method so that the EventManager knows that it should be registered.
 * The priority of the method is also set with this.
 *
 * @author DarkMagician6
 * @see Priority
 *
 * @since July 30, 2013
 */
@MustBeDocumented
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER)
@Retention(AnnotationRetention.RUNTIME)
annotation class EventTarget(val value: Byte = Priority.MEDIUM)
