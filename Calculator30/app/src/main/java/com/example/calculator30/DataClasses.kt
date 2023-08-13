package com.example.calculator30

// equation ,result
data class History(var s1: String = "",var s2: String = "",var type: Int = 0)
//for viewmodel
data class viewModelDataClass(var a: History,var b: History)
//for notes
data class NoteInfo(var subject:String = "",var topics:String = "",
                    var title:String = "",var imgURL: String = "")