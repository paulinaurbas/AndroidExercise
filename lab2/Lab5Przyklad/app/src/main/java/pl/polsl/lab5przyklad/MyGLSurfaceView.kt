package pl.polsl.lab5przyklad

import android.content.Context
import android.opengl.GLSurfaceView

class MyGLSurfaceView(context: Context?) : GLSurfaceView(context) {
    init {
        setRenderer(MyRenderer(context))
    }

}