package com.example.noteapp

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import kotlinx.android.synthetic.main.fragment_show_notes.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ShowNotesFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ShowNotesFragment : android.app.Fragment() {

    val listNotes = ArrayList<NoteItem>()

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_show_notes, container, false)



    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ShowNotesFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ShowNotesFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHasOptionsMenu(true)

        //dummy data
//        listNotes.add(NoteItem(1,"fff","wdoowdkokdwmdwomd"))
//        listNotes.add(NoteItem(2,"bbb","ojdojwdkokdkwdkdpk"))

        //from data base
        querySearch("%")
    }

     fun querySearch(search:String){
        var dbManager = DbManager(this!!.activity!!)

        //query line
        //1 projection --> the columns that i need
        val projection = arrayOf("ID","Title","Description")
        //2 selectionArgs --> behind WHERE value of specefec of raw , == value
        val selectionArgs = arrayOf(search)
        //selection --> behind WHERE the column name
        val selection = "Title like ?"

        val cursor = dbManager.query(projection,selection,selectionArgs,"Title")

        if (cursor.moveToFirst()){
            listNotes.clear()
            do {
                val id = cursor.getInt(cursor.getColumnIndex("ID"))
                val title = cursor.getString(cursor.getColumnIndex("Title"))
                val desc = cursor.getString(cursor.getColumnIndex("Description"))

                listNotes.add(NoteItem(id,title,desc))

                var myAdapter = NoteAdapter(this!!.activity!!,listNotes)
                notes_list.adapter = myAdapter

            }while (cursor.moveToNext())
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.show_notes_menu,menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item!!.itemId){
            R.id.addNotes ->{
                view!!.findNavController().navigate(R.id.action_showNotesFragment_to_addNotesFragment)
            }
        }
        return super.onOptionsItemSelected(item)
    }


}