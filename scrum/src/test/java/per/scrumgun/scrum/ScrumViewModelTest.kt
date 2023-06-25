package per.scrumgun.scrum

import com.jraska.livedata.TestObserver
import com.jraska.livedata.test
import io.mockk.coVerify
import io.mockk.mockk
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import per.scrumgun.core.DefaultTheme
import per.scrumgun.core.util.DateFormatUtils
import per.scrumgun.domain.chat.ObserveChatUseCase
import per.scrumgun.domain.friend.ObserveFriendUseCase
import per.scrumgun.domain.message.GetMessageUseCase
import per.scrumgun.domain.message.SendMessageUseCase
import per.scrumgun.domain.message.SetBlockMessageUseCase
import per.scrumgun.domain.message.SetWorkMessageUseCase
import per.scrumgun.scrum.model.UiChat
import per.scrumgun.test.base.BaseViewModelTest
import per.scrumgun.test.testdata.*
import per.scrumgun.test.utils.*
import java.util.*

@RunWith(JUnit4::class)
class ScrumViewModelTest : BaseViewModelTest() {
    private val setWorkMessageUseCase: SetWorkMessageUseCase = mockk()
    private val setBlockMessageUseCase: SetBlockMessageUseCase = mockk()
    private val getMessageUseCase: GetMessageUseCase = mockk()
    private val sendMessageUseCase: SendMessageUseCase = mockk()
    private val observeFriendUseCase: ObserveFriendUseCase = mockk()
    private val observeChatUseCase: ObserveChatUseCase = mockk()
    private val themeViewModelDelegate: ThemeViewModelDelegate = ThemeViewModelDelegateImpl()

    private lateinit var underTest: ScrumViewModel
    private lateinit var observeChatsObserver: TestObserver<List<UiChat>>
    private lateinit var scrumViewModelFailedEventObserver: TestObserver<String>
    private lateinit var isWorkMessageEmptyObserver: TestObserver<Boolean>
    private lateinit var isBlockMessageEmptyObserver: TestObserver<Boolean>
    private lateinit var isMessageEmptyObserver: TestObserver<Boolean>

    private fun initViewModel() {
        underTest = ScrumViewModel(
            setWorkMessageUseCase,
            setBlockMessageUseCase,
            getMessageUseCase,
            sendMessageUseCase,
            observeFriendUseCase,
            observeChatUseCase,
            provideFakeCoroutinesDispatcherProvider(coroutinesTestRule.testDispatcher),
            themeViewModelDelegate
        )
        observeChatsObserver = underTest.observeChats.test()
        scrumViewModelFailedEventObserver = underTest.scrumViewModelFailedEvent.test()
        isWorkMessageEmptyObserver = underTest.isWorkMessageEmpty.test()
        isBlockMessageEmptyObserver = underTest.isBlockMessageEmpty.test()
        isMessageEmptyObserver = underTest.isMessageEmpty.test()
    }

    @Test
    fun testObserveChat() {
        observeFriendUseCase.mockResult { _, flowCollector ->
            flowCollector.emit(successResult(FriendTestData.all()))
        }
        observeChatUseCase.mockResult { _, flowCollector ->
            flowCollector.emit(successResult(ChatTestData.all()))
        }
        themeViewModelDelegate.setTheme(ThemeTestData.defaultTheme)

        initViewModel()

        observeChatsObserver.assertValue(
            listOf(
                UiChat(
                    id = "101",
                    workMessage = "workMessage",
                    blockMessage = "blockMessage",
                    senderName = "friend1",
                    sentTime = DateFormatUtils.formatDateMonthYearSlashWithTime(
                        Date(TimeTestData.EPOCH_2022_11_23_MIDNIGHT),
                        TimeZone.getDefault()
                    ),
                    textColor = DefaultTheme.TEXT
                ),
                UiChat(
                    id = "102",
                    workMessage = "workMessage",
                    blockMessage = "blockMessage",
                    senderName = "friend2",
                    sentTime = DateFormatUtils.formatDateMonthYearSlashWithTime(
                        Date(TimeTestData.EPOCH_2022_11_23_MIDNIGHT),
                        TimeZone.getDefault()
                    ),
                    textColor = DefaultTheme.TEXT
                )
            )
        )
        coVerify(exactly = 1) {
            observeFriendUseCase.invoke(
                Unit, provideFakeCoroutinesDispatcherProvider(coroutinesTestRule.testDispatcher).io
            )
            observeChatUseCase.invoke(
                Unit, provideFakeCoroutinesDispatcherProvider(coroutinesTestRule.testDispatcher).io
            )
        }
    }

    @Test
    fun testObserveChatFail() {
        observeFriendUseCase.mockResult { _, flowCollector ->
            flowCollector.emit(failResult())
        }
        observeChatUseCase.mockResult { _, flowCollector ->
            flowCollector.emit(failResult())
        }
        themeViewModelDelegate.setTheme(ThemeTestData.defaultTheme)

        initViewModel()

        observeChatsObserver.assertValue(listOf())
        coVerify(exactly = 1) {
            observeFriendUseCase.invoke(
                Unit, provideFakeCoroutinesDispatcherProvider(coroutinesTestRule.testDispatcher).io
            )
            observeChatUseCase.invoke(
                Unit, provideFakeCoroutinesDispatcherProvider(coroutinesTestRule.testDispatcher).io
            )
        }
    }

    @Test
    fun testGetMessageNotEmpty() {
        observeFriendUseCase.mockResult { _, flowCollector ->
            flowCollector.emit(successResult(FriendTestData.all()))
        }
        observeChatUseCase.mockResult { _, flowCollector ->
            flowCollector.emit(successResult(ChatTestData.all()))
        }
        getMessageUseCase.mockReturnResult {
            successResult(MessageTestData.messageNotEmpty)
        }

        initViewModel()

        isWorkMessageEmptyObserver.assertValue(false)
        isBlockMessageEmptyObserver.assertValue(false)
        isMessageEmptyObserver.assertValue(true)
        coVerify(exactly = 1) {
            getMessageUseCase.invoke(Unit)
            observeFriendUseCase.invoke(
                Unit, provideFakeCoroutinesDispatcherProvider(coroutinesTestRule.testDispatcher).io
            )
            observeChatUseCase.invoke(
                Unit, provideFakeCoroutinesDispatcherProvider(coroutinesTestRule.testDispatcher).io
            )
        }
    }

    @Test
    fun testGetMessageWorkEmpty() {
        observeFriendUseCase.mockResult { _, flowCollector ->
            flowCollector.emit(successResult(FriendTestData.all()))
        }
        observeChatUseCase.mockResult { _, flowCollector ->
            flowCollector.emit(successResult(ChatTestData.all()))
        }
        getMessageUseCase.mockReturnResult {
            successResult(MessageTestData.messageWorkEmpty)
        }

        initViewModel()

        isWorkMessageEmptyObserver.assertValue(true)
        isBlockMessageEmptyObserver.assertValue(false)
        isMessageEmptyObserver.assertValue(false)
        coVerify(exactly = 1) {
            getMessageUseCase.invoke(Unit)
            observeFriendUseCase.invoke(
                Unit, provideFakeCoroutinesDispatcherProvider(coroutinesTestRule.testDispatcher).io
            )
            observeChatUseCase.invoke(
                Unit, provideFakeCoroutinesDispatcherProvider(coroutinesTestRule.testDispatcher).io
            )
        }
    }

    @Test
    fun testGetMessageBlockEmpty() {
        observeFriendUseCase.mockResult { _, flowCollector ->
            flowCollector.emit(successResult(FriendTestData.all()))
        }
        observeChatUseCase.mockResult { _, flowCollector ->
            flowCollector.emit(successResult(ChatTestData.all()))
        }
        getMessageUseCase.mockReturnResult {
            successResult(MessageTestData.messageBlockEmpty)
        }

        initViewModel()

        isWorkMessageEmptyObserver.assertValue(false)
        isBlockMessageEmptyObserver.assertValue(true)
        isMessageEmptyObserver.assertValue(false)
        coVerify(exactly = 1) {
            getMessageUseCase.invoke(Unit)
            observeFriendUseCase.invoke(
                Unit, provideFakeCoroutinesDispatcherProvider(coroutinesTestRule.testDispatcher).io
            )
            observeChatUseCase.invoke(
                Unit, provideFakeCoroutinesDispatcherProvider(coroutinesTestRule.testDispatcher).io
            )
        }
    }

    @Test
    fun testGetMessageEmpty() {
        observeFriendUseCase.mockResult { _, flowCollector ->
            flowCollector.emit(successResult(FriendTestData.all()))
        }
        observeChatUseCase.mockResult { _, flowCollector ->
            flowCollector.emit(successResult(ChatTestData.all()))
        }
        getMessageUseCase.mockReturnResult {
            successResult(MessageTestData.messageEmpty)
        }

        initViewModel()

        isWorkMessageEmptyObserver.assertValue(true)
        isBlockMessageEmptyObserver.assertValue(true)
        isMessageEmptyObserver.assertValue(false)
        coVerify(exactly = 1) {
            getMessageUseCase.invoke(Unit)
            observeFriendUseCase.invoke(
                Unit, provideFakeCoroutinesDispatcherProvider(coroutinesTestRule.testDispatcher).io
            )
            observeChatUseCase.invoke(
                Unit, provideFakeCoroutinesDispatcherProvider(coroutinesTestRule.testDispatcher).io
            )
        }
    }

    @Test
    fun testGetMessageFail() {
        observeFriendUseCase.mockResult { _, flowCollector ->
            flowCollector.emit(successResult(FriendTestData.all()))
        }
        observeChatUseCase.mockResult { _, flowCollector ->
            flowCollector.emit(successResult(ChatTestData.all()))
        }
        getMessageUseCase.mockReturnResult {
            failResult()
        }

        initViewModel()

        isWorkMessageEmptyObserver.assertNoValue()
        isBlockMessageEmptyObserver.assertNoValue()
        isMessageEmptyObserver.assertNoValue()
        coVerify(exactly = 1) {
            getMessageUseCase.invoke(Unit)
            observeFriendUseCase.invoke(
                Unit, provideFakeCoroutinesDispatcherProvider(coroutinesTestRule.testDispatcher).io
            )
            observeChatUseCase.invoke(
                Unit, provideFakeCoroutinesDispatcherProvider(coroutinesTestRule.testDispatcher).io
            )
        }
    }

    @Test
    fun testSetWorkMessage() {
        observeFriendUseCase.mockResult { _, flowCollector ->
            flowCollector.emit(successResult(FriendTestData.all()))
        }
        observeChatUseCase.mockResult { _, flowCollector ->
            flowCollector.emit(successResult(ChatTestData.all()))
        }
        setWorkMessageUseCase.mockReturnResult {
            successResult(Unit)
        }
        getMessageUseCase.mockReturnResult {
            successResult(MessageTestData.messageBlockEmpty)
        }

        initViewModel()
        underTest.setWorkMessage("workMessage")

        scrumViewModelFailedEventObserver.assertNoValue()
        isWorkMessageEmptyObserver.assertValue(false)
        isBlockMessageEmptyObserver.assertValue(true)
        isMessageEmptyObserver.assertValue(false)
        coVerify(exactly = 1) {
            setWorkMessageUseCase.invoke("workMessage")
            observeFriendUseCase.invoke(
                Unit, provideFakeCoroutinesDispatcherProvider(coroutinesTestRule.testDispatcher).io
            )
            observeChatUseCase.invoke(
                Unit, provideFakeCoroutinesDispatcherProvider(coroutinesTestRule.testDispatcher).io
            )
        }
        coVerify(exactly = 2) {
            getMessageUseCase.invoke(Unit)
        }
    }

    @Test
    fun testSetWorkMessageFail() {
        observeFriendUseCase.mockResult { _, flowCollector ->
            flowCollector.emit(successResult(FriendTestData.all()))
        }
        observeChatUseCase.mockResult { _, flowCollector ->
            flowCollector.emit(successResult(ChatTestData.all()))
        }
        setWorkMessageUseCase.mockReturnResult {
            failResult()
        }

        initViewModel()
        underTest.setWorkMessage("workMessage")

        scrumViewModelFailedEventObserver.assertHasValue()
        isWorkMessageEmptyObserver.assertNoValue()
        isBlockMessageEmptyObserver.assertNoValue()
        isMessageEmptyObserver.assertNoValue()
        coVerify(exactly = 1) {
            getMessageUseCase.invoke(Unit)
            setWorkMessageUseCase.invoke("workMessage")
            observeFriendUseCase.invoke(
                Unit, provideFakeCoroutinesDispatcherProvider(coroutinesTestRule.testDispatcher).io
            )
            observeChatUseCase.invoke(
                Unit, provideFakeCoroutinesDispatcherProvider(coroutinesTestRule.testDispatcher).io
            )
        }
    }

    @Test
    fun testSetBlockMessage() {
        observeFriendUseCase.mockResult { _, flowCollector ->
            flowCollector.emit(successResult(FriendTestData.all()))
        }
        observeChatUseCase.mockResult { _, flowCollector ->
            flowCollector.emit(successResult(ChatTestData.all()))
        }
        setBlockMessageUseCase.mockReturnResult {
            successResult(Unit)
        }
        getMessageUseCase.mockReturnResult {
            successResult(MessageTestData.messageWorkEmpty)
        }

        initViewModel()
        underTest.setBlockMessage("blockMessage")

        scrumViewModelFailedEventObserver.assertNoValue()
        isWorkMessageEmptyObserver.assertValue(true)
        isBlockMessageEmptyObserver.assertValue(false)
        isMessageEmptyObserver.assertValue(false)
        coVerify(exactly = 1) {
            setBlockMessageUseCase.invoke("blockMessage")
            observeFriendUseCase.invoke(
                Unit, provideFakeCoroutinesDispatcherProvider(coroutinesTestRule.testDispatcher).io
            )
            observeChatUseCase.invoke(
                Unit, provideFakeCoroutinesDispatcherProvider(coroutinesTestRule.testDispatcher).io
            )
        }
        coVerify(exactly = 2) {
            getMessageUseCase.invoke(Unit)
        }
    }

    @Test
    fun testSetBlockMessageFail() {
        observeFriendUseCase.mockResult { _, flowCollector ->
            flowCollector.emit(successResult(FriendTestData.all()))
        }
        observeChatUseCase.mockResult { _, flowCollector ->
            flowCollector.emit(successResult(ChatTestData.all()))
        }
        setBlockMessageUseCase.mockReturnResult {
            failResult()
        }

        initViewModel()
        underTest.setBlockMessage("blockMessage")

        scrumViewModelFailedEventObserver.assertHasValue()
        isWorkMessageEmptyObserver.assertNoValue()
        isBlockMessageEmptyObserver.assertNoValue()
        isMessageEmptyObserver.assertNoValue()
        coVerify(exactly = 1) {
            getMessageUseCase.invoke(Unit)
            setBlockMessageUseCase.invoke("blockMessage")
            observeFriendUseCase.invoke(
                Unit, provideFakeCoroutinesDispatcherProvider(coroutinesTestRule.testDispatcher).io
            )
            observeChatUseCase.invoke(
                Unit, provideFakeCoroutinesDispatcherProvider(coroutinesTestRule.testDispatcher).io
            )
        }
    }

    @Test
    fun testSendBlockMessage() {
        observeFriendUseCase.mockResult { _, flowCollector ->
            flowCollector.emit(successResult(FriendTestData.all()))
        }
        observeChatUseCase.mockResult { _, flowCollector ->
            flowCollector.emit(successResult(ChatTestData.all()))
        }
        sendMessageUseCase.mockReturnResult {
            successResult(Unit)
        }
        getMessageUseCase.mockReturnResult {
            successResult(MessageTestData.messageWorkEmpty)
        }

        initViewModel()
        underTest.sendMessage()

        scrumViewModelFailedEventObserver.assertNoValue()
        coVerify(exactly = 1) {
            sendMessageUseCase.invoke(Unit)
            observeFriendUseCase.invoke(
                Unit, provideFakeCoroutinesDispatcherProvider(coroutinesTestRule.testDispatcher).io
            )
            observeChatUseCase.invoke(
                Unit, provideFakeCoroutinesDispatcherProvider(coroutinesTestRule.testDispatcher).io
            )
        }
        coVerify(exactly = 2) {
            getMessageUseCase.invoke(Unit)
        }
    }

    @Test
    fun testSendBlockMessageFail() {
        observeFriendUseCase.mockResult { _, flowCollector ->
            flowCollector.emit(successResult(FriendTestData.all()))
        }
        observeChatUseCase.mockResult { _, flowCollector ->
            flowCollector.emit(successResult(ChatTestData.all()))
        }
        sendMessageUseCase.mockReturnResult {
            failResult()
        }

        initViewModel()
        underTest.sendMessage()

        scrumViewModelFailedEventObserver.assertHasValue()
        coVerify(exactly = 1) {
            getMessageUseCase.invoke(Unit)
            sendMessageUseCase.invoke(Unit)
            observeFriendUseCase.invoke(
                Unit, provideFakeCoroutinesDispatcherProvider(coroutinesTestRule.testDispatcher).io
            )
            observeChatUseCase.invoke(
                Unit, provideFakeCoroutinesDispatcherProvider(coroutinesTestRule.testDispatcher).io
            )
        }
    }
}
