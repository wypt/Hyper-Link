package com.darkmagician6.eventapi.types

/**
 * The priority for the dispatcher to determine what method should be invoked first.
 * Ram was talking about the memory usage of the way I store the data so I decided
 * to just use bytes for the priority because they take up only 8 bits of memory
 * per value compared to the 32 bits per value of an enum (Same as an integer).
 *
 * @author DarkMagician6
 * @since August 3, 2013
 */
object Priority {
    const val
            /**
             * Highest priority, called first.
             */
            HIGHEST: Byte = 0

    /**
     * High priority, called after the highest priority.
     */
    const val HIGH: Byte = 1

    /**
     * Medium priority, called after the high priority.
     */
    const val MEDIUM: Byte = 2

    /**
     * Low priority, called after the medium priority.
     */
    const val LOW: Byte = 3

    /**
     * Lowest priority, called after all the other priorities.
     */
    const val LOWEST: Byte = 4

    /**
     * Array containing all the prioriy values.
     */
    @JvmField
    val VALUE_ARRAY: ByteArray

    /**
     * Sets up the VALUE_ARRAY the first time anything in this class is called.
     */
    init {
        VALUE_ARRAY = byteArrayOf(
                HIGHEST,
                HIGH,
                MEDIUM,
                LOW,
                LOWEST
        )
    }
}
