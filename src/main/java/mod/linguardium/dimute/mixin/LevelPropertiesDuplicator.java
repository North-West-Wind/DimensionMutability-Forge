package mod.linguardium.dimute.mixin;

import com.mojang.serialization.Lifecycle;
import mod.linguardium.dimute.api.copyableProperties;
import net.minecraft.world.gen.GeneratorOptions;
import net.minecraft.world.level.LevelInfo;
import net.minecraft.world.level.LevelProperties;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(LevelProperties.class)
public class LevelPropertiesDuplicator implements copyableProperties {


    @Shadow private LevelInfo levelInfo;

    @Shadow @Final private GeneratorOptions generatorOptions;

    @Shadow(aliases={"field_25426"},remap=false) @Final private Lifecycle lifecycle;

    @Override
    public LevelProperties copy() {
        return new LevelProperties(levelInfo,generatorOptions,lifecycle);

    }
}
