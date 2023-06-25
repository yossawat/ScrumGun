package per.scrumgun.data.model

import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import per.scrumgun.test.testdata.MessageTestData

@RunWith(JUnit4::class)
class MessageToMessageEntityMapperTest {

    private lateinit var underTest: MessageToMessageEntityMapper

    @Before
    fun setUp() {
        underTest = MessageToMessageEntityMapper
    }

    @Test
    fun testMapUiMessageEntityTypeWork() {
        val result = underTest.map(
            MessageToMessageEntityMapper.Params(
                messageEntity = MessageTestData.MessageEntityTestData.messageEntityEmpty,
                message = "workMessage",
                case = MessageToMessageEntityMapper.Params.Type.WORK
            )
        )

        assertThat(
            "Result workMessage should be 'workMessage'",
            result.workMessage,
            equalTo(MessageTestData.messageNotEmpty.workMessage),
        )
        assertThat(
            "Result workMessage should be 'null'",
            result.blockMessage,
            equalTo(null),
        )
    }

    @Test
    fun testMapUiMessageEntityTypeBlock() {
        val result = underTest.map(
            MessageToMessageEntityMapper.Params(
                messageEntity = MessageTestData.MessageEntityTestData.messageEntityEmpty,
                message = "blockMessage",
                case = MessageToMessageEntityMapper.Params.Type.BLOCK
            )
        )

        assertThat(
            "Result workMessage should be 'null'",
            result.workMessage,
            equalTo(null),
        )
        assertThat(
            "Result workMessage should be 'blockMessage'",
            result.blockMessage,
            equalTo(MessageTestData.messageNotEmpty.blockMessage),
        )
    }
}
