package ru.netology.nmedia.di

import com.google.android.gms.tasks.Task
import com.google.firebase.messaging.FirebaseMessaging
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Singleton

interface FirebaseMessagingService {
    fun getToken(): Task<String>
}

class FirebaseMessagingServiceImpl : FirebaseMessagingService {
    override fun getToken(): Task<String> = FirebaseMessaging.getInstance().token
}

@Module
@InstallIn(SingletonComponent::class)
object FirebaseMessagingModule {
    @Provides
    @Singleton
    fun provideFirebaseMessagingService(): FirebaseMessagingService = FirebaseMessagingServiceImpl()
}