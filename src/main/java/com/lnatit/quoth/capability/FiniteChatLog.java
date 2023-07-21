package com.lnatit.quoth.capability;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraftforge.common.capabilities.AutoRegisterCapability;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.LinkedList;

import static com.lnatit.quoth.Quoth.MODLOG;

@AutoRegisterCapability
public class FiniteChatLog implements IChatLog
{
    public static final Capability<FiniteChatLog> CAPABILITY = CapabilityManager.get(new CapabilityToken<>()
    {
    });

    // TODO maybe LinkedList would be better?
    protected final LinkedList<Entry> list = new LinkedList<>();
    protected int capacity = 64;

    @Override
    public void addEntry(@Nullable String sender, String contents, long timeStamp)
    {
        if (list.size() >= capacity)
            list.poll();
        list.offer(Entry.createEntry(sender, contents, timeStamp));
    }

    @Override
    public void addEntry(@Nullable String sender, String contents)
    {
        addEntry(contents, sender, 0L);
    }

    @Override
    public void clear()
    {
        list.clear();
    }

    public void setCapacity(int maxChatNum)
    {
        this.capacity = maxChatNum;
    }

    @Override
    public @NotNull CompoundTag serializeNBT()
    {
        ListTag list = new ListTag();
        this.list.forEach(e -> list.add(e.serializeNBT()));
        CompoundTag tag = new CompoundTag();
        tag.put("fclog", list);
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt)
    {
        if (list.size() != 0)
        {
            MODLOG.warn("Trying to overwrite FiniteChatLog data!");
            return;
        }

        if (nbt.contains("fclog"))
        {
            ListTag list = nbt.getList("fclog", Tag.TAG_COMPOUND);
            for (int i = 0; i < list.size(); i++)
            {
                Entry entry = Entry.createEntry(list.getCompound(i));
                if (entry != null)
                    this.list.add(entry);
            }
        }
    }

}
