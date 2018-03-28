package ru.nix13.downloader

import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.scene.control.ProgressBar
import javafx.scene.control.TextArea
import javafx.scene.control.TextField
import javafx.stage.Stage

class Main: Application() {
    override fun start(primaryStage: Stage) {
        val root = FXMLLoader.load<Parent>(javaClass.getResource("main.fxml"))
        primaryStage.title = "Downloader"
        primaryStage.scene = Scene(root)
        Controller.links_list = root.lookup("#links") as TextArea
        Controller.save_name = root.lookup("#save_name") as TextField
        primaryStage.show()
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>){
            launch(Main::class.java)
        }
    }
}