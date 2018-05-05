package com.ferreusveritas.exampletrees.categories

import com.ferreusveritas.exampletrees.ModConstants
import net.minecraft.init.Items
import net.minecraft.item.ItemStack
import net.minecraft.item.crafting.IRecipe
import net.minecraftforge.event.RegistryEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.eventhandler.EventPriority
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.common.registry.GameRegistry

@Mod.EventBusSubscriber(modid = ModConstants.MODID)
object ModRecipes {

    @JvmStatic @SubscribeEvent(priority = EventPriority.LOWEST)
    fun registerRecipes(event: RegistryEvent.Register<IRecipe>) {

        GameRegistry.addSmelting(ModBlocks.ironLog, ItemStack(Items.IRON_INGOT), 0f)
    }

}
