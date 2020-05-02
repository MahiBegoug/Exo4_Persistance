package com.example.roomtesting.ui


import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment

import com.example.roomtesting.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_add_note.*
import java.text.SimpleDateFormat
import java.util.*
import android.graphics.drawable.ColorDrawable
import android.graphics.Color
import android.os.AsyncTask
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.navigation.Navigation
import com.example.roomtesting.db.Note
import com.example.roomtesting.db.NoteDataBase
import kotlinx.coroutines.launch
import net.mm2d.color.chooser.ColorChooserDialog
import android.view.MenuItem
import android.view.Menu




class AddNoteFragment : BaseFragment(),ColorChooserDialog.Callback{

    private var colorcode: Int = 0
    private var _date : String? = null
    private var note: Note? = null

    lateinit var mDateListener : DatePickerDialog.OnDateSetListener

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_add_note, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }

    companion object {
        private const val REQUEST_CODE = 10
    }

    override fun onColorChooserResult(requestCode: Int, resultCode: Int, color: Int) {
        if (requestCode != REQUEST_CODE) return
        this.colorcode = color
        //sample.setBackgroundColor(color)
    }


    private fun getDate()
    {

    }

    private fun getColorCode()
    {

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        arguments?.let {
            note = AddNoteFragmentArgs.fromBundle(it).note
            text_title.setText(note?.title)
            text_note.setText(note?.body)
        }

        date_btn.setOnClickListener {
            var calendar = Calendar.getInstance()
            var year = calendar.get(Calendar.YEAR)
            var month = calendar.get(Calendar.MONTH)
            var day = calendar.get(Calendar.DAY_OF_MONTH)

            val picker = DatePickerDialog(
                requireContext(),
                android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                mDateListener,year,month,day
            )

            picker.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            picker.show()
        }

        mDateListener = DatePickerDialog.OnDateSetListener { datePicker, year, month, day ->
            var cal = Calendar.getInstance()
            cal.set(year,month,day)
            val myFormat = "dd/MM/yyyy" // mention the format you need
            val sdf = SimpleDateFormat(myFormat, Locale.US)
            //Log.i("time",sdf.format(cal.time))

            this._date = sdf.format(cal.time)
        }



        color_btn.setOnClickListener { ColorChooserDialog.show(this,REQUEST_CODE,colorcode) }

        save_btn.setOnClickListener {
            val noteTitle = text_title.text.toString().trim()
            val noteBody = text_note.text.toString().trim()
            val noteDate = this._date
            val noteColor = this.colorcode

            if (noteTitle.isEmpty()){
                text_title.error = "title required"
                text_note.requestFocus()
                return@setOnClickListener
            }

            if (noteBody.isEmpty()){
                text_note.error = "note required"
                text_note.requestFocus()
                return@setOnClickListener
            }

            launch {
                val mNote = Note(noteTitle,noteBody,"15/12/2020",noteColor)


                context?.let {
                    if (note==null) {

                        NoteDataBase(it).getNoteDao().addNote(mNote)
                        it.toast("Note Saved")

                    } else {
                        mNote.id = note!!.id
                        NoteDataBase(it).getNoteDao().updateNote(mNote)
                        it.toast("Note updated")
                    }

                    val action = AddNoteFragmentDirections.actionSaveNote()
                    view?.let { it1 -> Navigation.findNavController(it1).navigate(action) }
                }

            }

        }

    }

    private fun deleteNote() {
        AlertDialog.Builder(context).apply {
            setTitle("Are youe ")
            setMessage("You cannot undo this operation")
            setPositiveButton("Yes") {_,_ ->
                launch {
                    NoteDataBase(context).getNoteDao().deleteNote(note!!)
                    val action = AddNoteFragmentDirections.actionSaveNote()
                    view?.let { it1 -> Navigation.findNavController(it1).navigate(action) }
                }
            }
            setNegativeButton("No"){_,_->}
        }.create().show()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId){
            R.id.delete -> if (note != null) deleteNote() else context?.toast("Cannot Delete")
        }
        return super.onOptionsItemSelected(item)
    }

    //inflate the menu
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)
    }

}