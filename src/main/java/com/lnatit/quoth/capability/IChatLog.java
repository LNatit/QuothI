package com.lnatit.quoth.capability;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.common.util.NonNullSupplier;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.LinkedList;

public interface IChatLog extends INBTSerializable<CompoundTag>
{
    void addEntry(@Nullable String sender, String contents, long timeStamp);

    void addEntry(@Nullable String sender, String contents);

    void clear();

    @Override
    @NotNull
    CompoundTag serializeNBT();

    @Override
    void deserializeNBT(CompoundTag nbt);

    class Provider<C extends IChatLog> implements ICapabilitySerializable<CompoundTag>
    {
        LazyOptional<C> opt;

        public Provider(NonNullSupplier<C> instanceSupplier)
        {
            opt = LazyOptional.of(instanceSupplier);
        }

        @Override
        public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side)
        {
            return cap == FiniteChatLog.CAPABILITY ? opt.cast() : LazyOptional.empty();
        }

        @Override
        public CompoundTag serializeNBT()
        {
            return opt.map(C::serializeNBT).orElse(new CompoundTag());
        }

        @Override
        public void deserializeNBT(CompoundTag nbt)
        {
            opt.ifPresent(cl -> cl.deserializeNBT(nbt));
        }
    }

    record Entry(String sender, String contents, long timeStamp)
    {
        public static final String SENDER = "sender";
        public static final String CONTENTS = "contents";
        public static final String TIMESTAMP = "time";

        public CompoundTag serializeNBT()
        {
            CompoundTag nbt = new CompoundTag();
            nbt.putString(SENDER, sender);
            nbt.putString(CONTENTS, contents);
            nbt.putLong(TIMESTAMP, timeStamp);
            return nbt;
        }

        public static Entry createEntry(CompoundTag nbt)
        {
            if (nbt.contains(CONTENTS))
                return new Entry(nbt.getString(SENDER), nbt.getString(CONTENTS), nbt.getLong(TIMESTAMP));
            return null;
        }

        public static Entry createEntry(String sender, String contents, long timeStamp)
        {
            if (sender == null)
                sender = "";
            return new Entry(sender, contents, timeStamp);
        }
    }
}
