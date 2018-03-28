package ru.nix13.downloader

import javafx.fxml.FXML
import javafx.scene.control.TextArea
import javafx.scene.control.TextField
import java.io.FileOutputStream
import java.net.URL
import java.nio.channels.Channels
import java.io.File

class Controller {
    companion object {
        lateinit var links_list: TextArea
        lateinit var save_name: TextField
    }

    @FXML
    private fun download(){
        val links: List<String> = links_list.text.split("\n")
        var saveName = save_name.text
        if(links[0].isNotEmpty())
        for (i in links.indices){
            val url = URL(links[i])
            println(links[i])
            val ext = File(links[i]).extension
            val rbc = Channels.newChannel(url.openStream())
            if(saveName.isEmpty()) saveName = "untitled"
            val fos = FileOutputStream(saveName + "_" + (i + 1).toString() + "." + ext)
            fos.channel.transferFrom(rbc, 0, java.lang.Long.MAX_VALUE)
            fos.close()
        }
    }
}