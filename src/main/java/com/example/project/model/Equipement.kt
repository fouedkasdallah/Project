package com.example.project.model

data class Equipement(
    var Inv: String? = null,
    var Type: String? = "",
    var NS: String? = "",
    var Marque: String? = "",
    var Modele: String? = "",
    var Localisation: String? = null,
    var Utilisateur: String? = null,
    var Info: String? = "",
    var Affected: Boolean? = null,
    var NestedAffectation: String? = null)
{
    override fun toString(): String {
        //return super.toString()
        return "N° d'inventaire : $Inv\n NS :$NS\n Type :$Type\n Modèle :$Marque $Modele\n Lieu :$Localisation\n\n Nouvelle affectation : $NestedAffectation\n utilisateur : $Utilisateur\n $eqenroute"
    }
    var eqenroute: String = when (Affected) {
        false-> "equipement en route"
        true -> "equipement affecte"
        null -> ""
    }
}

data class EquipementId(
    var Inventaire: String? = null,
    var equipement: Equipement = Equipement()
)