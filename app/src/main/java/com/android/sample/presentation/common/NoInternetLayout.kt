package com.android.sample.presentation.common

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.widget.LinearLayoutCompat
import com.android.sample.extension.singleClickListener
import com.cardinalhealth.sample.R
import kotlinx.android.synthetic.main.no_intenet_layout.view.*

class NoInternetLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayoutCompat(context, attrs, defStyleAttr) {

    private var callback: Callback? = null

    init {
        View.inflate(context, R.layout.no_intenet_layout, this)
        retry_button.singleClickListener { callback?.onRetryClick() }
    }

    fun setCallbackListener(callback: Callback) {
        this.callback = callback
    }

    interface Callback {
        fun onRetryClick()
    }
}