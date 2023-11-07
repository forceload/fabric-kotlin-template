package io.github.forceload.sample

import com.mojang.logging.LogUtils
import net.fabricmc.api.EnvType
import net.fabricmc.api.ModInitializer
import net.fabricmc.loader.api.FabricLoader
import net.minecraft.client.MinecraftClient

object SampleMod: ModInitializer {
    const val MOD_ID = "sample"
    private lateinit var loaderInstance: FabricLoader
    lateinit var clientInstance: MinecraftClient

    val logger = LogUtils.getLogger()

    override fun onInitialize() {
        loaderInstance = FabricLoader.getInstance()
        clientInstance = MinecraftClient.getInstance()

        when (loaderInstance.environmentType) {
            EnvType.CLIENT -> {
                TODO("Write Your Code")
            }

            else -> logger.error("This mode is only supported on the client!")
        }
    }
}