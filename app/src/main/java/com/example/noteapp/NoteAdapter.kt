package com.example.noteapp

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import kotlinx.android.synthetic.main.note_item.view.*

class NoteAdapter:BaseAdapter {
    //1 list and context
    var listNoteAdapter= ArrayList<NoteItem>()
    var context:Context?=null

    //2 constructor
    constructor(context: Context,listNoteAdapter: ArrayList<NoteItem>):super(){
        this.context=context
        this.listNoteAdapter=listNoteAdapter
    }


    override fun getCount(): Int {

        return listNoteAdapter.size
    }

    override fun getItem(position: Int): Any {

        return listNoteAdapter[position]
    }

    override fun getItemId(position: Int): Long {

        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        val layoutInflater:LayoutInflater = LayoutInflater.from(context)
        var myView = layoutInflater.inflate(R.layout.note_item,null)
        val note = listNoteAdapter[position]

        myView.show_title.text = note.noteName
        myView.show_note.text =note.noteDesc

        //delete
        myView.bt_delete.setOnClickListener{
            val dbManager=DbManager(this.context!!)
            val selectionArgs = arrayOf(note.noteId.toString())
            dbManager.delete("ID=?",selectionArgs)
            try {
                val showNotesFragment = ShowNotesFragment()
                showNotesFragment.querySearch("%")
            }catch (ex:Exception){}
        }

        // update
        myView.bt_edit.setOnClickListener {
            goToUpdate(note)
        }

        return myView
    }

    fun goToUpdate(note: NoteItem) {

        var bundle= Bundle()
        bundle.putInt("Id", note.noteId!!)
        bundle.putString("Title",note.noteName)
        bundle.putString("Desc",note.noteDesc)

//        view!!.findNavController().navigate(R.id.showNotesFragment)

    }


}