package com.daisy.picky.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.daisy.picky.R
import jp.wasabeef.blurry.Blurry

class ReadyDialog(context: Context
) : Dialog(context) {

    private lateinit var exitButton: TextView
    private lateinit var stayButton: TextView
    private lateinit var closeButton:TextView
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
        closeButton = findViewById(R.id.btn_close)
        exitText = findViewById(R.id.tx_exit)

        exitButton.visibility = View.GONE
        stayButton.visibility = View.GONE
        closeButton.visibility = View.VISIBLE
        exitText.text = "Coming soon!"

        closeButton.setOnClickListener {
            dismiss()
        }

    }
}
