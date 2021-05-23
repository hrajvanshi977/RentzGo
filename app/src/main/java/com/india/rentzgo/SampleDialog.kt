package com.india.rentzgo

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatDialogFragment

class SampleDialog(textInDialog: String) : AppCompatDialogFragment() {
    private val textInDialog: String = textInDialog
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val layoutInflater = activity?.layoutInflater
        val dialogView = layoutInflater!!.inflate(R.layout.sample_dialog, null)
        val sampleDialogTextView = dialogView.findViewById<TextView>(R.id.sampleDialogTextView)
        sampleDialogTextView.setText(textInDialog)
        val builder = AlertDialog.Builder(activity)
        val cancel_dialog = dialogView.findViewById(R.id.cancel_dialog) as TextView
        cancel_dialog.setOnClickListener {
            dismiss()
        }
        builder.setView(dialogView).setPositiveButton("") { dialog, which ->
        }.create()
        return builder.create()
    }
}