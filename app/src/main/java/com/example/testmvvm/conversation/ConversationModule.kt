package com.example.testmvvm.conversation

import android.content.Context
import com.example.testmvvm.data.DataRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ConversationModule {

    @Singleton
    @Provides
    fun provideConversationRepository(@ApplicationContext context: Context) = ConversationRepository(context)
}