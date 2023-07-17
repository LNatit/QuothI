package com.lnatit.quoth.handlers;

import com.lnatit.quoth.commands.BChatCommand;
import com.lnatit.quoth.commands.ChatCommand;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandSourceStack;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.lnatit.quoth.Quoth.MODID;

@Mod.EventBusSubscriber(modid = MODID)
public class CommandRegistryHandler
{
    @SubscribeEvent
    public static void onCommandRegister(RegisterCommandsEvent event)
    {
        CommandDispatcher<CommandSourceStack> dispatcher = event.getDispatcher();

        ChatCommand.register(dispatcher);
        BChatCommand.register(dispatcher);
    }
}
