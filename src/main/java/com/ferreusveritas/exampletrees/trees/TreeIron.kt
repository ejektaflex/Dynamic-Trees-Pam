package com.ferreusveritas.exampletrees.trees

import com.ferreusveritas.dynamictrees.blocks.BlockDynamicSapling
import com.ferreusveritas.dynamictrees.systems.dropcreators.DropCreatorSeed
import com.ferreusveritas.dynamictrees.trees.Species
import com.ferreusveritas.dynamictrees.trees.TreeFamily
import com.ferreusveritas.exampletrees.ModConstants
import com.ferreusveritas.exampletrees.categories.ModBlocks
import net.minecraft.block.Block
import net.minecraft.init.Blocks
import net.minecraft.item.ItemStack
import net.minecraft.util.ResourceLocation
import net.minecraft.util.math.BlockPos
import net.minecraft.world.IBlockAccess
import net.minecraft.world.World
import net.minecraft.world.biome.Biome
import net.minecraftforge.common.BiomeDictionary
import net.minecraftforge.common.BiomeDictionary.Type
import java.util.*

class TreeIron : TreeFamily(ResourceLocation(ModConstants.MODID, "iron")) {

    //Species need not be created as a nested class.  They can be created after the tree has already been constructed.
    inner class TreeIronSpecies(treeFamily: TreeFamily) : Species(treeFamily.name, treeFamily, ModBlocks.ironLeavesProperties) {

        init {

            //Immensely slow-growing, stocky tree that pulls trace amounts of iron from the dirt
            setBasicGrowingParameters(0.5f, 10.0f, getUpProbability(), getLowestBranchHeight(), 0.1f)

            //Setup the dynamic sapling.  This could be done outside of the constructor but here is fine.
            dynamicSapling = BlockDynamicSapling("ironsapling").defaultState

            //This will allow the tree to grow in the Mesa which has very little traditional dirt.
            addAcceptableSoil(Blocks.STAINED_HARDENED_CLAY, Blocks.HARDENED_CLAY, Blocks.SAND)

            //Let's pretend that iron trees have a hard time around water because of rust or something
            envFactor(Type.BEACH, 0.1f)
            envFactor(Type.WET, 0.25f)
            envFactor(Type.WATER, 0.25f)
            envFactor(Type.DRY, 1.05f)

            addDropCreator(object : DropCreatorSeed(0.1f) {
                override fun getHarvestDrop(world: World?, species: Species, leafPos: BlockPos?, random: Random, dropList: List<ItemStack>, soilLife: Int, fortune: Int): List<ItemStack> {
                    return dropList
                }

                override fun getLeavesDrop(access: IBlockAccess?, species: Species, breakPos: BlockPos?, random: Random, dropList: List<ItemStack>, fortune: Int): List<ItemStack> {
                    return dropList
                }
            })
        }

        override fun isBiomePerfect(biome: Biome?): Boolean {
            //Let's pretend that Dry Mesa biomes have a lot of iron in the clays that help these trees grow.
            return BiomeDictionary.hasType(biome!!, Type.MESA)
        }

        /*@Override
		public void addJoCodes() {
			//Disable adding of JoCodes
		}*/

    }

    init {

        //Set up primitive log. This controls what is dropped on harvest.
        primitiveLog = ModBlocks.ironLog.getDefaultState()

        ModBlocks.ironLeavesProperties.setTree(this)
    }

    override fun createSpecies() {
        setCommonSpecies(TreeIronSpecies(this))
        getCommonSpecies().generateSeed()
    }

    //Since we created a DynamicSapling in the common species we need to let it out to be registered.
    override fun getRegisterableBlocks(blockList: MutableList<Block>): List<Block> {
        blockList.add(getCommonSpecies().dynamicSapling.block)
        return super.getRegisterableBlocks(blockList)
    }

}
