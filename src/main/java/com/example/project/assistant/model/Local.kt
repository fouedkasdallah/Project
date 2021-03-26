package com.example.project.assistant.model

data class Local(
    var Zone: String? = null,
    var immeuble: String? = null,
    var etage: String? = null,
    var nom: String? = null,
    var designation: String? = null,
    var type: String? = "zone",
    var currentState: Int = 0 ) {

    fun goToState(s : Int){
        when(s){
            0-> {
                Zone = null
                immeuble = null
                etage = null
                nom = null
                designation = null
                type = "zone"
                currentState=0
            }
            1 ->{
                Zone=designation
                immeuble = null
                etage = null
                nom = null
                designation = null
                type="immeuble"
                currentState=1
            }
            2 -> {
                immeuble=designation
                etage = null
                nom = null
                designation = null
                type="etage"
                currentState=2
            }
            3 -> {
                etage=designation
                nom = null
                designation = null
                type="lieu"
                currentState=3
            }
            4 ->{
                nom=designation
                designation = null
                currentState=4
            }

        }


    }
    fun goToNextState(){
        goToState(currentState+1)
    }
}