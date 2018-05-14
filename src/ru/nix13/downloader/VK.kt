package ru.nix13.downloader

import com.vk.api.sdk.client.VkApiClient
import com.vk.api.sdk.client.actors.UserActor
import com.vk.api.sdk.httpclient.HttpTransportClient

object VK {
    val vk = VkApiClient(HttpTransportClient.getInstance())
    val actor = UserActor(Config.fromJSON().id, Config.fromJSON().accessToken)
    val sleep = Config.fromJSON().sleep
}