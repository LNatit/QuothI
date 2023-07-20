package com.lnatit.quoth.capability;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraftforge.common.capabilities.*;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

import static com.lnatit.quoth.Quoth.MODLOG;

@AutoRegisterCapability
public class ChatLogger implements IChatLog
{
    public static final Capability<ChatLogger> CAPABILITY = CapabilityManager.get(new CapabilityToken<>()
    {
    });

    // TODO maybe LinkedList would be better?
    private List<Entry> logger = new ArrayList<>();
    private int maxChatNum;

    @Override
    public void addEntry(@Nullable String sender, String contents, long timeStamp)
    {
        logger.add(Entry.createEntry(sender, contents, timeStamp));
    }

    @Override
    public void addEntry(@Nullable String sender, String contents)
    {
        addEntry(contents, sender, 0L);
    }

    @Override
    public void clear()
    {

    }

    @Override
    public @NotNull CompoundTag serializeNBT()
    {
        ListTag list = new ListTag();
        logger.forEach(e -> list.add(e.serializeNBT()));
        CompoundTag tag = new CompoundTag();
        tag.put("clog", list);
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt)
    {
        if (logger.size() != 0)
        {
            MODLOG.warn("Trying to overwrite ChatLogger data!");
            return;
        }

        if (nbt.contains("clog"))
        {
            ListTag list = nbt.getList("clog", Tag.TAG_COMPOUND);
            for (int i = 0; i < list.size(); i++)
            {
                Entry entry = Entry.createEntry(list.getCompound(i));
                if (entry != null)
                    logger.add(entry);
            }
        }
    }

}
