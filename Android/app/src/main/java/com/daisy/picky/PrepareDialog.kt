package com.daisy.picky

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.TextView
import androidx.cardview.widget.CardView
import jp.wasabeef.blurry.Blurry

class PrepareDialog(val point:Int, context: Context,
                     Interface: CustomDialogInterface) : Dialog(context){

    // 액티비티에서 인터페이스를 받아옴
    private var customDialogInterface: CustomDialogInterface = Interface

    private lateinit var exitButton: TextView
    private lateinit var stayButton: TextView
    private lateinit var dialogView: CardView
    private lateinit var exitText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_exit)

        dialogView = findViewById(R.id.popup_exit)
        dialogView.setBackgroundResource(R.drawable.dialog_popup)

        Blurry.with(context)
            .radius(25)
            .sampling(2)
            .async()
            .animate(500)
            .onto(dialogView)

        exitButton = findViewById(R.id.btn_yes)
        stayButton = findViewById(R.id.btn_no)
        exitText = findViewById<TextView>(R.id.tx_exit)

        stayButton.text = "Again"
        exitText.text = "Finish\nYou got " + point + " point"

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
