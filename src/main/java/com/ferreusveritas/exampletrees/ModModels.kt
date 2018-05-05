package com.ferreusveritas.exampletrees


import net.minecraftforge.client.event.ModelRegistryEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly

@Mod.EventBusSubscriber(modid = ModConstants.MODID)
object ModModels {

    @JvmStatic
    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    fun registerModels(event: ModelRegistryEvent) {
        ExampleTrees.proxy!!.registerModels()
    }

}