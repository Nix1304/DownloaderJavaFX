package ru.nix13.downloader

import com.google.gson.GsonBuilder
import com.google.gson.annotations.SerializedName
import java.io.File

class Config {
    var sleep: Long = 0

    @SerializedName("Access Token")
    lateinit var accessToken: String

    @SerializedName("User ID")
    var id: Int = 0

    companion object {
        fun createFile() {
            val output = File("config.json")
            output.createNewFile()
            output.writeText("{\n" +
                    "   \"sleep\": 0,\n" +
                    "   \"Access Token\": \"at\",\n" +
                    "   \"User ID\": 1\n" +
                    "}")
        }

        fun fromJSON(): Config {
            val input = File("config.json")
            lateinit var conf: Config
            if (input.exists()) {
                val builder = GsonBuilder()
                val gson = builder.create()
                conf = gson.fromJson(input.readText(), Config::class.java)
            }
            return conf
        }
    }
}