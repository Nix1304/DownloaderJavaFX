package ru.nix13.downloader.utils

import java.util.*

object Utils {
    private val letters = HashMap<String, String>()

    fun toTranslit(text: String): String {
        letters["А"] = "A"
        letters["Б"] = "B"
        letters["В"] = "V"
        letters["Г"] = "G"
        letters["Д"] = "D"
        letters["Е"] = "E"
        letters["Ё"] = "E"
        letters["Ж"] = "ZH"
        letters["З"] = "Z"
        letters["И"] = "I"
        letters["Й"] = "I"
        letters["К"] = "K"
        letters["Л"] = "L"
        letters["М"] = "M"
        letters["Н"] = "N"
        letters["О"] = "O"
        letters["П"] = "P"
        letters["Р"] = "R"
        letters["С"] = "S"
        letters["Т"] = "T"
        letters["У"] = "U"
        letters["Ф"] = "F"
        letters["Х"] = "H"
        letters["Ц"] = "C"
        letters["Ч"] = "CH"
        letters["Ш"] = "SH"
        letters["Щ"] = "SH"
        letters["Ъ"] = "'"
        letters["Ы"] = "Y"
        letters["Ъ"] = "'"
        letters["Э"] = "E"
        letters["Ю"] = "U"
        letters["Я"] = "YA"
        letters["а"] = "a"
        letters["б"] = "b"
        letters["в"] = "v"
        letters["г"] = "g"
        letters["д"] = "d"
        letters["е"] = "e"
        letters["ё"] = "e"
        letters["ж"] = "zh"
        letters["з"] = "z"
        letters["и"] = "i"
        letters["й"] = "i"
        letters["к"] = "k"
        letters["л"] = "l"
        letters["м"] = "m"
        letters["н"] = "n"
        letters["о"] = "o"
        letters["п"] = "p"
        letters["р"] = "r"
        letters["с"] = "s"
        letters["т"] = "t"
        letters["у"] = "u"
        letters["ф"] = "f"
        letters["х"] = "h"
        letters["ц"] = "c"
        letters["ч"] = "ch"
        letters["ш"] = "sh"
        letters["щ"] = "sh"
        letters["ъ"] = "'"
        letters["ы"] = "y"
        letters["ъ"] = "'"
        letters["э"] = "e"
        letters["ю"] = "u"
        letters["я"] = "ya"
        val sb = StringBuilder(text.length)
        for (i in 0 until text.length) {
            val l = text.substring(i, i + 1)
            if (letters.containsKey(l)) {
                sb.append(letters[l])
            } else {
                sb.append(l)
            }
        }
        return sb.toString()
    }
}