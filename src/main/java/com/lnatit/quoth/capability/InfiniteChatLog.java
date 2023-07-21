package com.lnatit.quoth.capability;

import com.google.common.collect.EvictingQueue;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static com.lnatit.quoth.Quoth.MODLOG;

public class InfiniteChatLog implements IChatLog
{
    public static final Capability<InfiniteChatLog> CAPABILITY = CapabilityManager.get(new CapabilityToken<>()
    {
    });

    protected final List<Entry> list = new ArrayList<>();
    protected int capacity = 64;

    @Override
    public void addEntry(@Nullable String sender, String contents, long timeStamp)
    {

    }

    @Override
    public void addEntry(@Nullable String sender, String contents)
    {

    }

    @Override
    public void clear()
    {
        list.clear();
    }

    @Override
    public @NotNull CompoundTag serializeNBT()
    {
        ListTag list = new ListTag();
        this.list.forEach(e -> list.add(e.serializeNBT()));
        CompoundTag tag = new CompoundTag();
        tag.put("iclog", list);
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt)
    {
        if (list.size() != 0)
        {
            MODLOG.warn("Trying to overwrite InfiniteChatLog data!");
            return;
        }

        if (nbt.contains("iclog"))
        {
            ListTag list = nbt.getList("iclog", Tag.TAG_COMPOUND);
            for (int i = 0; i < list.size(); i++)
            {
                Entry entry = Entry.createEntry(list.getCompound(i));
                if (entry != null)
                    this.list.add(entry);
            }
        }
    }
}
