package util

import org.koin.java.KoinJavaComponent

inline fun <reified T : Any> inject(): Lazy<T> = KoinJavaComponent.inject(T::class.java)
inline fun <reified T : Any> get(): T = KoinJavaComponent.get(T::class.java)