package mod.linguardium.dimute.mixin;

import net.minecraft.world.level.storage.DerivedLevelData;
import net.minecraft.world.level.storage.ServerLevelData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(DerivedLevelData.class)
public interface UnmodifiableLevelPropertiesAccessor {
    @Accessor
    ServerLevelData getWrapped();
}
