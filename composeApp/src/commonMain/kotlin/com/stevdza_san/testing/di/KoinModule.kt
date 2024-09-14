package com.stevdza_san.testing.di

import com.stevdza_san.testing.data.MongoDB
import com.stevdza_san.testing.domain.MongoRepository
import org.koin.core.context.startKoin
import org.koin.dsl.module
import com.stevdza_san.testing.presentation.screen.home.HomeViewModel
import com.stevdza_san.testing.presentation.screen.task.TaskViewModel

val mongoModule = module {
    single<MongoRepository> { MongoDB() }
    factory { HomeViewModel(mongoDB = get()) }
    factory { TaskViewModel(mongoDB = get()) }
}

fun initializeKoin() {
    startKoin {
        modules(mongoModule)
    }
}