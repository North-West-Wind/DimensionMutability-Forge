package mod.linguardium.dimute;

import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(Main.MOD_ID)
public class Main {

    public static Logger LOGGER = LogManager.getLogger();

    public static final String MOD_ID = "dimute";
    public static final String MOD_NAME = "Dimension Mutability";

    public static void log(Level level, String message){
        LOGGER.log(level, "["+MOD_NAME+"] " + message);
    }

}