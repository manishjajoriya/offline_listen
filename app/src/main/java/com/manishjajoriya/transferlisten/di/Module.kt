package com.manishjajoriya.transferlisten.di

import android.app.Application
import com.ketch.DownloadConfig
import com.ketch.Ketch
import com.ketch.NotificationConfig
import com.manishjajoriya.transferlisten.R
import com.manishjajoriya.transferlisten.data.local.FileDownloader
import com.manishjajoriya.transferlisten.data.remote.MusicApi
import com.manishjajoriya.transferlisten.domain.usecase.api.MusicApiUseCase
import com.manishjajoriya.transferlisten.domain.usecase.api.SearchUseCase
import com.manishjajoriya.transferlisten.domain.usecase.api.StreamUseCase
import com.manishjajoriya.transferlisten.domain.usecase.local.DownloadSongUseCase
import com.manishjajoriya.transferlisten.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object Module {

  @Provides
  @Singleton
  fun provideMusicApiInstance(): MusicApi =
      Retrofit.Builder()
          .baseUrl(Constants.BASE_URL)
          .addConverterFactory(GsonConverterFactory.create())
          .build()
          .create(MusicApi::class.java)

  @Provides
  @Singleton
  fun provideMusicApiUseCase(musicApi: MusicApi) =
      MusicApiUseCase(
          searchUseCase = SearchUseCase(musicApi),
          streamUseCase = StreamUseCase(musicApi),
      )

  @Provides
  @Singleton
  fun provideKetch(application: Application) =
      Ketch.builder()
          .setNotificationConfig(
              NotificationConfig(enabled = true, smallIcon = R.drawable.ic_launcher_foreground)
          )
          .setDownloadConfig(DownloadConfig(connectTimeOutInMs = 15000, readTimeOutInMs = 15000))
          .build(application)

  @Provides
  @Singleton
  fun provideFileDownloader(application: Application, ketch: Ketch) =
      FileDownloader(application, ketch)

  @Provides
  @Singleton
  fun provideDownloadSongUseCase(fileDownloader: FileDownloader) =
      DownloadSongUseCase(fileDownloader)
}
