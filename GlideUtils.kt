package utils

import android.content.Context
import android.graphics.Bitmap
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions

object GlideUtils {

    lateinit var drawOption: RequestOptions
    lateinit var instance: Glide

    fun setInstance(context: Context) {
        if (!::instance.isInitialized) {
            instance = Glide.get(context)
        }
    }

    /* Stack */
    fun clearCache(context: Context) {
        setInstance(context)

        instance.clearDiskCache()
    }

    fun clearMemory(context: Context) {
        setInstance(context)

        instance.clearMemory()
    }

    fun getContext (context: Context, useBitmap:Boolean = false): Any {
        var context:Any = Glide.with(context)

        if (useBitmap) {
            context = (context as RequestManager).asBitmap()
        } else {
            context = context as RequestManager
        }

        if (!(context is RequestManager)) {
            (context is RequestBuilder<*>) {
                context = context as RequestBuilder<Bitmap>
            }
        }

        return context
    }

    fun draw(drawType: String, context: Context, imageURL: String, imageView: ImageView, useBitmap:Boolean = false, quality: Int = 100, width: Int = -1, height: Int = -1, roundCorners: Int = -1) {
        var context:Any = getContext(context, useBitmap)

        if (!::drawOption.isInitialized) {
            if (width == -1 && height == -1) {
                drawOption = RequestOptions.encodeQualityOf(100)
            } else {
                var _width = width
                var _height = height

                if (width == -1 && height != -1) {
                    var _width = height
                } else if (width != -1 && height == -1) {
                    var _height = width
                }

                drawOption = RequestOptions().override(_width, _height)
            }
        }

        if (roundCorners != -1) {
            drawOption = drawOption.transform(RoundedCorners(roundCorners))
        }

        when (drawType) {
            "circleCrop" -> {
                drawOption = drawOption.circleCrop()
            }
            "centerCrop" -> {
                drawOption = drawOption.centerCrop()
            }
            "centerInside" -> {
                drawOption = drawOption.centerInside()
            }
            else -> {
                drawOption = drawOption.centerCrop()
            }
        }

        if (context is RequestManager) {
            context = (context as RequestManager)

            (context as RequestManager).load(imageURL).apply(drawOption).into(imageView)
        } else if (context is RequestBuilder<*>) {
            context = (context as RequestBuilder<Bitmap>)

            (context as RequestBuilder<Bitmap>).load(imageURL).apply(drawOption).into(imageView)
        }
    }

    fun drawCenterInside(context: Context, imageURL: String, imageView: ImageView, useBitmap:Boolean = false, quality: Int = 100, width: Int = -1, height: Int = -1, roundCorners: Int = -1) {
        this.draw("centerInside", context, imageURL, imageView, useBitmap, quality, width, height, roundCorners)
    }

    fun drawCircleCrop(context: Context, imageURL: String, imageView: ImageView, useBitmap:Boolean = false, quality: Int = 100, width: Int = -1, height: Int = -1, roundCorners: Int = -1) {
        this.draw("circleCrop", context, imageURL, imageView, useBitmap, quality, width, height, roundCorners)
    }

    fun drawCenterCrop(context: Context, imageURL: String, imageView: ImageView, useBitmap:Boolean = false, quality: Int = 100, width: Int = -1, height: Int = -1, roundCorners: Int = -1) {
        this.draw("circleCrop", context, imageURL, imageView, useBitmap, quality, width, height, roundCorners)
    }
}

private operator fun Boolean.invoke(function: () -> Unit) {}
