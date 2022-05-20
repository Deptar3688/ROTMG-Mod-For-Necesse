import engine.commands.CommandsManager;
import engine.modLoader.annotations.ModEntry;
import engine.registries.*;
import entity.mobs.HumanTexture;
import entity.mobs.MobTexture;
import gfx.gameTexture.GameTexture;
import inventory.recipe.Ingredient;
import inventory.recipe.Recipe;
import inventory.recipe.Recipes;
import level.maps.biomes.Biome;
import rotmg.items.swords.KnightOfOryxSword;
import rotmg.mobs.hostile.HumantestMob;
import rotmg.mobs.hostile.KnightOfOryxMob;

@ModEntry
public class ROTMGMod {

    public void init() {

        // Register tiles ----------------------------------------------------------------------

        //

        // Register objects ----------------------------------------------------------------------

        //

        // Register items ----------------------------------------------------------------------
        ItemRegistry.registerItem("knightoforyxsword", new KnightOfOryxSword(), 20, true);

        // Register mobs ----------------------------------------------------------------------
        MobRegistry.registerMob("humantestmob", HumantestMob.class, true);
        MobRegistry.registerMob("knightoforyxmob", KnightOfOryxMob.class, true);
        //

        // Register buffs ----------------------------------------------------------------------

    }

    public void initResources() {
        HumantestMob.texture = fromHumans("human/humantest");
        KnightOfOryxMob.texture = fromHumans("human/knightoforyx");
        KnightOfOryxMob.texturewithsword = fromHumans("human/knightoforyxwithsword");
    }

    private static GameTexture fromFile(String path, boolean preAntialias) {
        return GameTexture.fromFile("mobs/" + path);
    }

    private static MobTexture fromFiles(String path, String shadowPath) {
        return new MobTexture(fromFile(path, true), fromFile(shadowPath, false));
    }

    private static MobTexture fromFiles(String path) {
        return fromFiles(path, path + "_shadow");
    }

    private static HumanTexture fromHumans(String path, String armsPath) {
        return new HumanTexture(fromFile(path, true), fromFile(armsPath + "_left", false), fromFile(armsPath + "_right", false));
    }

    private static HumanTexture fromHumans(String path) {
        return fromHumans(path, path + "arms");
    }


    public void postInit() {
        // Add recipes
    }
}
