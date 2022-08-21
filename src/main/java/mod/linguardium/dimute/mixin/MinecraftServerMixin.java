package mod.linguardium.dimute.mixin;

import mod.linguardium.dimute.api.copyableProperties;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.DerivedLevelData;
import net.minecraft.world.level.storage.PrimaryLevelData;
import net.minecraft.world.level.storage.ServerLevelData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(MinecraftServer.class)
public class MinecraftServerMixin {
    @Unique
    private boolean debug;

    @Unique
    private ResourceKey<Level> dimension;

    /*@Redirect(at=@At(value="INVOKE",target="Lnet/minecraft/server/level/ServerLevel;<init>(Lnet/minecraft/server/MinecraftServer;Ljava/util/concurrent/Executor;Lnet/minecraft/world/level/storage/LevelStorageSource$LevelStorageAccess;Lnet/minecraft/world/level/storage/ServerLevelData;Lnet/minecraft/resources/ResourceKey;Lnet/minecraft/core/Holder;Lnet/minecraft/server/level/progress/ChunkProgressListener;Lnet/minecraft/world/level/chunk/ChunkGenerator;ZJLjava/util/List;Z)Lnet/minecraft/server/level/ServerLevel;",ordinal = 1),method="createLevels")
    private ServerLevel setAndCopyMutableProperties(ServerLevel instance, MinecraftServer server, Executor executor, LevelStorageSource.LevelStorageAccess storage, ServerLevelData data, ResourceKey<Level> resourceKey, Holder holder, ChunkProgressListener listener, ChunkGenerator generator, boolean debug, long p_203771_, List<CustomSpawner> list, boolean timeticks) {

        ServerLevelData newData = null;
        if (data instanceof DerivedLevelData) {
            ServerLevelData baseProperties = ((UnmodifiableLevelPropertiesAccessor) data).getWrapped();
                if (baseProperties instanceof PrimaryLevelData) {
                    newData = ((copyableProperties)baseProperties).copy();
                }else {
                    newData = baseProperties;
                }
        }else{
            newData = data;
        }

        if (!debug && !resourceKey.location().getNamespace().equals("minecraft"))
            timeticks = true; // set timeticks on if debug is off and it isnt a vanilla dimension
        return new ServerLevel(server, executor, storage, newData, resourceKey, holder, listener, generator, debug, p_203771_, list, timeticks);
    }*/

    @ModifyArg(method = "createLevels", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/level/ServerLevel;<init>(Lnet/minecraft/server/MinecraftServer;Ljava/util/concurrent/Executor;Lnet/minecraft/world/level/storage/LevelStorageSource$LevelStorageAccess;Lnet/minecraft/world/level/storage/ServerLevelData;Lnet/minecraft/resources/ResourceKey;Lnet/minecraft/core/Holder;Lnet/minecraft/server/level/progress/ChunkProgressListener;Lnet/minecraft/world/level/chunk/ChunkGenerator;ZJLjava/util/List;Z)V", ordinal = 1), index = 3)
    private ServerLevelData setAndCopyMutableLevelData(ServerLevelData data) {
        ServerLevelData newData = null;
        if (data instanceof DerivedLevelData) {
            ServerLevelData baseProperties = ((UnmodifiableLevelPropertiesAccessor) data).getWrapped();
            if (baseProperties instanceof PrimaryLevelData) {
                newData = ((copyableProperties)baseProperties).copy();
            } else {
                newData = baseProperties;
            }
        } else {
            newData = data;
        }
        return newData;
    }

    @ModifyArg(method = "createLevels", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/level/ServerLevel;<init>(Lnet/minecraft/server/MinecraftServer;Ljava/util/concurrent/Executor;Lnet/minecraft/world/level/storage/LevelStorageSource$LevelStorageAccess;Lnet/minecraft/world/level/storage/ServerLevelData;Lnet/minecraft/resources/ResourceKey;Lnet/minecraft/core/Holder;Lnet/minecraft/server/level/progress/ChunkProgressListener;Lnet/minecraft/world/level/chunk/ChunkGenerator;ZJLjava/util/List;Z)V", ordinal = 1), index = 4)
    private ResourceKey<Level> getDimension(ResourceKey<Level> dimension) {
        this.dimension = dimension;
        return dimension;
    }

    @ModifyArg(method = "createLevels", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/level/ServerLevel;<init>(Lnet/minecraft/server/MinecraftServer;Ljava/util/concurrent/Executor;Lnet/minecraft/world/level/storage/LevelStorageSource$LevelStorageAccess;Lnet/minecraft/world/level/storage/ServerLevelData;Lnet/minecraft/resources/ResourceKey;Lnet/minecraft/core/Holder;Lnet/minecraft/server/level/progress/ChunkProgressListener;Lnet/minecraft/world/level/chunk/ChunkGenerator;ZJLjava/util/List;Z)V", ordinal = 1), index = 8)
    private boolean getDebug(boolean debug) {
        this.debug = debug;
        return debug;
    }

    @ModifyArg(method = "createLevels", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/level/ServerLevel;<init>(Lnet/minecraft/server/MinecraftServer;Ljava/util/concurrent/Executor;Lnet/minecraft/world/level/storage/LevelStorageSource$LevelStorageAccess;Lnet/minecraft/world/level/storage/ServerLevelData;Lnet/minecraft/resources/ResourceKey;Lnet/minecraft/core/Holder;Lnet/minecraft/server/level/progress/ChunkProgressListener;Lnet/minecraft/world/level/chunk/ChunkGenerator;ZJLjava/util/List;Z)V", ordinal = 1), index = 11)
    private boolean setTimeTicks(boolean timeTicks) {
        if (!debug && !dimension.location().getNamespace().equals("minecraft"))
            return true;
        return timeTicks;
    }
}
