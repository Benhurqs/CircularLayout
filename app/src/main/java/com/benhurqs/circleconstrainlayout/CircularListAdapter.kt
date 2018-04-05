package com.benhurqs.circleconstrainlayout

import android.content.Context
import android.view.View
import java.util.*

/**
 * Created by benhur.souza on 05/04/18.
 */
interface CircularListAdapter<Holder: CircularListAdapter.CircularHolder>{

    interface CircularHolder{

    }

    fun onCreateItemView(parent: Context): View
    fun onBindViewHolder(holder: Holder, position: Int)
}