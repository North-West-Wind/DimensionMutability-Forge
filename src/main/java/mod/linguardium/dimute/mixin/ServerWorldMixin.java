package mod.linguardium.dimute.mixin;

import mod.linguardium.dimute.api.copyableProperties;
import net.minecraft.network.Packet;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.WorldGenerationProgressListener;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.profiler.Profiler;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.MutableWorldProperties;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionOptions;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.level.LevelProperties;
import net.minecraft.world.level.ServerWorldProperties;
import net.minecraft.world.level.UnmodifiableLevelProperties;
import net.minecraft.world.level.storage.LevelStorage;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.concurrent.Executor;
import java.util.function.Supplier;

@Mixin(ServerWorld.class)
public abstract class ServerWorldMixin extends World {

    protected ServerWorldMixin(MutableWorldProperties properties, RegistryKey<World> registryRef, RegistryEntry<DimensionType> dimension, Supplier<Profiler> profiler, boolean isClient, boolean debugWorld, long seed, int maxChainedNeighborUpdates) {
        super(properties, registryRef, dimension, profiler, isClient, debugWorld, seed, maxChainedNeighborUpdates);
    }

    @ModifyVariable(at=@At(value="HEAD"),method="<init>",argsOnly = true)
    private static ServerWorldProperties mutableProperties(ServerWorldProperties immutable) {
        if (immutable instanceof UnmodifiableLevelProperties) {
            ServerWorldProperties baseProperties = ((UnmodifiableLevelPropertiesAccessor)immutable).getWorldProperties();
            if (baseProperties instanceof LevelProperties) {
                return ((copyableProperties)baseProperties).copy();
            }else {
                return baseProperties;
            }
        }
        return immutable;
    }
    @ModifyVariable(method="<init>",at = @At("HEAD"),argsOnly = true, ordinal = 1)
    private static boolean setTicksTime(boolean ticksTime, MinecraftServer server, Executor workerExecutor, LevelStorage.Session session, ServerWorldProperties properties, RegistryKey<World> worldKey, DimensionOptions dimensionOptions, WorldGenerationProgressListener worldGenerationProgressListener, boolean debugWorld) {
        if (!debugWorld && !worldKey.getValue().getNamespace().equals("minecraft")) {
            return true;
        }
        return ticksTime;
    }
    @Redirect(at=@At(value="INVOKE",target="Lnet/minecraft/server/PlayerManager;sendToAll(Lnet/minecraft/network/Packet;)V"),method="tickWeather")
    private void sendToThisDimension(PlayerManager playerManager, Packet<?> packet) {
        playerManager.sendToDimension(packet, getRegistryKey());
    }
}
