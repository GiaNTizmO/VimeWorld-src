package net.xtrafrancyz.mods.pvp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import net.minecraft.client.Minecraft;
import net.xtrafrancyz.mods.texteria.util.Position;

public abstract class PvpOptions
{
    public static boolean effectEnabled = true;
    public static int effectTextSize = 2;
    public static int effectScale = 2;
    public static Position effectPosition = Position.TOP_RIGHT;
    private static File optionsFile;

    public static void load(Minecraft mc)
    {
        if (optionsFile == null)
        {
            optionsFile = new File(mc.mcDataDir, "options-pvp.txt");
        }

        if (optionsFile.exists())
        {
            try
            {
                BufferedReader bufferedreader = new BufferedReader(new FileReader(optionsFile));
                Throwable throwable = null;

                try
                {
                    String s;

                    try
                    {
                        while ((s = bufferedreader.readLine()) != null)
                        {
                            try
                            {
                                String[] astring = s.split(":");
                                String s1 = astring[0];
                                byte b0 = -1;

                                switch (s1.hashCode())
                                {
                                    case -879913510:
                                        if (s1.equals("effectPosition"))
                                        {
                                            b0 = 3;
                                        }

                                        break;

                                    case 1247392880:
                                        if (s1.equals("effectEnabled"))
                                        {
                                            b0 = 0;
                                        }

                                        break;

                                    case 1649652281:
                                        if (s1.equals("effectScale"))
                                        {
                                            b0 = 2;
                                        }

                                        break;

                                    case 1663580031:
                                        if (s1.equals("effectTextSize"))
                                        {
                                            b0 = 1;
                                        }
                                }

                                switch (b0)
                                {
                                    case 0:
                                        effectEnabled = astring[1].equals("true");
                                        break;

                                    case 1:
                                        effectTextSize = Integer.parseInt(astring[1]);
                                        break;

                                    case 2:
                                        effectScale = Integer.parseInt(astring[1]);
                                        break;

                                    case 3:
                                        effectPosition = Position.valueOf(astring[1]);
                                }
                            }
                            catch (Exception exception)
                            {
                                PvPMod.log.info((String)("Failed to parse option \'" + s + "\'"), (Throwable)exception);
                            }
                        }
                    }
                    catch (Throwable throwable2)
                    {
                        throwable = throwable2;
                        throw throwable2;
                    }
                }
                finally
                {
                    if (bufferedreader != null)
                    {
                        if (throwable != null)
                        {
                            try
                            {
                                bufferedreader.close();
                            }
                            catch (Throwable throwable1)
                            {
                                throwable.addSuppressed(throwable1);
                            }
                        }
                        else
                        {
                            bufferedreader.close();
                        }
                    }
                }
            }
            catch (IOException ioexception)
            {
                PvPMod.log.error((String)"Failed to load pvp options", (Throwable)ioexception);
            }
        }
    }

    public static void save()
    {
        try
        {
            PrintWriter printwriter = new PrintWriter(new FileWriter(optionsFile));
            Throwable throwable = null;

            try
            {
                printwriter.println("effectEnabled:" + effectEnabled);
                printwriter.println("effectTextSize:" + effectTextSize);
                printwriter.println("effectScale:" + effectScale);
                printwriter.println("effectPosition:" + effectPosition.name());
            }
            catch (Throwable throwable2)
            {
                throwable = throwable2;
                throw throwable2;
            }
            finally
            {
                if (printwriter != null)
                {
                    if (throwable != null)
                    {
                        try
                        {
                            printwriter.close();
                        }
                        catch (Throwable throwable1)
                        {
                            throwable.addSuppressed(throwable1);
                        }
                    }
                    else
                    {
                        printwriter.close();
                    }
                }
            }
        }
        catch (IOException ioexception)
        {
            PvPMod.log.error((String)"Failed to save pvp options", (Throwable)ioexception);
        }
    }
}
