package com.ferreusveritas.exampletrees

import com.ferreusveritas.dynamictrees.api.TreeRegistry
import com.ferreusveritas.dynamictrees.api.WorldGenRegistry
import com.ferreusveritas.dynamictrees.api.worldgen.BiomePropertySelectors.EnumChance
import com.ferreusveritas.dynamictrees.api.worldgen.BiomePropertySelectors.StaticSpeciesSelector
import com.ferreusveritas.dynamictrees.worldgen.BiomeDataBase.Operation
import com.ferreusveritas.dynamictrees.worldgen.TreeGenerator
import com.ferreusveritas.exampletrees.proxy.CommonProxy
import net.minecraft.util.ResourceLocation
import net.minecraft.world.biome.Biome
import net.minecraftforge.common.BiomeDictionary
import net.minecraftforge.common.BiomeDictionary.Type
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.Mod.EventHandler
import net.minecraftforge.fml.common.Mod.Instance
import net.minecraftforge.fml.common.SidedProxy
import net.minecraftforge.fml.common.event.FMLInitializationEvent
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent

@Mod(modid = ModConstants.MODID, version = ModConstants.VERSION, dependencies = "after:dynamictrees;", modLanguageAdapter = ModConstants.ADAPTER)
object ExampleTrees {

    @Instance(ModConstants.MODID)
    var instance: ExampleTrees? = null

    @SidedProxy(clientSide = "com.ferreusveritas.exampletrees.proxy.ClientProxy", serverSide = "com.ferreusveritas.exampletrees.proxy.CommonProxy")
    var proxy: CommonProxy? = null

    //Run before anything else. Read your config, create blocks, items, etc.
    @EventHandler
    fun preInit(event: FMLPreInitializationEvent) {

        ModBlocks.preInit()
        ModItems.preInit()
        ModTrees.preInit()

        proxy!!.preInit()
    }

    //Do your mod setup. Build whatever data structures you care about.
    @EventHandler
    fun init(event: FMLInitializationEvent) {

        populateBiomeDataBase()

        proxy!!.init()
    }

    

    //Populate the Biome DataBase with generation instructions
    fun populateBiomeDataBase() {

        if (WorldGenRegistry.isWorldGenEnabled()) {
            val ironTree = TreeRegistry.findSpecies(ResourceLocation(ModConstants.MODID, "iron"))
            val ironTreeSelector = StaticSpeciesSelector(ironTree)
            val dbase = TreeGenerator.getTreeGenerator().biomeDataBase

            Biome.REGISTRY.forEach { biome ->
                if (BiomeDictionary.hasType(biome, Type.MESA)) {    //We want this tree to generate in mesa biomes
                    dbase.setSpeciesSelector(biome, ironTreeSelector, Operation.REPLACE)
                    dbase.setChanceSelector(biome, { rnd, spc, rad -> if (rnd.nextFloat() < 0.025f) EnumChance.OK else EnumChance.UNHANDLED }, Operation.SPLICE_BEFORE)
                    dbase.setDensitySelector(biome, { rnd, nd -> -1.0 }, Operation.SPLICE_BEFORE)
                }
            }
        }

    }


}
