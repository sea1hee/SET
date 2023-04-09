package com.daisy.picky

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.widget.TextView

class ExitDialog(context: Context, Interface: CustomDialogInterface) : Dialog(context) {

    // 액티비티에서 인터페이스를 받아옴
    private var customDialogInterface: CustomDialogInterface = Interface

    private lateinit var exitButton: TextView
    private lateinit var stayButton: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_exit)

        exitButton = findViewById(R.id.btn_yes)
        stayButton = findViewById(R.id.btn_no)

        // 배경을 투명하게함
        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        exitButton.setOnClickListener {
            customDialogInterface.onExitButtonClicked()
            dismiss()
        }

        stayButton.setOnClickListener {
            customDialogInterface.onStayButtonClicked()
            dismiss()
        }
    }
}

interface CustomDialogInterface {
    fun onExitButtonClicked()

    fun onStayButtonClicked()
}