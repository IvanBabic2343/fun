package test.app.funapp

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.component.KoinComponent
import org.koin.core.context.GlobalContext.startKoin
import test.app.data.di.dataModule
import test.app.domain.di.domainModule
import test.app.presentation.di.presentationModule
import timber.log.Timber

class App : Application(), KoinComponent {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(listOf(dataModule, domainModule, presentationModule))
        }
        initTimber()
    }

    private fun initTimber() {
            Timber.plant(Timber.DebugTree())
    }
}
