package com.hajarslamah.task_management

import android.app.AlertDialog
import android.app.Dialog
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import java.util.*
private const val REQUEST_DATE = 0
private const val DIALOG_DATE = "DialogDate"
class InputDialogFragment:DialogFragment() ,DatePickerFragment.Callbacks{

    val task=TaskMang()
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val view=activity?.layoutInflater?.inflate(R.layout.new_dailog_task,null)
        val titleEditText=view?.findViewById(R.id.title) as EditText
        val detailsEditText=view?.findViewById(R.id.details) as EditText
        val dateButton =view?.findViewById(R.id.task_date) as Button
      dateButton.setOnClickListener {
          DatePickerFragment().apply {
              DatePickerFragment.newInstance(task.time_end).apply {
                  setTargetFragment(this@InputDialogFragment, REQUEST_DATE)
                  show(this@InputDialogFragment.requireFragmentManager(), DIALOG_DATE)
              }}}

        dateButton.setText(task.time_end.toString())

        return AlertDialog.Builder(requireContext(),R.style.ThemeOverlay_AppCompat_Dialog_Alert)
            .setView(view)
            .setPositiveButton("Add"){ dialog,_ ->
                val task=TaskMang(
                    UUID.randomUUID(),
                    titleEditText.text.toString(),
                    detailsEditText.text.toString() ,
                    task.time_end
                )
                targetFragment?.let { fragment ->
                    (fragment as Callbacks).addTask(task)
                }
            }.setNegativeButton("Cancel"){dialog,_ ->
                dialog.cancel()
           }.create()

                  }
//    override fun onStart() {
//        super.onStart()
//
//        val titleWatcher = object : TextWatcher {
//            override fun beforeTextChanged(
//                sequence: CharSequence?,
//                start: Int,
//                count: Int,
//                after: Int
//            )
//            {
//                // This space intentionally left blank
//            }
//
//
//            override fun onTextChanged(
//                sequence: CharSequence?,
//                start: Int,
//                before: Int,
//                count: Int
//            ) {
//                task.title_task = sequence.toString()
//                task.details = sequence.toString()
//            }
//
//
//            override fun afterTextChanged(p0: Editable?) {
//
//            }
//
//        }
//
//        titleEditText.addTextChangedListener(titleWatcher)


interface Callbacks {
    fun addTask(task: TaskMang)

}
    override fun onDateSelected(date: Date) {
        task.time_end=date
        //updateUI()
    }
}