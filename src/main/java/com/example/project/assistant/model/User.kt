package com.example.project.assistant.model

data class User(var matricule : String? = null,
                var nom : String?=null,
                var role : String?=null ,
                var lieuxversion : Int=0,
                var equipementsversion : Int =0)
