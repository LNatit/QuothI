package com.lnatit.quoth.handlers;

import com.lnatit.quoth.commands.ChatCommand;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.lnatit.quoth.Quoth.MODID;

@Mod.EventBusSubscriber(modid = MODID)
public class CommandRegistryHandler
{
    @SubscribeEvent
    public static void onCommandsRegister(RegisterCommandsEvent event)
    {
        ChatCommand.register(event.getDispatcher());
    }
}
