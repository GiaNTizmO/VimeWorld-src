package net.minecraft.client.renderer;

import com.google.common.collect.Maps;
import java.util.Map;
import java.util.Map.Entry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBed;
import net.minecraft.block.BlockCactus;
import net.minecraft.block.BlockColored;
import net.minecraft.block.BlockCommandBlock;
import net.minecraft.block.BlockDispenser;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.BlockDoublePlant;
import net.minecraft.block.BlockDropper;
import net.minecraft.block.BlockFenceGate;
import net.minecraft.block.BlockFire;
import net.minecraft.block.BlockFlowerPot;
import net.minecraft.block.BlockHopper;
import net.minecraft.block.BlockJukebox;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockLogStripped1;
import net.minecraft.block.BlockLogStripped2;
import net.minecraft.block.BlockNewLeaf;
import net.minecraft.block.BlockNewLog;
import net.minecraft.block.BlockOldLeaf;
import net.minecraft.block.BlockOldLog;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.BlockPrismarine;
import net.minecraft.block.BlockRedSandstone;
import net.minecraft.block.BlockRedstoneWire;
import net.minecraft.block.BlockReed;
import net.minecraft.block.BlockSand;
import net.minecraft.block.BlockSandStone;
import net.minecraft.block.BlockSapling;
import net.minecraft.block.BlockSilverfish;
import net.minecraft.block.BlockStone;
import net.minecraft.block.BlockStoneBrick;
import net.minecraft.block.BlockStoneSlab;
import net.minecraft.block.BlockStoneSlabNew;
import net.minecraft.block.BlockTNT;
import net.minecraft.block.BlockTallGrass;
import net.minecraft.block.BlockTripWire;
import net.minecraft.block.BlockWall;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.BlockModelShapes.1;
import net.minecraft.client.renderer.BlockModelShapes.2;
import net.minecraft.client.renderer.BlockModelShapes.3;
import net.minecraft.client.renderer.BlockModelShapes.4;
import net.minecraft.client.renderer.BlockModelShapes.5;
import net.minecraft.client.renderer.BlockModelShapes.6;
import net.minecraft.client.renderer.BlockModelShapes.7;
import net.minecraft.client.renderer.block.statemap.BlockStateMapper;
import net.minecraft.client.renderer.block.statemap.IStateMapper;
import net.minecraft.client.renderer.block.statemap.StateMap.Builder;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.init.Blocks;

public class BlockModelShapes
{
    private final Map<IBlockState, IBakedModel> bakedModelStore = Maps.<IBlockState, IBakedModel>newIdentityHashMap();
    private final BlockStateMapper blockStateMapper = new BlockStateMapper();
    private final ModelManager modelManager;

    public BlockModelShapes(ModelManager manager)
    {
        this.modelManager = manager;
        this.registerAllBlocks();
    }

    public BlockStateMapper getBlockStateMapper()
    {
        return this.blockStateMapper;
    }

    public TextureAtlasSprite getTexture(IBlockState state)
    {
        Block block = state.getBlock();
        IBakedModel ibakedmodel = this.getModelForState(state);

        if (ibakedmodel == null || ibakedmodel == this.modelManager.getMissingModel())
        {
            if (block == Blocks.wall_sign || block == Blocks.standing_sign || block == Blocks.chest || block == Blocks.trapped_chest || block == Blocks.standing_banner || block == Blocks.wall_banner)
            {
                return this.modelManager.getTextureMap().getAtlasSprite("minecraft:blocks/planks_oak");
            }

            if (block == Blocks.ender_chest)
            {
                return this.modelManager.getTextureMap().getAtlasSprite("minecraft:blocks/obsidian");
            }

            if (block == Blocks.flowing_lava || block == Blocks.lava)
            {
                return this.modelManager.getTextureMap().getAtlasSprite("minecraft:blocks/lava_still");
            }

            if (block == Blocks.flowing_water || block == Blocks.water)
            {
                return this.modelManager.getTextureMap().getAtlasSprite("minecraft:blocks/water_still");
            }

            if (block == Blocks.skull)
            {
                return this.modelManager.getTextureMap().getAtlasSprite("minecraft:blocks/soul_sand");
            }

            if (block == Blocks.barrier)
            {
                return this.modelManager.getTextureMap().getAtlasSprite("minecraft:items/barrier");
            }
        }

        if (ibakedmodel == null)
        {
            ibakedmodel = this.modelManager.getMissingModel();
        }

        return ibakedmodel.getParticleTexture();
    }

    public IBakedModel getModelForState(IBlockState state)
    {
        IBakedModel ibakedmodel = (IBakedModel)this.bakedModelStore.get(state);

        if (ibakedmodel == null)
        {
            ibakedmodel = this.modelManager.getMissingModel();
        }

        return ibakedmodel;
    }

    public ModelManager getModelManager()
    {
        return this.modelManager;
    }

    public void reloadModels()
    {
        this.bakedModelStore.clear();

        for (Entry<IBlockState, ModelResourceLocation> entry : this.blockStateMapper.putAllStateModelLocations().entrySet())
        {
            this.bakedModelStore.put(entry.getKey(), this.modelManager.getModel((ModelResourceLocation)entry.getValue()));
        }
    }

    public void registerBlockWithStateMapper(Block assoc, IStateMapper stateMapper)
    {
        this.blockStateMapper.registerBlockStateMapper(assoc, stateMapper);
    }

    public void registerBuiltInBlocks(Block... builtIns)
    {
        this.blockStateMapper.registerBuiltInBlocks(builtIns);
    }

    private void registerAllBlocks()
    {
        this.registerBuiltInBlocks(new Block[] {Blocks.air, Blocks.flowing_water, Blocks.water, Blocks.flowing_lava, Blocks.lava, Blocks.piston_extension, Blocks.chest, Blocks.ender_chest, Blocks.trapped_chest, Blocks.standing_sign, Blocks.skull, Blocks.end_portal, Blocks.barrier, Blocks.wall_sign, Blocks.wall_banner, Blocks.standing_banner});
        this.registerBlockWithStateMapper(Blocks.stone, (new Builder()).withName(BlockStone.VARIANT).build());
        this.registerBlockWithStateMapper(Blocks.prismarine, (new Builder()).withName(BlockPrismarine.VARIANT).build());
        this.registerBlockWithStateMapper(Blocks.leaves, (new Builder()).withName(BlockOldLeaf.VARIANT).withSuffix("_leaves").ignore(new IProperty[] {BlockLeaves.CHECK_DECAY, BlockLeaves.DECAYABLE}).build());
        this.registerBlockWithStateMapper(Blocks.leaves2, (new Builder()).withName(BlockNewLeaf.VARIANT).withSuffix("_leaves").ignore(new IProperty[] {BlockLeaves.CHECK_DECAY, BlockLeaves.DECAYABLE}).build());
        this.registerBlockWithStateMapper(Blocks.cactus, (new Builder()).ignore(new IProperty[] {BlockCactus.AGE}).build());
        this.registerBlockWithStateMapper(Blocks.reeds, (new Builder()).ignore(new IProperty[] {BlockReed.AGE}).build());
        this.registerBlockWithStateMapper(Blocks.jukebox, (new Builder()).ignore(new IProperty[] {BlockJukebox.HAS_RECORD}).build());
        this.registerBlockWithStateMapper(Blocks.command_block, (new Builder()).ignore(new IProperty[] {BlockCommandBlock.TRIGGERED}).build());
        this.registerBlockWithStateMapper(Blocks.cobblestone_wall, (new Builder()).withName(BlockWall.VARIANT).withSuffix("_wall").build());
        this.registerBlockWithStateMapper(Blocks.double_plant, (new Builder()).withName(BlockDoublePlant.VARIANT).ignore(new IProperty[] {BlockDoublePlant.field_181084_N}).build());
        this.registerBlockWithStateMapper(Blocks.oak_fence_gate, (new Builder()).ignore(new IProperty[] {BlockFenceGate.POWERED}).build());
        this.registerBlockWithStateMapper(Blocks.spruce_fence_gate, (new Builder()).ignore(new IProperty[] {BlockFenceGate.POWERED}).build());
        this.registerBlockWithStateMapper(Blocks.birch_fence_gate, (new Builder()).ignore(new IProperty[] {BlockFenceGate.POWERED}).build());
        this.registerBlockWithStateMapper(Blocks.jungle_fence_gate, (new Builder()).ignore(new IProperty[] {BlockFenceGate.POWERED}).build());
        this.registerBlockWithStateMapper(Blocks.dark_oak_fence_gate, (new Builder()).ignore(new IProperty[] {BlockFenceGate.POWERED}).build());
        this.registerBlockWithStateMapper(Blocks.acacia_fence_gate, (new Builder()).ignore(new IProperty[] {BlockFenceGate.POWERED}).build());
        this.registerBlockWithStateMapper(Blocks.tripwire, (new Builder()).ignore(new IProperty[] {BlockTripWire.DISARMED, BlockTripWire.POWERED}).build());
        this.registerBlockWithStateMapper(Blocks.double_wooden_slab, (new Builder()).withName(BlockPlanks.VARIANT).withSuffix("_double_slab").build());
        this.registerBlockWithStateMapper(Blocks.wooden_slab, (new Builder()).withName(BlockPlanks.VARIANT).withSuffix("_slab").build());
        this.registerBlockWithStateMapper(Blocks.tnt, (new Builder()).ignore(new IProperty[] {BlockTNT.EXPLODE}).build());
        this.registerBlockWithStateMapper(Blocks.fire, (new Builder()).ignore(new IProperty[] {BlockFire.AGE}).build());
        this.registerBlockWithStateMapper(Blocks.redstone_wire, (new Builder()).ignore(new IProperty[] {BlockRedstoneWire.POWER}).build());
        this.registerBlockWithStateMapper(Blocks.oak_door, (new Builder()).ignore(new IProperty[] {BlockDoor.POWERED}).build());
        this.registerBlockWithStateMapper(Blocks.spruce_door, (new Builder()).ignore(new IProperty[] {BlockDoor.POWERED}).build());
        this.registerBlockWithStateMapper(Blocks.birch_door, (new Builder()).ignore(new IProperty[] {BlockDoor.POWERED}).build());
        this.registerBlockWithStateMapper(Blocks.jungle_door, (new Builder()).ignore(new IProperty[] {BlockDoor.POWERED}).build());
        this.registerBlockWithStateMapper(Blocks.acacia_door, (new Builder()).ignore(new IProperty[] {BlockDoor.POWERED}).build());
        this.registerBlockWithStateMapper(Blocks.dark_oak_door, (new Builder()).ignore(new IProperty[] {BlockDoor.POWERED}).build());
        this.registerBlockWithStateMapper(Blocks.iron_door, (new Builder()).ignore(new IProperty[] {BlockDoor.POWERED}).build());
        this.registerBlockWithStateMapper(Blocks.wool, (new Builder()).withName(BlockColored.COLOR).withSuffix("_wool").build());
        this.registerBlockWithStateMapper(Blocks.carpet, (new Builder()).withName(BlockColored.COLOR).withSuffix("_carpet").build());
        this.registerBlockWithStateMapper(Blocks.stained_hardened_clay, (new Builder()).withName(BlockColored.COLOR).withSuffix("_stained_hardened_clay").build());
        this.registerBlockWithStateMapper(Blocks.stained_glass_pane, (new Builder()).withName(BlockColored.COLOR).withSuffix("_stained_glass_pane").build());
        this.registerBlockWithStateMapper(Blocks.stained_glass, (new Builder()).withName(BlockColored.COLOR).withSuffix("_stained_glass").build());
        this.registerBlockWithStateMapper(Blocks.sandstone, (new Builder()).withName(BlockSandStone.TYPE).build());
        this.registerBlockWithStateMapper(Blocks.red_sandstone, (new Builder()).withName(BlockRedSandstone.TYPE).build());
        this.registerBlockWithStateMapper(Blocks.tallgrass, (new Builder()).withName(BlockTallGrass.TYPE).build());
        this.registerBlockWithStateMapper(Blocks.bed, (new Builder()).ignore(new IProperty[] {BlockBed.OCCUPIED}).build());
        this.registerBlockWithStateMapper(Blocks.yellow_flower, (new Builder()).withName(Blocks.yellow_flower.getTypeProperty()).build());
        this.registerBlockWithStateMapper(Blocks.red_flower, (new Builder()).withName(Blocks.red_flower.getTypeProperty()).build());
        this.registerBlockWithStateMapper(Blocks.stone_slab, (new Builder()).withName(BlockStoneSlab.VARIANT).withSuffix("_slab").build());
        this.registerBlockWithStateMapper(Blocks.stone_slab2, (new Builder()).withName(BlockStoneSlabNew.VARIANT).withSuffix("_slab").build());
        this.registerBlockWithStateMapper(Blocks.monster_egg, (new Builder()).withName(BlockSilverfish.VARIANT).withSuffix("_monster_egg").build());
        this.registerBlockWithStateMapper(Blocks.stonebrick, (new Builder()).withName(BlockStoneBrick.VARIANT).build());
        this.registerBlockWithStateMapper(Blocks.dispenser, (new Builder()).ignore(new IProperty[] {BlockDispenser.TRIGGERED}).build());
        this.registerBlockWithStateMapper(Blocks.dropper, (new Builder()).ignore(new IProperty[] {BlockDropper.b}).build());
        this.registerBlockWithStateMapper(Blocks.log, (new Builder()).withName(BlockOldLog.VARIANT).withSuffix("_log").build());
        this.registerBlockWithStateMapper(Blocks.log2, (new Builder()).withName(BlockNewLog.VARIANT).withSuffix("_log").build());
        this.registerBlockWithStateMapper(Blocks.planks, (new Builder()).withName(BlockPlanks.VARIANT).withSuffix("_planks").build());
        this.registerBlockWithStateMapper(Blocks.sapling, (new Builder()).withName(BlockSapling.TYPE).withSuffix("_sapling").build());
        this.registerBlockWithStateMapper(Blocks.sand, (new Builder()).withName(BlockSand.VARIANT).build());
        this.registerBlockWithStateMapper(Blocks.hopper, (new Builder()).ignore(new IProperty[] {BlockHopper.ENABLED}).build());
        this.registerBlockWithStateMapper(Blocks.flower_pot, (new Builder()).ignore(new IProperty[] {BlockFlowerPot.LEGACY_DATA}).build());
        this.registerBlockWithStateMapper(Blocks.quartz_block, new 1(this));
        this.registerBlockWithStateMapper(Blocks.deadbush, new 2(this));
        this.registerBlockWithStateMapper(Blocks.pumpkin_stem, new 3(this));
        this.registerBlockWithStateMapper(Blocks.melon_stem, new 4(this));
        this.registerBlockWithStateMapper(Blocks.dirt, new 5(this));
        this.registerBlockWithStateMapper(Blocks.double_stone_slab, new 6(this));
        this.registerBlockWithStateMapper(Blocks.double_stone_slab2, new 7(this));
        this.registerBlockWithStateMapper(Blocks.concrete, (new Builder()).withName(BlockColored.COLOR).withSuffix("_concrete").build());
        this.registerBlockWithStateMapper(Blocks.concrete_powder, (new Builder()).withName(BlockColored.COLOR).withSuffix("_concrete_powder").build());
        this.registerBlockWithStateMapper(Blocks.log_stripped1, (new Builder()).withName(BlockLogStripped1.VARIANT).withSuffix("_log_stripped").build());
        this.registerBlockWithStateMapper(Blocks.log_stripped2, (new Builder()).withName(BlockLogStripped2.VARIANT).withSuffix("_log_stripped").build());
    }
}
