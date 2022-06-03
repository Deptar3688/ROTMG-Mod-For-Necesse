import engine.commands.CommandsManager;
import engine.modLoader.annotations.ModEntry;
import engine.registries.*;
import entity.mobs.HumanTexture;
import entity.mobs.MobTexture;
import gfx.gameTexture.GameTexture;
import inventory.recipe.Ingredient;
import inventory.recipe.Recipe;
import inventory.recipe.Recipes;
import level.gameObject.ModularCarpetObject;
import level.gameObject.WallObject;
import level.maps.biomes.Biome;
import rotmg.items.swords.KnightOfOryxSword;
import rotmg.level.gameTile.*;
import rotmg.mobs.hostile.HumantestMob;
import rotmg.mobs.hostile.KnightOfOryxMob;

import java.awt.*;

import static engine.registries.ObjectRegistry.getObject;

@ModEntry
public class ROTMGMod {

    public void init() {

        // Register tiles ----------------------------------------------------------------------
        TileRegistry.registerTile("darkwatertile", new DarkWaterTile(), 1, false);
        TileRegistry.registerTile("darksandtile", new DarkSandTile(), 1, true);
        TileRegistry.registerTile("darkgrasstile", new DarkGrassTile(), 1, true);
        //

        // Register objects ----------------------------------------------------------------------
        ObjectRegistry.registerObject("redcarpet", new ModularCarpetObject("redcarpet", new Color(96, 23, 13)), 25.0F, true);
        int[] castleWallIDs = WallObject.registerWallObjects("castle", "castlewall", 1, new Color(94, 95, 109), 10.0F, 20.0F);
        WallObject castleWall = (WallObject)getObject(castleWallIDs[0]);
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
