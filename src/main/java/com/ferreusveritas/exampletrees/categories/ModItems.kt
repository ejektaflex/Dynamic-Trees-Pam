package com.ferreusveritas.exampletrees.categories

import com.ferreusveritas.dynamictrees.api.TreeHelper
import com.ferreusveritas.exampletrees.ModConstants
import net.minecraft.block.Block
import net.minecraft.item.Item
import net.minecraft.item.ItemBlock
import net.minecraftforge.event.RegistryEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import java.util.*

@Mod.EventBusSubscriber(modid = ModConstants.MODID)
object ModItems {

    lateinit var itemIronLog: Item

    fun preInit() {
        //Mod Specific items are created here
        itemIronLog = ItemBlock(ModBlocks.ironLog).setRegistryName(ModBlocks.ironLog.registryName!!)
    }

    @JvmStatic @SubscribeEvent
    fun registerItems(event: RegistryEvent.Register<Item>) {
        val registry = event.registry

        val treeItems = ArrayList<Item>()
        ModTrees.exampleTrees.forEach { tree -> tree.getRegisterableItems(treeItems) }
        TreeHelper.getLeavesMapForModId(ModConstants.MODID).forEach { key, block -> treeItems.add(makeItemBlock(block)) }

        registry.register(itemIronLog)
        registry.registerAll(*treeItems.toTypedArray())
    }

    private fun makeItemBlock(block: Block): Item {
        return ItemBlock(block).setRegistryName(block.registryName!!)
    }

}
