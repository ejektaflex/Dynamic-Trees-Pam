package com.ferreusveritas.exampletrees

import com.ferreusveritas.dynamictrees.api.TreeHelper
import com.ferreusveritas.dynamictrees.blocks.LeavesProperties
import com.ferreusveritas.exampletrees.blocks.BlockIronLog
import net.minecraft.block.Block
import net.minecraft.block.BlockColored
import net.minecraft.block.BlockNewLeaf
import net.minecraft.block.BlockPlanks
import net.minecraft.block.state.IBlockState
import net.minecraft.init.Blocks
import net.minecraft.item.EnumDyeColor
import net.minecraft.util.math.BlockPos
import net.minecraft.world.IBlockAccess
import net.minecraftforge.event.RegistryEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly
import java.util.*

@Mod.EventBusSubscriber(modid = ModConstants.MODID)
object ModBlocks {

    lateinit var ironLog: BlockIronLog

    lateinit var ironLeavesProperties: LeavesProperties
    lateinit var coalLeavesProperties: LeavesProperties
    lateinit var exampleLeavesProperties: Array<LeavesProperties>

    fun preInit() {
        ironLog = BlockIronLog()

        //Set up primitive leaves. This controls what is dropped on shearing, leaves replacement, etc.
        ironLeavesProperties = object : LeavesProperties(Blocks.WOOL.defaultState.withProperty(BlockColored.COLOR, EnumDyeColor.SILVER)) {
            @SideOnly(Side.CLIENT)
            override fun foliageColorMultiplier(state: IBlockState?, world: IBlockAccess?, pos: BlockPos?): Int {
                val hashmap = 32 and (pos!!.x * 2536123 xor pos.y * 642361431 xor pos.z * 86547653)
                val r = 150 + (32 and hashmap)   //173
                val g = 56 + (16 and hashmap * 763621)
                val b = 24

                return r shl 16 or (g shl 8) or b
            }
        }

        coalLeavesProperties = object : LeavesProperties(Blocks.LEAVES2.defaultState.withProperty(BlockNewLeaf.VARIANT, BlockPlanks.EnumType.ACACIA)) {
            @SideOnly(Side.CLIENT)
            override fun foliageColorMultiplier(state: IBlockState?, world: IBlockAccess?, pos: BlockPos?): Int {
                return 0x00D1C451
            }
        }

        //For this demonstration it is vital that these are never reordered.  If a leaves properties is removed from the
        //mod then there should be a LeavesProperties.NULLPROPERTIES used as a placeholder.
        exampleLeavesProperties = arrayOf(ironLeavesProperties, coalLeavesProperties)

        var seq = 0

        for (lp in exampleLeavesProperties) {
            TreeHelper.getLeavesBlockForSequence(ModConstants.MODID, seq++, lp)
        }
    }

    @SubscribeEvent
    fun registerBlocks(event: RegistryEvent.Register<Block>) {
        val registry = event.registry

        val treeBlocks = ArrayList<Block>()
        ModTrees.exampleTrees.forEach { tree -> tree.getRegisterableBlocks(treeBlocks) }

        treeBlocks.addAll(TreeHelper.getLeavesMapForModId(ModConstants.MODID).values)

        registry.register(ironLog)
        registry.registerAll(*treeBlocks.toTypedArray())
    }

}
