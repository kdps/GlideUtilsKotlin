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
    lateinit var glideInstance: Any

    class iteratorOption {
        fun drawImage(imageURL: String, imageView: ImageView) {
            drawImage(imageURL, imageView)
        }

        fun setRoundCorners(roundCorners: Int = -1) {
            setRoundCorners(roundCorners)
        }

        fun setDrawOptions(drawType: String) {
            setDrawOptions(drawType)
        }

        fun setSize(width: Int = -1, height: Int = -1) {
            setSize(width, height)
        }
    }

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
        glideInstance = Glide.with(context)

        if (useBitmap) {
            glideInstance = (glideInstance as RequestManager).asBitmap()
        } else {
            glideInstance = glideInstance as RequestManager
        }

        if (!(glideInstance is RequestManager)) {
            (glideInstance is RequestBuilder<*>) {
                glideInstance = glideInstance as RequestBuilder<Bitmap>
            }
        }

        return context
    }

    fun setSize(width: Int = -1, height: Int = -1) {
        var _width = width
        var _height = height

        if (width == -1 && height != -1) {
            var _width = height
        } else if (width != -1 && height == -1) {
            var _height = width
        }

        drawOption = drawOption.override(_width, _height)
    }

    fun setRoundCorners(roundCorners: Int = -1) {
        drawOption = drawOption.transform(RoundedCorners(roundCorners))
    }

    fun getDrawObject(context: Context, quality: Int = 100): iteratorOption {
        glideInstance = getContext(context)

        if (!::drawOption.isInitialized) {
            drawOption = RequestOptions.encodeQualityOf(100)
        }

        return iteratorOption()
    }
    
    fun draw(drawType: String, context: Context, imageURL: String, imageView: ImageView, useBitmap:Boolean = false, quality: Int = 100, width: Int = -1, height: Int = -1, roundCorners: Int = -1) {
        glideInstance = getContext(context, useBitmap)

        if (!::drawOption.isInitialized) {
            drawOption = RequestOptions.encodeQualityOf(100)
        }

        if (width != -1 && height != 1) {
            setSize(width, height)
        }

        if (roundCorners != -1) {
            setRoundCorners(roundCorners)
        }

        setDrawOptions(drawType)

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

        drawImage(imageURL, imageView)
    }

    fun setDrawOptions(drawType: String) {
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
    }

    fun drawImage(imageURL: String, imageView: ImageView) {
        if (glideInstance is RequestManager) {
            glideInstance = (glideInstance as RequestManager)

            (glideInstance as RequestManager).load(imageURL).apply(drawOption).into(imageView)
        } else if (glideInstance is RequestBuilder<*>) {
            glideInstance = (glideInstance as RequestBuilder<Bitmap>)

            (glideInstance as RequestBuilder<Bitmap>).load(imageURL).apply(drawOption).into(imageView)
        }
    }

    fun drawCenterInside(context: Context, imageURL: String, imageView: ImageView, useBitmap:Boolean = false, quality: Int = 100, width: Int = -1, height: Int = -1, roundCorners: Int = -1): iteratorOption {
        this.draw("centerInside", context, imageURL, imageView, useBitmap, quality, width, height, roundCorners)

        return iteratorOption()
    }

    fun drawCircleCrop(context: Context, imageURL: String, imageView: ImageView, useBitmap:Boolean = false, quality: Int = 100, width: Int = -1, height: Int = -1, roundCorners: Int = -1): iteratorOption {
        this.draw("circleCrop", context, imageURL, imageView, useBitmap, quality, width, height, roundCorners)

        return iteratorOption()
    }

    fun drawCenterCrop(context: Context, imageURL: String, imageView: ImageView, useBitmap:Boolean = false, quality: Int = 100, width: Int = -1, height: Int = -1, roundCorners: Int = -1): iteratorOption {
        this.draw("circleCrop", context, imageURL, imageView, useBitmap, quality, width, height, roundCorners)

        return iteratorOption()
    }
}

private operator fun Boolean.invoke(function: () -> Unit) {}
