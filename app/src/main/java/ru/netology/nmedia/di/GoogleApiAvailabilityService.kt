package ru.netology.nmedia.di

import android.app.Activity
import android.app.Dialog
import android.content.Context
import com.google.android.gms.common.GoogleApiAvailability
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Singleton


interface GoogleApiAvailabilityService {
    fun isGooglePlayServicesAvailable(context: Context): Int
    fun isUserResolvableError(errorCode: Int): Boolean
    fun getErrorDialog(activity: Activity, errorCode: Int, requestCode: Int): Dialog?

}

class GoogleApiAvailabilityServiceImpl: GoogleApiAvailabilityService {
    override fun isGooglePlayServicesAvailable(context: Context): Int =
        GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(context)

    override fun isUserResolvableError(errorCode: Int): Boolean =
        GoogleApiAvailability.getInstance().isUserResolvableError(errorCode)

    override fun getErrorDialog(activity: Activity, errorCode: Int, requestCode: Int): Dialog? =
        GoogleApiAvailability.getInstance().getErrorDialog(activity, errorCode, requestCode)

}

@Module
@InstallIn(SingletonComponent::class)
object GoogleApiAvailabilityModule {
    @Provides
    @Singleton
    fun provideGoogleApiAvailabilityService(): GoogleApiAvailabilityService = GoogleApiAvailabilityServiceImpl()
}