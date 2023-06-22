package com.biryulindevelop.unsplash.di

import com.biryulindevelop.unsplash.domain.usecase.implimentations.DigestPagingUseCaseImpl
import com.biryulindevelop.unsplash.domain.usecase.implimentations.GetDigestInfoUseCaseImpl
import com.biryulindevelop.unsplash.domain.usecase.implimentations.GetProfileUseCaseImpl
import com.biryulindevelop.unsplash.domain.usecase.implimentations.LikeDetailPhotoUseCaseImpl
import com.biryulindevelop.unsplash.domain.usecase.implimentations.OnePhotoDetailsUseCaseImpl
import com.biryulindevelop.unsplash.domain.usecase.implimentations.PhotoLikeUseCaseImpl
import com.biryulindevelop.unsplash.domain.usecase.implimentations.PhotosPagingUseCaseImpl
import com.biryulindevelop.unsplash.domain.usecase.interfaceces.DigestPagingUseCase
import com.biryulindevelop.unsplash.domain.usecase.interfaceces.GetDigestInfoUseCase
import com.biryulindevelop.unsplash.domain.usecase.interfaceces.GetProfileUseCase
import com.biryulindevelop.unsplash.domain.usecase.interfaceces.LikeDetailPhotoUseCase
import com.biryulindevelop.unsplash.domain.usecase.interfaceces.OnePhotoDetailsUseCase
import com.biryulindevelop.unsplash.domain.usecase.interfaceces.PhotoLikeUseCase
import com.biryulindevelop.unsplash.domain.usecase.interfaceces.PhotosPagingUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class UseCaseModule {

    @Singleton
    @Binds
    abstract fun bindDigestPagingUseCase(
        digestPagingUseCase: DigestPagingUseCaseImpl
    ): DigestPagingUseCase

    @Singleton
    @Binds
    abstract fun bindGetDigestInfoUseCase(
        getDigestInfoUseCase: GetDigestInfoUseCaseImpl
    ): GetDigestInfoUseCase

    @Singleton
    @Binds
    abstract fun bindGetProfileUseCase(
        getProfileUseCase: GetProfileUseCaseImpl
    ): GetProfileUseCase

    @Singleton
    @Binds
    abstract fun bindLikeDetailPhotoUseCase(
        likeDetailPhotoUseCase: LikeDetailPhotoUseCaseImpl
    ): LikeDetailPhotoUseCase

    @Singleton
    @Binds
    abstract fun bindOnePhotoDetailsUseCase(
        onePhotoDetailsUseCase: OnePhotoDetailsUseCaseImpl
    ): OnePhotoDetailsUseCase

    @Singleton
    @Binds
    abstract fun bindPhotoLikeUseCase(
        photoLikeUseCase: PhotoLikeUseCaseImpl
    ): PhotoLikeUseCase

    @Singleton
    @Binds
    abstract fun bindPhotosPagingUseCase(
        photosPagingUseCase: PhotosPagingUseCaseImpl
    ): PhotosPagingUseCase


}