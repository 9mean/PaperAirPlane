package com.example.paperairplane

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.util.Log
import android.view.View
import android.view.WindowManager
import com.example.paperairplane.`interface`.MainOptionClickInterface
import com.example.paperairplane.databinding.DialogMainOptionBinding

class MainOptionDialog(context: Context,clickInterface: MainOptionClickInterface):Dialog(context){
    private val dialog=Dialog(context)
    var mclickInterface=clickInterface
    lateinit var dialogMainOptionBinding:DialogMainOptionBinding
    val dialogView=layoutInflater.inflate(R.layout.dialog_main_option, null)
    override fun show(){
        Log.d("TAG", "show: ")
        mclickInterface.click(1)
        dialog.show()
    }

    override fun onDetachedFromWindow() {
        Log.d("TAG", "detach: ")
        super.onDetachedFromWindow()
    }

    override fun setOnDismissListener(listener: DialogInterface.OnDismissListener?) {
        Log.d("TAG", "dialogd listeber ")
        super.setOnDismissListener(listener)
    }
    override fun dismiss() {
        //mainOptionView.setBackgroundResource(R.drawable.home_options)
        Log.d("TAG", "dismiss: ")
        mclickInterface.click(0)
        dialog.dismiss()
    }

    override fun cancel() {
        Log.d("TAG", "Cancle: ")
        mclickInterface.click(0)
        super.cancel()
    }
    fun myDig(){
        dialogMainOptionBinding= DialogMainOptionBinding.inflate(layoutInflater)
        dialog.setContentView(dialogMainOptionBinding.root)
        dialog.window!!.setBackgroundDrawableResource(android.R.color.transparent);
        dialog.window!!.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT)
        dialog.setCanceledOnTouchOutside(true)
        dialog.setCancelable(true)
        dialogMainOptionBinding.people.setOnClickListener {
            mclickInterface.click(2)
        }
        dialogMainOptionBinding.food.setOnClickListener {
            mclickInterface.click(3)
        }
        dialogMainOptionBinding.map.setOnClickListener {
            mclickInterface.click(4)
        }
        dialogMainOptionBinding.faq.setOnClickListener {
            mclickInterface.click(5)
        }
        dialogMainOptionBinding.street.setOnClickListener {
            mclickInterface.click(6)
        }
        dialogMainOptionBinding.setting.setOnClickListener {
            mclickInterface.click(7)
        }
    }

}