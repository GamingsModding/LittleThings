package com.gamingsmod.littlethings.command.command;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;

public class CommandTimeShortcut extends CommandBase
{
    public static final String DAY = "day";
    public static final String NIGHT = "night";
    private String shor;

    public CommandTimeShortcut(String shor)
    {
        this.shor = shor;
    }

    @Override
    public int getRequiredPermissionLevel()
    {
        return 2;
    }

    @Override
    public String getCommandName()
    {
        return shor;
    }

    @Override
    public String getCommandUsage(ICommandSender sender)
    {
        return "/" + shor + " [day number]";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException
    {
        int day;
        if (args.length >= 1) {
            try {
                day = Integer.valueOf(args[0]);
                if (shor == DAY) {
                    server.getEntityWorld().setWorldTime(day * 24000L);
                } else if (shor == NIGHT) {
                    server.getEntityWorld().setWorldTime((day * 24000L) + 13000);
                }
            } catch (NumberFormatException e) {
                throw new CommandException("First argument must be a number");
            }
        } else {
            long i = server.getEntityWorld().getWorldTime() + 24000L;
            long time = i - i % 24000L;

            day = (int) Math.floor(time / 24000);

            if (shor == DAY) {
                server.getEntityWorld().setWorldTime(time);
            } else if (shor == NIGHT) {
                server.getEntityWorld().setWorldTime(time + 13000);
            }
        }

        notifyCommandListener(sender, this, "command.littlethings.time", shor, day);
    }
}
