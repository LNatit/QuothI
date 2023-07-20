package com.lnatit.quoth.handlers;

import com.lnatit.quoth.capability.ChatLogger;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class ChatReceivedHandler
{
    @SubscribeEvent
    public static void onServerChat(ServerChatEvent event)
    {
        ServerPlayer player = event.getPlayer();
        LevelChunk chunk = player.level().getChunkAt(BlockPos.containing(player.position()));

        chunk.getCapability(ChatLogger.CAPABILITY).ifPresent(cl -> cl.addEntry(event.getUsername(), event.getRawText(), player.server.getNextTickTime()));
    }
}
