package br.com.fiap.hodestito.pokermao.view.di

import android.content.Context
import br.com.fiap.hodestito.pokermao.view.api.AuthInterceptor
import br.com.fiap.hodestito.pokermao.view.api.PokemonService
import br.com.fiap.hodestito.pokermao.view.detail.DetailViewModel
import br.com.fiap.hodestito.pokermao.view.form.FormPokemonViewModel
import br.com.fiap.hodestito.pokermao.view.list.ListPokemonsViewModel
import br.com.fiap.hodestito.pokermao.view.repository.PokemonRepository
import br.com.fiap.hodestito.pokermao.view.repository.PokemonRepositoryImpl
import br.com.fiap.hodestito.pokermao.view.splash.SplashViewModel
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.squareup.picasso.OkHttp3Downloader
import com.squareup.picasso.Picasso
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class Modules {

    private fun createNetworkClient(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl("https://pokedexdx.herokuapp.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun createOkhttpClientAuth(authInterceptor: Interceptor): OkHttpClient {
        val builder = OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .addNetworkInterceptor(StethoInterceptor())
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
        return builder.build()
    }

    val viewModelModule = module {
        viewModel { SplashViewModel(get()) }
        viewModel { ListPokemonsViewModel(get()) }
        viewModel { FormPokemonViewModel(get()) }
        viewModel { DetailViewModel(get()) }
    }
    val repositoryModule = module {
        single<PokemonRepository> { PokemonRepositoryImpl(get()) }
    }
    val networkModule = module {
        single<Interceptor> { AuthInterceptor() }
        single { createNetworkClient(get()).create(PokemonService::class.java) }
        single { createOkhttpClientAuth(get()) }
        single { createPicassoAuth(get(), get()) }
    }

    private fun createPicassoAuth(context: Context, okHttpClient: OkHttpClient): Picasso {
        return Picasso
            .Builder(context)
            .downloader(OkHttp3Downloader(okHttpClient))
            .build()
    }

}