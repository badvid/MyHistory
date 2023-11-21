package com.bad.mifamilia.models

data class Family(val idPerson: Int,
                  val idParent: Int,
                  val idUser: Int,
                  val dateCreated: String,
                  val firstName: String,
                  val lastName: String,
                  val nroDoc: String,
                  val email: String,
                  val descrip: String,
                  val photo: String,
                  val oParent: Parent)