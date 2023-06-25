package per.scrumgun.test.utils

import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Assert.assertTrue
import org.junit.Assert.fail

@Suppress("NOTHING_TO_INLINE")
inline fun <T> T.assertValue(expected: T): T {
    assertThat("Value $this should be equal to $expected", this, equalTo(expected))
    return this
}

@Suppress("NOTHING_TO_INLINE")
inline fun <T> List<T>.assertValues(vararg values: T): List<T> {
    val size = this.size
    if (size != values.size) {
        fail("Value count differs; expected: ${values.size} ${values.contentToString()} but was: $size $this ")
    }

    for (valueIndex in 0 until size) {
        val actualItem = this[valueIndex]
        val expectedItem: T = values[valueIndex]
        if (expectedItem != actualItem) {
            fail("Values at index $valueIndex differ; expected: $expectedItem but was: $actualItem")
        }
    }

    return this
}

inline fun <T> T.assertValue(block: (T) -> Boolean): T {
    assertTrue("Value $this does not match the predicate.", block(this))
    return this
}
