package com.benhurqs.circularlayout.widget

import android.animation.Animator
import android.animation.ValueAnimator
import android.content.Context
import android.support.constraint.ConstraintLayout
import android.support.constraint.ConstraintSet
import android.util.AttributeSet
import android.view.View
import android.view.animation.LinearInterpolator
import com.benhurqs.circularlayout.animation.AnimationType
import java.util.*

/**
 * Created by benhur.souza on 04/04/18.
 */
class CircularLayout: ConstraintLayout {

    interface OnClickItemListener{
        fun onClickItem(position: Int, item: View)
    }

    interface OnAnimationItemListener{
        fun onAnimationStart(position: Int, item: View)
        fun onAnimationEnd(position: Int, item: View)
    }

    interface OnAnimationListener{
        fun onAnimationStart()
        fun onAnimationEnd()
    }

    private var animated = true
    private var animationDelay: Long = 0
    private var animationType = AnimationType.CLOCKWISE
    private var animationDuration = 300
    private var circleRadius = 300
    private var centerView: View? = null
    private var clickListener: OnClickItemListener? = null
    private var animationListener: OnAnimationListener? = null
    private var animationItemListener: OnAnimationItemListener? = null

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)


    fun start(){
        var list = ArrayList<View>()
        for(i in 0..(this.childCount-1)){
            //Item
            list.add(this.getChildAt(i))
        }
        setItens(list)
    }

    /**
     * Set center item
     */
    fun setCenter(centerView: View?): CircularLayout{
        this.centerView = centerView
        return this
    }

    /**
     * Set circle radius item
     */
    fun setCircleRadius(circleRadius: Int): CircularLayout{
        this.circleRadius = circleRadius
        return this
    }

    /**
     * Set animation
     */
    fun animation(animated: Boolean): CircularLayout{
        this.animated = animated
        return this
    }

    /**
     * Set animation type
     *
     * CLOCKWISE
     * ANTICLOCKWISE
     *
     */
    fun setAnimationType(type: Int): CircularLayout{
        this.animationType = type
        return this
    }


    /**
     * Set animation durations in milisecounds
     */
    fun setAnimationDurations(animationDuration: Int): CircularLayout{
        this.animationDuration = animationDuration
        return this
    }


    /**
     * Set click item listener
     */
    fun setOnClickItemListener(listener: OnClickItemListener): CircularLayout{
        this.clickListener = listener
        return this
    }

    /**
     * Set circular layout animation listener
     */
    fun setOnAnimationListener(listener: OnAnimationListener): CircularLayout{
        this.animationListener = listener
        return this
    }

    /**
     * set animation listener for each item
     */
    fun setOnAnimationItemListener(listener: OnAnimationItemListener): CircularLayout{
        this.animationItemListener = listener
        return this
    }

    private fun setItens(itemList: List<View>){
        this.removeAllViews()
        var set = ConstraintSet()

        //Initialize center point
        if(centerView == null) {
            centerView = View(this.context)
            centerView?.id = 123.toInt()
        }

        this.addView(centerView, 0)

        set.clone(this)
        set.connect(centerView!!.id, ConstraintSet.TOP, this.id, ConstraintSet.TOP, 0)
        set.connect(centerView!!.id, ConstraintSet.BOTTOM, this.id, ConstraintSet.BOTTOM, 0)
        set.connect(centerView!!.id, ConstraintSet.RIGHT, this.id, ConstraintSet.RIGHT, 0)
        set.connect(centerView!!.id, ConstraintSet.LEFT, this.id, ConstraintSet.LEFT, 0)
        set.applyTo(this)

        val angle = 360/itemList.size
        itemList.forEachIndexed { index, item ->
            this.addView(item)
            val finalAngle = angle*index.toFloat()

            item.setOnClickListener {
                if(clickListener!= null){
                    clickListener?.onClickItem(index, item)
                }
            }

            val layoutParams = item.getLayoutParams() as LayoutParams
            layoutParams.circleAngle = finalAngle
            layoutParams.circleRadius = circleRadius
            layoutParams.circleConstraint = this.id
            item.setLayoutParams(layoutParams)

            if(animated) {
                item.visibility = View.INVISIBLE
                circularAnimatorStart(item, finalAngle.toInt(), index, itemList.size)
            }
        }

    }

    private fun circularAnimatorStart(view: View, finalAngle: Int, position: Int, total: Int){

        var anim =  ValueAnimator.ofInt(0, finalAngle)
        anim.duration = (position.toLong()) * animationDuration

        if(animationType == AnimationType.ANTICLOCKWISE){
            anim =  ValueAnimator.ofInt(360, finalAngle)
            anim.duration = (total - position.toLong()) * animationDuration
        }

        anim.addListener(object : Animator.AnimatorListener{
            override fun onAnimationRepeat(animation: Animator?) {
            }

            override fun onAnimationEnd(animation: Animator?) {
                if(animationItemListener != null){
                    animationItemListener?.onAnimationEnd(position, view)
                }

                if(animationListener != null && ((animationType == AnimationType.CLOCKWISE && position == total-1) || (animationType == AnimationType.ANTICLOCKWISE && position == 0))){
                    animationListener?.onAnimationEnd()
                }
            }

            override fun onAnimationCancel(animation: Animator?) {
            }

            override fun onAnimationStart(animation: Animator?) {
                if(animationItemListener != null){
                    animationItemListener?.onAnimationStart(position, view)
                }

                if((animationListener != null) && ((animationType == AnimationType.CLOCKWISE && position == 0) || (animationType == AnimationType.ANTICLOCKWISE && position == total-1))){
                    animationListener?.onAnimationStart()
                }
            }
        })

        anim.addUpdateListener { valueAnimator ->
            if(animationListener != null )

                view.visibility = View.VISIBLE
            val valor = valueAnimator.animatedValue as Int
            val layoutParams = view.getLayoutParams() as ConstraintLayout.LayoutParams
            layoutParams.circleAngle = valor.toFloat()
            view.setLayoutParams(layoutParams)
        }


        anim.interpolator = LinearInterpolator()
//        anim.repeatMode = ValueAnimator.RESTART
//        anim.repeatCount = ValueAnimator.INFINITE

        anim.startDelay = animationDelay
        anim.start()
    }

}