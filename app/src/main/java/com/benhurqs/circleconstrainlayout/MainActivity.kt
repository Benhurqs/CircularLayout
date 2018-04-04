package com.benhurqs.circleconstrainlayout

import android.animation.ValueAnimator
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.constraint.ConstraintSet
import android.view.View
import android.view.animation.LinearInterpolator
import android.widget.ImageView
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        test(10)
    }

    private fun test(total: Int){
        var list = ArrayList<View>()
        for(i in 1..total){
            //Item
            var item = ImageView(this)
            item.setImageDrawable(this.resources.getDrawable(R.drawable.notification_bg_normal))
            item.id = i
            list.add(item)
        }

        setItens(list)
    }

    private fun setItens(itemList: List<View>){
        main.removeAllViews()
        var set = ConstraintSet()

        //Center
        var center = ImageView(this)
        center.setImageDrawable(this.resources.getDrawable(R.drawable.abc_ab_share_pack_mtrl_alpha))
        center.id = 123.toInt()

        main.addView(center, 0)

        set.clone(main)
        set.connect(center.id, ConstraintSet.TOP, main.id, ConstraintSet.TOP, 0)
        set.connect(center.id, ConstraintSet.BOTTOM, main.id, ConstraintSet.BOTTOM, 0)
        set.connect(center.id, ConstraintSet.RIGHT, main.id, ConstraintSet.RIGHT, 0)
        set.connect(center.id, ConstraintSet.LEFT, main.id, ConstraintSet.LEFT, 0)
        set.applyTo(main)

        val angle = 360/itemList.size
        itemList.forEachIndexed { index, item ->
            main.addView(item)
            val finalAngle = angle*index.toFloat()
            item.visibility = View.INVISIBLE

            val layoutParams = item.getLayoutParams() as ConstraintLayout.LayoutParams
            layoutParams.circleAngle = finalAngle
            layoutParams.circleRadius = 100
            layoutParams.circleConstraint = main.id
            item.setLayoutParams(layoutParams)

            circularAnimatorStart(item, finalAngle.toInt(), index)
        }

//        for((index, item) in itemList){
//
//            main.addView(item)
//
//            val layoutParams = item.getLayoutParams() as ConstraintLayout.LayoutParams
//            layoutParams.circleAngle = 45.toFloat()
//            layoutParams.circleRadius = 100
//            layoutParams.circleConstraint = main.id
//            item.setLayoutParams(layoutParams)
//        }

        //Item
//        var item = ImageView(this)
//        item.setImageDrawable(this.resources.getDrawable(R.drawable.notification_bg_normal))
//        item.id = 321.toInt()


    }



    private fun circularAnimatorStart(view: View, finalAngle: Int, position: Int){
//        var k = 72
//
        var anim =  ValueAnimator.ofInt(0, finalAngle);
        anim.addUpdateListener { valueAnimator ->

            view.visibility = View.VISIBLE
            val valor = valueAnimator.animatedValue as Int
            val layoutParams = view.getLayoutParams() as ConstraintLayout.LayoutParams
            layoutParams.circleAngle = valor.toFloat()
            view.setLayoutParams(layoutParams)
        }



        anim.duration = (position.toLong()) * 300
        anim.interpolator = LinearInterpolator()
//        anim.repeatMode = ValueAnimator.RESTART
//        anim.repeatCount = ValueAnimator.INFINITE

        anim.startDelay = TimeUnit.SECONDS.toMillis(1)
        anim.start()
    }

    fun onClickTest(v: View){
        test(15)
    }
}
