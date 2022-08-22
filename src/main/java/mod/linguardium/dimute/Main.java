package mod.linguardium.dimute;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.loading.FMLPaths;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.nio.file.Path;

@Mod(Main.MOD_ID)
public class Main {

    public static Logger LOGGER = LogManager.getLogger();

    public static final String MOD_ID = "dimute";
    public static final String MOD_NAME = "Dimension Mutability";

    public static void log(Level level, String message){
        LOGGER.log(level, "["+MOD_NAME+"] " + message);
    }

    public static class Config {
        private static final Path CONFIG_PATH = FMLPaths.CONFIGDIR.get().resolve(MOD_ID + ".json");
        private static boolean separateMinecraft = false;

        static {
            boolean needRewrite = false;
            if (!CONFIG_PATH.toFile().exists()) needRewrite = true;
            else {
                try {
                    JsonObject json = (JsonObject) JsonParser.parseReader(new FileReader(CONFIG_PATH.toFile()));
                    if (!json.has("separateMinecraft")) needRewrite = true;
                    else separateMinecraft = json.get("separateMinecraft").getAsBoolean();
                } catch (FileNotFoundException ignored) { }
            }
            if (needRewrite) {
                try {
                    JsonObject json = new JsonObject();
                    json.addProperty("separateMinecraft", separateMinecraft);

                    PrintWriter writer = new PrintWriter(CONFIG_PATH.toFile());
                    writer.write(json.toString());
                    writer.flush();
                    writer.close();
                } catch (FileNotFoundException ignored) { }
            }
        }

        public static boolean shouldSeparateMinecraft() {
            return separateMinecraft;
        }
    }

}