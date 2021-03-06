package com.ferreusveritas.exampletrees.blocks

import com.ferreusveritas.dynamictrees.DynamicTrees
import net.minecraft.block.BlockLog
import net.minecraft.block.state.BlockStateContainer
import net.minecraft.block.state.IBlockState

class BlockIronLog(name: String) : BlockLog() {

    constructor() : this("ironlog") {
        setCreativeTab(DynamicTrees.dynamicTreesTab)
    }

    init {
        this.defaultState = this.blockState.baseState.withProperty(BlockLog.LOG_AXIS, BlockLog.EnumAxis.Y)
        defaultState = this.blockState.baseState
        unlocalizedName = name
        setRegistryName(name)
    }

    override fun createBlockState(): BlockStateContainer {
        return BlockStateContainer(this, BlockLog.LOG_AXIS)
    }

    override fun getStateFromMeta(meta: Int): IBlockState {

        var iblockstate = this.defaultState

        iblockstate = when (meta and 12) {
            0 -> iblockstate.withProperty(BlockLog.LOG_AXIS, BlockLog.EnumAxis.Y)
            4 -> iblockstate.withProperty(BlockLog.LOG_AXIS, BlockLog.EnumAxis.X)
            8 -> iblockstate.withProperty(BlockLog.LOG_AXIS, BlockLog.EnumAxis.Z)
            else -> iblockstate.withProperty(BlockLog.LOG_AXIS, BlockLog.EnumAxis.NONE)
        }

        return iblockstate
    }

    /**
     * Convert the BlockState into the correct metadata value
     */
    override fun getMetaFromState(state: IBlockState): Int {
        var i = 0

        when (state.getValue(BlockLog.LOG_AXIS)) {
            BlockLog.EnumAxis.X -> i = i or 4
            BlockLog.EnumAxis.Z -> i = i or 8
            BlockLog.EnumAxis.NONE -> i = i or 12
            else -> {
            }
        }

        return i
    }

}
