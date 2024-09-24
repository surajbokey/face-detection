package com.surajbokey.facedetection.core.di

import com.surajbokey.facedetection.detection.data.FaceDetectionDataSource
import com.surajbokey.facedetection.detection.data.FaceDetectionDataSourceImpl
import com.surajbokey.facedetection.detection.data.FaceDetectionRepositoryImpl
import com.surajbokey.facedetection.detection.domain.FaceDetectionRepository
import com.surajbokey.facedetection.gallery.data.GalleryDataSource
import com.surajbokey.facedetection.gallery.data.GalleryDataSourceImpl
import com.surajbokey.facedetection.gallery.data.GalleryRepositoryImpl
import com.surajbokey.facedetection.gallery.domain.models.GalleryRepository
import com.surajbokey.facedetection.permission.data.PermissionRepositoryImpl
import com.surajbokey.facedetection.permission.domain.PermissionRepository
import com.surajbokey.facedetection.tagging.data.FaceTagRepositoryImpl
import com.surajbokey.facedetection.tagging.domain.FaceTagRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    @Binds
    abstract fun bindPermissionRepository(
        repositoryImpl: PermissionRepositoryImpl
    ): PermissionRepository

    @Binds
    abstract fun bindGalleryDataSource(
        galleryDataSourceImpl: GalleryDataSourceImpl
    ): GalleryDataSource

    @Binds
    abstract fun bindGalleryRepository(
        galleryRepositoryImpl: GalleryRepositoryImpl
    ): GalleryRepository

    @Binds
    abstract fun bindFaceDetectionDataSource(
        faceDetectionDataSourceImpl: FaceDetectionDataSourceImpl
    ): FaceDetectionDataSource

    @Binds
    abstract fun bindFaceDetectionRepository(
        faceDetectionRepositoryImpl: FaceDetectionRepositoryImpl
    ): FaceDetectionRepository

    @Binds
    abstract fun bindFaceTagRepository(
        faceTagRepositoryImpl: FaceTagRepositoryImpl
    ): FaceTagRepository
}