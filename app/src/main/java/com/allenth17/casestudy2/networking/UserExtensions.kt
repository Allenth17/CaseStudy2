package com.allenth17.casestudy2.networking

/**
 * DummyJSON icon username format:
 * first name in lowercase + first letter of last name in lowercase.
 * e.g., Emily Johnson -> emilyj
 */
fun User.iconUsername(): String {
    val first = firstName.orEmpty().trim()
    val last = lastName.orEmpty().trim()
    val prefix = first.lowercase()
    val suffix = last.firstOrNull()?.lowercaseChar()?.toString().orEmpty()
    return (prefix + suffix)
}