package com.example.noteapp

class NoteItem {
    var noteId:Int?=null
    var noteName:String?=null
    var noteDesc:String?=null

    constructor(noteId: Int?, noteName: String?, noteDesc: String?) {
        this.noteId = noteId
        this.noteName = noteName
        this.noteDesc = noteDesc
    }
}