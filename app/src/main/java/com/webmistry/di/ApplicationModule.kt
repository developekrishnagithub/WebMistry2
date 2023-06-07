package com.webmistry.di

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object ApplicationModule {

//    @Provides
//    @Singleton
//    fun provideDatabase(
//        @ApplicationContext context: Context
//    ) = Room.databaseBuilder(
//        context, WebTabDatabase::class.java, "web_tab_DB"
//    ).allowMainThreadQueries()
//        .fallbackToDestructiveMigration().build()
//
//
//    @Provides
//    @Singleton
//    fun provideDao(webTabDatabase: WebTabDatabase) = webTabDatabase.getDao()
}