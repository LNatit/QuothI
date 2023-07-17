package com.lnatit.quoth.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.OutgoingChatMessage;
import net.minecraft.network.chat.PlayerChatMessage;
import net.minecraft.server.level.ServerPlayer;

public class ChatCommand
{
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher)
    {
        LiteralArgumentBuilder<CommandSourceStack> builder = Commands.literal("chatas");

        builder.requires(executor -> executor.hasPermission(2))
               .then(Commands.argument("targets", EntityArgument.players())
                             .then(Commands.argument("content", StringArgumentType.greedyString())
                                           .executes(ChatCommand::sendChat)
                             )
               );

        dispatcher.register(builder);
    }

    // TODO do abstractions
    private static int sendChat(CommandContext<CommandSourceStack> context) throws CommandSyntaxException
    {
        for (ServerPlayer player : EntityArgument.getPlayers(context, "targets"))
        {
            for (ServerPlayer to : player.server.getPlayerList().getPlayers())
            {
                to.sendChatMessage(OutgoingChatMessage.create(
                                           PlayerChatMessage.system(StringArgumentType.getString(context, "content"))),
                                   player.shouldFilterMessageTo(to),
                                   ChatType.bind(ChatType.CHAT, player)
                );
            }
        }

        return 0;
    }
}
