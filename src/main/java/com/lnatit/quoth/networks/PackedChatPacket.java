package com.lnatit.quoth.networks;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class PackedChatPacket
{
    public PackedChatPacket()
    {
    }

    public void encode(FriendlyByteBuf buf)
    {

    }

    public static void handle(PackedChatPacket packet, Supplier<NetworkEvent.Context> contextSupplier)
    {
        NetworkEvent.Context context = contextSupplier.get();

        context.enqueueWork(() -> {

            context.setPacketHandled(true);
        });
    }
}
