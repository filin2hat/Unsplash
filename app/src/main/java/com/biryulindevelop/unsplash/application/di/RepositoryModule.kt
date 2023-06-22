package com.biryulindevelop.unsplash.application.di

import com.biryulindevelop.unsplash.data.repository.DigestRemoteRepositoryImpl
import com.biryulindevelop.unsplash.data.repository.LocalRepositoryImpl
import com.biryulindevelop.unsplash.data.repository.PhotoRemoteRepositoryImpl
import com.biryulindevelop.unsplash.data.repository.PhotosPagingSourceRepositoryImpl
import com.biryulindevelop.unsplash.data.repository.ProfileRemoteRepositoryImpl
import com.biryulindevelop.unsplash.domain.repository.DigestRemoteRepository
import com.biryulindevelop.unsplash.domain.repository.LocalRepository
import com.biryulindevelop.unsplash.domain.repository.PhotoRemoteRepository
import com.biryulindevelop.unsplash.domain.repository.PhotosPagingSourceRepository
import com.biryulindevelop.unsplash.domain.repository.ProfileRemoteRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun bindPhotoRemoteRepository(
        photoRemoteRepository: PhotoRemoteRepositoryImpl
    ): PhotoRemoteRepository

    @Singleton
    @Binds
    abstract fun bindLocalRepository(
        localRepository: LocalRepositoryImpl
    ): LocalRepository

    @Singleton
    @Binds
    abstract fun bindDigestRemoteRepository(
        digestRemoteRepository: DigestRemoteRepositoryImpl
    ): DigestRemoteRepository

    @Singleton
    @Binds
    abstract fun bindProfileRemoteRepository(
        profileRemoteRepository: ProfileRemoteRepositoryImpl
    ): ProfileRemoteRepository

    @Singleton
    @Binds
    abstract fun bindPhotosPagingSourceRepository(
        photosPagingSourceRepository: PhotosPagingSourceRepositoryImpl
    ): PhotosPagingSourceRepository
}