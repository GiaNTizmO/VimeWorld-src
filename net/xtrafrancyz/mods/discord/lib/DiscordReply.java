package net.xtrafrancyz.mods.discord.lib;

public enum DiscordReply
{
    NO(0),
    YES(1),
    IGNORE(2);

    public final int reply;

    private DiscordReply(int reply)
    {
        this.reply = reply;
    }
}
