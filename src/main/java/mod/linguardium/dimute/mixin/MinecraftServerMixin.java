package mod.linguardium.dimute.mixin;

import mod.linguardium.dimute.api.copyableProperties;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.WorldGenerationProgressListener;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;
import net.minecraft.world.border.WorldBorder;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.gen.Spawner;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.level.LevelProperties;
import net.minecraft.world.level.ServerWorldProperties;
import net.minecraft.world.level.UnmodifiableLevelProperties;
import net.minecraft.world.level.storage.LevelStorage;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;

@Mixin(MinecraftServer.class)
public class MinecraftServerMixin {
    @ModifyArgs(at=@At(value="INVOKE",target="Lnet/minecraft/server/world/ServerWorld;<init>(Lnet/minecraft/server/MinecraftServer;Ljava/util/concurrent/Executor;Lnet/minecraft/world/level/storage/LevelStorage$Session;Lnet/minecraft/world/level/ServerWorldProperties;Lnet/minecraft/util/registry/RegistryKey;Lnet/minecraft/world/dimension/DimensionType;Lnet/minecraft/server/WorldGenerationProgressListener;Lnet/minecraft/world/gen/chunk/ChunkGenerator;ZJLjava/util/List;Z)V",ordinal = 1),method="createWorlds")
    private void setAndCopyMutableProperties(Args args) {

        ServerWorldProperties immutable = args.get(3);

        ServerWorldProperties p=null;
        if (immutable instanceof UnmodifiableLevelProperties) {
            ServerWorldProperties baseProperties = ((UnmodifiableLevelPropertiesAccessor)immutable).getProperties();
                if (baseProperties instanceof LevelProperties) {
                    args.set(3,((copyableProperties)baseProperties).copy());
                }else {
                    args.set(3,baseProperties);
                }
        }else{
            args.set(3,immutable);
        }

        RegistryKey<World> worldResourceKey = args.get(4);
        boolean DebugMode = args.get(8);
        if (!DebugMode && !worldResourceKey.getValue().getNamespace().equals("minecraft"))
            args.set(11,true); // set timeticks on if debug is off and it isnt a vanilla dimension
    }
}
