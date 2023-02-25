package com.example.caas

import java.io.Serializable

data class BadaList(var listOfRecords: MutableList<Record>): Serializable
