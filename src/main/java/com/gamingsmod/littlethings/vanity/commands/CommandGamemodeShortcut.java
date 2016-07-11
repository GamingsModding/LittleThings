package com.gamingsmod.littlethings.vanity.commands;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.CommandGameMode;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;

public class CommandGamemodeShortcut extends CommandBase
{
    private String shortcut;
    private String fullName;

    public CommandGamemodeShortcut()
    {}

    public CommandGamemodeShortcut(String shortcut, String fullName)
    {
        this.shortcut = shortcut;
        this.fullName = fullName;
    }

    @Override
    public int getRequiredPermissionLevel()
    {
        return 2;
    }

    @Override
    public String getCommandName()
    {
        return shortcut;
    }

    @Override
    public String getCommandUsage(ICommandSender sender)
    {
        return "/" + shortcut + " [player]";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException
    {
        CommandGameMode gm = new CommandGameMode();
        String[] newArgs = new String[args.length + 1];
        newArgs[0] = fullName;

        if (args.length == 1) newArgs[1] = args[0];

        gm.execute(server, sender, newArgs);
    }
}
