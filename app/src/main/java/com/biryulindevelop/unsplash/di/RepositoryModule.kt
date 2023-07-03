package com.biryulindevelop.unsplash.di


import com.biryulindevelop.data.repository.DigestRemoteRepositoryImpl
import com.biryulindevelop.data.repository.LocalRepositoryImpl
import com.biryulindevelop.data.repository.PhotoRemoteRepositoryImpl
import com.biryulindevelop.data.repository.PhotosPagingSourceRepositoryImpl
import com.biryulindevelop.data.repository.ProfileRemoteRepositoryImpl
import com.biryulindevelop.domain.repository.DigestRemoteRepository
import com.biryulindevelop.domain.repository.LocalRepository
import com.biryulindevelop.domain.repository.PhotoRemoteRepository
import com.biryulindevelop.domain.repository.PhotosPagingSourceRepository
import com.biryulindevelop.domain.repository.ProfileRemoteRepository
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