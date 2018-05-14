package ru.nix13.downloader

import com.vk.api.sdk.exceptions.ApiAccessAlbumException
import javafx.fxml.FXML
import javafx.scene.control.TextField
import ru.nix13.downloader.VK.actor
import ru.nix13.downloader.VK.sleep
import ru.nix13.downloader.VK.vk
import ru.nix13.downloader.utils.Utils.toTranslit
import java.io.File
import java.io.FileOutputStream
import java.net.URL
import java.nio.channels.Channels
import java.nio.channels.ReadableByteChannel
import java.nio.file.Files
import java.nio.file.Paths

class VKController {
    companion object {
        lateinit var group_id: TextField
        lateinit var query: TextField
        private val photosPath = System.getProperty("user.dir") + "/vk/photos/"
        private val docsPath = System.getProperty("user.dir") + "/vk/docs/"
        private val groupPath = System.getProperty("user.dir") + "/vk/groups/"
    }

    @FXML
    private fun downloadFromGroupWall() {
        val groupId = getGroupId(group_id.text)
        val wall = vk.wall().get(actor).ownerId(groupId).execute()
        val attachmentSize = wall.items[0].attachments.size - 1
        val type = wall.items[0].attachments[attachmentSize].type.value
        for (i in 0..wall.count) {
            if (type == "photo") {
                val wallAlbumSizes = vk.photos().get(actor).ownerId(groupId).albumId("wall").photoSizes(true).count(1).offset(i).execute().items[0].sizes
                val url = URL(wallAlbumSizes[wallAlbumSizes.size.minus(1)].src)
                val rbc = Channels.newChannel(url.openStream())
                try {
                    if (!File("$groupPath/${group_id.text}").exists()) Files.createDirectories(Paths.get("$groupPath/${group_id.text}"))
                    if (!File("$groupPath/${group_id.text}/${group_id.text}_${i.plus(1)}.jpg").exists()) {
                        Thread.sleep(sleep)
                        val fos = FileOutputStream("$groupPath/${group_id.text}/${group_id.text}_${i.plus(1)}.jpg")
                        fos.channel.transferFrom(rbc, 0, java.lang.Long.MAX_VALUE)
                        fos.close()
                    }
                } catch (e: ApiAccessAlbumException) {
                    e.printStackTrace()
                } catch (e: IndexOutOfBoundsException) {
                }
                rbc.close()
            }
        }
    }

    @FXML
    private fun searchAndDownloadDocs() {
        val count = vk.docs().search(actor, query.text).execute().count
        for (i in 0..count) {
            Thread.sleep(sleep)
            val docItems = vk.docs().search(actor, query.text).count(1).offset(i).execute().items[0]
            val ext = docItems.ext
            if (!File(docsPath + query.text + "/" + query.text + "_" + ("${i.plus(1)}.$ext")).exists()) {
                val doc = docItems.ownerId.toString() + "_" + docItems.id.toString()
                val url = URL(vk.docs().getById(actor, doc).execute()[0].url)
                val rbc = Channels.newChannel(url.openStream())
                val path = docsPath + query.text
                val f = File("$path/${query.text}_${i.plus(1)}.$ext")
                if (!File(path).exists()) Files.createDirectories(Paths.get(docsPath + query.text))
                val fos = FileOutputStream(f)
                fos.channel.transferFrom(rbc, 0, java.lang.Long.MAX_VALUE)
                fos.close()
            }
        }
    }

    @FXML
    private fun downloadAllAlbums() {
        val count = vk.photos().getAlbums(actor).ownerId(Config.fromJSON().id).execute().count
        var fos: FileOutputStream
        var rbc: ReadableByteChannel
        for (i in 0..count) {
            Thread.sleep(sleep)
            val albums = vk.photos().getAlbums(actor).ownerId(Config.fromJSON().id).offset(i).execute()
            val id = albums.items[0].id
            val photosCount = albums.items[0].size.minus(1)
            val name = albums.items[0].title
            for (j in 0..photosCount) {
                if (!File(photosPath + toTranslit(name)).exists()) Files.createDirectories(Paths.get(photosPath + toTranslit(name)))
                Thread.sleep(200)
                val photos = vk.photos().get(actor).albumId(id.toString()).ownerId(Config.fromJSON().id).offset(j).count(1).photoSizes(true).execute()
                val sizesIndx = photos.items[0].sizes.size.minus(1)
                val url = URL(photos.items[0].sizes[sizesIndx].src)
                fos = FileOutputStream("$photosPath${toTranslit(name)}/${toTranslit(name)}_${j.plus(1)}.jpg")
                rbc = Channels.newChannel(url.openStream())
                fos.channel.transferFrom(rbc, 0, Long.MAX_VALUE)
                fos.close()
                rbc.close()
            }
        }
    }

    private fun getGroupId(group: String): Int = -vk.groups().getById(actor).groupId(group).execute()[0].id
}