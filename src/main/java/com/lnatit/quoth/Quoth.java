package com.lnatit.quoth;

import com.lnatit.quoth.networks.NetworkManager;
import com.mojang.logging.LogUtils;
import net.minecraft.server.level.ChunkLevel;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraftforge.fml.common.Mod;
import org.slf4j.Logger;

import static com.lnatit.quoth.Quoth.MODID;

@Mod(MODID)
public class Quoth
{
    public static final String MODID = "quoth";
    public static final Logger MODLOG = LogUtils.getLogger();

    public Quoth()
    {
        NetworkManager.register();
    }
}
