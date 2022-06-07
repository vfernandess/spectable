package com.voidx.spectable.widget.songcover

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.core.view.children
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.voidx.spectable.R
import com.voidx.spectable.extension.safeLet
import com.voidx.spectable.feature.music.space.Song

private const val MODE_NONE = -1
private const val MODE_ONE_COVER = 0
private const val MODE_TWO_COVER = 1
private const val MODE_THREE_COVER = 2
private const val MODE_FOUR_COVER = 3

class SongCover @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private var first = ImageLoader(null, null)
    private var second = ImageLoader(null, null)
    private var third = ImageLoader(null, null)
    private var fourth = ImageLoader(null, null)


    private var mode = MODE_NONE

    init {
        LayoutInflater.from(context).inflate(R.layout.widget_song_cover, this, true)
    }

    private val imageView: ImageView?
        get() = children.firstOrNull() as? ImageView

    fun buildCover(
        first: Song?,
        second: Song?,
        third: Song?,
        fourth: Song?
    ) {

        cancel(this.first)
        this.first.target = first?.let(::loadImage)

        cancel(this.second)
        this.second.target = second?.let(::loadImage)

        cancel(this.third)
        this.third.target = third?.let(::loadImage)

        cancel(this.fourth)
        this.fourth.target = fourth?.let(::loadImage)
    }

    private fun cancel(loader: ImageLoader) {
        loader.target?.let {
            Glide.with(context).clear(it)
        }
    }

    private fun loadImage(song: Song): SimpleTarget {
        return Glide
            .with(context)
            .asBitmap()
            .load(song.thumbnail)
            .into(SimpleTarget(::onBitmapLoaded))
    }

    private fun onBitmapLoaded(bitmap: Bitmap?) {
        when {
            first.bitmap == null -> {
                Log.d("SongCover", "MODE_ONE_COVER")
                first.bitmap = bitmap
                mode = MODE_ONE_COVER
            }

            second.bitmap == null -> {
                Log.d("SongCover", "MODE_TWO_COVER")
                second.bitmap = bitmap
                mode = MODE_TWO_COVER
            }

            third.bitmap == null -> {
                Log.d("SongCover", "MODE_THREE_COVER")
                third.bitmap = bitmap
                mode = MODE_THREE_COVER
            }

            fourth.bitmap == null -> {
                Log.d("SongCover", "MODE_FOUR_COVER")
                fourth.bitmap = bitmap
                mode = MODE_FOUR_COVER
            }
        }

        buildCovers()
    }

    private fun buildCovers() {
        if (width <= 0 || height <= 0) {
            return
        }

        var finalBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)

        var canvas = Canvas(finalBitmap)
        when (mode) {
            MODE_ONE_COVER ->
                first.bitmap?.let {
                    val scaleBitmap = Bitmap.createScaledBitmap(it, width, height, false)
//                    it.recycle()

                    canvas.drawBitmap(scaleBitmap, 0f, 0f, null)
                }
            MODE_TWO_COVER ->
                safeLet(first.bitmap, second.bitmap) { first, second ->
                    val height = height / 2

                    val firstScaleBitmap = Bitmap.createScaledBitmap(first, width, height, false)
//                    first.recycle()

                    val secondScaleBitmap = Bitmap.createScaledBitmap(second, width, height, false)
//                    second.recycle()

                    canvas.drawBitmap(firstScaleBitmap, 0f, 0f, null)
                    canvas.drawBitmap(secondScaleBitmap, 0f, height.toFloat(), null)
                }
            MODE_THREE_COVER ->
                safeLet(first.bitmap, second.bitmap, third.bitmap) { first, second, third ->
                    val width = width / 2
                    val height = height / 2

                    val firstScaleBitmap = Bitmap.createScaledBitmap(first, width, height, false)
//                    first.recycle()

                    val secondScaleBitmap = Bitmap.createScaledBitmap(second, width, height, false)
//                    second.recycle()

                    val thirdScaleBitmap = Bitmap.createScaledBitmap(third, this.width, height, false)
//                    third.recycle()

                    canvas.drawBitmap(firstScaleBitmap, 0f, 0f, null)
                    canvas.drawBitmap(secondScaleBitmap, width.toFloat(), 0f, null)
                    canvas.drawBitmap(thirdScaleBitmap, 0f, height.toFloat(), null)
                }

            MODE_FOUR_COVER ->
                safeLet(
                    first.bitmap,
                    second.bitmap,
                    third.bitmap,
                    fourth.bitmap
                ) { first, second, third, fourth ->
                    val width = width / 2
                    val height = height / 2

                    val firstScaleBitmap = Bitmap.createScaledBitmap(first, width, height, false)
//                    first.recycle()

                    val secondScaleBitmap = Bitmap.createScaledBitmap(second, width, height, false)
//                    second.recycle()

                    val thirdScaleBitmap = Bitmap.createScaledBitmap(third, width, height, false)
//                    third.recycle()

                    val fourthScaleBitmap = Bitmap.createScaledBitmap(fourth, width, height, false)
//                    fourth.recycle()

                    canvas.drawBitmap(firstScaleBitmap, 0f, 0f, null)
                    canvas.drawBitmap(secondScaleBitmap, width.toFloat(), 0f, null)
                    canvas.drawBitmap(thirdScaleBitmap, 0f, height.toFloat(), null)
                    canvas.drawBitmap(fourthScaleBitmap, width.toFloat(), height.toFloat(), null)
                }
        }

        imageView?.setImageBitmap(finalBitmap)
    }
}

private data class ImageLoader(
    var target: SimpleTarget?,
    var bitmap: Bitmap?
)

private class SimpleTarget(
    private val onBitmapLoaded: (bitmap: Bitmap) -> Unit
) : CustomTarget<Bitmap>() {
    override fun onLoadFailed(errorDrawable: Drawable?) {
    }

    override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
        Log.d("SongCover", "SimpleTarget::onResourceReady")
        onBitmapLoaded.invoke(resource)
    }

    override fun onLoadCleared(placeholder: Drawable?) {
    }
}
