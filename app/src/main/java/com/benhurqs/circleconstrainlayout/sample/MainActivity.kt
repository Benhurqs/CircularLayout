package com.benhurqs.circleconstrainlayout.sample

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.benhurqs.circleconstrainlayout.R
import com.benhurqs.circularlayout.animation.AnimationType
import com.benhurqs.circularlayout.widget.CircularLayout
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), CircularLayout.OnClickItemListener, CircularLayout.OnAnimationListener, CircularLayout.OnAnimationItemListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        circularLayout
//                .setAnimationType(AnimationType.ANTICLOCKWISE)
                .setCircleRadius(200)
                .setOnClickItemListener(this)
                .setOnAnimationListener(this)
                .setOnAnimationItemListener(this)
                .start()

    }

    /********* Animation Item Listener ************/
    override fun onAnimationStart(position: Int, item: View) {

    }

    override fun onAnimationEnd(position: Int, item: View) {
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

    fun onClickTest(v: View){
        circularLayout.start()
    }

}
