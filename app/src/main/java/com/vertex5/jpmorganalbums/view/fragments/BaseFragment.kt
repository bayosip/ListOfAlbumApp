package com.vertex5.jpmorganalbums.view.fragments

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.vertex5.jpmorganalbums.model.util.GeneralUtils
import com.vertex5.jpmorganalbums.view.FragmentListener

abstract class BaseFragment: Fragment() {

    protected var listener: FragmentListener?=null
    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as FragmentListener
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialiseWidgets()
    }

    protected  abstract fun initialiseWidgets()

    protected fun showToast(msg:String){
        activity?.let { GeneralUtils.message(it, msg) }
    }
}