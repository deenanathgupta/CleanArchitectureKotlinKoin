package com.android.cardinalhealthtask.presentation.base

import androidx.recyclerview.widget.DiffUtil


abstract class BaseDiff<M>(private val oldList: List<M>, private val newList: List<M>) : DiffUtil.Callback() {

    override fun areContentsTheSame(oldPosition: Int, newPosition: Int) = oldList[oldPosition] == newList[newPosition]

    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size
}