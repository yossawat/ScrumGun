package per.scrumgun.core.util

import android.graphics.Bitmap
import android.net.Uri
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.core.net.toUri
import com.facebook.common.executors.CallerThreadExecutor
import com.facebook.common.references.CloseableReference
import com.facebook.datasource.DataSource
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.drawee.view.SimpleDraweeView
import com.facebook.imagepipeline.common.ResizeOptions
import com.facebook.imagepipeline.datasource.BaseBitmapDataSubscriber
import com.facebook.imagepipeline.image.CloseableImage
import com.facebook.imagepipeline.request.ImageRequestBuilder
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

fun SimpleDraweeView.load(
    url: String?,
    resizeOptions: ResizeOptions?
) {
    if (url?.isBlank() == true) {
        return
    }
    val request = ImageRequestBuilder.newBuilderWithSource(url?.toUri())
        .setResizeOptions(resizeOptions)
        .build()
    controller = Fresco.newDraweeControllerBuilder()
        .setOldController(controller)
        .setImageRequest(request)
        .build()
}

fun SimpleDraweeView.load(
    @DrawableRes
    drawableRes: Int
) {
    this.setImageResource(drawableRes)
}

fun SimpleDraweeView.load(
    uri: Uri?,
    resizeOptions: ResizeOptions?
) {
    uri ?: return
    val request = ImageRequestBuilder.newBuilderWithSource(uri)
        .setResizeOptions(resizeOptions)
        .build()
    controller = Fresco.newDraweeControllerBuilder()
        .setOldController(controller)
        .setImageRequest(request)
        .build()
}

fun SimpleDraweeView.loadDynamic(
    url: String?,
    ratio: Float,
    resizeOptions: ResizeOptions
) {
    this.aspectRatio = ratio
    this.load(url, resizeOptions)
}

suspend fun ImageView.loadBlocking(
    url: String?,
    resizeOptions: ResizeOptions?
) {
    if (url.isNullOrBlank()) {
        return
    }
    suspendCoroutine<Unit> { continuation ->
        val imageRequest = ImageRequestBuilder.newBuilderWithSource(Uri.parse(url))
            .setResizeOptions(resizeOptions)
            .build()

        val imagePipeline = Fresco.getImagePipeline()
        val dataSource = imagePipeline.fetchDecodedImage(imageRequest, this)

        dataSource.subscribe(object : BaseBitmapDataSubscriber() {
            override fun onNewResultImpl(
                bitmap: Bitmap?
            ) {
                if (dataSource.isFinished) {
                    if (bitmap != null) {
                        this@loadBlocking.setImageBitmap(Bitmap.createBitmap(bitmap))
                        dataSource.close()
                    }
                    continuation.resume(Unit)
                }
            }

            override fun onFailureImpl(dataSource: DataSource<CloseableReference<CloseableImage>>) {
                dataSource.close()
                continuation.resume(Unit)
            }
        }, CallerThreadExecutor.getInstance())
    }
}
