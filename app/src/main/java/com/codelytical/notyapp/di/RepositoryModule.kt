package com.codelytical.notyapp.di

import com.codelytical.core.repository.NotyNoteRepository
import com.codelytical.core.repository.NotyUserRepository
import com.codelytical.data.local.repository.NotyLocalNoteRepository
import com.codelytical.data.local.repository.NotyLocalUserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    fun notyLocalUserRepository(notyAuthRepository: NotyLocalUserRepository): NotyUserRepository

    @Binds
    @LocalRepository
    fun notyLocalNoteRepository(localRepository: NotyLocalNoteRepository): NotyNoteRepository

}

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class LocalRepository

