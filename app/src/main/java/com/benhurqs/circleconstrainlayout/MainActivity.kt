package com.benhurqs.circleconstrainlayout

import android.animation.ValueAnimator
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.constraint.ConstraintSet
import android.util.TimeUtils
import android.view.View
import android.view.animation.LinearInterpolator
import android.widget.ImageView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity(), CircularList.OnClickItemListener, CircularList.OnAnimationListener, CircularList.OnAnimationItemListener {

    var list = ArrayList<View>()
    var numItem = 5
    var adapter: ItemAdapter?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        createList(10)
        circularList.circleRadius = 200
        circularList.setOnClickItemListener(this)
                .setOnAnimationListener(this)
                .setOnAnimationItemListener(this)
                .start()


//        val listItem = ArrayList<ItemObj>()
//        var item = ItemObj()
//        item.img = R.drawable.barbecue
//        item.text = "Barbecue"
//        listItem.add(item)
//
//        item = ItemObj()
//        item.img = R.drawable.tomate
//        item.text = "Tomate"
//        listItem.add(item)
//
//        item = ItemObj()
//        item.img = R.drawable.manjericao
//        item.text = "Manjericão"
//        listItem.add(item)

//        adapter = ItemAdapter(listItem)
//        circularList.setAdapter(adapter!!)
    }

    /********* Animation Item Listener ************/
    override fun onAnimationStart(position: Int, item: View) {
//        var text = item.findViewById<View>(R.id.text)
//        text.visibility = View.INVISIBLE

    }

    override fun onAnimationEnd(position: Int, item: View) {
//        var text = item.findViewById<View>(R.id.text)
//        text.visibility = View.VISIBLE
    }

    /******** Animation Listener *********/
    override fun onAnimationStart() {
        showMsg("Start animation")
    }

    override fun onAnimationEnd() {
        showMsg("Finish animation")
    }

    /******* Click Item Listener ********/
    override fun onClickItem(position: Int, item: View) {
        showMsg("click item -> $position")
    }

    private fun showMsg(msg: String){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

//    private fun createList(total: Int){
//        list.clear()
//        for(i in 1..total){
//            //Item
//            var item = layoutInflater.inflate(R.layout.item_list, null)
//            list.add(item)
//
//        }
//    }


//    fun onClickTest(v: View){
//        createList(numItem)
//        circularList.animated = false
//        circularList.setItens(list)
//    }
//
//    fun onClickCenter(v: View){
//        createList(numItem)
//        var center = ImageView(this)
//        center.setImageDrawable(this.resources.getDrawable(R.drawable.abc_ab_share_pack_mtrl_alpha))
//        center.id = 123.toInt()
//
//        circularList.animated = false
//        circularList.setItens(center, list)
//    }
//
//    fun onClickAnimation(v: View){
//        createList(numItem)
//
//        circularList.animationDuration = 300
//        circularList.animationDelay = 0
//        circularList.animated = true
//        circularList.animationClockwise = true
//        circularList.setItens(list)
//    }
//
//    fun onClickAnimationDelay(v: View){
//        createList(numItem)
//
//        circularList.animationDuration = 300
//        circularList.animationDelay = TimeUnit.SECONDS.toMillis(2)
//        circularList.animated = true
//        circularList.animationClockwise = true
//        circularList.setItens(list)
//    }
//
//    fun onClickAnimationAntiClockwise(v: View){
//        createList(numItem)
//
//        circularList.animationDuration = 300
//        circularList.animationDelay = 0
//        circularList.animated = true
//        circularList.animationClockwise = false
//        circularList.setItens(list)
//    }
//
//    fun onClickAnimationDuration(v: View){
//        createList(numItem)
//
//        circularList.animationDuration = 1500
//        circularList.animationDelay = 0
//        circularList.animated = true
//        circularList.setItens(list)
//    }
}
