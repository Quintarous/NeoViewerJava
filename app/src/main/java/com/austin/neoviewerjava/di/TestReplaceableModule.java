package com.austin.neoviewerjava.di;

import android.content.Context;

import androidx.room.Room;

import com.austin.neoviewerjava.database.NeoDatabase;
import com.austin.neoviewerjava.network.NeoService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
@InstallIn(SingletonComponent.class)
public class TestReplaceableModule {

    @Singleton
    @Provides
    public static NeoService provideNeoService() {
        return new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(NeoService.BASE_URL)
                .build()
                .create(NeoService.class);
    }

    @Singleton
    @Provides
    public static NeoDatabase provideNeoDatabase(@ApplicationContext Context context) {
        // TODO change this to a real database when the app is complete
        return Room.inMemoryDatabaseBuilder(
                context,
                NeoDatabase.class
        )
                .fallbackToDestructiveMigration()
                .build();
    }
}
