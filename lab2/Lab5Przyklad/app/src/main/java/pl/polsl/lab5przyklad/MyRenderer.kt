package pl.polsl.lab5przyklad

import android.app.Activity
import android.content.Context
import android.opengl.GLSurfaceView
import android.opengl.GLU
import java.nio.ByteBuffer
import java.nio.ByteOrder
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

class MyRenderer(con:Context?): GLSurfaceView.Renderer, Activity() {
    private var kat = 0f
    private val numberOfPeaks = 300;
    private var arrayPeaks = FloatArray(numberOfPeaks * 2)
    private var ratio = 1.0f
    private var positionOfBall = 0.0f
    private var x = 0.0f
    private val radius = 0.1f
    private var oneDir = 1.0f
    private val speed = 0.005f
    internal var context: Context? = null
    private var accValue = "No nie dziala"

    init{
        context = con
    }

    override fun onDrawFrame(gl: GL10?) {
        //val wierzcholki = floatArrayOf(-1.0f, 0.0f, -1.0f, 0.0f, 1.0f, -1.0f, 1.0f, 0.0f, -1.0f, 1.0f, -1.0f, -1.0f)
        if((positionOfBall > 1.0f - radius - speed) || (positionOfBall < -1.0f + radius + speed)){
            oneDir *= -1
        }

        positionOfBall += oneDir * speed
        

            val bufor = ByteBuffer.allocateDirect(2 * numberOfPeaks * 4)
            bufor.order(ByteOrder.nativeOrder())
            val bufor_wierzcholkow = bufor.asFloatBuffer()
            generatePeaks()
            bufor_wierzcholkow.put(arrayPeaks)
            bufor_wierzcholkow.position(0)
            gl?.glClear(GL10.GL_COLOR_BUFFER_BIT or GL10.GL_DEPTH_BUFFER_BIT)
            gl?.glColor4f(0.0f, 0.0f, 1.0f, 1.0f)
            gl?.glLoadIdentity()
            gl?.glTranslatef(positionOfBall, 0.0f, 0.0f)
            gl?.glEnableClientState(GL10.GL_VERTEX_ARRAY)
            gl?.glVertexPointer(2, GL10.GL_FLOAT, 0, bufor_wierzcholkow)
            gl?.glDrawArrays(GL10.GL_TRIANGLE_FAN, 0, numberOfPeaks)
            gl?.glDisableClientState(GL10.GL_VERTEX_ARRAY)
    }

    fun generatePeaks() {
        var y = 0.0f
        var it = 0
        var remainingPeaks = numberOfPeaks - 1;

        arrayPeaks[it] = x
        it += 1
        arrayPeaks[it] = y
        it += 1

        for (i in 0..remainingPeaks - 1) {
            var angle = (i.toFloat() / (remainingPeaks - 1).toFloat())
            var radianAngle = ((angle * PI * 2)).toFloat()

            arrayPeaks[it] = x + cos(radianAngle) * radius * ratio
            it += 1
            arrayPeaks[it] = y + sin(radianAngle) * radius * 2
            it += 1
        }
    }
        override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
        gl?.glViewport(0, 0, width, height)
        ratio = height.toFloat()/width.toFloat() * 2
        gl?.glMatrixMode(GL10.GL_PROJECTION)
        gl?.glLoadIdentity()
        GLU.gluPerspective(gl, 45.0f, 0.5f, -1.0f, -10.0f)
        gl?.glClearColor(0.0f, 1.0f, 0.0f, 1.0f)
    }

    override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
    }
}