package br.com.dias.andre

import dao.ColumnDao
import dao.ItemDao
import dao.UserDao
import org.koin.dsl.module
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.reactivestreams.KMongo
import workers.column.ColumnWorker
import workers.item.ItemWorker
import workers.kanban.KanbanWorker
import workers.user.UserWorker

val login: String = System.getProperty("LOGIN_MONGO_ENV")
val password: String = System.getProperty("PASSWORD_MONGO_ENV")
val url: String = System.getProperty("URL_MONGO_ENV")

val applicationModule = module {
    single { KMongo.createClient("mongodb+srv://$login:$password@$url.mongodb.net").coroutine.getDatabase("kanban") }

    single { UserDao() }
    single { ColumnDao() }
    single { ItemDao() }

    factory { UserWorker() }
    factory { ColumnWorker() }
    factory { KanbanWorker() }
    factory { ItemWorker() }
}


