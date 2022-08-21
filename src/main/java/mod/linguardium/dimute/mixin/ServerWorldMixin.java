package mod.linguardium.dimute.mixin;

import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.storage.WritableLevelData;
import org.spongepowered.asm.mixin.Mixin;

import java.util.function.Supplier;

@Mixin(ServerLevel.class)
public abstract class ServerWorldMixin extends Level implements ServerLevelAccessor {

    protected ServerWorldMixin(WritableLevelData properties, ResourceKey<Level> registryKey, Holder<DimensionType> dimensionType, Supplier<ProfilerFiller> supplier, boolean bl, boolean bl2, long l) {
        super(properties, registryKey, dimensionType, supplier, bl, bl2, l);
    }

    // Forge already fixed this
    /*@Redirect(at=@At(value="INVOKE",target="Lnet/minecraft/server/PlayerManager;sendToAll(Lnet/minecraft/network/Packet;)V"),method="tickWeather")
    private void sendToThisDimension(PlayerManager playerManager, Packet<?> packet) {
        playerManager.sendToDimension(packet, getRegistryKey());
    }*/
}
