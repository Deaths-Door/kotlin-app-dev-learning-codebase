package com.example.calculator30.viewmodel

import android.util.Log
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.calculator30.History
import com.example.calculator30.NoteInfo
import com.example.calculator30.fragments.pages.Notes
import com.example.calculator30.viewModelDataClass
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.*

class HistoryViewModel: ViewModel() {
    //written numbers on cal
    val writtenNums = MutableLiveData<viewModelDataClass>()
    //change the values
    fun changeValue(index:Int ,newValue: History){
        runIo{
            if(index == 0){
                var x = writtenNums.value?.b
                if(x == null){
                    x = History("","")
                }
                writtenNums.postValue(viewModelDataClass(newValue,x))
            }
            else{
                var x = writtenNums.value?.a
                if(x == null){
                    x = History("","")
                }
                writtenNums.postValue(viewModelDataClass(x,newValue))
            }
           // writtenNums.postValue(newValue)
        }
    }
    //textview convertor
    var lastClicked = 0

    //all notes
    private val fsNotes = FirebaseFirestore.getInstance().collection("notes")
    val allNotes = ArrayList<NoteInfo>()
    //var done = false
    fun getAllNotes(){
        fsNotes.get().addOnSuccessListener { documents ->
            val x = documents.toObjects(NoteInfo::class.java)
            allNotes.addAll(x)
          //  allNotes.postValue(x)
            // allNotes = documents.toObjects(NoteInfo::class.java)
            //  allNotes as MutableLiveData<*>
            //done = true
            // Notes().setRvAdapter()
        }
     //   withContext(Dispatchers.Default) {

       // }
    }

}
/**extension function for [ViewModel] scope**/
fun ViewModel.runIo(
    /*Custom type of dispatchers is what thread or threads the corresponding coroutine uses for its execution*/
    dispatchers: CoroutineDispatcher = Dispatchers.IO, function:suspend CoroutineScope.() -> Unit){
    //equals this
    viewModelScope.launch(dispatchers) {
        function()
    }
}