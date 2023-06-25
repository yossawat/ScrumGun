package per.scrumgun

import android.app.Application
import com.facebook.drawee.backends.pipeline.Fresco
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import per.scrumgun.core.di.CoreComponent
import per.scrumgun.data.di.DataComponent
import per.scrumgun.di.AppComponent
import per.scrumgun.domain.di.DomainComponent
import per.scrumgun.scrum.di.ScrumComponent
import timber.log.Timber

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MyApplication)
        }

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        Fresco.initialize(this)

        AppComponent.init()
        CoreComponent.init()
        DataComponent.init()
        DomainComponent.init()
        ScrumComponent.init()
    }
}
