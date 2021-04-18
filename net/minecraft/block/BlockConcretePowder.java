package net.minecraft.block;

import java.util.List;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class BlockConcretePowder extends BlockFalling
{
    public static final PropertyEnum<EnumDyeColor> COLOR = PropertyEnum.create("color", EnumDyeColor.class);

    public BlockConcretePowder()
    {
        this.j(this.M.getBaseState().withProperty(COLOR, EnumDyeColor.WHITE));
        this.a(CreativeTabs.tabBlock);
    }

    public void onEndFalling(World worldIn, BlockPos pos)
    {
        super.onEndFalling(worldIn, pos);
    }

    private boolean checkHarden(World p_checkHarden_1_, BlockPos p_checkHarden_2_, IBlockState p_checkHarden_3_)
    {
        for (EnumFacing enumfacing : EnumFacing.VALUES)
        {
            if (enumfacing != EnumFacing.DOWN)
            {
                Block block = p_checkHarden_1_.getBlockState(p_checkHarden_2_.offset(enumfacing)).getBlock();

                if (block == Blocks.water || block == Blocks.flowing_water)
                {
                    p_checkHarden_1_.setBlockState(p_checkHarden_2_, Blocks.concrete.getDefaultState().withProperty(BlockColored.COLOR, p_checkHarden_3_.getValue(COLOR)), 3);
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Called when a neighboring block changes.
     */
    public void onNeighborBlockChange(World worldIn, BlockPos pos, IBlockState state, Block neighborBlock)
    {
        if (!this.checkHarden(worldIn, pos, state))
        {
            super.onNeighborBlockChange(worldIn, pos, state, neighborBlock);
        }
    }

    public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state)
    {
        if (!this.checkHarden(worldIn, pos, state))
        {
            super.onBlockAdded(worldIn, pos, state);
        }
    }

    public int a(IBlockState p_a_1_)
    {
        return ((EnumDyeColor)p_a_1_.getValue(COLOR)).getMetadata();
    }

    public void a(Item p_a_1_, CreativeTabs p_a_2_, List<ItemStack> p_a_3_)
    {
        for (EnumDyeColor enumdyecolor : EnumDyeColor.values())
        {
            p_a_3_.add(new ItemStack(p_a_1_, 1, enumdyecolor.getMetadata()));
        }
    }

    public MapColor g(IBlockState p_g_1_)
    {
        return ((EnumDyeColor)p_g_1_.getValue(COLOR)).getMapColor();
    }

    public IBlockState a(int p_a_1_)
    {
        return this.Q().withProperty(COLOR, EnumDyeColor.byMetadata(p_a_1_));
    }

    public int c(IBlockState p_c_1_)
    {
        return ((EnumDyeColor)p_c_1_.getValue(COLOR)).getMetadata();
    }

    protected BlockState e()
    {
        return new BlockState(this, new IProperty[] {COLOR});
    }
}
