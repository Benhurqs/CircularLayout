package com.benhurqs.circleconstrainlayout

import android.animation.Animator
import android.animation.ValueAnimator
import android.content.Context
import android.support.constraint.ConstraintLayout
import android.support.constraint.ConstraintSet
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.animation.LinearInterpolator
import android.widget.Adapter

/**
 * Created by benhur.souza on 04/04/18.
 */
class CircularList: ConstraintLayout{

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

    var animated = true
    var animationDelay: Long = 0
    var animationClockwise = true
    var animationDuration = 300
    var circleRadius = 300
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

    fun setItens(itemList: List<View>){
        setItens(null, itemList)
    }

    fun setOnClickItemListener(listener: OnClickItemListener): CircularList{
        this.clickListener = listener
        return this
    }

    fun setOnAnimationListener(listener: OnAnimationListener): CircularList{
        this.animationListener = listener
        return this
    }

    fun setOnAnimationItemListener(listener: OnAnimationItemListener): CircularList{
        this.animationItemListener = listener
        return this
    }

    fun setItens(centerView: View? = null, itemList: List<View>){
        this.removeAllViews()
        var set = ConstraintSet()

        //Center
        var center = View(this.context)
        center.id = 123.toInt()

        if(centerView != null) {
            center = centerView
        }

        this.addView(center, 0)

        set.clone(this)
        set.connect(center!!.id, ConstraintSet.TOP, this.id, ConstraintSet.TOP, 0)
        set.connect(center.id, ConstraintSet.BOTTOM, this.id, ConstraintSet.BOTTOM, 0)
        set.connect(center.id, ConstraintSet.RIGHT, this.id, ConstraintSet.RIGHT, 0)
        set.connect(center.id, ConstraintSet.LEFT, this.id, ConstraintSet.LEFT, 0)
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

            val layoutParams = item.getLayoutParams() as ConstraintLayout.LayoutParams
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

        if(!animationClockwise){
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

                if(animationListener != null && ((animationClockwise && position == total-1) || (!animationClockwise && position == 0))){
                    animationListener?.onAnimationEnd()
                }
            }

            override fun onAnimationCancel(animation: Animator?) {
            }

            override fun onAnimationStart(animation: Animator?) {
                if(animationItemListener != null){
                    animationItemListener?.onAnimationStart(position, view)
                }

                if((animationListener != null) && ((animationClockwise && position == 0) || (!animationClockwise && position == total-1))){
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