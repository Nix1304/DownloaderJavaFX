package ru.nix13.downloader

import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.scene.control.TextArea
import javafx.scene.control.TextField
import javafx.stage.Stage
import java.io.File

class Main : Application() {
    override fun start(primaryStage: Stage) {
        val root = FXMLLoader.load<Parent>(javaClass.getResource("main.fxml"))
        primaryStage.title = "Downloader"
        primaryStage.scene = Scene(root)
        primaryStage.isResizable = false
        Controller.links_list = root.lookup("#links") as TextArea
        Controller.save_name = root.lookup("#save_name") as TextField
        Controller.extension = root.lookup("#extension") as TextField
        primaryStage.show()
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            if(!File("config.json").exists()) Config.createFile()
            launch(Main::class.java)
        }
    }
}