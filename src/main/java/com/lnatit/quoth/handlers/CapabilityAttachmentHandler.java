package com.lnatit.quoth.handlers;

import com.lnatit.quoth.capability.ChatLogger;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.lnatit.quoth.Quoth.MODID;

@Mod.EventBusSubscriber
public class CapabilityAttachmentHandler
{
    @SubscribeEvent
    public static void onCapAttached(AttachCapabilitiesEvent<LevelChunk> event)
    {
        event.addCapability(new ResourceLocation(MODID, "clog"), );
    }
}
