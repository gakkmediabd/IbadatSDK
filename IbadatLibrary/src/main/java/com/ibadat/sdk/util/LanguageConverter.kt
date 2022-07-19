package com.ibadat.sdk.util

object LanguageConverter {
    fun getDateInBangla(string: String): String {
        val banglaNumber = arrayOf('০', '১', '২', '৩', '৪', '৫', '৬', '৭', '৮', '৯')
        val engNumber = arrayOf('0', '1', '2', '3', '4', '5', '6', '7', '8', '9')
        var values = ""
        val character = string.toCharArray()
        for (i in character.indices) {
            var c: Char? = null
            for (j in engNumber.indices) {
                if (character[i] == engNumber[j]) {
                    c = banglaNumber[j]
                    break
                } else {
                    c = character[i]
                }
            }
            values += c
        }
        return values
    }

    fun convertToNumber(string: String): String {
        val banglaNumber = arrayOf('০', '১', '২', '৩', '৪', '৫', '৬', '৭', '৮', '৯')
        val engNumber = arrayOf('0', '1', '2', '3', '4', '5', '6', '7', '8', '9')
        var values = ""
        val character = string.toCharArray()
        for (i in character.indices) {
            var c: Char? = null
            for (j in engNumber.indices) {
                if (character[i] == engNumber[j]) {
                    c = banglaNumber[j]
                    break
                } else {
                    c = character[i]
                }
            }
            values += c
        }
        return values
    }

    fun getNumberByLocale(strValue: String): String {
        val number: Array<Char> =
//            if (AppPreference.language == "en" || AppPreference.language == "ms"
//            ) {
//                arrayOf('0', '1', '2', '3', '4', '5', '6', '7', '8', '9')
//            } else {
                arrayOf('০', '১', '২', '৩', '৪', '৫', '৬', '৭', '৮', '৯')
           // }
        val engNumber = arrayOf('0', '1', '2', '3', '4', '5', '6', '7', '8', '9')
        val values = StringBuilder()
        val character = strValue.toCharArray()
        for (c1 in character) {
            var c: Char? = null
            for (j in engNumber.indices) {
                if (c1 == engNumber[j]) {
                    c = number[j]
                    break
                } else {
                    c = c1
                }
            }
            values.append(c)
        }
        return values.toString()
    }


    fun getNumber(number: Int): String {
        return if (number < 10) {
            "0$number"
        } else number.toString()
    }
}