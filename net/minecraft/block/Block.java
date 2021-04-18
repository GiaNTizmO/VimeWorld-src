package net.minecraft.block;

import com.google.common.collect.UnmodifiableIterator;
import java.util.List;
import java.util.Random;
import net.minecraft.block.BlockBanner.BlockBannerHanging;
import net.minecraft.block.BlockBanner.BlockBannerStanding;
import net.minecraft.block.BlockPressurePlate.Sensitivity;
import net.minecraft.block.BlockPurpurSlab.Double;
import net.minecraft.block.BlockPurpurSlab.Half;
import net.minecraft.block.BlockStoneBrick.EnumType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.ObjectIntIdentityMap;
import net.minecraft.util.RegistryNamespacedDefaultedByKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraft.util.Vec3;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.xtrafrancyz.covered.ObfValue;

public class Block
{
    private static final ObfValue.OInteger OBFVAL_253 = ObfValue.create(254);
    private static final ObfValue.OInteger OBFVAL_252 = ObfValue.create(210);
    private static final ObfValue.OInteger OBFVAL_251 = ObfValue.create(209);
    private static final ObfValue.OInteger OBFVAL_250 = ObfValue.create(208);
    private static final ObfValue.OInteger OBFVAL_249 = ObfValue.create(207);
    private static final ObfValue.OInteger OBFVAL_248 = ObfValue.create(199);
    private static final ObfValue.OInteger OBFVAL_247 = ObfValue.create(198);
    private static final ObfValue.OInteger OBFVAL_246 = ObfValue.create(252);
    private static final ObfValue.OFloat OBFVAL_245 = ObfValue.create(1.8F);
    private static final ObfValue.OInteger OBFVAL_244 = ObfValue.create(251);
    private static final ObfValue.OInteger OBFVAL_243 = ObfValue.create(216);
    private static final ObfValue.OInteger OBFVAL_242 = ObfValue.create(215);
    private static final ObfValue.OInteger OBFVAL_241 = ObfValue.create(214);
    private static final ObfValue.OInteger OBFVAL_240 = ObfValue.create(213);
    private static final ObfValue.OInteger OBFVAL_239 = ObfValue.create(206);
    private static final ObfValue.OInteger OBFVAL_238 = ObfValue.create(205);
    private static final ObfValue.OInteger OBFVAL_237 = ObfValue.create(204);
    private static final ObfValue.OInteger OBFVAL_236 = ObfValue.create(203);
    private static final ObfValue.OInteger OBFVAL_235 = ObfValue.create(202);
    private static final ObfValue.OInteger OBFVAL_234 = ObfValue.create(201);
    private static final ObfValue.OInteger OBFVAL_233 = ObfValue.create(197);
    private static final ObfValue.OInteger OBFVAL_232 = ObfValue.create(196);
    private static final ObfValue.OInteger OBFVAL_231 = ObfValue.create(195);
    private static final ObfValue.OInteger OBFVAL_230 = ObfValue.create(194);
    private static final ObfValue.OInteger OBFVAL_229 = ObfValue.create(193);
    private static final ObfValue.OInteger OBFVAL_228 = ObfValue.create(192);
    private static final ObfValue.OInteger OBFVAL_227 = ObfValue.create(191);
    private static final ObfValue.OInteger OBFVAL_226 = ObfValue.create(190);
    private static final ObfValue.OInteger OBFVAL_225 = ObfValue.create(189);
    private static final ObfValue.OInteger OBFVAL_224 = ObfValue.create(188);
    private static final ObfValue.OInteger OBFVAL_223 = ObfValue.create(187);
    private static final ObfValue.OInteger OBFVAL_222 = ObfValue.create(186);
    private static final ObfValue.OInteger OBFVAL_221 = ObfValue.create(185);
    private static final ObfValue.OInteger OBFVAL_220 = ObfValue.create(184);
    private static final ObfValue.OInteger OBFVAL_219 = ObfValue.create(183);
    private static final ObfValue.OInteger OBFVAL_218 = ObfValue.create(182);
    private static final ObfValue.OInteger OBFVAL_217 = ObfValue.create(181);
    private static final ObfValue.OInteger OBFVAL_216 = ObfValue.create(180);
    private static final ObfValue.OInteger OBFVAL_215 = ObfValue.create(179);
    private static final ObfValue.OInteger OBFVAL_214 = ObfValue.create(178);
    private static final ObfValue.OInteger OBFVAL_213 = ObfValue.create(177);
    private static final ObfValue.OInteger OBFVAL_212 = ObfValue.create(176);
    private static final ObfValue.OInteger OBFVAL_211 = ObfValue.create(175);
    private static final ObfValue.OInteger OBFVAL_210 = ObfValue.create(174);
    private static final ObfValue.OInteger OBFVAL_209 = ObfValue.create(173);
    private static final ObfValue.OInteger OBFVAL_208 = ObfValue.create(172);
    private static final ObfValue.OInteger OBFVAL_207 = ObfValue.create(171);
    private static final ObfValue.OInteger OBFVAL_206 = ObfValue.create(170);
    private static final ObfValue.OInteger OBFVAL_205 = ObfValue.create(169);
    private static final ObfValue.OInteger OBFVAL_204 = ObfValue.create(168);
    private static final ObfValue.OInteger OBFVAL_203 = ObfValue.create(167);
    private static final ObfValue.OInteger OBFVAL_202 = ObfValue.create(166);
    private static final ObfValue.OInteger OBFVAL_201 = ObfValue.create(165);
    private static final ObfValue.OInteger OBFVAL_200 = ObfValue.create(164);
    private static final ObfValue.OInteger OBFVAL_199 = ObfValue.create(163);
    private static final ObfValue.OInteger OBFVAL_198 = ObfValue.create(162);
    private static final ObfValue.OInteger OBFVAL_197 = ObfValue.create(161);
    private static final ObfValue.OInteger OBFVAL_196 = ObfValue.create(160);
    private static final ObfValue.OFloat OBFVAL_195 = ObfValue.create(7.0F);
    private static final ObfValue.OFloat OBFVAL_194 = ObfValue.create(1.25F);
    private static final ObfValue.OInteger OBFVAL_193 = ObfValue.create(159);
    private static final ObfValue.OInteger OBFVAL_192 = ObfValue.create(158);
    private static final ObfValue.OInteger OBFVAL_191 = ObfValue.create(157);
    private static final ObfValue.OInteger OBFVAL_190 = ObfValue.create(156);
    private static final ObfValue.OInteger OBFVAL_189 = ObfValue.create(155);
    private static final ObfValue.OFloat OBFVAL_188 = ObfValue.create(8.0F);
    private static final ObfValue.OInteger OBFVAL_187 = ObfValue.create(154);
    private static final ObfValue.OInteger OBFVAL_186 = ObfValue.create(153);
    private static final ObfValue.OInteger OBFVAL_185 = ObfValue.create(152);
    private static final ObfValue.OInteger OBFVAL_184 = ObfValue.create(151);
    private static final ObfValue.OInteger OBFVAL_183 = ObfValue.create(149);
    private static final ObfValue.OInteger OBFVAL_182 = ObfValue.create(150);
    private static final ObfValue.OInteger OBFVAL_181 = ObfValue.create(148);
    private static final ObfValue.OInteger OBFVAL_180 = ObfValue.create(147);
    private static final ObfValue.OInteger OBFVAL_179 = ObfValue.create(146);
    private static final ObfValue.OInteger OBFVAL_178 = ObfValue.create(145);
    private static final ObfValue.OInteger OBFVAL_177 = ObfValue.create(144);
    private static final ObfValue.OInteger OBFVAL_176 = ObfValue.create(143);
    private static final ObfValue.OInteger OBFVAL_175 = ObfValue.create(142);
    private static final ObfValue.OInteger OBFVAL_174 = ObfValue.create(141);
    private static final ObfValue.OInteger OBFVAL_173 = ObfValue.create(140);
    private static final ObfValue.OInteger OBFVAL_172 = ObfValue.create(139);
    private static final ObfValue.OInteger OBFVAL_171 = ObfValue.create(138);
    private static final ObfValue.OInteger OBFVAL_170 = ObfValue.create(137);
    private static final ObfValue.OInteger OBFVAL_169 = ObfValue.create(136);
    private static final ObfValue.OInteger OBFVAL_168 = ObfValue.create(135);
    private static final ObfValue.OInteger OBFVAL_167 = ObfValue.create(134);
    private static final ObfValue.OInteger OBFVAL_166 = ObfValue.create(133);
    private static final ObfValue.OInteger OBFVAL_165 = ObfValue.create(132);
    private static final ObfValue.OInteger OBFVAL_164 = ObfValue.create(131);
    private static final ObfValue.OFloat OBFVAL_163 = ObfValue.create(1000.0F);
    private static final ObfValue.OFloat OBFVAL_162 = ObfValue.create(22.5F);
    private static final ObfValue.OInteger OBFVAL_161 = ObfValue.create(130);
    private static final ObfValue.OInteger OBFVAL_160 = ObfValue.create(129);
    private static final ObfValue.OInteger OBFVAL_159 = ObfValue.create(128);
    private static final ObfValue.OInteger OBFVAL_158 = ObfValue.create(127);
    private static final ObfValue.OInteger OBFVAL_157 = ObfValue.create(126);
    private static final ObfValue.OInteger OBFVAL_156 = ObfValue.create(125);
    private static final ObfValue.OInteger OBFVAL_155 = ObfValue.create(124);
    private static final ObfValue.OInteger OBFVAL_154 = ObfValue.create(123);
    private static final ObfValue.OInteger OBFVAL_153 = ObfValue.create(122);
    private static final ObfValue.OInteger OBFVAL_152 = ObfValue.create(121);
    private static final ObfValue.OInteger OBFVAL_151 = ObfValue.create(120);
    private static final ObfValue.OInteger OBFVAL_150 = ObfValue.create(119);
    private static final ObfValue.OInteger OBFVAL_149 = ObfValue.create(118);
    private static final ObfValue.OInteger OBFVAL_148 = ObfValue.create(117);
    private static final ObfValue.OInteger OBFVAL_147 = ObfValue.create(116);
    private static final ObfValue.OInteger OBFVAL_146 = ObfValue.create(115);
    private static final ObfValue.OInteger OBFVAL_145 = ObfValue.create(114);
    private static final ObfValue.OInteger OBFVAL_144 = ObfValue.create(113);
    private static final ObfValue.OInteger OBFVAL_143 = ObfValue.create(112);
    private static final ObfValue.OInteger OBFVAL_142 = ObfValue.create(111);
    private static final ObfValue.OInteger OBFVAL_141 = ObfValue.create(110);
    private static final ObfValue.OInteger OBFVAL_140 = ObfValue.create(109);
    private static final ObfValue.OInteger OBFVAL_139 = ObfValue.create(108);
    private static final ObfValue.OInteger OBFVAL_138 = ObfValue.create(107);
    private static final ObfValue.OInteger OBFVAL_137 = ObfValue.create(106);
    private static final ObfValue.OInteger OBFVAL_136 = ObfValue.create(105);
    private static final ObfValue.OInteger OBFVAL_135 = ObfValue.create(104);
    private static final ObfValue.OInteger OBFVAL_134 = ObfValue.create(103);
    private static final ObfValue.OInteger OBFVAL_133 = ObfValue.create(102);
    private static final ObfValue.OInteger OBFVAL_132 = ObfValue.create(101);
    private static final ObfValue.OInteger OBFVAL_131 = ObfValue.create(100);
    private static final ObfValue.OInteger OBFVAL_130 = ObfValue.create(99);
    private static final ObfValue.OInteger OBFVAL_129 = ObfValue.create(98);
    private static final ObfValue.OInteger OBFVAL_128 = ObfValue.create(97);
    private static final ObfValue.OInteger OBFVAL_127 = ObfValue.create(96);
    private static final ObfValue.OInteger OBFVAL_126 = ObfValue.create(95);
    private static final ObfValue.OInteger OBFVAL_125 = ObfValue.create(94);
    private static final ObfValue.OInteger OBFVAL_124 = ObfValue.create(93);
    private static final ObfValue.OInteger OBFVAL_123 = ObfValue.create(92);
    private static final ObfValue.OInteger OBFVAL_122 = ObfValue.create(91);
    private static final ObfValue.OFloat OBFVAL_121 = ObfValue.create(0.75F);
    private static final ObfValue.OInteger OBFVAL_120 = ObfValue.create(90);
    private static final ObfValue.OInteger OBFVAL_119 = ObfValue.create(89);
    private static final ObfValue.OInteger OBFVAL_118 = ObfValue.create(88);
    private static final ObfValue.OInteger OBFVAL_117 = ObfValue.create(87);
    private static final ObfValue.OInteger OBFVAL_116 = ObfValue.create(86);
    private static final ObfValue.OInteger OBFVAL_115 = ObfValue.create(85);
    private static final ObfValue.OInteger OBFVAL_114 = ObfValue.create(84);
    private static final ObfValue.OInteger OBFVAL_113 = ObfValue.create(83);
    private static final ObfValue.OInteger OBFVAL_112 = ObfValue.create(82);
    private static final ObfValue.OInteger OBFVAL_111 = ObfValue.create(81);
    private static final ObfValue.OInteger OBFVAL_110 = ObfValue.create(80);
    private static final ObfValue.OInteger OBFVAL_109 = ObfValue.create(79);
    private static final ObfValue.OFloat OBFVAL_108 = ObfValue.create(0.1F);
    private static final ObfValue.OInteger OBFVAL_107 = ObfValue.create(78);
    private static final ObfValue.OInteger OBFVAL_106 = ObfValue.create(77);
    private static final ObfValue.OInteger OBFVAL_105 = ObfValue.create(76);
    private static final ObfValue.OInteger OBFVAL_104 = ObfValue.create(75);
    private static final ObfValue.OFloat OBFVAL_103 = ObfValue.create(0.625F);
    private static final ObfValue.OInteger OBFVAL_102 = ObfValue.create(74);
    private static final ObfValue.OInteger OBFVAL_101 = ObfValue.create(73);
    private static final ObfValue.OInteger OBFVAL_100 = ObfValue.create(72);
    private static final ObfValue.OInteger OBFVAL_99 = ObfValue.create(71);
    private static final ObfValue.OInteger OBFVAL_98 = ObfValue.create(70);
    private static final ObfValue.OInteger OBFVAL_97 = ObfValue.create(69);
    private static final ObfValue.OInteger OBFVAL_96 = ObfValue.create(68);
    private static final ObfValue.OInteger OBFVAL_95 = ObfValue.create(67);
    private static final ObfValue.OInteger OBFVAL_94 = ObfValue.create(66);
    private static final ObfValue.OFloat OBFVAL_93 = ObfValue.create(0.4F);
    private static final ObfValue.OInteger OBFVAL_92 = ObfValue.create(65);
    private static final ObfValue.OInteger OBFVAL_91 = ObfValue.create(64);
    private static final ObfValue.OInteger OBFVAL_90 = ObfValue.create(63);
    private static final ObfValue.OFloat OBFVAL_89 = ObfValue.create(0.875F);
    private static final ObfValue.OInteger OBFVAL_88 = ObfValue.create(62);
    private static final ObfValue.OInteger OBFVAL_87 = ObfValue.create(61);
    private static final ObfValue.OInteger OBFVAL_86 = ObfValue.create(60);
    private static final ObfValue.OInteger OBFVAL_85 = ObfValue.create(59);
    private static final ObfValue.OInteger OBFVAL_84 = ObfValue.create(58);
    private static final ObfValue.OInteger OBFVAL_83 = ObfValue.create(57);
    private static final ObfValue.OInteger OBFVAL_82 = ObfValue.create(56);
    private static final ObfValue.OInteger OBFVAL_81 = ObfValue.create(55);
    private static final ObfValue.OFloat OBFVAL_80 = ObfValue.create(2.5F);
    private static final ObfValue.OInteger OBFVAL_79 = ObfValue.create(54);
    private static final ObfValue.OInteger OBFVAL_78 = ObfValue.create(53);
    private static final ObfValue.OInteger OBFVAL_77 = ObfValue.create(52);
    private static final ObfValue.OInteger OBFVAL_76 = ObfValue.create(51);
    private static final ObfValue.OFloat OBFVAL_75 = ObfValue.create(0.9375F);
    private static final ObfValue.OInteger OBFVAL_74 = ObfValue.create(50);
    private static final ObfValue.OFloat OBFVAL_73 = ObfValue.create(2000.0F);
    private static final ObfValue.OFloat OBFVAL_72 = ObfValue.create(50.0F);
    private static final ObfValue.OInteger OBFVAL_71 = ObfValue.create(49);
    private static final ObfValue.OInteger OBFVAL_70 = ObfValue.create(48);
    private static final ObfValue.OInteger OBFVAL_69 = ObfValue.create(47);
    private static final ObfValue.OInteger OBFVAL_68 = ObfValue.create(46);
    private static final ObfValue.OInteger OBFVAL_67 = ObfValue.create(45);
    private static final ObfValue.OInteger OBFVAL_66 = ObfValue.create(44);
    private static final ObfValue.OInteger OBFVAL_65 = ObfValue.create(43);
    private static final ObfValue.OInteger OBFVAL_64 = ObfValue.create(42);
    private static final ObfValue.OInteger OBFVAL_63 = ObfValue.create(41);
    private static final ObfValue.OInteger OBFVAL_62 = ObfValue.create(40);
    private static final ObfValue.OInteger OBFVAL_61 = ObfValue.create(39);
    private static final ObfValue.OFloat OBFVAL_60 = ObfValue.create(0.125F);
    private static final ObfValue.OInteger OBFVAL_59 = ObfValue.create(38);
    private static final ObfValue.OInteger OBFVAL_58 = ObfValue.create(37);
    private static final ObfValue.OInteger OBFVAL_57 = ObfValue.create(36);
    private static final ObfValue.OInteger OBFVAL_56 = ObfValue.create(35);
    private static final ObfValue.OInteger OBFVAL_55 = ObfValue.create(34);
    private static final ObfValue.OInteger OBFVAL_54 = ObfValue.create(33);
    private static final ObfValue.OInteger OBFVAL_53 = ObfValue.create(32);
    private static final ObfValue.OInteger OBFVAL_52 = ObfValue.create(31);
    private static final ObfValue.OFloat OBFVAL_51 = ObfValue.create(4.0F);
    private static final ObfValue.OInteger OBFVAL_50 = ObfValue.create(30);
    private static final ObfValue.OInteger OBFVAL_49 = ObfValue.create(29);
    private static final ObfValue.OInteger OBFVAL_48 = ObfValue.create(28);
    private static final ObfValue.OFloat OBFVAL_47 = ObfValue.create(0.7F);
    private static final ObfValue.OInteger OBFVAL_46 = ObfValue.create(27);
    private static final ObfValue.OInteger OBFVAL_45 = ObfValue.create(26);
    private static final ObfValue.OInteger OBFVAL_44 = ObfValue.create(25);
    private static final ObfValue.OInteger OBFVAL_43 = ObfValue.create(24);
    private static final ObfValue.OFloat OBFVAL_42 = ObfValue.create(0.8F);
    private static final ObfValue.OFloat OBFVAL_41 = ObfValue.create(3.5F);
    private static final ObfValue.OInteger OBFVAL_40 = ObfValue.create(23);
    private static final ObfValue.OInteger OBFVAL_39 = ObfValue.create(22);
    private static final ObfValue.OInteger OBFVAL_38 = ObfValue.create(21);
    private static final ObfValue.OFloat OBFVAL_37 = ObfValue.create(0.3F);
    private static final ObfValue.OInteger OBFVAL_36 = ObfValue.create(20);
    private static final ObfValue.OInteger OBFVAL_35 = ObfValue.create(19);
    private static final ObfValue.OInteger OBFVAL_34 = ObfValue.create(18);
    private static final ObfValue.OInteger OBFVAL_33 = ObfValue.create(17);
    private static final ObfValue.OInteger OBFVAL_32 = ObfValue.create(16);
    private static final ObfValue.OInteger OBFVAL_31 = ObfValue.create(14);
    private static final ObfValue.OInteger OBFVAL_30 = ObfValue.create(13);
    private static final ObfValue.OInteger OBFVAL_29 = ObfValue.create(11);
    private static final ObfValue.OInteger OBFVAL_28 = ObfValue.create(9);
    private static final ObfValue.OInteger OBFVAL_27 = ObfValue.create(8);
    private static final ObfValue.OFloat OBFVAL_26 = ObfValue.create(6000000.0F);
    private static final ObfValue.OInteger OBFVAL_25 = ObfValue.create(7);
    private static final ObfValue.OInteger OBFVAL_24 = ObfValue.create(6);
    private static final ObfValue.OInteger OBFVAL_23 = ObfValue.create(5);
    private static final ObfValue.OInteger OBFVAL_22 = ObfValue.create(4);
    private static final ObfValue.OFloat OBFVAL_21 = ObfValue.create(2.0F);
    private static final ObfValue.OInteger OBFVAL_20 = ObfValue.create(2);
    private static final ObfValue.OFloat OBFVAL_19 = ObfValue.create(10.0F);
    private static final ObfValue.OFloat OBFVAL_18 = ObfValue.create(1.5F);
    private static final ObfValue.OFloat OBFVAL_17 = ObfValue.create(0.2F);
    private static final ObfValue.OFloat OBFVAL_16 = ObfValue.create(0.025F);
    private static final ObfValue.OInteger OBFVAL_15 = ObfValue.create(16777215);
    private static final ObfValue.ODouble OBFVAL_14 = ObfValue.create(0.5D);
    private static final ObfValue.OFloat OBFVAL_13 = ObfValue.create(0.5F);
    private static final ObfValue.OFloat OBFVAL_12 = ObfValue.create(30.0F);
    private static final ObfValue.OFloat OBFVAL_11 = ObfValue.create(100.0F);
    private static final ObfValue.OInteger OBFVAL_10 = ObfValue.create(10);
    private static final ObfValue.OFloat OBFVAL_9 = ObfValue.create(-1.0F);
    private static final ObfValue.OFloat OBFVAL_8 = ObfValue.create(5.0F);
    private static final ObfValue.OInteger OBFVAL_7 = ObfValue.create(3);
    private static final ObfValue.OFloat OBFVAL_6 = ObfValue.create(3.0F);
    private static final ObfValue.OFloat OBFVAL_5 = ObfValue.create(15.0F);
    private static final ObfValue.OInteger OBFVAL_4 = ObfValue.create(255);
    private static final ObfValue.OFloat OBFVAL_3 = ObfValue.create(0.6F);
    private static final ObfValue.OInteger OBFVAL_2 = ObfValue.create(15);
    private static final ObfValue.OInteger OBFVAL_1 = ObfValue.create(4095);
    private static final ObfValue.OInteger OBFVAL_0 = ObfValue.create(12);

    /** ResourceLocation for the Air block */
    private static final ResourceLocation AIR_ID = new ResourceLocation("air");
    public static final RegistryNamespacedDefaultedByKey<ResourceLocation, Block> blockRegistry = new RegistryNamespacedDefaultedByKey(AIR_ID);
    public static final ObjectIntIdentityMap BLOCK_STATE_IDS = new ObjectIntIdentityMap();
    private CreativeTabs displayOnCreativeTab;
    public static final Block.SoundType soundTypeStone = new Block.SoundType("stone", 1.0F, 1.0F);

    /** the wood sound type */
    public static final Block.SoundType soundTypeWood = new Block.SoundType("wood", 1.0F, 1.0F);

    /** the gravel sound type */
    public static final Block.SoundType soundTypeGravel = new Block.SoundType("gravel", 1.0F, 1.0F);
    public static final Block.SoundType soundTypeGrass = new Block.SoundType("grass", 1.0F, 1.0F);
    public static final Block.SoundType soundTypePiston = new Block.SoundType("stone", 1.0F, 1.0F);
    public static final Block.SoundType soundTypeMetal = new Block.SoundType("stone", 1.0F, 1.5F);
    public static final Block.SoundType soundTypeGlass = new Block.SoundType("stone", 1.0F, 1.0F)
    {
        public String getBreakSound()
        {
            return "dig.glass";
        }
        public String getPlaceSound()
        {
            return "step.stone";
        }
    };
    public static final Block.SoundType soundTypeCloth = new Block.SoundType("cloth", 1.0F, 1.0F);
    public static final Block.SoundType soundTypeSand = new Block.SoundType("sand", 1.0F, 1.0F);
    public static final Block.SoundType soundTypeSnow = new Block.SoundType("snow", 1.0F, 1.0F);
    public static final Block.SoundType soundTypeLadder = new Block.SoundType("ladder", 1.0F, 1.0F)
    {
        public String getBreakSound()
        {
            return "dig.wood";
        }
    };
    public static final Block.SoundType soundTypeAnvil = new Block.SoundType("anvil", 0.3F, 1.0F)
    {
        public String getBreakSound()
        {
            return "dig.stone";
        }
        public String getPlaceSound()
        {
            return "random.anvil_land";
        }
    };
    public static final Block.SoundType SLIME_SOUND = new Block.SoundType("slime", 1.0F, 1.0F)
    {
        public String getBreakSound()
        {
            return "mob.slime.big";
        }
        public String getPlaceSound()
        {
            return "mob.slime.big";
        }
        public String getStepSound()
        {
            return "mob.slime.small";
        }
    };
    protected boolean fullBlock;

    /** How much light is subtracted for going through this block */
    protected int lightOpacity;
    protected boolean translucent;

    /** Amount of light emitted */
    protected int lightValue;

    /**
     * Flag if block should use the brightest neighbor light value as its own
     */
    protected boolean useNeighborBrightness;

    /** Indicates how many hits it takes to break a block. */
    protected ObfValue.OFloat blockHardness;

    /** Indicates how much this block can resist explosions */
    protected float blockResistance;
    protected boolean enableStats;

    /**
     * Flags whether or not this block is of a type that needs random ticking. Ref-counted by ExtendedBlockStorage in
     * order to broadly cull a chunk from the random chunk update list for efficiency's sake.
     */
    protected boolean needsRandomTick;

    /** true if the Block contains a Tile Entity */
    protected boolean isBlockContainer;
    protected double minX;
    protected double minY;
    protected double minZ;
    protected double maxX;
    protected double maxY;
    protected double maxZ;

    /** Sound of stepping on the block */
    public Block.SoundType stepSound;
    public float blockParticleGravity;
    protected final Material blockMaterial;
    protected final MapColor field_181083_K;

    /**
     * Determines how much velocity is maintained while moving on top of this block
     */
    public ObfValue.OFloat slipperiness;
    protected final BlockState blockState;
    private IBlockState defaultBlockState;
    private String unlocalizedName;

    public static int getIdFromBlock(Block blockIn)
    {
        return blockRegistry.b(blockIn);
    }

    /**
     * Get a unique ID for the given BlockState, containing both BlockID and metadata
     */
    public static int getStateId(IBlockState state)
    {
        Block block = state.getBlock();
        return getIdFromBlock(block) + (block.getMetaFromState(state) << OBFVAL_0.get());
    }

    public static Block getBlockById(int id)
    {
        return (Block)blockRegistry.getObjectById(id);
    }

    /**
     * Get a BlockState by it's ID (see getStateId)
     */
    public static IBlockState getStateById(int id)
    {
        int i = id & OBFVAL_1.get();
        int j = id >> OBFVAL_0.get() & OBFVAL_2.get();
        return getBlockById(i).getStateFromMeta(j);
    }

    public static Block getBlockFromItem(Item itemIn)
    {
        return itemIn instanceof ItemBlock ? ((ItemBlock)itemIn).getBlock() : null;
    }

    public static Block getBlockFromName(String name)
    {
        ResourceLocation resourcelocation = new ResourceLocation(name);

        if (blockRegistry.d(resourcelocation))
        {
            return (Block)blockRegistry.getObject(resourcelocation);
        }
        else
        {
            try
            {
                return (Block)blockRegistry.getObjectById(Integer.parseInt(name));
            }
            catch (NumberFormatException var3)
            {
                return null;
            }
        }
    }

    public boolean isFullBlock()
    {
        return this.fullBlock;
    }

    public int getLightOpacity()
    {
        return this.lightOpacity;
    }

    /**
     * Used in the renderer to apply ambient occlusion
     */
    public boolean isTranslucent()
    {
        return this.translucent;
    }

    public int getLightValue()
    {
        return this.lightValue;
    }

    /**
     * Should block use the brightest neighbor light value as its own
     */
    public boolean getUseNeighborBrightness()
    {
        return this.useNeighborBrightness;
    }

    /**
     * Get a material of block
     */
    public Material getMaterial()
    {
        return this.blockMaterial;
    }

    /**
     * Get the MapColor for this Block and the given BlockState
     */
    public MapColor getMapColor(IBlockState state)
    {
        return this.field_181083_K;
    }

    /**
     * Convert the given metadata into a BlockState for this Block
     */
    public IBlockState getStateFromMeta(int meta)
    {
        return this.getDefaultState();
    }

    /**
     * Convert the BlockState into the correct metadata value
     */
    public int getMetaFromState(IBlockState state)
    {
        if (state != null && !state.getPropertyNames().isEmpty())
        {
            throw new IllegalArgumentException("Don\'t know how to convert " + state + " back into data...");
        }
        else
        {
            return 0;
        }
    }

    /**
     * Get the actual Block state of this Block at the given position. This applies properties not visible in the
     * metadata, such as fence connections.
     */
    public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos)
    {
        return state;
    }

    public Block(Material p_i46399_1_, MapColor p_i46399_2_)
    {
        this.blockHardness = 0.0F.new OFloat();
        this.slipperiness = 0.0F.new OFloat();
        this.enableStats = true;
        this.stepSound = soundTypeStone;
        this.blockParticleGravity = 1.0F;
        this.slipperiness.set(OBFVAL_3.get());
        this.blockMaterial = p_i46399_1_;
        this.field_181083_K = p_i46399_2_;
        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
        this.fullBlock = this.isOpaqueCube();
        this.lightOpacity = this.isOpaqueCube() ? OBFVAL_4.get() : 0;
        this.translucent = !p_i46399_1_.blocksLight();
        this.blockState = this.createBlockState();
        this.setDefaultState(this.blockState.getBaseState());
    }

    protected Block(Material materialIn)
    {
        this(materialIn, materialIn.getMaterialMapColor());
    }

    /**
     * Sets the footstep sound for the block. Returns the object for convenience in constructing.
     */
    protected Block setStepSound(Block.SoundType sound)
    {
        this.stepSound = sound;
        return this;
    }

    /**
     * Sets how much light is blocked going through this block. Returns the object for convenience in constructing.
     */
    protected Block setLightOpacity(int opacity)
    {
        this.lightOpacity = opacity;
        return this;
    }

    /**
     * Sets the light value that the block emits. Returns resulting block instance for constructing convenience. Args:
     * level
     */
    protected Block setLightLevel(float value)
    {
        this.lightValue = (int)(OBFVAL_5.get() * value);
        return this;
    }

    /**
     * Sets the the blocks resistance to explosions. Returns the object for convenience in constructing.
     */
    protected Block setResistance(float resistance)
    {
        this.blockResistance = resistance * OBFVAL_6.get();
        return this;
    }

    /**
     * Indicate if a material is a normal solid opaque cube
     */
    public boolean isBlockNormalCube()
    {
        return this.blockMaterial.blocksMovement() && this.isFullCube();
    }

    /**
     * Used for nearly all game logic (non-rendering) purposes. Use Forge-provided isNormalCube(IBlockAccess, BlockPos)
     * instead.
     */
    public boolean isNormalCube()
    {
        return this.blockMaterial.isOpaque() && this.isFullCube() && !this.canProvidePower();
    }

    public boolean isVisuallyOpaque()
    {
        return this.blockMaterial.blocksMovement() && this.isFullCube();
    }

    public boolean isFullCube()
    {
        return true;
    }

    public boolean isPassable(IBlockAccess worldIn, BlockPos pos)
    {
        return !this.blockMaterial.blocksMovement();
    }

    /**
     * The type of render function called. 3 for standard block models, 2 for TESR's, 1 for liquids, -1 is no render
     */
    public int getRenderType()
    {
        return OBFVAL_7.get();
    }

    /**
     * Whether this Block can be replaced directly by other blocks (true for e.g. tall grass)
     */
    public boolean isReplaceable(World worldIn, BlockPos pos)
    {
        return false;
    }

    /**
     * Sets how many hits it takes to break a block.
     */
    protected Block setHardness(float hardness)
    {
        this.blockHardness.set(hardness);

        if (this.blockResistance < hardness * OBFVAL_8.get())
        {
            this.blockResistance = hardness * OBFVAL_8.get();
        }

        return this;
    }

    protected Block setBlockUnbreakable()
    {
        this.setHardness(OBFVAL_9.get());
        return this;
    }

    public float getBlockHardness(World worldIn, BlockPos pos)
    {
        return this.blockHardness.get();
    }

    /**
     * Sets whether this block type will receive random update ticks
     */
    protected Block setTickRandomly(boolean shouldTick)
    {
        this.needsRandomTick = shouldTick;
        return this;
    }

    /**
     * Returns whether or not this block is of a type that needs random ticking. Called for ref-counting purposes by
     * ExtendedBlockStorage in order to broadly cull a chunk from the random chunk update list for efficiency's sake.
     */
    public boolean getTickRandomly()
    {
        return this.needsRandomTick;
    }

    public boolean hasTileEntity()
    {
        return this.isBlockContainer;
    }

    protected final void setBlockBounds(float minX, float minY, float minZ, float maxX, float maxY, float maxZ)
    {
        this.minX = (double)minX;
        this.minY = (double)minY;
        this.minZ = (double)minZ;
        this.maxX = (double)maxX;
        this.maxY = (double)maxY;
        this.maxZ = (double)maxZ;
    }

    public int getMixedBrightnessForBlock(IBlockAccess worldIn, BlockPos pos)
    {
        Block block = worldIn.getBlockState(pos).getBlock();
        int i = worldIn.getCombinedLight(pos, block.getLightValue());

        if (i == 0 && block instanceof BlockSlab)
        {
            pos = pos.down();
            block = worldIn.getBlockState(pos).getBlock();
            return worldIn.getCombinedLight(pos, block.getLightValue());
        }
        else
        {
            return i;
        }
    }

    public boolean shouldSideBeRendered(IBlockAccess worldIn, BlockPos pos, EnumFacing side)
    {
        return side == EnumFacing.DOWN && this.minY > 0.0D ? true : (side == EnumFacing.UP && this.maxY < 1.0D ? true : (side == EnumFacing.NORTH && this.minZ > 0.0D ? true : (side == EnumFacing.SOUTH && this.maxZ < 1.0D ? true : (side == EnumFacing.WEST && this.minX > 0.0D ? true : (side == EnumFacing.EAST && this.maxX < 1.0D ? true : !worldIn.getBlockState(pos).getBlock().isOpaqueCube())))));
    }

    /**
     * Whether this Block is solid on the given Side
     */
    public boolean isBlockSolid(IBlockAccess worldIn, BlockPos pos, EnumFacing side)
    {
        return worldIn.getBlockState(pos).getBlock().getMaterial().isSolid();
    }

    public AxisAlignedBB getSelectedBoundingBox(World worldIn, BlockPos pos)
    {
        return new AxisAlignedBB((double)pos.n() + this.minX, (double)pos.o() + this.minY, (double)pos.p() + this.minZ, (double)pos.n() + this.maxX, (double)pos.o() + this.maxY, (double)pos.p() + this.maxZ);
    }

    /**
     * Add all collision boxes of this Block to the list that intersect with the given mask.
     */
    public void addCollisionBoxesToList(World worldIn, BlockPos pos, IBlockState state, AxisAlignedBB mask, List<AxisAlignedBB> list, Entity collidingEntity)
    {
        AxisAlignedBB axisalignedbb = this.getCollisionBoundingBox(worldIn, pos, state);

        if (axisalignedbb != null && mask.intersectsWith(axisalignedbb))
        {
            list.add(axisalignedbb);
        }
    }

    public AxisAlignedBB getCollisionBoundingBox(World worldIn, BlockPos pos, IBlockState state)
    {
        return new AxisAlignedBB((double)pos.n() + this.minX, (double)pos.o() + this.minY, (double)pos.p() + this.minZ, (double)pos.n() + this.maxX, (double)pos.o() + this.maxY, (double)pos.p() + this.maxZ);
    }

    /**
     * Used to determine ambient occlusion and culling when rebuilding chunks for render
     */
    public boolean isOpaqueCube()
    {
        return true;
    }

    public boolean canCollideCheck(IBlockState state, boolean hitIfLiquid)
    {
        return this.isCollidable();
    }

    /**
     * Returns if this block is collidable (only used by Fire). Args: x, y, z
     */
    public boolean isCollidable()
    {
        return true;
    }

    /**
     * Called randomly when setTickRandomly is set to true (used by e.g. crops to grow, etc.)
     */
    public void randomTick(World worldIn, BlockPos pos, IBlockState state, Random random)
    {
        this.updateTick(worldIn, pos, state, random);
    }

    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand)
    {
    }

    public void randomDisplayTick(World worldIn, BlockPos pos, IBlockState state, Random rand)
    {
    }

    /**
     * Called when a player destroys this Block
     */
    public void onBlockDestroyedByPlayer(World worldIn, BlockPos pos, IBlockState state)
    {
    }

    /**
     * Called when a neighboring block changes.
     */
    public void onNeighborBlockChange(World worldIn, BlockPos pos, IBlockState state, Block neighborBlock)
    {
    }

    /**
     * How many world ticks before ticking
     */
    public int tickRate(World worldIn)
    {
        return OBFVAL_10.get();
    }

    public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state)
    {
    }

    public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
    {
    }

    /**
     * Returns the quantity of items to drop on block destruction.
     */
    public int quantityDropped(Random random)
    {
        return 1;
    }

    /**
     * Get the Item that this Block should drop when harvested.
     */
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return Item.getItemFromBlock(this);
    }

    /**
     * Get the hardness of this Block relative to the ability of the given player
     */
    public float getPlayerRelativeBlockHardness(EntityPlayer playerIn, World worldIn, BlockPos pos)
    {
        float f = this.getBlockHardness(worldIn, pos);
        return f < 0.0F ? 0.0F : (!playerIn.canHarvestBlock(this) ? playerIn.getToolDigEfficiency(this) / f / OBFVAL_11.get() : playerIn.getToolDigEfficiency(this) / f / OBFVAL_12.get());
    }

    /**
     * Spawn this Block's drops into the World as EntityItems
     */
    public final void dropBlockAsItem(World worldIn, BlockPos pos, IBlockState state, int forture)
    {
        this.dropBlockAsItemWithChance(worldIn, pos, state, 1.0F, forture);
    }

    /**
     * Spawns this Block's drops into the World as EntityItems.
     */
    public void dropBlockAsItemWithChance(World worldIn, BlockPos pos, IBlockState state, float chance, int fortune)
    {
        if (!worldIn.isRemote)
        {
            int i = this.quantityDroppedWithBonus(fortune, worldIn.rand);

            for (int j = 0; j < i; ++j)
            {
                if (worldIn.rand.nextFloat() <= chance)
                {
                    Item item = this.getItemDropped(state, worldIn.rand, fortune);

                    if (item != null)
                    {
                        spawnAsEntity(worldIn, pos, new ItemStack(item, 1, this.damageDropped(state)));
                    }
                }
            }
        }
    }

    /**
     * Spawns the given ItemStack as an EntityItem into the World at the given position
     */
    public static void spawnAsEntity(World worldIn, BlockPos pos, ItemStack stack)
    {
        if (!worldIn.isRemote && worldIn.getGameRules().getBoolean("doTileDrops"))
        {
            float f = OBFVAL_13.get();
            double d0 = (double)(worldIn.rand.nextFloat() * f) + (double)(1.0F - f) * OBFVAL_14.get();
            double d1 = (double)(worldIn.rand.nextFloat() * f) + (double)(1.0F - f) * OBFVAL_14.get();
            double d2 = (double)(worldIn.rand.nextFloat() * f) + (double)(1.0F - f) * OBFVAL_14.get();
            EntityItem entityitem = new EntityItem(worldIn, (double)pos.n() + d0, (double)pos.o() + d1, (double)pos.p() + d2, stack);
            entityitem.setDefaultPickupDelay();
            worldIn.spawnEntityInWorld(entityitem);
        }
    }

    /**
     * Spawns the given amount of experience into the World as XP orb entities
     */
    protected void dropXpOnBlockBreak(World worldIn, BlockPos pos, int amount)
    {
        if (!worldIn.isRemote)
        {
            while (amount > 0)
            {
                int i = EntityXPOrb.getXPSplit(amount);
                amount -= i;
                worldIn.spawnEntityInWorld(new EntityXPOrb(worldIn, (double)pos.n() + OBFVAL_14.get(), (double)pos.o() + OBFVAL_14.get(), (double)pos.p() + OBFVAL_14.get(), i));
            }
        }
    }

    /**
     * Gets the metadata of the item this Block can drop. This method is called when the block gets destroyed. It
     * returns the metadata of the dropped item based on the old metadata of the block.
     */
    public int damageDropped(IBlockState state)
    {
        return 0;
    }

    /**
     * Returns how much this block can resist explosions from the passed in entity.
     */
    public float getExplosionResistance(Entity exploder)
    {
        return this.blockResistance / OBFVAL_8.get();
    }

    /**
     * Ray traces through the blocks collision from start vector to end vector returning a ray trace hit.
     */
    public MovingObjectPosition collisionRayTrace(World worldIn, BlockPos pos, Vec3 start, Vec3 end)
    {
        this.setBlockBoundsBasedOnState(worldIn, pos);
        start = start.addVector((double)(-pos.n()), (double)(-pos.o()), (double)(-pos.p()));
        end = end.addVector((double)(-pos.n()), (double)(-pos.o()), (double)(-pos.p()));
        Vec3 vec3 = start.getIntermediateWithXValue(end, this.minX);
        Vec3 vec31 = start.getIntermediateWithXValue(end, this.maxX);
        Vec3 vec32 = start.getIntermediateWithYValue(end, this.minY);
        Vec3 vec33 = start.getIntermediateWithYValue(end, this.maxY);
        Vec3 vec34 = start.getIntermediateWithZValue(end, this.minZ);
        Vec3 vec35 = start.getIntermediateWithZValue(end, this.maxZ);

        if (!this.isVecInsideYZBounds(vec3))
        {
            vec3 = null;
        }

        if (!this.isVecInsideYZBounds(vec31))
        {
            vec31 = null;
        }

        if (!this.isVecInsideXZBounds(vec32))
        {
            vec32 = null;
        }

        if (!this.isVecInsideXZBounds(vec33))
        {
            vec33 = null;
        }

        if (!this.isVecInsideXYBounds(vec34))
        {
            vec34 = null;
        }

        if (!this.isVecInsideXYBounds(vec35))
        {
            vec35 = null;
        }

        Vec3 vec36 = null;

        if (vec3 != null && (vec36 == null || start.squareDistanceTo(vec3) < start.squareDistanceTo(vec36)))
        {
            vec36 = vec3;
        }

        if (vec31 != null && (vec36 == null || start.squareDistanceTo(vec31) < start.squareDistanceTo(vec36)))
        {
            vec36 = vec31;
        }

        if (vec32 != null && (vec36 == null || start.squareDistanceTo(vec32) < start.squareDistanceTo(vec36)))
        {
            vec36 = vec32;
        }

        if (vec33 != null && (vec36 == null || start.squareDistanceTo(vec33) < start.squareDistanceTo(vec36)))
        {
            vec36 = vec33;
        }

        if (vec34 != null && (vec36 == null || start.squareDistanceTo(vec34) < start.squareDistanceTo(vec36)))
        {
            vec36 = vec34;
        }

        if (vec35 != null && (vec36 == null || start.squareDistanceTo(vec35) < start.squareDistanceTo(vec36)))
        {
            vec36 = vec35;
        }

        if (vec36 == null)
        {
            return null;
        }
        else
        {
            EnumFacing enumfacing = null;

            if (vec36 == vec3)
            {
                enumfacing = EnumFacing.WEST;
            }

            if (vec36 == vec31)
            {
                enumfacing = EnumFacing.EAST;
            }

            if (vec36 == vec32)
            {
                enumfacing = EnumFacing.DOWN;
            }

            if (vec36 == vec33)
            {
                enumfacing = EnumFacing.UP;
            }

            if (vec36 == vec34)
            {
                enumfacing = EnumFacing.NORTH;
            }

            if (vec36 == vec35)
            {
                enumfacing = EnumFacing.SOUTH;
            }

            return new MovingObjectPosition(vec36.addVector((double)pos.n(), (double)pos.o(), (double)pos.p()), enumfacing, pos);
        }
    }

    /**
     * Checks if a vector is within the Y and Z bounds of the block.
     */
    private boolean isVecInsideYZBounds(Vec3 point)
    {
        return point == null ? false : point.yCoord >= this.minY && point.yCoord <= this.maxY && point.zCoord >= this.minZ && point.zCoord <= this.maxZ;
    }

    /**
     * Checks if a vector is within the X and Z bounds of the block.
     */
    private boolean isVecInsideXZBounds(Vec3 point)
    {
        return point == null ? false : point.xCoord >= this.minX && point.xCoord <= this.maxX && point.zCoord >= this.minZ && point.zCoord <= this.maxZ;
    }

    /**
     * Checks if a vector is within the X and Y bounds of the block.
     */
    private boolean isVecInsideXYBounds(Vec3 point)
    {
        return point == null ? false : point.xCoord >= this.minX && point.xCoord <= this.maxX && point.yCoord >= this.minY && point.yCoord <= this.maxY;
    }

    /**
     * Called when this Block is destroyed by an Explosion
     */
    public void onBlockDestroyedByExplosion(World worldIn, BlockPos pos, Explosion explosionIn)
    {
    }

    public EnumWorldBlockLayer getBlockLayer()
    {
        return EnumWorldBlockLayer.SOLID;
    }

    public boolean canReplace(World worldIn, BlockPos pos, EnumFacing side, ItemStack stack)
    {
        return this.canPlaceBlockOnSide(worldIn, pos, side);
    }

    /**
     * Check whether this Block can be placed on the given side
     */
    public boolean canPlaceBlockOnSide(World worldIn, BlockPos pos, EnumFacing side)
    {
        return this.canPlaceBlockAt(worldIn, pos);
    }

    public boolean canPlaceBlockAt(World worldIn, BlockPos pos)
    {
        return worldIn.getBlockState(pos).getBlock().blockMaterial.isReplaceable();
    }

    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ)
    {
        return false;
    }

    /**
     * Triggered whenever an entity collides with this block (enters into the block)
     */
    public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, Entity entityIn)
    {
    }

    /**
     * Called by ItemBlocks just before a block is actually set in the world, to allow for adjustments to the
     * IBlockstate
     */
    public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
    {
        return this.getStateFromMeta(meta);
    }

    public void onBlockClicked(World worldIn, BlockPos pos, EntityPlayer playerIn)
    {
    }

    public Vec3 modifyAcceleration(World worldIn, BlockPos pos, Entity entityIn, Vec3 motion)
    {
        return motion;
    }

    public void setBlockBoundsBasedOnState(IBlockAccess worldIn, BlockPos pos)
    {
    }

    /**
     * returns the block bounderies minX value
     */
    public final double getBlockBoundsMinX()
    {
        return this.minX;
    }

    /**
     * returns the block bounderies maxX value
     */
    public final double getBlockBoundsMaxX()
    {
        return this.maxX;
    }

    /**
     * returns the block bounderies minY value
     */
    public final double getBlockBoundsMinY()
    {
        return this.minY;
    }

    /**
     * returns the block bounderies maxY value
     */
    public final double getBlockBoundsMaxY()
    {
        return this.maxY;
    }

    /**
     * returns the block bounderies minZ value
     */
    public final double getBlockBoundsMinZ()
    {
        return this.minZ;
    }

    /**
     * returns the block bounderies maxZ value
     */
    public final double getBlockBoundsMaxZ()
    {
        return this.maxZ;
    }

    public int getBlockColor()
    {
        return OBFVAL_15.get();
    }

    public int getRenderColor(IBlockState state)
    {
        return OBFVAL_15.get();
    }

    public int colorMultiplier(IBlockAccess worldIn, BlockPos pos, int renderPass)
    {
        return OBFVAL_15.get();
    }

    public final int colorMultiplier(IBlockAccess worldIn, BlockPos pos)
    {
        return this.colorMultiplier(worldIn, pos, 0);
    }

    public int getWeakPower(IBlockAccess worldIn, BlockPos pos, IBlockState state, EnumFacing side)
    {
        return 0;
    }

    /**
     * Can this block provide power. Only wire currently seems to have this change based on its state.
     */
    public boolean canProvidePower()
    {
        return false;
    }

    /**
     * Called When an Entity Collided with the Block
     */
    public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn)
    {
    }

    public int getStrongPower(IBlockAccess worldIn, BlockPos pos, IBlockState state, EnumFacing side)
    {
        return 0;
    }

    /**
     * Sets the block's bounds for rendering it as an item
     */
    public void setBlockBoundsForItemRender()
    {
    }

    public void harvestBlock(World worldIn, EntityPlayer player, BlockPos pos, IBlockState state, TileEntity te)
    {
        player.triggerAchievement(StatList.mineBlockStatArray[getIdFromBlock(this)]);
        player.addExhaustion(OBFVAL_16.get());

        if (this.canSilkHarvest() && EnchantmentHelper.getSilkTouchModifier(player))
        {
            ItemStack itemstack = this.createStackedBlock(state);

            if (itemstack != null)
            {
                spawnAsEntity(worldIn, pos, itemstack);
            }
        }
        else
        {
            int i = EnchantmentHelper.getFortuneModifier(player);
            this.dropBlockAsItem(worldIn, pos, state, i);
        }
    }

    protected boolean canSilkHarvest()
    {
        return this.isFullCube() && !this.isBlockContainer;
    }

    protected ItemStack createStackedBlock(IBlockState state)
    {
        int i = 0;
        Item item = Item.getItemFromBlock(this);

        if (item != null && item.getHasSubtypes())
        {
            i = this.getMetaFromState(state);
        }

        return new ItemStack(item, 1, i);
    }

    /**
     * Get the quantity dropped based on the given fortune level
     */
    public int quantityDroppedWithBonus(int fortune, Random random)
    {
        return this.quantityDropped(random);
    }

    /**
     * Called by ItemBlocks after a block is set in the world, to allow post-place logic
     */
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
    {
    }

    public boolean func_181623_g()
    {
        return !this.blockMaterial.isSolid() && !this.blockMaterial.isLiquid();
    }

    public Block setUnlocalizedName(String name)
    {
        this.unlocalizedName = name;
        return this;
    }

    /**
     * Gets the localized name of this block. Used for the statistics page.
     */
    public String getLocalizedName()
    {
        return StatCollector.translateToLocal(this.getUnlocalizedName() + ".name");
    }

    /**
     * Returns the unlocalized name of the block with "tile." appended to the front.
     */
    public String getUnlocalizedName()
    {
        return "tile." + this.unlocalizedName;
    }

    /**
     * Called on both Client and Server when World#addBlockEvent is called
     */
    public boolean onBlockEventReceived(World worldIn, BlockPos pos, IBlockState state, int eventID, int eventParam)
    {
        return false;
    }

    /**
     * Return the state of blocks statistics flags - if the block is counted for mined and placed.
     */
    public boolean getEnableStats()
    {
        return this.enableStats;
    }

    protected Block disableStats()
    {
        this.enableStats = false;
        return this;
    }

    public int getMobilityFlag()
    {
        return this.blockMaterial.getMaterialMobility();
    }

    /**
     * Returns the default ambient occlusion value based on block opacity
     */
    public float getAmbientOcclusionLightValue()
    {
        return this.isBlockNormalCube() ? OBFVAL_17.get() : 1.0F;
    }

    /**
     * Block's chance to react to a living entity falling on it.
     */
    public void onFallenUpon(World worldIn, BlockPos pos, Entity entityIn, float fallDistance)
    {
        entityIn.fall(fallDistance, 1.0F);
    }

    /**
     * Called when an Entity lands on this Block. This method *must* update motionY because the entity will not do that
     * on its own
     */
    public void onLanded(World worldIn, Entity entityIn)
    {
        entityIn.motionY = 0.0D;
    }

    public Item getItem(World worldIn, BlockPos pos)
    {
        return Item.getItemFromBlock(this);
    }

    public int getDamageValue(World worldIn, BlockPos pos)
    {
        return this.damageDropped(worldIn.getBlockState(pos));
    }

    /**
     * returns a list of blocks with the same ID, but different meta (eg: wood returns 4 blocks)
     */
    public void getSubBlocks(Item itemIn, CreativeTabs tab, List<ItemStack> list)
    {
        list.add(new ItemStack(itemIn, 1, 0));
    }

    /**
     * Returns the CreativeTab to display the given block on.
     */
    public CreativeTabs getCreativeTabToDisplayOn()
    {
        return this.displayOnCreativeTab;
    }

    public Block setCreativeTab(CreativeTabs tab)
    {
        this.displayOnCreativeTab = tab;
        return this;
    }

    public void onBlockHarvested(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player)
    {
    }

    /**
     * Called similar to random ticks, but only when it is raining.
     */
    public void fillWithRain(World worldIn, BlockPos pos)
    {
    }

    /**
     * Returns true only if block is flowerPot
     */
    public boolean isFlowerPot()
    {
        return false;
    }

    public boolean requiresUpdates()
    {
        return true;
    }

    /**
     * Return whether this block can drop from an explosion.
     */
    public boolean canDropFromExplosion(Explosion explosionIn)
    {
        return true;
    }

    public boolean isAssociatedBlock(Block other)
    {
        return this == other;
    }

    public static boolean isEqualTo(Block blockIn, Block other)
    {
        return blockIn != null && other != null ? (blockIn == other ? true : blockIn.isAssociatedBlock(other)) : false;
    }

    public boolean hasComparatorInputOverride()
    {
        return false;
    }

    public int getComparatorInputOverride(World worldIn, BlockPos pos)
    {
        return 0;
    }

    /**
     * Possibly modify the given BlockState before rendering it on an Entity (Minecarts, Endermen, ...)
     */
    public IBlockState getStateForEntityRender(IBlockState state)
    {
        return state;
    }

    protected BlockState createBlockState()
    {
        return new BlockState(this, new IProperty[0]);
    }

    public BlockState getBlockState()
    {
        return this.blockState;
    }

    protected final void setDefaultState(IBlockState state)
    {
        this.defaultBlockState = state;
    }

    public final IBlockState getDefaultState()
    {
        return this.defaultBlockState;
    }

    /**
     * Get the OffsetType for this Block. Determines if the model is rendered slightly offset.
     */
    public Block.EnumOffsetType getOffsetType()
    {
        return Block.EnumOffsetType.NONE;
    }

    public String toString()
    {
        return "Block{" + blockRegistry.c(this) + "}";
    }

    public static void registerBlocks()
    {
        registerBlock(0, AIR_ID, (new BlockAir()).c("air"));
        registerBlock(1, "stone", (new BlockStone()).c(OBFVAL_18.get()).setResistance(OBFVAL_19.get()).setStepSound(soundTypePiston).setUnlocalizedName("stone"));
        registerBlock(OBFVAL_20.get(), "grass", (new BlockGrass()).c(OBFVAL_3.get()).setStepSound(soundTypeGrass).setUnlocalizedName("grass"));
        registerBlock(OBFVAL_7.get(), "dirt", (new BlockDirt()).c(OBFVAL_13.get()).setStepSound(soundTypeGravel).setUnlocalizedName("dirt"));
        Block block = (new Block(Material.rock)).setHardness(OBFVAL_21.get()).setResistance(OBFVAL_19.get()).setStepSound(soundTypePiston).setUnlocalizedName("stonebrick").setCreativeTab(CreativeTabs.tabBlock);
        registerBlock(OBFVAL_22.get(), "cobblestone", block);
        Block block1 = (new BlockPlanks()).setHardness(OBFVAL_21.get()).setResistance(OBFVAL_8.get()).setStepSound(soundTypeWood).setUnlocalizedName("wood");
        registerBlock(OBFVAL_23.get(), "planks", block1);
        registerBlock(OBFVAL_24.get(), "sapling", (new BlockSapling()).c(0.0F).setStepSound(soundTypeGrass).setUnlocalizedName("sapling"));
        registerBlock(OBFVAL_25.get(), "bedrock", (new Block(Material.rock)).setBlockUnbreakable().setResistance(OBFVAL_26.get()).setStepSound(soundTypePiston).setUnlocalizedName("bedrock").disableStats().setCreativeTab(CreativeTabs.tabBlock));
        registerBlock(OBFVAL_27.get(), "flowing_water", (new BlockDynamicLiquid(Material.water)).c(OBFVAL_11.get()).setLightOpacity(OBFVAL_7.get()).setUnlocalizedName("water").disableStats());
        registerBlock(OBFVAL_28.get(), "water", (new BlockStaticLiquid(Material.water)).c(OBFVAL_11.get()).setLightOpacity(OBFVAL_7.get()).setUnlocalizedName("water").disableStats());
        registerBlock(OBFVAL_10.get(), "flowing_lava", (new BlockDynamicLiquid(Material.lava)).c(OBFVAL_11.get()).setLightLevel(1.0F).setUnlocalizedName("lava").disableStats());
        registerBlock(OBFVAL_29.get(), "lava", (new BlockStaticLiquid(Material.lava)).c(OBFVAL_11.get()).setLightLevel(1.0F).setUnlocalizedName("lava").disableStats());
        registerBlock(OBFVAL_0.get(), "sand", (new BlockSand()).c(OBFVAL_13.get()).setStepSound(soundTypeSand).setUnlocalizedName("sand"));
        registerBlock(OBFVAL_30.get(), "gravel", (new BlockGravel()).c(OBFVAL_3.get()).setStepSound(soundTypeGravel).setUnlocalizedName("gravel"));
        registerBlock(OBFVAL_31.get(), "gold_ore", (new BlockOre()).c(OBFVAL_6.get()).setResistance(OBFVAL_8.get()).setStepSound(soundTypePiston).setUnlocalizedName("oreGold"));
        registerBlock(OBFVAL_2.get(), "iron_ore", (new BlockOre()).c(OBFVAL_6.get()).setResistance(OBFVAL_8.get()).setStepSound(soundTypePiston).setUnlocalizedName("oreIron"));
        registerBlock(OBFVAL_32.get(), "coal_ore", (new BlockOre()).c(OBFVAL_6.get()).setResistance(OBFVAL_8.get()).setStepSound(soundTypePiston).setUnlocalizedName("oreCoal"));
        registerBlock(OBFVAL_33.get(), "log", (new BlockOldLog()).c("log"));
        registerBlock(OBFVAL_34.get(), "leaves", (new BlockOldLeaf()).c("leaves"));
        registerBlock(OBFVAL_35.get(), "sponge", (new BlockSponge()).c(OBFVAL_3.get()).setStepSound(soundTypeGrass).setUnlocalizedName("sponge"));
        registerBlock(OBFVAL_36.get(), "glass", (new BlockGlass(Material.glass, false)).c(OBFVAL_37.get()).setStepSound(soundTypeGlass).setUnlocalizedName("glass"));
        registerBlock(OBFVAL_38.get(), "lapis_ore", (new BlockOre()).c(OBFVAL_6.get()).setResistance(OBFVAL_8.get()).setStepSound(soundTypePiston).setUnlocalizedName("oreLapis"));
        registerBlock(OBFVAL_39.get(), "lapis_block", (new Block(Material.iron, MapColor.lapisColor)).setHardness(OBFVAL_6.get()).setResistance(OBFVAL_8.get()).setStepSound(soundTypePiston).setUnlocalizedName("blockLapis").setCreativeTab(CreativeTabs.tabBlock));
        registerBlock(OBFVAL_40.get(), "dispenser", (new BlockDispenser()).c(OBFVAL_41.get()).setStepSound(soundTypePiston).setUnlocalizedName("dispenser"));
        Block block2 = (new BlockSandStone()).a(soundTypePiston).setHardness(OBFVAL_42.get()).setUnlocalizedName("sandStone");
        registerBlock(OBFVAL_43.get(), "sandstone", block2);
        registerBlock(OBFVAL_44.get(), "noteblock", (new BlockNote()).c(OBFVAL_42.get()).setUnlocalizedName("musicBlock"));
        registerBlock(OBFVAL_45.get(), "bed", (new BlockBed()).a(soundTypeWood).setHardness(OBFVAL_17.get()).setUnlocalizedName("bed").disableStats());
        registerBlock(OBFVAL_46.get(), "golden_rail", (new BlockRailPowered()).c(OBFVAL_47.get()).setStepSound(soundTypeMetal).setUnlocalizedName("goldenRail"));
        registerBlock(OBFVAL_48.get(), "detector_rail", (new BlockRailDetector()).c(OBFVAL_47.get()).setStepSound(soundTypeMetal).setUnlocalizedName("detectorRail"));
        registerBlock(OBFVAL_49.get(), "sticky_piston", (new BlockPistonBase(true)).c("pistonStickyBase"));
        registerBlock(OBFVAL_50.get(), "web", (new BlockWeb()).e(1).setHardness(OBFVAL_51.get()).setUnlocalizedName("web"));
        registerBlock(OBFVAL_52.get(), "tallgrass", (new BlockTallGrass()).c(0.0F).setStepSound(soundTypeGrass).setUnlocalizedName("tallgrass"));
        registerBlock(OBFVAL_53.get(), "deadbush", (new BlockDeadBush()).c(0.0F).setStepSound(soundTypeGrass).setUnlocalizedName("deadbush"));
        registerBlock(OBFVAL_54.get(), "piston", (new BlockPistonBase(false)).c("pistonBase"));
        registerBlock(OBFVAL_55.get(), "piston_head", (new BlockPistonExtension()).c("pistonBase"));
        registerBlock(OBFVAL_56.get(), "wool", (new BlockColored(Material.cloth)).c(OBFVAL_42.get()).setStepSound(soundTypeCloth).setUnlocalizedName("cloth"));
        registerBlock(OBFVAL_57.get(), "piston_extension", new BlockPistonMoving());
        registerBlock(OBFVAL_58.get(), "yellow_flower", (new BlockYellowFlower()).c(0.0F).setStepSound(soundTypeGrass).setUnlocalizedName("flower1"));
        registerBlock(OBFVAL_59.get(), "red_flower", (new BlockRedFlower()).c(0.0F).setStepSound(soundTypeGrass).setUnlocalizedName("flower2"));
        Block block3 = (new BlockMushroom()).c(0.0F).setStepSound(soundTypeGrass).setLightLevel(OBFVAL_60.get()).setUnlocalizedName("mushroom");
        registerBlock(OBFVAL_61.get(), "brown_mushroom", block3);
        Block block4 = (new BlockMushroom()).c(0.0F).setStepSound(soundTypeGrass).setUnlocalizedName("mushroom");
        registerBlock(OBFVAL_62.get(), "red_mushroom", block4);
        registerBlock(OBFVAL_63.get(), "gold_block", (new Block(Material.iron, MapColor.goldColor)).setHardness(OBFVAL_6.get()).setResistance(OBFVAL_19.get()).setStepSound(soundTypeMetal).setUnlocalizedName("blockGold").setCreativeTab(CreativeTabs.tabBlock));
        registerBlock(OBFVAL_64.get(), "iron_block", (new Block(Material.iron, MapColor.ironColor)).setHardness(OBFVAL_8.get()).setResistance(OBFVAL_19.get()).setStepSound(soundTypeMetal).setUnlocalizedName("blockIron").setCreativeTab(CreativeTabs.tabBlock));
        registerBlock(OBFVAL_65.get(), "double_stone_slab", (new BlockDoubleStoneSlab()).c(OBFVAL_21.get()).setResistance(OBFVAL_19.get()).setStepSound(soundTypePiston).setUnlocalizedName("stoneSlab"));
        registerBlock(OBFVAL_66.get(), "stone_slab", (new BlockHalfStoneSlab()).c(OBFVAL_21.get()).setResistance(OBFVAL_19.get()).setStepSound(soundTypePiston).setUnlocalizedName("stoneSlab"));
        Block block5 = (new Block(Material.rock, MapColor.redColor)).setHardness(OBFVAL_21.get()).setResistance(OBFVAL_19.get()).setStepSound(soundTypePiston).setUnlocalizedName("brick").setCreativeTab(CreativeTabs.tabBlock);
        registerBlock(OBFVAL_67.get(), "brick_block", block5);
        registerBlock(OBFVAL_68.get(), "tnt", (new BlockTNT()).c(0.0F).setStepSound(soundTypeGrass).setUnlocalizedName("tnt"));
        registerBlock(OBFVAL_69.get(), "bookshelf", (new BlockBookshelf()).c(OBFVAL_18.get()).setStepSound(soundTypeWood).setUnlocalizedName("bookshelf"));
        registerBlock(OBFVAL_70.get(), "mossy_cobblestone", (new Block(Material.rock)).setHardness(OBFVAL_21.get()).setResistance(OBFVAL_19.get()).setStepSound(soundTypePiston).setUnlocalizedName("stoneMoss").setCreativeTab(CreativeTabs.tabBlock));
        registerBlock(OBFVAL_71.get(), "obsidian", (new BlockObsidian()).c(OBFVAL_72.get()).setResistance(OBFVAL_73.get()).setStepSound(soundTypePiston).setUnlocalizedName("obsidian"));
        registerBlock(OBFVAL_74.get(), "torch", (new BlockTorch()).c(0.0F).setLightLevel(OBFVAL_75.get()).setStepSound(soundTypeWood).setUnlocalizedName("torch"));
        registerBlock(OBFVAL_76.get(), "fire", (new BlockFire()).setHardness(0.0F).setLightLevel(1.0F).setStepSound(soundTypeCloth).setUnlocalizedName("fire").disableStats());
        registerBlock(OBFVAL_77.get(), "mob_spawner", (new BlockMobSpawner()).c(OBFVAL_8.get()).setStepSound(soundTypeMetal).setUnlocalizedName("mobSpawner").disableStats());
        registerBlock(OBFVAL_78.get(), "oak_stairs", (new BlockStairs(block1.getDefaultState().withProperty(BlockPlanks.VARIANT, BlockPlanks.EnumType.OAK))).setUnlocalizedName("stairsWood"));
        registerBlock(OBFVAL_79.get(), "chest", (new BlockChest(0)).c(OBFVAL_80.get()).setStepSound(soundTypeWood).setUnlocalizedName("chest"));
        registerBlock(OBFVAL_81.get(), "redstone_wire", (new BlockRedstoneWire()).c(0.0F).setStepSound(soundTypeStone).setUnlocalizedName("redstoneDust").disableStats());
        registerBlock(OBFVAL_82.get(), "diamond_ore", (new BlockOre()).c(OBFVAL_6.get()).setResistance(OBFVAL_8.get()).setStepSound(soundTypePiston).setUnlocalizedName("oreDiamond"));
        registerBlock(OBFVAL_83.get(), "diamond_block", (new Block(Material.iron, MapColor.diamondColor)).setHardness(OBFVAL_8.get()).setResistance(OBFVAL_19.get()).setStepSound(soundTypeMetal).setUnlocalizedName("blockDiamond").setCreativeTab(CreativeTabs.tabBlock));
        registerBlock(OBFVAL_84.get(), "crafting_table", (new BlockWorkbench()).c(OBFVAL_80.get()).setStepSound(soundTypeWood).setUnlocalizedName("workbench"));
        registerBlock(OBFVAL_85.get(), "wheat", (new BlockCrops()).c("crops"));
        Block block6 = (new BlockFarmland()).c(OBFVAL_3.get()).setStepSound(soundTypeGravel).setUnlocalizedName("farmland");
        registerBlock(OBFVAL_86.get(), "farmland", block6);
        registerBlock(OBFVAL_87.get(), "furnace", (new BlockFurnace(false)).c(OBFVAL_41.get()).setStepSound(soundTypePiston).setUnlocalizedName("furnace").setCreativeTab(CreativeTabs.tabDecorations));
        registerBlock(OBFVAL_88.get(), "lit_furnace", (new BlockFurnace(true)).c(OBFVAL_41.get()).setStepSound(soundTypePiston).setLightLevel(OBFVAL_89.get()).setUnlocalizedName("furnace"));
        registerBlock(OBFVAL_90.get(), "standing_sign", (new BlockStandingSign()).c(1.0F).setStepSound(soundTypeWood).setUnlocalizedName("sign").disableStats());
        registerBlock(OBFVAL_91.get(), "wooden_door", (new BlockDoor(Material.wood)).c(OBFVAL_6.get()).setStepSound(soundTypeWood).setUnlocalizedName("doorOak").disableStats());
        registerBlock(OBFVAL_92.get(), "ladder", (new BlockLadder()).c(OBFVAL_93.get()).setStepSound(soundTypeLadder).setUnlocalizedName("ladder"));
        registerBlock(OBFVAL_94.get(), "rail", (new BlockRail()).c(OBFVAL_47.get()).setStepSound(soundTypeMetal).setUnlocalizedName("rail"));
        registerBlock(OBFVAL_95.get(), "stone_stairs", (new BlockStairs(block.getDefaultState())).setUnlocalizedName("stairsStone"));
        registerBlock(OBFVAL_96.get(), "wall_sign", (new BlockWallSign()).c(1.0F).setStepSound(soundTypeWood).setUnlocalizedName("sign").disableStats());
        registerBlock(OBFVAL_97.get(), "lever", (new BlockLever()).c(OBFVAL_13.get()).setStepSound(soundTypeWood).setUnlocalizedName("lever"));
        registerBlock(OBFVAL_98.get(), "stone_pressure_plate", (new BlockPressurePlate(Material.rock, Sensitivity.MOBS)).c(OBFVAL_13.get()).setStepSound(soundTypePiston).setUnlocalizedName("pressurePlateStone"));
        registerBlock(OBFVAL_99.get(), "iron_door", (new BlockDoor(Material.iron)).c(OBFVAL_8.get()).setStepSound(soundTypeMetal).setUnlocalizedName("doorIron").disableStats());
        registerBlock(OBFVAL_100.get(), "wooden_pressure_plate", (new BlockPressurePlate(Material.wood, Sensitivity.EVERYTHING)).c(OBFVAL_13.get()).setStepSound(soundTypeWood).setUnlocalizedName("pressurePlateWood"));
        registerBlock(OBFVAL_101.get(), "redstone_ore", (new BlockRedstoneOre(false)).c(OBFVAL_6.get()).setResistance(OBFVAL_8.get()).setStepSound(soundTypePiston).setUnlocalizedName("oreRedstone").setCreativeTab(CreativeTabs.tabBlock));
        registerBlock(OBFVAL_102.get(), "lit_redstone_ore", (new BlockRedstoneOre(true)).a(OBFVAL_103.get()).setHardness(OBFVAL_6.get()).setResistance(OBFVAL_8.get()).setStepSound(soundTypePiston).setUnlocalizedName("oreRedstone"));
        registerBlock(OBFVAL_104.get(), "unlit_redstone_torch", (new BlockRedstoneTorch(false)).c(0.0F).setStepSound(soundTypeWood).setUnlocalizedName("notGate"));
        registerBlock(OBFVAL_105.get(), "redstone_torch", (new BlockRedstoneTorch(true)).c(0.0F).setLightLevel(OBFVAL_13.get()).setStepSound(soundTypeWood).setUnlocalizedName("notGate").setCreativeTab(CreativeTabs.tabRedstone));
        registerBlock(OBFVAL_106.get(), "stone_button", (new BlockButtonStone()).c(OBFVAL_13.get()).setStepSound(soundTypePiston).setUnlocalizedName("button"));
        registerBlock(OBFVAL_107.get(), "snow_layer", (new BlockSnow()).c(OBFVAL_108.get()).setStepSound(soundTypeSnow).setUnlocalizedName("snow").setLightOpacity(0));
        registerBlock(OBFVAL_109.get(), "ice", (new BlockIce()).c(OBFVAL_13.get()).setLightOpacity(OBFVAL_7.get()).setStepSound(soundTypeGlass).setUnlocalizedName("ice"));
        registerBlock(OBFVAL_110.get(), "snow", (new BlockSnowBlock()).c(OBFVAL_17.get()).setStepSound(soundTypeSnow).setUnlocalizedName("snow"));
        registerBlock(OBFVAL_111.get(), "cactus", (new BlockCactus()).c(OBFVAL_93.get()).setStepSound(soundTypeCloth).setUnlocalizedName("cactus"));
        registerBlock(OBFVAL_112.get(), "clay", (new BlockClay()).c(OBFVAL_3.get()).setStepSound(soundTypeGravel).setUnlocalizedName("clay"));
        registerBlock(OBFVAL_113.get(), "reeds", (new BlockReed()).c(0.0F).setStepSound(soundTypeGrass).setUnlocalizedName("reeds").disableStats());
        registerBlock(OBFVAL_114.get(), "jukebox", (new BlockJukebox()).c(OBFVAL_21.get()).setResistance(OBFVAL_19.get()).setStepSound(soundTypePiston).setUnlocalizedName("jukebox"));
        registerBlock(OBFVAL_115.get(), "fence", (new BlockFence(Material.wood, BlockPlanks.EnumType.OAK.func_181070_c())).c(OBFVAL_21.get()).setResistance(OBFVAL_8.get()).setStepSound(soundTypeWood).setUnlocalizedName("fence"));
        Block block7 = (new BlockPumpkin()).c(1.0F).setStepSound(soundTypeWood).setUnlocalizedName("pumpkin");
        registerBlock(OBFVAL_116.get(), "pumpkin", block7);
        registerBlock(OBFVAL_117.get(), "netherrack", (new BlockNetherrack()).c(OBFVAL_93.get()).setStepSound(soundTypePiston).setUnlocalizedName("hellrock"));
        registerBlock(OBFVAL_118.get(), "soul_sand", (new BlockSoulSand()).c(OBFVAL_13.get()).setStepSound(soundTypeSand).setUnlocalizedName("hellsand"));
        registerBlock(OBFVAL_119.get(), "glowstone", (new BlockGlowstone(Material.glass)).c(OBFVAL_37.get()).setStepSound(soundTypeGlass).setLightLevel(1.0F).setUnlocalizedName("lightgem"));
        registerBlock(OBFVAL_120.get(), "portal", (new BlockPortal()).c(OBFVAL_9.get()).setStepSound(soundTypeGlass).setLightLevel(OBFVAL_121.get()).setUnlocalizedName("portal"));
        registerBlock(OBFVAL_122.get(), "lit_pumpkin", (new BlockPumpkin()).c(1.0F).setStepSound(soundTypeWood).setLightLevel(1.0F).setUnlocalizedName("litpumpkin"));
        registerBlock(OBFVAL_123.get(), "cake", (new BlockCake()).c(OBFVAL_13.get()).setStepSound(soundTypeCloth).setUnlocalizedName("cake").disableStats());
        registerBlock(OBFVAL_124.get(), "unpowered_repeater", (new BlockRedstoneRepeater(false)).c(0.0F).setStepSound(soundTypeWood).setUnlocalizedName("diode").disableStats());
        registerBlock(OBFVAL_125.get(), "powered_repeater", (new BlockRedstoneRepeater(true)).c(0.0F).setStepSound(soundTypeWood).setUnlocalizedName("diode").disableStats());
        registerBlock(OBFVAL_126.get(), "stained_glass", (new BlockStainedGlass(Material.glass)).c(OBFVAL_37.get()).setStepSound(soundTypeGlass).setUnlocalizedName("stainedGlass"));
        registerBlock(OBFVAL_127.get(), "trapdoor", (new BlockTrapDoor(Material.wood)).c(OBFVAL_6.get()).setStepSound(soundTypeWood).setUnlocalizedName("trapdoor").disableStats());
        registerBlock(OBFVAL_128.get(), "monster_egg", (new BlockSilverfish()).c(OBFVAL_121.get()).setUnlocalizedName("monsterStoneEgg"));
        Block block8 = (new BlockStoneBrick()).c(OBFVAL_18.get()).setResistance(OBFVAL_19.get()).setStepSound(soundTypePiston).setUnlocalizedName("stonebricksmooth");
        registerBlock(OBFVAL_129.get(), "stonebrick", block8);
        registerBlock(OBFVAL_130.get(), "brown_mushroom_block", (new BlockHugeMushroom(Material.wood, MapColor.dirtColor, block3)).c(OBFVAL_17.get()).setStepSound(soundTypeWood).setUnlocalizedName("mushroom"));
        registerBlock(OBFVAL_131.get(), "red_mushroom_block", (new BlockHugeMushroom(Material.wood, MapColor.redColor, block4)).c(OBFVAL_17.get()).setStepSound(soundTypeWood).setUnlocalizedName("mushroom"));
        registerBlock(OBFVAL_132.get(), "iron_bars", (new BlockPane(Material.iron, true)).c(OBFVAL_8.get()).setResistance(OBFVAL_19.get()).setStepSound(soundTypeMetal).setUnlocalizedName("fenceIron"));
        registerBlock(OBFVAL_133.get(), "glass_pane", (new BlockPane(Material.glass, false)).c(OBFVAL_37.get()).setStepSound(soundTypeGlass).setUnlocalizedName("thinGlass"));
        Block block9 = (new BlockMelon()).c(1.0F).setStepSound(soundTypeWood).setUnlocalizedName("melon");
        registerBlock(OBFVAL_134.get(), "melon_block", block9);
        registerBlock(OBFVAL_135.get(), "pumpkin_stem", (new BlockStem(block7)).c(0.0F).setStepSound(soundTypeWood).setUnlocalizedName("pumpkinStem"));
        registerBlock(OBFVAL_136.get(), "melon_stem", (new BlockStem(block9)).c(0.0F).setStepSound(soundTypeWood).setUnlocalizedName("pumpkinStem"));
        registerBlock(OBFVAL_137.get(), "vine", (new BlockVine()).c(OBFVAL_17.get()).setStepSound(soundTypeGrass).setUnlocalizedName("vine"));
        registerBlock(OBFVAL_138.get(), "fence_gate", (new BlockFenceGate(BlockPlanks.EnumType.OAK)).c(OBFVAL_21.get()).setResistance(OBFVAL_8.get()).setStepSound(soundTypeWood).setUnlocalizedName("fenceGate"));
        registerBlock(OBFVAL_139.get(), "brick_stairs", (new BlockStairs(block5.getDefaultState())).setUnlocalizedName("stairsBrick"));
        registerBlock(OBFVAL_140.get(), "stone_brick_stairs", (new BlockStairs(block8.getDefaultState().withProperty(BlockStoneBrick.VARIANT, EnumType.DEFAULT))).setUnlocalizedName("stairsStoneBrickSmooth"));
        registerBlock(OBFVAL_141.get(), "mycelium", (new BlockMycelium()).c(OBFVAL_3.get()).setStepSound(soundTypeGrass).setUnlocalizedName("mycel"));
        registerBlock(OBFVAL_142.get(), "waterlily", (new BlockLilyPad()).c(0.0F).setStepSound(soundTypeGrass).setUnlocalizedName("waterlily"));
        Block block10 = (new BlockNetherBrick()).c(OBFVAL_21.get()).setResistance(OBFVAL_19.get()).setStepSound(soundTypePiston).setUnlocalizedName("netherBrick").setCreativeTab(CreativeTabs.tabBlock);
        registerBlock(OBFVAL_143.get(), "nether_brick", block10);
        registerBlock(OBFVAL_144.get(), "nether_brick_fence", (new BlockFence(Material.rock, MapColor.netherrackColor)).c(OBFVAL_21.get()).setResistance(OBFVAL_19.get()).setStepSound(soundTypePiston).setUnlocalizedName("netherFence"));
        registerBlock(OBFVAL_145.get(), "nether_brick_stairs", (new BlockStairs(block10.getDefaultState())).setUnlocalizedName("stairsNetherBrick"));
        registerBlock(OBFVAL_146.get(), "nether_wart", (new BlockNetherWart()).c("netherStalk"));
        registerBlock(OBFVAL_147.get(), "enchanting_table", (new BlockEnchantmentTable()).c(OBFVAL_8.get()).setResistance(OBFVAL_73.get()).setUnlocalizedName("enchantmentTable"));
        registerBlock(OBFVAL_148.get(), "brewing_stand", (new BlockBrewingStand()).c(OBFVAL_13.get()).setLightLevel(OBFVAL_60.get()).setUnlocalizedName("brewingStand"));
        registerBlock(OBFVAL_149.get(), "cauldron", (new BlockCauldron()).c(OBFVAL_21.get()).setUnlocalizedName("cauldron"));
        registerBlock(OBFVAL_150.get(), "end_portal", (new BlockEndPortal(Material.portal)).c(OBFVAL_9.get()).setResistance(OBFVAL_26.get()));
        registerBlock(OBFVAL_151.get(), "end_portal_frame", (new BlockEndPortalFrame()).a(soundTypeGlass).setLightLevel(OBFVAL_60.get()).setHardness(OBFVAL_9.get()).setUnlocalizedName("endPortalFrame").setResistance(OBFVAL_26.get()).setCreativeTab(CreativeTabs.tabDecorations));
        registerBlock(OBFVAL_152.get(), "end_stone", (new Block(Material.rock, MapColor.sandColor)).setHardness(OBFVAL_6.get()).setResistance(OBFVAL_5.get()).setStepSound(soundTypePiston).setUnlocalizedName("whiteStone").setCreativeTab(CreativeTabs.tabBlock));
        registerBlock(OBFVAL_153.get(), "dragon_egg", (new BlockDragonEgg()).c(OBFVAL_6.get()).setResistance(OBFVAL_5.get()).setStepSound(soundTypePiston).setLightLevel(OBFVAL_60.get()).setUnlocalizedName("dragonEgg"));
        registerBlock(OBFVAL_154.get(), "redstone_lamp", (new BlockRedstoneLight(false)).c(OBFVAL_37.get()).setStepSound(soundTypeGlass).setUnlocalizedName("redstoneLight").setCreativeTab(CreativeTabs.tabRedstone));
        registerBlock(OBFVAL_155.get(), "lit_redstone_lamp", (new BlockRedstoneLight(true)).c(OBFVAL_37.get()).setStepSound(soundTypeGlass).setUnlocalizedName("redstoneLight"));
        registerBlock(OBFVAL_156.get(), "double_wooden_slab", (new BlockDoubleWoodSlab()).c(OBFVAL_21.get()).setResistance(OBFVAL_8.get()).setStepSound(soundTypeWood).setUnlocalizedName("woodSlab"));
        registerBlock(OBFVAL_157.get(), "wooden_slab", (new BlockHalfWoodSlab()).c(OBFVAL_21.get()).setResistance(OBFVAL_8.get()).setStepSound(soundTypeWood).setUnlocalizedName("woodSlab"));
        registerBlock(OBFVAL_158.get(), "cocoa", (new BlockCocoa()).c(OBFVAL_17.get()).setResistance(OBFVAL_8.get()).setStepSound(soundTypeWood).setUnlocalizedName("cocoa"));
        registerBlock(OBFVAL_159.get(), "sandstone_stairs", (new BlockStairs(block2.getDefaultState().withProperty(BlockSandStone.TYPE, net.minecraft.block.BlockSandStone.EnumType.SMOOTH))).setUnlocalizedName("stairsSandStone"));
        registerBlock(OBFVAL_160.get(), "emerald_ore", (new BlockOre()).c(OBFVAL_6.get()).setResistance(OBFVAL_8.get()).setStepSound(soundTypePiston).setUnlocalizedName("oreEmerald"));
        registerBlock(OBFVAL_161.get(), "ender_chest", (new BlockEnderChest()).c(OBFVAL_162.get()).setResistance(OBFVAL_163.get()).setStepSound(soundTypePiston).setUnlocalizedName("enderChest").setLightLevel(OBFVAL_13.get()));
        registerBlock(OBFVAL_164.get(), "tripwire_hook", (new BlockTripWireHook()).c("tripWireSource"));
        registerBlock(OBFVAL_165.get(), "tripwire", (new BlockTripWire()).c("tripWire"));
        registerBlock(OBFVAL_166.get(), "emerald_block", (new Block(Material.iron, MapColor.emeraldColor)).setHardness(OBFVAL_8.get()).setResistance(OBFVAL_19.get()).setStepSound(soundTypeMetal).setUnlocalizedName("blockEmerald").setCreativeTab(CreativeTabs.tabBlock));
        registerBlock(OBFVAL_167.get(), "spruce_stairs", (new BlockStairs(block1.getDefaultState().withProperty(BlockPlanks.VARIANT, BlockPlanks.EnumType.SPRUCE))).setUnlocalizedName("stairsWoodSpruce"));
        registerBlock(OBFVAL_168.get(), "birch_stairs", (new BlockStairs(block1.getDefaultState().withProperty(BlockPlanks.VARIANT, BlockPlanks.EnumType.BIRCH))).setUnlocalizedName("stairsWoodBirch"));
        registerBlock(OBFVAL_169.get(), "jungle_stairs", (new BlockStairs(block1.getDefaultState().withProperty(BlockPlanks.VARIANT, BlockPlanks.EnumType.JUNGLE))).setUnlocalizedName("stairsWoodJungle"));
        registerBlock(OBFVAL_170.get(), "command_block", (new BlockCommandBlock()).x().setResistance(OBFVAL_26.get()).setUnlocalizedName("commandBlock"));
        registerBlock(OBFVAL_171.get(), "beacon", (new BlockBeacon()).c("beacon").setLightLevel(1.0F));
        registerBlock(OBFVAL_172.get(), "cobblestone_wall", (new BlockWall(block)).setUnlocalizedName("cobbleWall"));
        registerBlock(OBFVAL_173.get(), "flower_pot", (new BlockFlowerPot()).c(0.0F).setStepSound(soundTypeStone).setUnlocalizedName("flowerPot"));
        registerBlock(OBFVAL_174.get(), "carrots", (new BlockCarrot()).c("carrots"));
        registerBlock(OBFVAL_175.get(), "potatoes", (new BlockPotato()).c("potatoes"));
        registerBlock(OBFVAL_176.get(), "wooden_button", (new BlockButtonWood()).c(OBFVAL_13.get()).setStepSound(soundTypeWood).setUnlocalizedName("button"));
        registerBlock(OBFVAL_177.get(), "skull", (new BlockSkull()).c(1.0F).setStepSound(soundTypePiston).setUnlocalizedName("skull"));
        registerBlock(OBFVAL_178.get(), "anvil", (new BlockAnvil()).c(OBFVAL_8.get()).setStepSound(soundTypeAnvil).setResistance(OBFVAL_73.get()).setUnlocalizedName("anvil"));
        registerBlock(OBFVAL_179.get(), "trapped_chest", (new BlockChest(1)).c(OBFVAL_80.get()).setStepSound(soundTypeWood).setUnlocalizedName("chestTrap"));
        registerBlock(OBFVAL_180.get(), "light_weighted_pressure_plate", (new BlockPressurePlateWeighted(Material.iron, OBFVAL_2.get(), MapColor.goldColor)).c(OBFVAL_13.get()).setStepSound(soundTypeWood).setUnlocalizedName("weightedPlate_light"));
        registerBlock(OBFVAL_181.get(), "heavy_weighted_pressure_plate", (new BlockPressurePlateWeighted(Material.iron, OBFVAL_182.get())).c(OBFVAL_13.get()).setStepSound(soundTypeWood).setUnlocalizedName("weightedPlate_heavy"));
        registerBlock(OBFVAL_183.get(), "unpowered_comparator", (new BlockRedstoneComparator(false)).c(0.0F).setStepSound(soundTypeWood).setUnlocalizedName("comparator").disableStats());
        registerBlock(OBFVAL_182.get(), "powered_comparator", (new BlockRedstoneComparator(true)).c(0.0F).setLightLevel(OBFVAL_103.get()).setStepSound(soundTypeWood).setUnlocalizedName("comparator").disableStats());
        registerBlock(OBFVAL_184.get(), "daylight_detector", new BlockDaylightDetector(false));
        registerBlock(OBFVAL_185.get(), "redstone_block", (new BlockCompressedPowered(Material.iron, MapColor.tntColor)).c(OBFVAL_8.get()).setResistance(OBFVAL_19.get()).setStepSound(soundTypeMetal).setUnlocalizedName("blockRedstone").setCreativeTab(CreativeTabs.tabRedstone));
        registerBlock(OBFVAL_186.get(), "quartz_ore", (new BlockOre(MapColor.netherrackColor)).c(OBFVAL_6.get()).setResistance(OBFVAL_8.get()).setStepSound(soundTypePiston).setUnlocalizedName("netherquartz"));
        registerBlock(OBFVAL_187.get(), "hopper", (new BlockHopper()).c(OBFVAL_6.get()).setResistance(OBFVAL_188.get()).setStepSound(soundTypeMetal).setUnlocalizedName("hopper"));
        Block block11 = (new BlockQuartz()).a(soundTypePiston).setHardness(OBFVAL_42.get()).setUnlocalizedName("quartzBlock");
        registerBlock(OBFVAL_189.get(), "quartz_block", block11);
        registerBlock(OBFVAL_190.get(), "quartz_stairs", (new BlockStairs(block11.getDefaultState().withProperty(BlockQuartz.VARIANT, net.minecraft.block.BlockQuartz.EnumType.DEFAULT))).setUnlocalizedName("stairsQuartz"));
        registerBlock(OBFVAL_191.get(), "activator_rail", (new BlockRailPowered()).c(OBFVAL_47.get()).setStepSound(soundTypeMetal).setUnlocalizedName("activatorRail"));
        registerBlock(OBFVAL_192.get(), "dropper", (new BlockDropper()).c(OBFVAL_41.get()).setStepSound(soundTypePiston).setUnlocalizedName("dropper"));
        registerBlock(OBFVAL_193.get(), "stained_hardened_clay", (new BlockColored(Material.rock)).c(OBFVAL_194.get()).setResistance(OBFVAL_195.get()).setStepSound(soundTypePiston).setUnlocalizedName("clayHardenedStained"));
        registerBlock(OBFVAL_196.get(), "stained_glass_pane", (new BlockStainedGlassPane()).c(OBFVAL_37.get()).setStepSound(soundTypeGlass).setUnlocalizedName("thinStainedGlass"));
        registerBlock(OBFVAL_197.get(), "leaves2", (new BlockNewLeaf()).c("leaves"));
        registerBlock(OBFVAL_198.get(), "log2", (new BlockNewLog()).c("log"));
        registerBlock(OBFVAL_199.get(), "acacia_stairs", (new BlockStairs(block1.getDefaultState().withProperty(BlockPlanks.VARIANT, BlockPlanks.EnumType.ACACIA))).setUnlocalizedName("stairsWoodAcacia"));
        registerBlock(OBFVAL_200.get(), "dark_oak_stairs", (new BlockStairs(block1.getDefaultState().withProperty(BlockPlanks.VARIANT, BlockPlanks.EnumType.DARK_OAK))).setUnlocalizedName("stairsWoodDarkOak"));
        registerBlock(OBFVAL_201.get(), "slime", (new BlockSlime()).c("slime").setStepSound(SLIME_SOUND));
        registerBlock(OBFVAL_202.get(), "barrier", (new BlockBarrier()).c("barrier"));
        registerBlock(OBFVAL_203.get(), "iron_trapdoor", (new BlockTrapDoor(Material.iron)).c(OBFVAL_8.get()).setStepSound(soundTypeMetal).setUnlocalizedName("ironTrapdoor").disableStats());
        registerBlock(OBFVAL_204.get(), "prismarine", (new BlockPrismarine()).c(OBFVAL_18.get()).setResistance(OBFVAL_19.get()).setStepSound(soundTypePiston).setUnlocalizedName("prismarine"));
        registerBlock(OBFVAL_205.get(), "sea_lantern", (new BlockSeaLantern(Material.glass)).c(OBFVAL_37.get()).setStepSound(soundTypeGlass).setLightLevel(1.0F).setUnlocalizedName("seaLantern"));
        registerBlock(OBFVAL_206.get(), "hay_block", (new BlockHay()).c(OBFVAL_13.get()).setStepSound(soundTypeGrass).setUnlocalizedName("hayBlock").setCreativeTab(CreativeTabs.tabBlock));
        registerBlock(OBFVAL_207.get(), "carpet", (new BlockCarpet()).c(OBFVAL_108.get()).setStepSound(soundTypeCloth).setUnlocalizedName("woolCarpet").setLightOpacity(0));
        registerBlock(OBFVAL_208.get(), "hardened_clay", (new BlockHardenedClay()).c(OBFVAL_194.get()).setResistance(OBFVAL_195.get()).setStepSound(soundTypePiston).setUnlocalizedName("clayHardened"));
        registerBlock(OBFVAL_209.get(), "coal_block", (new Block(Material.rock, MapColor.blackColor)).setHardness(OBFVAL_8.get()).setResistance(OBFVAL_19.get()).setStepSound(soundTypePiston).setUnlocalizedName("blockCoal").setCreativeTab(CreativeTabs.tabBlock));
        registerBlock(OBFVAL_210.get(), "packed_ice", (new BlockPackedIce()).setHardness(OBFVAL_13.get()).setStepSound(soundTypeGlass).setUnlocalizedName("icePacked"));
        registerBlock(OBFVAL_211.get(), "double_plant", new BlockDoublePlant());
        registerBlock(OBFVAL_212.get(), "standing_banner", (new BlockBannerStanding()).c(1.0F).setStepSound(soundTypeWood).setUnlocalizedName("banner").disableStats());
        registerBlock(OBFVAL_213.get(), "wall_banner", (new BlockBannerHanging()).c(1.0F).setStepSound(soundTypeWood).setUnlocalizedName("banner").disableStats());
        registerBlock(OBFVAL_214.get(), "daylight_detector_inverted", new BlockDaylightDetector(true));
        Block block12 = (new BlockRedSandstone()).a(soundTypePiston).setHardness(OBFVAL_42.get()).setUnlocalizedName("redSandStone");
        registerBlock(OBFVAL_215.get(), "red_sandstone", block12);
        registerBlock(OBFVAL_216.get(), "red_sandstone_stairs", (new BlockStairs(block12.getDefaultState().withProperty(BlockRedSandstone.TYPE, net.minecraft.block.BlockRedSandstone.EnumType.SMOOTH))).setUnlocalizedName("stairsRedSandStone"));
        registerBlock(OBFVAL_217.get(), "double_stone_slab2", (new BlockDoubleStoneSlabNew()).c(OBFVAL_21.get()).setResistance(OBFVAL_19.get()).setStepSound(soundTypePiston).setUnlocalizedName("stoneSlab2"));
        registerBlock(OBFVAL_218.get(), "stone_slab2", (new BlockHalfStoneSlabNew()).c(OBFVAL_21.get()).setResistance(OBFVAL_19.get()).setStepSound(soundTypePiston).setUnlocalizedName("stoneSlab2"));
        registerBlock(OBFVAL_219.get(), "spruce_fence_gate", (new BlockFenceGate(BlockPlanks.EnumType.SPRUCE)).c(OBFVAL_21.get()).setResistance(OBFVAL_8.get()).setStepSound(soundTypeWood).setUnlocalizedName("spruceFenceGate"));
        registerBlock(OBFVAL_220.get(), "birch_fence_gate", (new BlockFenceGate(BlockPlanks.EnumType.BIRCH)).c(OBFVAL_21.get()).setResistance(OBFVAL_8.get()).setStepSound(soundTypeWood).setUnlocalizedName("birchFenceGate"));
        registerBlock(OBFVAL_221.get(), "jungle_fence_gate", (new BlockFenceGate(BlockPlanks.EnumType.JUNGLE)).c(OBFVAL_21.get()).setResistance(OBFVAL_8.get()).setStepSound(soundTypeWood).setUnlocalizedName("jungleFenceGate"));
        registerBlock(OBFVAL_222.get(), "dark_oak_fence_gate", (new BlockFenceGate(BlockPlanks.EnumType.DARK_OAK)).c(OBFVAL_21.get()).setResistance(OBFVAL_8.get()).setStepSound(soundTypeWood).setUnlocalizedName("darkOakFenceGate"));
        registerBlock(OBFVAL_223.get(), "acacia_fence_gate", (new BlockFenceGate(BlockPlanks.EnumType.ACACIA)).c(OBFVAL_21.get()).setResistance(OBFVAL_8.get()).setStepSound(soundTypeWood).setUnlocalizedName("acaciaFenceGate"));
        registerBlock(OBFVAL_224.get(), "spruce_fence", (new BlockFence(Material.wood, BlockPlanks.EnumType.SPRUCE.func_181070_c())).c(OBFVAL_21.get()).setResistance(OBFVAL_8.get()).setStepSound(soundTypeWood).setUnlocalizedName("spruceFence"));
        registerBlock(OBFVAL_225.get(), "birch_fence", (new BlockFence(Material.wood, BlockPlanks.EnumType.BIRCH.func_181070_c())).c(OBFVAL_21.get()).setResistance(OBFVAL_8.get()).setStepSound(soundTypeWood).setUnlocalizedName("birchFence"));
        registerBlock(OBFVAL_226.get(), "jungle_fence", (new BlockFence(Material.wood, BlockPlanks.EnumType.JUNGLE.func_181070_c())).c(OBFVAL_21.get()).setResistance(OBFVAL_8.get()).setStepSound(soundTypeWood).setUnlocalizedName("jungleFence"));
        registerBlock(OBFVAL_227.get(), "dark_oak_fence", (new BlockFence(Material.wood, BlockPlanks.EnumType.DARK_OAK.func_181070_c())).c(OBFVAL_21.get()).setResistance(OBFVAL_8.get()).setStepSound(soundTypeWood).setUnlocalizedName("darkOakFence"));
        registerBlock(OBFVAL_228.get(), "acacia_fence", (new BlockFence(Material.wood, BlockPlanks.EnumType.ACACIA.func_181070_c())).c(OBFVAL_21.get()).setResistance(OBFVAL_8.get()).setStepSound(soundTypeWood).setUnlocalizedName("acaciaFence"));
        registerBlock(OBFVAL_229.get(), "spruce_door", (new BlockDoor(Material.wood)).c(OBFVAL_6.get()).setStepSound(soundTypeWood).setUnlocalizedName("doorSpruce").disableStats());
        registerBlock(OBFVAL_230.get(), "birch_door", (new BlockDoor(Material.wood)).c(OBFVAL_6.get()).setStepSound(soundTypeWood).setUnlocalizedName("doorBirch").disableStats());
        registerBlock(OBFVAL_231.get(), "jungle_door", (new BlockDoor(Material.wood)).c(OBFVAL_6.get()).setStepSound(soundTypeWood).setUnlocalizedName("doorJungle").disableStats());
        registerBlock(OBFVAL_232.get(), "acacia_door", (new BlockDoor(Material.wood)).c(OBFVAL_6.get()).setStepSound(soundTypeWood).setUnlocalizedName("doorAcacia").disableStats());
        registerBlock(OBFVAL_233.get(), "dark_oak_door", (new BlockDoor(Material.wood)).c(OBFVAL_6.get()).setStepSound(soundTypeWood).setUnlocalizedName("doorDarkOak").disableStats());
        Block block13 = (new Block(Material.rock, MapColor.magentaColor)).setHardness(OBFVAL_18.get()).setResistance(OBFVAL_19.get()).setStepSound(soundTypePiston).setCreativeTab(CreativeTabs.tabBlock).setUnlocalizedName("purpurBlock");
        registerBlock(OBFVAL_234.get(), "purpur_block", block13);
        registerBlock(OBFVAL_235.get(), "purpur_pillar", (new BlockCustomPillar(Material.rock, MapColor.magentaColor)).c(OBFVAL_18.get()).setResistance(OBFVAL_19.get()).setStepSound(soundTypePiston).setCreativeTab(CreativeTabs.tabBlock).setUnlocalizedName("purpurPillar"));
        registerBlock(OBFVAL_236.get(), "purpur_stairs", (new BlockStairs(block13.getDefaultState())).setUnlocalizedName("stairsPurpur"));
        registerBlock(OBFVAL_237.get(), "purpur_double_slab", (new Double()).c(OBFVAL_21.get()).setResistance(OBFVAL_19.get()).setStepSound(soundTypeStone).setUnlocalizedName("purpurSlab"));
        registerBlock(OBFVAL_238.get(), "purpur_slab", (new Half()).c(OBFVAL_21.get()).setResistance(OBFVAL_19.get()).setStepSound(soundTypeStone).setUnlocalizedName("purpurSlab"));
        registerBlock(OBFVAL_239.get(), "end_bricks", (new Block(Material.rock)).setStepSound(soundTypeStone).setHardness(OBFVAL_42.get()).setCreativeTab(CreativeTabs.tabBlock).setUnlocalizedName("endBricks"));
        registerBlock(OBFVAL_240.get(), "magma", (new BlockMagma()).c(OBFVAL_13.get()).setStepSound(soundTypeStone).setUnlocalizedName("magma"));
        registerBlock(OBFVAL_241.get(), "nether_wart_block", (new Block(Material.grass, MapColor.redColor)).setCreativeTab(CreativeTabs.tabBlock).setHardness(1.0F).setStepSound(soundTypeWood).setUnlocalizedName("netherWartBlock"));
        registerBlock(OBFVAL_242.get(), "red_nether_brick", (new BlockNetherBrick()).c(OBFVAL_21.get()).setResistance(OBFVAL_19.get()).setStepSound(soundTypeStone).setUnlocalizedName("redNetherBrick"));
        registerBlock(OBFVAL_243.get(), "bone_block", (new BlockBone()).a(soundTypeStone).setUnlocalizedName("boneBlock"));
        registerBlock(OBFVAL_244.get(), "concrete", (new BlockColored(Material.rock)).c(OBFVAL_245.get()).setStepSound(soundTypePiston).setUnlocalizedName("concrete"));
        registerBlock(OBFVAL_246.get(), "concrete_powder", (new BlockConcretePowder()).c(OBFVAL_13.get()).setStepSound(soundTypeSand).setUnlocalizedName("concretePowder"));
        registerBlock(OBFVAL_247.get(), "crimson_nylium", (new Block(Material.rock, MapColor.netherrackColor)).setHardness(OBFVAL_93.get()).setStepSound(soundTypePiston).setCreativeTab(CreativeTabs.tabBlock).setUnlocalizedName("crimsonNylium"));
        registerBlock(OBFVAL_248.get(), "warped_nylium", (new Block(Material.rock, MapColor.cyanColor)).setHardness(OBFVAL_93.get()).setStepSound(soundTypePiston).setCreativeTab(CreativeTabs.tabBlock).setUnlocalizedName("warpedNylium"));
        registerBlock(OBFVAL_249.get(), "crimson_stairs", (new BlockStairs(block1.getDefaultState().withProperty(BlockPlanks.VARIANT, BlockPlanks.EnumType.CRIMSON))).setUnlocalizedName("stairsWoodCrimson"));
        registerBlock(OBFVAL_250.get(), "warped_stairs", (new BlockStairs(block1.getDefaultState().withProperty(BlockPlanks.VARIANT, BlockPlanks.EnumType.WARPED))).setUnlocalizedName("stairsWoodWarped"));
        registerBlock(OBFVAL_251.get(), "log_stripped1", (new BlockLogStripped1()).c("logStripped"));
        registerBlock(OBFVAL_252.get(), "log_stripped2", (new BlockLogStripped2()).c("logStripped"));
        registerBlock(OBFVAL_253.get(), "lucky_block", (new Block(Material.sponge)).setHardness(OBFVAL_3.get()).setStepSound(soundTypeGrass).setUnlocalizedName("luckyBlock").setCreativeTab(CreativeTabs.tabBlock));
        blockRegistry.validateKey();

        for (Block block14 : blockRegistry)
        {
            if (block14.blockMaterial == Material.air)
            {
                block14.useNeighborBrightness = false;
            }
            else
            {
                boolean flag = false;
                boolean flag1 = block14 instanceof BlockStairs;
                boolean flag2 = block14 instanceof BlockSlab;
                boolean flag3 = block14 == block6;
                boolean flag4 = block14.translucent;
                boolean flag5 = block14.lightOpacity == 0;

                if (flag1 || flag2 || flag3 || flag4 || flag5)
                {
                    flag = true;
                }

                block14.useNeighborBrightness = flag;
            }
        }

        for (Block block15 : blockRegistry)
        {
            UnmodifiableIterator unmodifiableiterator = block15.getBlockState().getValidStates().iterator();

            while (unmodifiableiterator.hasNext())
            {
                IBlockState iblockstate = (IBlockState)unmodifiableiterator.next();
                int i = blockRegistry.b(block15) << OBFVAL_22.get() | block15.getMetaFromState(iblockstate);
                BLOCK_STATE_IDS.put(iblockstate, i);
            }
        }
    }

    private static void registerBlock(int id, ResourceLocation textualID, Block block_)
    {
        blockRegistry.register(id, textualID, block_);
    }

    private static void registerBlock(int id, String textualID, Block block_)
    {
        registerBlock(id, new ResourceLocation(textualID), block_);
    }

    static
    {
        ObfValue.beginGeneration();
        ObfValue.endGeneration(new byte[] {(byte) - 67, (byte)71, (byte)59, (byte)45, (byte) - 85, (byte)11, (byte) - 49, (byte) - 54});
    }

    public static enum EnumOffsetType
    {
        NONE,
        XZ,
        XYZ;
    }

    public static class SoundType
    {
        public final String soundName;
        public final float volume;
        public final float frequency;

        public SoundType(String name, float volume, float frequency)
        {
            this.soundName = name;
            this.volume = volume;
            this.frequency = frequency;
        }

        public float getVolume()
        {
            return this.volume;
        }

        public float getFrequency()
        {
            return this.frequency;
        }

        public String getBreakSound()
        {
            return "dig." + this.soundName;
        }

        public String getStepSound()
        {
            return "step." + this.soundName;
        }

        public String getPlaceSound()
        {
            return this.getBreakSound();
        }
    }
}
