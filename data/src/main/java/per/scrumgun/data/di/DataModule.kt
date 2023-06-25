package per.scrumgun.data.di

import org.koin.dsl.module
import per.scrumgun.data.chat.*
import per.scrumgun.data.friend.*
import per.scrumgun.data.message.*
import per.scrumgun.data.theme.*
import per.scrumgun.data.user.*
import per.scrumgun.domain.chat.ChatRepository
import per.scrumgun.domain.friend.FriendRepository
import per.scrumgun.domain.message.MessageRepository
import per.scrumgun.domain.theme.ThemeRepository
import per.scrumgun.domain.user.UserRepository

val dataModule = module {
    single<UserRepository> {
        UserRepositoryImpl(get(), get(), get(), get(), get())
    }
    single<NetworkUserDataSource> {
        NetworkUserDataSourceImpl()
    }
    single<LocalUserDataSource> {
        LocalUserDataSourceImpl(get())
    }

    single<MessageRepository> {
        MessageRepositoryImpl(get(), get())
    }
    single<NetworkMessageDataSource> {
        NetworkMessageDataSourceImpl(get())
    }
    single<LocalMessageDataSource> {
        LocalMessageDataSourceImpl(get())
    }

    single<FriendRepository> {
        FriendRepositoryImpl(get(), get())
    }
    single<NetworkFriendDataSource> {
        NetworkFriendDataSourceImpl(get())
    }
    single<LocalFriendDataSource> {
        LocalFriendDataSourceImpl(get())
    }

    single<ChatRepository> {
        ChatRepositoryImpl(get(), get())
    }
    single<NetworkChatDataSource> {
        NetworkChatDataSourceImpl(get())
    }
    single<LocalChatDataSource> {
        LocalChatDataSourceImpl(get())
    }

    single<ThemeRepository> {
        ThemeRepositoryImpl(get(), get())
    }
    single<NetworkThemeDataSource> {
        NetworkThemeDataSourceImpl(get())
    }
    single<LocalThemeDataSource> {
        LocalThemeDataSourceImpl(get())
    }
}
