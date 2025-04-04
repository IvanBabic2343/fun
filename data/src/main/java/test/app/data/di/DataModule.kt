package test.app.data.di

import okhttp3.OkHttpClient
import org.koin.dsl.module
import test.app.data.api.RetrofitClient
import test.app.data.repository.NetworkRepoImpl
import test.app.domain.repository.NetworkRepo

val dataModule = module {

    single {
        OkHttpClient.Builder().build()
    }

    single { RetrofitClient.createApi() }

    single<NetworkRepo> { NetworkRepoImpl(get()) }
}
