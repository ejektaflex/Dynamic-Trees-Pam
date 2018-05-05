package com.ferreusveritas.exampletrees.proxy

import com.ferreusveritas.dynamictrees.api.TreeHelper
import com.ferreusveritas.dynamictrees.api.client.ModelHelper
import com.ferreusveritas.dynamictrees.blocks.BlockDynamicLeaves
import com.ferreusveritas.dynamictrees.blocks.BlockDynamicSapling
import com.ferreusveritas.exampletrees.ModBlocks
import com.ferreusveritas.exampletrees.ModConstants
import com.ferreusveritas.exampletrees.ModTrees
import net.minecraft.item.Item
import net.minecraft.world.ColorizerFoliage

class ClientProxy : CommonProxy() {

    override fun preInit() {
        super.preInit()
    }

    override fun init() {
        super.init()
        registerColorHandlers()
    }

    override fun registerModels() {

        ModelHelper.regModel(ModBlocks.ironLog)

        //TREE PARTS

        //Register Meshers for Branches and Seeds
        for (tree in ModTrees.exampleTrees) {
            ModelHelper.regModel(tree.dynamicBranch)//Register Branch itemBlock
            ModelHelper.regModel(tree.commonSpecies.seed)//Register Seed Item Models
            ModelHelper.regModel(tree)//Register custom state mapper for branch
        }

        //Register GrowingLeavesBlocks Meshers and Colorizers
        for (leaves in TreeHelper.getLeavesMapForModId(ModConstants.MODID).values) {
            val item = Item.getItemFromBlock(leaves)
            ModelHelper.regModel(item)
        }

    }

    fun registerColorHandlers() {

        val magenta = 0x00FF00FF//for errors.. because magenta sucks.

        //TREE PARTS

        //Register GrowingLeavesBlocks Colorizers
        for (leaves in TreeHelper.getLeavesMapForModId(ModConstants.MODID).values) {

            ModelHelper.regColorHandler(leaves) { state, worldIn, pos, tintIndex ->
                val block = state.block
                if (TreeHelper.isLeaves(block)) {
                    (block as BlockDynamicLeaves).getProperties(state).foliageColorMultiplier(state, worldIn, pos)
                } else magenta
            }

            ModelHelper.regColorHandler(Item.getItemFromBlock(leaves)) { stack, tintIndex -> ColorizerFoliage.getFoliageColorBasic() }
        }

        //Register Sapling Colorizers
        for (tree in ModTrees.exampleTrees) {
            ModelHelper.regDynamicSaplingColorHandler(tree.commonSpecies.dynamicSapling.block as BlockDynamicSapling)
        }

    }

}
