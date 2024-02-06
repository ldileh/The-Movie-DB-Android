package com.themoviedb.core.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.themoviedb.core.R

class ContainerView(context: Context?, attrs: AttributeSet?) : LinearLayout(context, attrs) {

    private val containerView: View
    private val viewError: View
    private val viewLoading: View
    private val tvError: TextView
    private val btnRetry: View

    private var contentViewId: Int = -1

    companion object{

        const val SHOW_VIEW_CONTENT = 0
        const val SHOW_VIEW_ERROR = 1
        const val SHOW_VIEW_LOADING = 2
    }

    init {
        inflate(context, R.layout.view_container, this)

        // init view
        containerView = findViewById(R.id.vc_view_container)
        viewError = findViewById(R.id.vc_view_error)
        viewLoading = findViewById(R.id.vc_view_loading)
        tvError = findViewById(R.id.vc_tv_error)
        btnRetry = findViewById(R.id.vc_btn_retry)

        // setting default attribute value
        context?.obtainStyledAttributes(attrs, R.styleable.ContainerView)?.apply {
            try {
                val showView = getInt(R.styleable.ContainerView_show_view, SHOW_VIEW_CONTENT)
                val errorMessage = getString(R.styleable.ContainerView_error_message)
                contentViewId = getInt(R.styleable.ContainerView_content_view_id, -1)

                setView(showView)
                tvError.text = errorMessage
            }finally {
                recycle()
            }
        }
    }

    private fun getContentView(): View? = if(contentViewId != -1) findViewById(contentViewId) else null

    fun setErrorMessage(message: String){
        tvError.text = message
    }

    fun setView(viewType: Int){
        when(viewType){
            SHOW_VIEW_ERROR -> {
                containerView.visibility = View.VISIBLE
                viewError.visibility = View.VISIBLE
                viewLoading.visibility = View.GONE
                getContentView()?.visibility = View.GONE
            }

            SHOW_VIEW_LOADING -> {
                containerView.visibility = View.VISIBLE
                viewError.visibility = View.GONE
                viewLoading.visibility = View.VISIBLE
                getContentView()?.visibility = View.GONE
            }

            else -> {
                containerView.visibility = View.GONE
                viewError.visibility = View.GONE
                viewLoading.visibility = View.GONE
                getContentView()?.visibility = View.VISIBLE
            }
        }
    }

    fun addListenerOnRetry(listener: OnClickListener){
        btnRetry.setOnClickListener(listener)
    }
}