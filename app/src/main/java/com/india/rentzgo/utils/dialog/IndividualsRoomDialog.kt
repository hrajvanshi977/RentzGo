package com.india.rentzgo.utils.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.core.view.get
import com.india.rentzgo.R

class IndividualsRoomDialog(itemId: Int, propertyFields: EditText, propertyFieldsText: String) : AppCompatDialogFragment() {
    private val itemId: Int = itemId
    private val propertyFields: EditText = propertyFields
    private val propertyFieldsText: String = propertyFieldsText

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val layoutInflater = activity?.layoutInflater
        val view = layoutInflater!!.inflate(R.layout.layout_dialog, null)
        val propertyFieldsTextView = view.findViewById(R.id.propertyFieldsTextView) as TextView
        propertyFieldsTextView.setText(propertyFieldsText)
        val builder = AlertDialog.Builder(activity)

        setViewForDialog(view, itemId)
        val cancel = view.findViewById(R.id.cancel_dialog) as TextView

        val listView = view.findViewById(R.id.dialog_list) as ListView

        listView.setOnItemClickListener { parent, view, position, id ->
            propertyFields.setText(parent.getItemAtPosition(position).toString())
            dismiss()
        }
        cancel.setOnClickListener {
            dismiss()
        }
        builder.setView(view)
            .setPositiveButton("", DialogInterface.OnClickListener { dialog, which ->
            })
        return builder.create()
    }

    private fun setViewForDialog(view: View, itemId: Int) {
        val listView = view.findViewById(R.id.dialog_list) as ListView
        val adapter = ArrayAdapter.createFromResource(
            requireActivity(),
            itemId,
            R.layout.layout_dialogbox
        )
        listView.adapter = adapter
    }
}