package br.com.dias.andre

import org.koin.dsl.module
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.reactivestreams.KMongo

val login: String = System.getProperty("LOGIN_MONGO_ENV")
val password: String = System.getProperty("PASSWORD_MONGO_ENV")
val url: String = System.getProperty("URL_MONGO_ENV")

val applicationModule = module {
    single { KMongo.createClient("mongodb+srv://$login:$password@$url.mongodb.net").coroutine.getDatabase("kanban") }
}


