package per.scrumgun.domain.di

import org.koin.dsl.module
import per.scrumgun.domain.chat.ObserveChatUseCase
import per.scrumgun.domain.friend.ObserveFriendUseCase
import per.scrumgun.domain.message.GetMessageUseCase
import per.scrumgun.domain.message.SendMessageUseCase
import per.scrumgun.domain.message.SetBlockMessageUseCase
import per.scrumgun.domain.message.SetWorkMessageUseCase
import per.scrumgun.domain.theme.ObserveThemeUseCase
import per.scrumgun.domain.user.GetUserUseCase
import per.scrumgun.domain.user.LogoutUserUseCase
import per.scrumgun.domain.user.SetUserUseCase

val domainModule = module {
    factory {
        GetUserUseCase(get())
    }
    factory {
        SetUserUseCase(get())
    }
    factory {
        LogoutUserUseCase(get())
    }
    factory {
        SetWorkMessageUseCase(get())
    }
    factory {
        SetBlockMessageUseCase(get())
    }
    factory {
        GetMessageUseCase(get())
    }
    factory {
        ObserveFriendUseCase(get())
    }
    factory {
        ObserveChatUseCase(get())
    }
    factory {
        SendMessageUseCase(get(), get())
    }
    factory {
        ObserveThemeUseCase(get())
    }
}
