package com.lnatit.quoth.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraftforge.fml.ModList;

public class BChatCommand
{
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher)
    {
        if (ModList.get().isLoaded("bchat"))
        {
            LiteralArgumentBuilder<CommandSourceStack> builder = Commands.literal("bchat");

//            builder.requires(executor -> executor.hasPermission(2))
//                   .then(Commands.argument("targets", EntityArgument.players())
//                                 .then(Commands.argument("content", StringArgumentType.greedyString())
//                                               .executes()
//                                 )
//                   );

            dispatcher.register(builder);
        }
    }
}
