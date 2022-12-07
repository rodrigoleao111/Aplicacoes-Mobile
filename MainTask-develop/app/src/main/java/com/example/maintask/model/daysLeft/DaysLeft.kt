package com.example.maintask.model.daysLeft

import java.time.LocalDate

class DaysLeft {
    companion object {
        fun getDaysLeftString(days: Int): String{
            val today = LocalDate.now().dayOfYear
            val daysLeft = days - today

            return when {
                daysLeft == 0 -> "Para hoje"
                daysLeft > 0 -> "Faltam ${daysLeft} dias"
                else -> "Esta há ${daysLeft * (-1)} dia(s) atrasado"
            }
        }
    }
}