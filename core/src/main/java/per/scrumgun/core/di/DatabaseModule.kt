package per.scrumgun.core.di

import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val databaseModule = module {
    single { DatabaseBuilder(androidApplication()).buildRoomDatabaseStorage() }

    single { DaoBuilder(get()).getUserDao() }
    single { DaoBuilder(get()).getMessageDao() }
    single { DaoBuilder(get()).getFriendDao() }
    single { DaoBuilder(get()).getChatDao() }
    single { DaoBuilder(get()).getThemeDao() }
}
