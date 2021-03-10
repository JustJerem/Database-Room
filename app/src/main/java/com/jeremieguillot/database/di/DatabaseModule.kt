package com.jeremieguillot.database.di

import android.content.Context
import androidx.room.Room
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.jeremieguillot.database.data.FirebaseShoppingRepository
import com.jeremieguillot.database.data.local.AppDatabase
import com.jeremieguillot.database.data.local.ShoppingItemDao
import com.jeremieguillot.database.domain.repository.ShoppingRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Singleton


@ExperimentalCoroutinesApi
@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideLocalDatabase(@ApplicationContext applicationContext: Context): AppDatabase {
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

    //Uncomment for local usage
/*    @Singleton
    @Provides
    fun provideLocalRepository(
        shoppingItemDao: ShoppingItemDao
    ): ShoppingRepository {
        return LocalShoppingRepository(shoppingItemDao)
    }*/

    //Uncomment for online usage
    @Singleton
    @Provides
    fun provideFirebaseRepository(
        ref: DatabaseReference
    ): ShoppingRepository {
        return FirebaseShoppingRepository(ref)
    }

    @Singleton
    @Provides
    fun generateShoppingDatabase(): DatabaseReference {
        val databaseName = "SHOPPING_ITEMS"
        val database = FirebaseDatabase.getInstance()
        return database.getReference(databaseName)
    }
}

