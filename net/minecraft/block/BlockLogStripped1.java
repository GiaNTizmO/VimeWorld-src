package net.minecraft.block;

import com.google.common.base.Predicate;
import java.util.List;
import net.minecraft.block.BlockLog.EnumAxis;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class BlockLogStripped1 extends BlockLog
{
    public static final PropertyEnum<BlockPlanks.EnumType> VARIANT = PropertyEnum.create("variant", BlockPlanks.EnumType.class, new Predicate<BlockPlanks.EnumType>()
    {
        public boolean apply(BlockPlanks.EnumType p_apply_1_)
        {
            return p_apply_1_.getMetadata() < 4;
        }
    });

    public BlockLogStripped1()
    {
        this.j(this.M.getBaseState().withProperty(VARIANT, BlockPlanks.EnumType.OAK).withProperty(LOG_AXIS, EnumAxis.Y));
    }

    public MapColor g(IBlockState p_g_1_)
    {
        BlockPlanks.EnumType blockplanks$enumtype = (BlockPlanks.EnumType)p_g_1_.getValue(VARIANT);

        switch ((EnumAxis)p_g_1_.getValue(LOG_AXIS))
        {
            case X:
            case Z:
            case NONE:
            default:
                switch (blockplanks$enumtype)
                {
                    case OAK:
                    default:
                        return BlockPlanks.EnumType.SPRUCE.func_181070_c();

                    case SPRUCE:
                        return BlockPlanks.EnumType.DARK_OAK.func_181070_c();

                    case BIRCH:
                        return MapColor.quartzColor;

                    case JUNGLE:
                        return BlockPlanks.EnumType.SPRUCE.func_181070_c();
                }

            case Y:
                return blockplanks$enumtype.func_181070_c();
        }
    }

    public void a(Item p_a_1_, CreativeTabs p_a_2_, List<ItemStack> p_a_3_)
    {
        p_a_3_.add(new ItemStack(p_a_1_, 1, BlockPlanks.EnumType.OAK.getMetadata()));
        p_a_3_.add(new ItemStack(p_a_1_, 1, BlockPlanks.EnumType.SPRUCE.getMetadata()));
        p_a_3_.add(new ItemStack(p_a_1_, 1, BlockPlanks.EnumType.BIRCH.getMetadata()));
        p_a_3_.add(new ItemStack(p_a_1_, 1, BlockPlanks.EnumType.JUNGLE.getMetadata()));
    }

    public IBlockState a(int p_a_1_)
    {
        IBlockState iblockstate = this.Q().withProperty(VARIANT, BlockPlanks.EnumType.byMetadata((p_a_1_ & 3) % 4));

        switch (p_a_1_ & 12)
        {
            case 0:
                iblockstate = iblockstate.withProperty(LOG_AXIS, EnumAxis.Y);
                break;

            case 4:
                iblockstate = iblockstate.withProperty(LOG_AXIS, EnumAxis.X);
                break;

            case 8:
                iblockstate = iblockstate.withProperty(LOG_AXIS, EnumAxis.Z);
                break;

            default:
                iblockstate = iblockstate.withProperty(LOG_AXIS, EnumAxis.NONE);
        }

        return iblockstate;
    }

    public int c(IBlockState p_c_1_)
    {
        int i = 0;
        i = i | ((BlockPlanks.EnumType)p_c_1_.getValue(VARIANT)).getMetadata();

        switch ((EnumAxis)p_c_1_.getValue(LOG_AXIS))
        {
            case X:
                i |= 4;
                break;

            case Z:
                i |= 8;
                break;

            case NONE:
                i |= 12;
        }

        return i;
    }

    protected BlockState e()
    {
        return new BlockState(this, new IProperty[] {VARIANT, LOG_AXIS});
    }

    protected ItemStack i(IBlockState p_i_1_)
    {
        return new ItemStack(Item.getItemFromBlock(this), 1, ((BlockPlanks.EnumType)p_i_1_.getValue(VARIANT)).getMetadata());
    }

    public int a(IBlockState p_a_1_)
    {
        return ((BlockPlanks.EnumType)p_a_1_.getValue(VARIANT)).getMetadata();
    }
}
