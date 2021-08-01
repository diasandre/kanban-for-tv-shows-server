package util

import org.koin.java.KoinJavaComponent

inline fun <reified T : Any> inject() = KoinJavaComponent.inject(T::class.java)
inline fun <reified T : Any> get() = KoinJavaComponent.get(T::class.java)
