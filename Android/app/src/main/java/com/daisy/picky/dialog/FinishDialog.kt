package com.daisy.picky.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.daisy.picky.R

class FinishDialog(val found:Int, val gameMode:Int, context: Context,
                   Interface: CustomDialogInterface
) : Dialog(context){

    // 액티비티에서 인터페이스를 받아옴
    private var customDialogInterface: CustomDialogInterface = Interface

    private lateinit var exitButton: TextView
    private lateinit var stayButton: TextView
    private lateinit var dialogView: CardView
    private lateinit var exitText: TextView
    private lateinit var scoreText: TextView
    private lateinit var finishText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_exit)

        dialogView = findViewById(R.id.popup_exit)
        dialogView.setBackgroundResource(R.drawable.dialog_popup)

        Blur.setDialogBlur(window!!)

        exitButton = findViewById(R.id.btn_yes)
        stayButton = findViewById(R.id.btn_no)
        exitText = findViewById(R.id.tx_exit)
        scoreText = findViewById(R.id.tx_score)
        finishText = findViewById(R.id.tx_finish)

        exitText.visibility=View.GONE
        finishText.visibility = View.VISIBLE
        scoreText.visibility = View.VISIBLE

        stayButton.text = context.resources.getString(R.string.dialog_finish_button2)
        if (gameMode == 1) {
            scoreText.text = context.resources.getString(R.string.dialog_finish)
        } else {
            if (found == 0) {
                scoreText.text =
                    context.resources.getString(R.string.dialog_finish_minute_mode_1) +" "+ found.toString() +" "+ context.resources.getString(
                        R.string.dialog_finish_minute_mode_2
                    )
            } else {
                scoreText.text =
                    context.resources.getString(R.string.dialog_finish_minute_mode_1) +" "+ found.toString() +" "+ context.resources.getString(
                        R.string.dialog_finish_minute_mode_2_1
                    )
            }
        }

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
