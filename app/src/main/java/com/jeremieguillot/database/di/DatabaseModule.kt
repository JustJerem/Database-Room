package com.jeremieguillot.database.di

import android.content.Context
import androidx.room.Room
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.jeremieguillot.database.data.local.AppDatabase
import com.jeremieguillot.database.data.local.ShoppingItemDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext applicationContext: Context): AppDatabase {
        return Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "database-name"
        ).build()
    }

    @Singleton
    @Provides
    fun provideShoppingDao(appDatabase: AppDatabase): ShoppingItemDao {
        return appDatabase.shoppingItemDao()
    }

    @Singleton
    @Provides
    fun provideFirebaseShoppingDatabase(): DatabaseReference {
        val databaseName = "SHOPPING_ITEMS"
        val database = FirebaseDatabase.getInstance()
        return database.getReference(databaseName)
    }
}

