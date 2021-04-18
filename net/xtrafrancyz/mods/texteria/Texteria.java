package net.xtrafrancyz.mods.texteria;

import io.netty.buffer.Unpooled;
import java.awt.Desktop;
import java.io.File;
import java.net.URI;
import java.util.List;
import java.util.ListIterator;
import java.util.function.Consumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.IReloadableResourceManager;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.IResourceManagerReloadListener;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.client.C17PacketCustomPayload;
import net.minecraft.network.play.server.S3FPacketCustomPayload;
import net.xtrafrancyz.VimeStorage;
import net.xtrafrancyz.mods.texteria.elements.Element2D;
import net.xtrafrancyz.mods.texteria.elements.Image;
import net.xtrafrancyz.mods.texteria.gui.GuiElementWrapper;
import net.xtrafrancyz.mods.texteria.gui.GuiRenderLayer;
import net.xtrafrancyz.mods.texteria.gui.TexteriaGui;
import net.xtrafrancyz.mods.texteria.gui.Visibility;
import net.xtrafrancyz.mods.texteria.resources.TexteriaResourceManager;
import net.xtrafrancyz.mods.texteria.scripting.KeyboardScripting;
import net.xtrafrancyz.mods.texteria.scripting.ScriptManager;
import net.xtrafrancyz.mods.texteria.sound.TSound;
import net.xtrafrancyz.mods.texteria.sound.TexteriaSound;
import net.xtrafrancyz.mods.texteria.world.Beam;
import net.xtrafrancyz.mods.texteria.world.Element3D;
import net.xtrafrancyz.mods.texteria.world.Line;
import net.xtrafrancyz.mods.texteria.world.TexteriaWorld;
import net.xtrafrancyz.mods.texteria.world.WorldGroup;
import net.xtrafrancyz.mods.texteria.world.aabb.BoundingBox;
import net.xtrafrancyz.mods.texteria.world.particle.Particle;
import net.xtrafrancyz.mods.texteria.world.particle.ParticleConfig;
import net.xtrafrancyz.util.ByteMap;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

public class Texteria implements IResourceManagerReloadListener
{
    public static final boolean debug = Boolean.getBoolean("Texteria.debug");
    private static final boolean printPackets = Boolean.getBoolean("Texteria.printPackets");
    public static final Texteria instance = new Texteria();
    public static final Logger log = LogManager.getLogger("Texteria");
    public static final String STORAGE_RELATIVE_PATH = "texteria/cache";
    public static long time = System.currentTimeMillis();
    public TexteriaGui gui;
    public TexteriaWorld world;
    public ScriptManager scriptManager;
    public VimeStorage storage;
    public TexteriaResourceManager resourceManager;
    private KeyboardScripting keyboardScripting;
    private TexteriaSound texteriaSound;
    private Minecraft mc;

    public void init(Minecraft mc)
    {
        this.mc = mc;
        File file1 = new File(Minecraft.getMinecraft().mcDataDir, "texteria");
        this.storage = new VimeStorage(new File(file1, "cacheIndex.bin"), new File(file1, "cache"));
        this.resourceManager = new TexteriaResourceManager();
        this.scriptManager = new ScriptManager(this);
        this.gui = new TexteriaGui(mc);
        this.world = new TexteriaWorld(mc);
        this.keyboardScripting = new KeyboardScripting();
        this.texteriaSound = new TexteriaSound(mc);

        if (mc.getResourceManager() instanceof IReloadableResourceManager)
        {
            ((IReloadableResourceManager)mc.getResourceManager()).registerReloadListener(this);
        }
    }

    public void onResourceManagerReload(IResourceManager resourceManager)
    {
        this.gui.elements.values().forEach((list) ->
        {
            list.forEach((w) -> {
                w.element.reload();
            });
        });
        this.gui.persistentElements.forEach((w) ->
        {
            w.element.reload();
        });

        for (Element3D element3d : this.world.elements.getAll())
        {
            element3d.reload();
        }

        this.texteriaSound.onResourceManagerReload();
    }

    public void onWorldChanged()
    {
        if (this.mc != null)
        {
            this.texteriaSound.clear();
        }
    }

    public void handleDisconnect()
    {
        TexteriaOptions.reset();
        this.onWorldChanged();

        if (this.mc != null)
        {
            this.gui.clearNow();
            this.world.elements.clearNow();
            this.world.particles.clearNow();
            this.world.boxes.clearNow();
            this.keyboardScripting.clear();
        }
    }

    public void stop()
    {
        this.storage.shutdown();
    }

    public boolean handleMouseEvent()
    {
        if (this.mc.theWorld == null)
        {
            return false;
        }
        else
        {
            int dwheel = Mouse.getEventDWheel();
            boolean buttonstate = Mouse.getEventButtonState();

            if (dwheel == 0 && !buttonstate)
            {
                return false;
            }
            else
            {
                dwheel = Integer.compare(dwheel, 0);
                boolean cancelled = false;
                int button = 0;

                if (buttonstate)
                {
                    button = Mouse.getEventButton();
                }

                if (this.mc.currentScreen == null)
                {
                    Element3D[] aelement3d = this.world.elements.getCachedSortedVisible();

                    for (int int = aelement3d.length - 1; int >= 0; --int)
                    {
                        Element3D element3d = aelement3d[int];

                        if (element3d.hover)
                        {
                            if (boolean)
                            {
                                boolean = element3d.mouseClick(int);
                            }

                            if (int != 0)
                            {
                                boolean = boolean || element3d.mouseWheel(int);
                            }

                            if (boolean)
                            {
                                break;
                            }
                        }
                    }
                }
                else
                {
                    for (GuiRenderLayer guirenderlayer : GuiRenderLayer.REVERSED_RENDER_ORDER)
                    {
                        List<GuiElementWrapper> list = (List)this.gui.elements.get(guirenderlayer);
                        ListIterator<GuiElementWrapper> listiterator = list.listIterator(list.size());

                        while (listiterator.hasPrevious() && !boolean)
                        {
                            GuiElementWrapper guielementwrapper = (GuiElementWrapper)listiterator.previous();

                            if (guielementwrapper.visible && guielementwrapper.element.hover)
                            {
                                if (boolean)
                                {
                                    boolean = guielementwrapper.element.mouseClick(0, 0, int);
                                }

                                if (int != 0)
                                {
                                    boolean = boolean || guielementwrapper.element.mouseWheel(int);
                                }
                            }
                        }
                    }
                }

                return boolean;
            }
        }
    }

    public boolean handleKeyboardEvent()
    {
        if (debug)
        {
            int int = Keyboard.getEventKey() == 0 ? Keyboard.getEventCharacter() + 256 : Keyboard.getEventKey();

            if (int == 36 && Keyboard.isKeyDown(157))
            {
                Image.debugSaveAtlases();
            }
        }

        return this.keyboardScripting.handleKeyboardEvent();
    }

    public void handleTexteriaPacket(S3FPacketCustomPayload packet)
    {
        time = System.currentTimeMillis();

        try
        {
            PacketBuffer packetbuffer = packet.getBufferData();
            int int = packetbuffer.readableBytes();
            int int = packetbuffer.readVarIntFromBuffer();

            if (printPackets)
            {
                printDebug("Received packet length {} with {} actions", new Object[] {Integer.valueOf(int), Integer.valueOf(int)});
            }

            for (int int = 0; int < int; ++int)
            {
                byte[] abyte = new byte[packetbuffer.readVarIntFromBuffer()];
                packetbuffer.readBytes(abyte);
                ByteMap bytemap = null;

                try
                {
                    this.handleAction(new ByteMap(abyte));
                }
                catch (Exception exception)
                {
                    log.warn((String)("Could not handle Texteria action (" + bytemap + ")"), (Throwable)exception);
                }
            }
        }
        catch (Exception exception1)
        {
            log.warn((String)"Can\'t handle Texteria packet", (Throwable)exception1);
        }
    }

    private void handleAction(ByteMap map) throws Exception
    {
        if (printPackets)
        {
            printDebug("Handle action {}", new Object[] {map.toString()});
        }

        String string = map.getString("%");
        byte byte = -1;

        switch (string.hashCode())
        {
            case -1823286276:
                if (string.equals("keyboard:reset"))
                {
                    byte = 23;
                }

                break;

            case -1396981145:
                if (string.equals("bb:add"))
                {
                    byte = 17;
                }

                break;

            case -1378331476:
                if (string.equals("3d:edit:in"))
                {
                    byte = 15;
                }

                break;

            case -1270519733:
                if (string.equals("add:bulk"))
                {
                    byte = 2;
                }

                break;

            case -990465795:
                if (string.equals("3d:add:to"))
                {
                    byte = 14;
                }

                break;

            case -928755742:
                if (string.equals("rm:all"))
                {
                    byte = 6;
                }

                break;

            case -856935711:
                if (string.equals("animate"))
                {
                    byte = 8;
                }

                break;

            case -726874522:
                if (string.equals("add:group"))
                {
                    byte = 3;
                }

                break;

            case -687468681:
                if (string.equals("keyboard:remove"))
                {
                    byte = 22;
                }

                break;

            case -496672533:
                if (string.equals("3d:rm:all"))
                {
                    byte = 13;
                }

                break;

            case -376740161:
                if (string.equals("sound:play"))
                {
                    byte = 24;
                }

                break;

            case -376642675:
                if (string.equals("sound:stop"))
                {
                    byte = 25;
                }

                break;

            case -356623100:
                if (string.equals("bb:edit"))
                {
                    byte = 19;
                }

                break;

            case -341064690:
                if (string.equals("resource"))
                {
                    byte = 27;
                }

                break;

            case 52223:
                if (string.equals("3dp"))
                {
                    byte = 9;
                }

                break;

            case 96417:
                if (string.equals("add"))
                {
                    byte = 1;
                }

                break;

            case 116079:
                if (string.equals("url"))
                {
                    byte = 20;
                }

                break;

            case 3108362:
                if (string.equals("edit"))
                {
                    byte = 7;
                }

                break;

            case 50138052:
                if (string.equals("3d:rm"))
                {
                    byte = 12;
                }

                break;

            case 93483957:
                if (string.equals("bb:rm"))
                {
                    byte = 18;
                }

                break;

            case 108404047:
                if (string.equals("reset"))
                {
                    byte = 0;
                }

                break;

            case 108587706:
                if (string.equals("rm:id"))
                {
                    byte = 4;
                }

                break;

            case 208141966:
                if (string.equals("keyboard:add"))
                {
                    byte = 21;
                }

                break;

            case 824656000:
                if (string.equals("rm:group"))
                {
                    byte = 5;
                }

                break;

            case 937635155:
                if (string.equals("3d:edit"))
                {
                    byte = 11;
                }

                break;

            case 1372807133:
                if (string.equals("option:set"))
                {
                    byte = 26;
                }

                break;

            case 1554263096:
                if (string.equals("3d:add"))
                {
                    byte = 10;
                }

                break;

            case 1783175584:
                if (string.equals("3d:rm:from"))
                {
                    byte = 16;
                }
        }

        switch (byte)
        {
            case 0:
                this.handleDisconnect();
                break;

            case 1:
                this.gui.addElement(new GuiElementWrapper(map, this.gui));
                break;

            case 2:
                for (ByteMap bytemap1 : map.getMapArray("e"))
                {
                    this.gui.addElement(new GuiElementWrapper(bytemap1, this.gui));
                }

                return;

            case 3:
                Visibility visibility = Visibility.DEFAULT;

                if (map.containsKey("vis"))
                {
                    visibility = new Visibility(map.getMapArray("vis"));
                }

                ByteMap[] abytemap = map.getMapArray("e");

                for (ByteMap bytemap : abytemap)
                {
                    GuiElementWrapper guielementwrapper = new GuiElementWrapper(bytemap, this.gui);
                    guielementwrapper.visibility = visibility;
                    this.gui.addElement(guielementwrapper);
                }

                return;

            case 4:
                this.gui.removeElement(map.getString("id"));
                break;

            case 5:
                String string3 = map.getString("group");

                if (string3.endsWith("*"))
                {
                    string3 = string3.substring(0, string3.length() - 1);
                }

                this.gui.removeGroup(string3);
                break;

            case 6:
                this.gui.clear();
                break;

            case 7:
                Element2D element2d1 = this.gui.getElement(map.getString("id"));

                if (element2d1 != null)
                {
                    element2d1.edit(map.getMap("data"));
                }

                break;

            case 8:
                Element2D element2d = this.gui.getElement(map.getString("id"));

                if (element2d != null)
                {
                    element2d.playAnimation(map.getMap("data"));
                }

                break;

            case 9:
                int int = map.getInt("am", 1);
                ParticleConfig particleconfig = new ParticleConfig(map);

                for (int int = 0; int < int; ++int)
                {
                    this.world.particles.add(new Particle(particleconfig));
                }

                return;

            case 10:
                String string2 = map.getString("type", "group");
                byte element = -1;

                switch (string2.hashCode())
                {
                    case 3019695:
                        if (string2.equals("beam"))
                        {
                            element = 1;
                        }

                        break;

                    case 3321844:
                        if (string2.equals("line"))
                        {
                            element = 2;
                        }

                        break;

                    case 98629247:
                        if (string2.equals("group"))
                        {
                            element = 0;
                        }
                }

                Element3D element3d1;

                switch (element)
                {
                    case 0:
                        element3d1 = new WorldGroup(map);
                        break;

                    case 1:
                        element3d1 = new Beam(map);
                        break;

                    case 2:
                        element3d1 = new Line(map);
                        break;

                    default:
                        log.warn("Unknown 3d:add type: " + string2);
                        return;
                }

                this.world.elements.add(element3d1);
                break;

            case 11:
                Element3D element3d4 = this.world.elements.get(map.getString("id", map.getString("group")));

                if (element3d4 != null)
                {
                    element3d4.edit(map.getMap("data"));
                }

                break;

            case 12:
                String string1 = map.getString("id", map.getString("group"));

                if (string1.endsWith("*"))
                {
                    string1 = string1.substring(0, string1.length() - 1);
                    this.world.elements.removeStartsWith(string1);
                }
                else
                {
                    this.world.elements.remove(string1);
                }

                break;

            case 13:
                this.world.elements.clear();
                break;

            case 14:
                Element3D element3d3 = this.world.elements.get(map.getString("group"));

                if (element3d3 instanceof WorldGroup)
                {
                    ((WorldGroup)element3d3).addElement(Element2D.construct(map.getMap("e"), (WorldGroup)element3d3));
                }

                break;

            case 15:
                Element3D element3d2 = this.world.elements.get(map.getString("group"));

                if (element3d2 instanceof WorldGroup)
                {
                    ((WorldGroup)element3d2).editElement(map.getString("id"), map.getMap("data"));
                }

                break;

            case 16:
                Element3D element3d = this.world.elements.get(map.getString("group"));

                if (element3d instanceof WorldGroup)
                {
                    ((WorldGroup)element3d).removeElement(map.getString("id"));
                }

                break;

            case 17:
                this.world.boxes.add(map.getString("id"), new BoundingBox(map));
                break;

            case 18:
                this.world.boxes.remove(map.getString("id"));
                break;

            case 19:
                this.world.boxes.edit(map.getString("id"), map.getMap("d"));
                break;

            case 20:
                Desktop.getDesktop().browse(new URI(map.getString("url")));
                break;

            case 21:
                this.keyboardScripting.addBinding(map.getInt("key"), map.getString("script"));
                break;

            case 22:
                this.keyboardScripting.removeBinding(map.getInt("key"));
                break;

            case 23:
                this.keyboardScripting.clear();
                break;

            case 24:
                this.texteriaSound.play(new TSound(map));
                break;

            case 25:
                this.texteriaSound.stop(map.getString("id"));
                break;

            case 26:
                TexteriaOptions.set(map);
                break;

            case 27:
                this.resourceManager.handlePacket(map);
        }
    }

    public static void printDebug(String str)
    {
        if (debug)
        {
            log.info("[Texteria] " + str);
        }
    }

    public static void printDebug(String str, Object... args)
    {
        if (debug)
        {
            log.info("[Texteria] " + str, args);
        }
    }

    public static void sendCallbackPacket(ByteMap data)
    {
        ByteMap bytemap = new ByteMap();
        bytemap.put("%", "callback");
        bytemap.put("data", data);
        sendPacket(bytemap);
    }

    public static void sendPacket(ByteMap map)
    {
        Minecraft.getMinecraft().getNetHandler().addToSendQueue(new C17PacketCustomPayload("Texteria", new PacketBuffer(Unpooled.wrappedBuffer(map.toByteArray()))));
    }
}
