package com.ivzb.chicks.ui.onboarding

import android.animation.ValueAnimator
import android.view.animation.AnimationUtils
import androidx.core.animation.addListener
import androidx.viewpager.widget.ViewPager

/**
 * Helper class for automatically scrolling pages of a [ViewPager] which does not allow you to
 * customize the speed at which it changes page when directly setting the current page.
 */
class ViewPagerPager(private val viewPager: ViewPager) {

    private val fastOutSlowIn =
        AnimationUtils.loadInterpolator(viewPager.context, android.R.interpolator.fast_out_slow_in)

    fun advance() {
        if (viewPager.width <= 0) return

        val current = viewPager.currentItem
        val next = ((current + 1) % requireNotNull(viewPager.adapter).count)
        val pages = next - current
        val dragDistance = pages * viewPager.width

        ValueAnimator.ofInt(0, dragDistance).apply {
            var dragProgress = 0
            var draggedPages = 0
            addListener(
                onStart = { viewPager.beginFakeDrag() },
                onEnd = { viewPager.endFakeDrag() }
            )
            addUpdateListener {
                if (!viewPager.isFakeDragging) {
                    // Sometimes onAnimationUpdate is called with initial value before
                    // onAnimationStart is called.
                    return@addUpdateListener
                }

                val dragPoint = (animatedValue as Int)
                val dragBy = dragPoint - dragProgress
                viewPager.fakeDragBy(-dragBy.toFloat())
                dragProgress = dragPoint

                // Fake dragging doesn't let you drag more than one page width. If we want to do
                // this then need to end and start a new fake drag.
                val draggedPagesProgress = dragProgress / viewPager.width
                if (draggedPagesProgress != draggedPages) {
                    viewPager.endFakeDrag()
                    viewPager.beginFakeDrag()
                    draggedPages = draggedPagesProgress
                }
            }
            duration = if (pages == 1) PAGE_CHANGE_DURATION else MULTI_PAGE_CHANGE_DURATION
            interpolator = fastOutSlowIn
        }.start()
    }

    companion object {
        private const val PAGE_CHANGE_DURATION = 400L
        private const val MULTI_PAGE_CHANGE_DURATION = 600L
    }
}
