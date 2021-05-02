import android.content.Context
import android.graphics.Camera
import android.os.Parcel
import android.os.Parcelable
import android.view.SurfaceHolder
import android.view.SurfaceView

class ShowCamera(context: Context?) : SurfaceView(context), SurfaceHolder.Callback {
    private lateinit var camera: Camera
    private lateinit var surfaceHolder : SurfaceHolder

    constructor(context: Context, camera: Camera) : this(context) {
        this.camera = camera
        surfaceHolder = holder
        surfaceHolder.addCallback(this)
    }

    override fun surfaceCreated(holder: SurfaceHolder) {
    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {
    }

}