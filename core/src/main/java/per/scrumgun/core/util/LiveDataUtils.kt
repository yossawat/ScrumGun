package per.scrumgun.core.util

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.hadilq.liveevent.LiveEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

fun <T> MutableLiveData<T>.setValueIfNew(newValue: T) {
    if (this.value != newValue) value = newValue
}

fun <T> MutableLiveData<T>.postValueIfNew(newValue: T) {
    if (this.value != newValue) postValue(newValue)
}

fun <T> LiveData<T>.toSingleEvent(): LiveEvent<T> {
    val result = LiveEvent<T>()
    result.addSource(this) {
        result.value = it
    }
    return result
}

fun <T> LiveData<T>.debounce(
    duration: Long = 1000L,
    coroutineScope: CoroutineScope
) = MediatorLiveData<T>().also { mld ->
    val source = this
    var job: Job? = null

    mld.addSource(source) {
        job?.cancel()
        job = coroutineScope.launch {
            delay(duration)
            mld.value = source.value
        }
    }
}

/**
 * An extension to `com.snakydesign.livedataextensions` library for combining four LiveData objects.
 * This function helps combines the latest values from multiple LiveData objects.
 */
fun <W, X, R> combineLatest(
    first: LiveData<W>,
    second: LiveData<X>,
    combineFunction: (W?, X?) -> R
): LiveData<R> {
    val finalLiveData: MediatorLiveData<R> = MediatorLiveData()

    val firstEmit: Emit<W?> = Emit()
    val secondEmit: Emit<X?> = Emit()

    val combine: () -> Unit = {
        if (firstEmit.emitted && secondEmit.emitted) {
            val combined =
                combineFunction(firstEmit.value, secondEmit.value)
            finalLiveData.value = combined
        }
    }

    finalLiveData.addSource(first) { value ->
        firstEmit.value = value
        combine()
    }
    finalLiveData.addSource(second) { value ->
        secondEmit.value = value
        combine()
    }

    return finalLiveData
}


fun <W, X, Y, R> combineLatest(
    first: LiveData<W>,
    second: LiveData<X>,
    third: LiveData<Y>,
    combineFunction: (W?, X?, Y?) -> R
): LiveData<R> {
    val finalLiveData: MediatorLiveData<R> = MediatorLiveData()

    val firstEmit: Emit<W?> = Emit()
    val secondEmit: Emit<X?> = Emit()
    val thirdEmit: Emit<Y?> = Emit()

    val combine: () -> Unit = {
        if (firstEmit.emitted && secondEmit.emitted && thirdEmit.emitted) {
            val combined =
                combineFunction(firstEmit.value, secondEmit.value, thirdEmit.value)
            finalLiveData.value = combined
        }
    }

    finalLiveData.addSource(first) { value ->
        firstEmit.value = value
        combine()
    }
    finalLiveData.addSource(second) { value ->
        secondEmit.value = value
        combine()
    }
    finalLiveData.addSource(third) { value ->
        thirdEmit.value = value
        combine()
    }

    return finalLiveData
}

fun <W, X, Y, Z, R> combineLatest(
    first: LiveData<W>,
    second: LiveData<X>,
    third: LiveData<Y>,
    fourth: LiveData<Z>,
    combineFunction: (W?, X?, Y?, Z?) -> R
): LiveData<R> {
    val finalLiveData: MediatorLiveData<R> = MediatorLiveData()

    val firstEmit: Emit<W?> = Emit()
    val secondEmit: Emit<X?> = Emit()
    val thirdEmit: Emit<Y?> = Emit()
    val forthEmit: Emit<Z?> = Emit()

    val combine: () -> Unit = {
        if (firstEmit.emitted && secondEmit.emitted && thirdEmit.emitted && forthEmit.emitted) {
            val combined =
                combineFunction(firstEmit.value, secondEmit.value, thirdEmit.value, forthEmit.value)
            finalLiveData.value = combined
        }
    }

    finalLiveData.addSource(first) { value ->
        firstEmit.value = value
        combine()
    }
    finalLiveData.addSource(second) { value ->
        secondEmit.value = value
        combine()
    }
    finalLiveData.addSource(third) { value ->
        thirdEmit.value = value
        combine()
    }
    finalLiveData.addSource(fourth) { value ->
        forthEmit.value = value
        combine()
    }

    return finalLiveData
}

fun <V, W, X, Y, Z, R> combineLatest(
    first: LiveData<V>,
    second: LiveData<W>,
    third: LiveData<X>,
    fourth: LiveData<Y>,
    fifth: LiveData<Z>,
    combineFunction: (V?, W?, X?, Y?, Z?) -> R
): LiveData<R> {
    val finalLiveData: MediatorLiveData<R> = MediatorLiveData()

    val firstEmit: Emit<V?> = Emit()
    val secondEmit: Emit<W?> = Emit()
    val thirdEmit: Emit<X?> = Emit()
    val forthEmit: Emit<Y?> = Emit()
    val fifthEmit: Emit<Z?> = Emit()

    val combine: () -> Unit = {
        if (firstEmit.emitted && secondEmit.emitted && thirdEmit.emitted && forthEmit.emitted && fifthEmit.emitted) {
            val combined =
                combineFunction(
                    firstEmit.value,
                    secondEmit.value,
                    thirdEmit.value,
                    forthEmit.value,
                    fifthEmit.value
                )
            finalLiveData.value = combined
        }
    }

    finalLiveData.addSource(first) { value ->
        firstEmit.value = value
        combine()
    }
    finalLiveData.addSource(second) { value ->
        secondEmit.value = value
        combine()
    }
    finalLiveData.addSource(third) { value ->
        thirdEmit.value = value
        combine()
    }
    finalLiveData.addSource(fourth) { value ->
        forthEmit.value = value
        combine()
    }
    finalLiveData.addSource(fifth) { value ->
        fifthEmit.value = value
        combine()
    }

    return finalLiveData
}

fun <U, V, W, X, Y, Z, R> combineLatest(
    first: LiveData<U>,
    second: LiveData<V>,
    third: LiveData<W>,
    fourth: LiveData<X>,
    fifth: LiveData<Y>,
    sixth: LiveData<Z>,
    combineFunction: (U?, V?, W?, X?, Y?, Z?) -> R
): LiveData<R> {
    val finalLiveData: MediatorLiveData<R> = MediatorLiveData()

    val firstEmit: Emit<U?> = Emit()
    val secondEmit: Emit<V?> = Emit()
    val thirdEmit: Emit<W?> = Emit()
    val forthEmit: Emit<X?> = Emit()
    val fifthEmit: Emit<Y?> = Emit()
    val sixthEmit: Emit<Z?> = Emit()

    val combine: () -> Unit = {
        if (firstEmit.emitted && secondEmit.emitted && thirdEmit.emitted && forthEmit.emitted && fifthEmit.emitted && sixthEmit.emitted) {
            val combined =
                combineFunction(
                    firstEmit.value,
                    secondEmit.value,
                    thirdEmit.value,
                    forthEmit.value,
                    fifthEmit.value,
                    sixthEmit.value
                )
            finalLiveData.value = combined
        }
    }

    finalLiveData.addSource(first) { value ->
        firstEmit.value = value
        combine()
    }
    finalLiveData.addSource(second) { value ->
        secondEmit.value = value
        combine()
    }
    finalLiveData.addSource(third) { value ->
        thirdEmit.value = value
        combine()
    }
    finalLiveData.addSource(fourth) { value ->
        forthEmit.value = value
        combine()
    }
    finalLiveData.addSource(fifth) { value ->
        fifthEmit.value = value
        combine()
    }
    finalLiveData.addSource(sixth) { value ->
        sixthEmit.value = value
        combine()
    }

    return finalLiveData
}

/**
 * Wrapper that wraps an emitted value.
 */
private class Emit<T> {

    internal var emitted: Boolean = false

    internal var value: T? = null
        set(value) {
            field = value
            emitted = true
        }

    fun reset() {
        value = null
        emitted = false
    }
}
