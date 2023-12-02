package com.daisy.picky.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.daisy.picky.R
import jp.wasabeef.blurry.Blurry


class ExitDialog(context: Context,
                 Interface: CustomDialogInterface
) : Dialog(context) {

    // 액티비티에서 인터페이스를 받아옴
    private var customDialogInterface: CustomDialogInterface = Interface

    private lateinit var exitButton: TextView
    private lateinit var stayButton: TextView
    private lateinit var dialogView: CardView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_exit)

        dialogView = findViewById(R.id.popup_exit)
        dialogView.setBackgroundResource(R.drawable.dialog_popup)

        Blur.setDialogBlur(window!!)

        exitButton = findViewById(R.id.btn_yes)
        stayButton = findViewById(R.id.btn_no)

        exitButton.setOnClickListener {
            customDialogInterface.onLeftButtonClicked()
            dismiss()
        }

        stayButton.setOnClickListener {
            customDialogInterface.onRightButtonClicked()
            dismiss()
        }



    }
}
