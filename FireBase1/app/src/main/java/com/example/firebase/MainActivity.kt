package com.example.firebase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import com.google.android.gms.common.util.Strings
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    var notes = ArrayList<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //list view adapter
        val arrayAdapter :ArrayAdapter<String> = ArrayAdapter<String>(this,R.layout.item_row,R.id.itemViewList,notes)
        listView.adapter = arrayAdapter


        val db : DatabaseReference = FirebaseDatabase.getInstance().reference
        btnDb.setOnClickListener {
            //data class for storing in subnodes

            val note = Note("note","World")
            //upload note to firebase
           // db.push().setValue(note)
            //push -> new node
            //child -> new folder thingy new sub node in parent
            db.child("note").push().setValue(note)
        }
       db.child("note").addChildEventListener(object : ChildEventListener{
            //when data node added to "note" node
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                notes.add(snapshot.getValue(String::class.java)!!)
                arrayAdapter.notifyDataSetChanged()
               /* if(snapshot.getValue(String::class.java) != null){
                    val data = snapshot.getValue(String::class.java)
                    notes.add(data!!)
                    arrayAdapter.notifyDataSetChanged()
                }*/
            }
            //existing data node changed
            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                TODO("Not yet implemented")
            }
            //when data is removed
            override fun onChildRemoved(snapshot: DataSnapshot) {
                TODO("Not yet implemented")
            }
            //when position subNode changes
            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                TODO("Not yet implemented")
            }
            //read operation failed
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}