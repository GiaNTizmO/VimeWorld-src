package net.minecraft.block;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class BlockCustomPillar extends BlockRotatedPillar
{
    public BlockCustomPillar(Material p_i20_1_, MapColor p_i20_2_)
    {
        super(p_i20_1_, p_i20_2_);
        this.j(this.M.getBaseState().withProperty(AXIS, EnumFacing.Axis.Y));
        this.a(CreativeTabs.tabBlock);
    }

    public BlockCustomPillar(Material p_i21_1_)
    {
        super(p_i21_1_);
        this.j(this.M.getBaseState().withProperty(AXIS, EnumFacing.Axis.Y));
        this.a(CreativeTabs.tabBlock);
    }

    public IBlockState a(int p_a_1_)
    {
        EnumFacing.Axis enumfacing$axis = EnumFacing.Axis.Y;
        int i = p_a_1_ & 12;

        if (i == 4)
        {
            enumfacing$axis = EnumFacing.Axis.X;
        }
        else if (i == 8)
        {
            enumfacing$axis = EnumFacing.Axis.Z;
        }

        return this.Q().withProperty(AXIS, enumfacing$axis);
    }

    public int c(IBlockState p_c_1_)
    {
        int i = 0;
        EnumFacing.Axis enumfacing$axis = (EnumFacing.Axis)p_c_1_.getValue(AXIS);

        if (enumfacing$axis == EnumFacing.Axis.X)
        {
            i |= 4;
        }
        else if (enumfacing$axis == EnumFacing.Axis.Z)
        {
            i |= 8;
        }

        return i;
    }

    protected BlockState e()
    {
        return new BlockState(this, new IProperty[] {AXIS});
    }

    protected ItemStack i(IBlockState p_i_1_)
    {
        return new ItemStack(Item.getItemFromBlock(this), 1, 0);
    }

    public IBlockState a(World p_a_1_, BlockPos p_a_2_, EnumFacing p_a_3_, float p_a_4_, float p_a_5_, float p_a_6_, int p_a_7_, EntityLivingBase p_a_8_)
    {
        return super.a(p_a_1_, p_a_2_, p_a_3_, p_a_4_, p_a_5_, p_a_6_, p_a_7_, p_a_8_).withProperty(AXIS, p_a_3_.getAxis());
    }
}
