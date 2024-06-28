package com.codelytical.notyapp.di

import android.app.Application
import androidx.work.WorkManager
import com.codelytical.core.preference.PreferenceManager
import com.codelytical.core.session.SessionManager
import com.codelytical.notyapp.preference.PreferenceManagerImpl
import com.codelytical.notyapp.preference.uiModePrefDataStore
import com.codelytical.notyapp.session.NotySharedPreferencesFactory
import com.codelytical.notyapp.session.SessionManagerImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.internal.Provider
import javax.inject.Singleton

fun <T> singletonProvider(provide: () -> T): Provider<T> = object : Provider<T> {
    private val instance: T by lazy { provide() }
    override fun get(): T = instance
}

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun providePreferenceManager(application: Application): PreferenceManager {
        return singletonProvider {
            PreferenceManagerImpl(application.uiModePrefDataStore)
        }.get()
    }

    @Singleton
    @Provides
    fun provideSessionManager(application: Application): SessionManager {
        return singletonProvider {
            SessionManagerImpl(NotySharedPreferencesFactory.sessionPreferences(application))
        }.get()
    }

    @Singleton
    @Provides
    fun provideWorkManager(application: Application): WorkManager {
        return singletonProvider {
            WorkManager.getInstance(application)
        }.get()
    }
}

