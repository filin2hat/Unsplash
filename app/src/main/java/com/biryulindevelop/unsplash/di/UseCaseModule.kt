package com.biryulindevelop.unsplash.di

import com.biryulindevelop.data.usecase.DigestPagingUseCaseImpl
import com.biryulindevelop.data.usecase.GetDigestInfoUseCaseImpl
import com.biryulindevelop.data.usecase.GetProfileUseCaseImpl
import com.biryulindevelop.data.usecase.LikeDetailPhotoUseCaseImpl
import com.biryulindevelop.data.usecase.OnePhotoDetailsUseCaseImpl
import com.biryulindevelop.data.usecase.PhotoLikeUseCaseImpl
import com.biryulindevelop.data.usecase.PhotosPagingUseCaseImpl
import com.biryulindevelop.domain.usecase.DigestPagingUseCase
import com.biryulindevelop.domain.usecase.GetDigestInfoUseCase
import com.biryulindevelop.domain.usecase.GetProfileUseCase
import com.biryulindevelop.domain.usecase.LikeDetailPhotoUseCase
import com.biryulindevelop.domain.usecase.OnePhotoDetailsUseCase
import com.biryulindevelop.domain.usecase.PhotoLikeUseCase
import com.biryulindevelop.domain.usecase.PhotosPagingUseCase
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
