package mod.linguardium.dimute.mixin;

import com.mojang.serialization.Lifecycle;
import mod.linguardium.dimute.api.copyableProperties;
import net.minecraft.world.level.LevelSettings;
import net.minecraft.world.level.levelgen.WorldGenSettings;
import net.minecraft.world.level.storage.PrimaryLevelData;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(PrimaryLevelData.class)
public class LevelPropertiesDuplicator implements copyableProperties {

    @Shadow private LevelSettings settings;

    @Shadow @Final private WorldGenSettings worldGenSettings;

    @Shadow @Final private Lifecycle worldGenSettingsLifecycle;

    @Override
    public PrimaryLevelData copy() {
        return new PrimaryLevelData(settings,worldGenSettings,worldGenSettingsLifecycle);
    }
}
