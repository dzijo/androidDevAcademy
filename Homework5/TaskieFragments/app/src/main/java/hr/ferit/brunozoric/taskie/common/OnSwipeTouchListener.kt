package hr.ferit.brunozoric.taskie.common

import android.content.Context
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View

open class OnSwipeTouchListener(ctx: Context) : View.OnTouchListener {

    private val gestureDetector: GestureDetector

    companion object {

        private val SWIPE_THRESHOLD = 100
        private val SWIPE_VELOCITY_THRESHOLD = 100
    }

    init {
        gestureDetector = GestureDetector(ctx, GestureListener())
    }

    override fun onTouch(v: View, event: MotionEvent): Boolean {
        return gestureDetector.onTouchEvent(event)
    }

    private inner class GestureListener : GestureDetector.SimpleOnGestureListener() {


        override fun onDown(e: MotionEvent): Boolean {
            return true
        }

        override fun onSingleTapUp(e: MotionEvent?): Boolean {
            try {
                onClick()
                return true
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return super.onSingleTapUp(e)
        }

        override fun onLongPress(e: MotionEvent?) {
            try {
                onLongPress()
            } catch (e: Exception) {
                e.printStackTrace()
            }
            super.onLongPress(e)
        }

        override fun onFling(e1: MotionEvent, e2: MotionEvent, velocityX: Float, velocityY: Float): Boolean {
            var result = false
            try {
                val distanceX = e2.x - e1.x
                if (Math.abs(distanceX) > SWIPE_THRESHOLD &&
                    Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD
                ) {
                    if (distanceX > 0)
                        onSwipeRight()
                    else
                        onSwipeLeft()
                    return true
                }
                return false
            } catch (exception: Exception) {
                exception.printStackTrace()
            }

            return result
        }

    }

    open fun onSwipeRight() {}

    open fun onSwipeLeft() {}

    open fun onClick() {}

    open fun onLongPress() {}
}