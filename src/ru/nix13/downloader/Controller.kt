package ru.nix13.downloader

import javafx.fxml.FXML
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.scene.control.TextArea
import javafx.scene.control.TextField
import javafx.stage.Stage
import java.io.File
import java.io.FileOutputStream
import java.net.URL
import java.nio.channels.Channels

class Controller {
    companion object {
        lateinit var links_list: TextArea
        lateinit var save_name: TextField
        lateinit var extension: TextField
    }

    @FXML
    private fun download() {
        val links: List<String> = links_list.text.split("\n")
        var saveName = save_name.text
        if (links[0].isNotEmpty()) for (i in links.indices) {
            val url = URL(links[i])
            val ext: String = if (extension.text.isEmpty()) File(links[i]).extension else extension.text
            val rbc = Channels.newChannel(url.openStream())
            if (saveName.isEmpty()) saveName = "untitled"
            val fos = FileOutputStream(saveName + "_" + (i + 1).toString() + "." + ext)
            fos.channel.transferFrom(rbc, 0, java.lang.Long.MAX_VALUE)
            fos.close()
            rbc.close()
        }
    }

    @FXML
    private fun openVKFunctions() {
        try {
            val fxmlLoader = FXMLLoader(javaClass.getResource("vk.fxml"))
            val root = fxmlLoader.load<Any>() as Parent
            val stage = Stage()
            stage.scene = Scene(root)
            stage.isResizable = false
            stage.show()
            VKController.group_id = root.lookup("#group_id") as TextField
            VKController.query = root.lookup("#query") as TextField
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}