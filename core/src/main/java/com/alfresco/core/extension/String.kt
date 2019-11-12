package com.alfresco.core.extension

/**
 * Created by Bogdan Roatis on 10/2/2019.
 */
inline fun CharSequence.isNotBlankNorEmpty(): Boolean = isNotBlank() && isNotEmpty()

inline fun CharSequence.isBlankOrEmpty(): Boolean = isBlank() || isEmpty()
