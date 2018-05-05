package com.ferreusveritas.exampletrees.categories

import com.ferreusveritas.dynamictrees.api.TreeBuilder
import com.ferreusveritas.dynamictrees.systems.dropcreators.DropCreatorHarvest
import com.ferreusveritas.dynamictrees.trees.Species
import com.ferreusveritas.dynamictrees.trees.TreeFamily
import com.ferreusveritas.exampletrees.ModConstants
import com.ferreusveritas.exampletrees.trees.TreeIron
import net.minecraft.block.BlockNewLeaf
import net.minecraft.block.BlockPlanks
import net.minecraft.init.Blocks
import net.minecraft.init.Items
import net.minecraft.item.ItemStack
import net.minecraft.util.ResourceLocation
import java.util.*

object ModTrees {

    //Sometimes it helps to cache a few blockstates
    val acaciaLeaves = Blocks.LEAVES2.defaultState.withProperty(BlockNewLeaf.VARIANT, BlockPlanks.EnumType.ACACIA)
    var exampleTrees = ArrayList<TreeFamily>()

    fun preInit() {

        //Method 1: Create the tree manually
        val ironTree = TreeIron()//All of the heavy lifting is done in the TreeIron class

        //Method 2: Use the tree builder
        val coalTree = TreeBuilder()
                .setName(ModConstants.MODID, "Coal")
                .setDynamicLeavesProperties(ModBlocks.coalLeavesProperties)
                .setPrimitiveLog(Blocks.COAL_BLOCK.defaultState)//Harvesting will result in coal blocks
                .setPrimitiveLeaves(acaciaLeaves)
                .build()

        //Make the tree drop coal when harvested for fun
        coalTree.commonSpecies.addDropCreator(DropCreatorHarvest(ResourceLocation(ModConstants.MODID, "coal"), ItemStack(Items.COAL), 0.001f))

        //Register all of the trees
        Collections.addAll(exampleTrees, ironTree, coalTree)
        exampleTrees.forEach { tree -> tree.registerSpecies(Species.REGISTRY) }
    }

}
