import necesse.engine.commands.CommandsManager;
import necesse.engine.modLoader.annotations.ModEntry;
import necesse.engine.registries.*;
import necesse.entity.mobs.HumanTexture;
import necesse.entity.mobs.MobTexture;

import necesse.gfx.gameTexture.GameTexture;
import necesse.inventory.recipe.Ingredient;
import necesse.inventory.recipe.Recipe;
import necesse.inventory.recipe.Recipes;
import necesse.level.gameObject.ModularCarpetObject;
import necesse.level.gameObject.WallObject;
import necesse.level.gameTile.PathTiledTile;
import necesse.level.maps.biomes.Biome;

// Items
import rotmg.items.swords.KnightOfOryxSword;

// Tiles
import rotmg.level.gameTile.*;
// Mobs
//import rotmg.level.maps.biomes.OryxCastleBiome;
import rotmg.mobs.hostile.*;

import java.awt.*;

import static necesse.engine.registries.ObjectRegistry.getObject;
import static necesse.level.gameTile.GameTile.tileTextures;

@ModEntry
public class ROTMGMod {

    public void init() {

        // Register tiles ----------------------------------------------------------------------
        TileRegistry.registerTile("darkwatertile", new DarkWaterTile(), 1, false);
        TileRegistry.registerTile("darksandtile", new DarkSandTile(), 1, true);
        TileRegistry.registerTile("darkgrasstile", new DarkGrassTile(), 1, true);
        TileRegistry.registerTile("corruptgrasstile", new CorruptGrassTile(), 1, true);

        TileRegistry.registerTile("oryxcastletile", new OryxCastleTile(), 1, false);
        TileRegistry.registerTile("oryxcastlefloor", new OryxCastleFloorTile(), 1, false);
        TileRegistry.registerTile("oryxcastledarkfloor", new OryxCastleDarkFloorTile(), 1, false);
        TileRegistry.registerTile("oryxcastlestonetile", new OryxCastleStoneTile(), 1, false);
        TileRegistry.registerTile("oryxcastlestonetilesquare", new OryxCastleStoneTileSquare(), 1, false);
        TileRegistry.registerTile("oryxcastlestonetilecircle", new OryxCastleStoneTileCircle(), 1, false);
        TileRegistry.registerTile("oryxcastlestonepath", new PathTiledTile("oryxcastlestonepath", new Color(66, 66, 66)), 5.0F, false);
        //

        // Register objects ----------------------------------------------------------------------
        ObjectRegistry.registerObject("redcarpet", new ModularCarpetObject("redcarpet", new Color(96, 23, 13)), 25.0F, true);
        int[] castleWallIDs = WallObject.registerWallObjects("castle", "castlewall", 1, new Color(94, 95, 109), 10.0F, 20.0F);
        WallObject castleWall = (WallObject)getObject(castleWallIDs[0]);
        int[] interiorWallIDs = WallObject.registerWallObjects("interior", "oryxinteriorwall", 100, new Color(0, 0, 0), 10.0F, 20.0F);
        WallObject interiorWall = (WallObject)getObject(interiorWallIDs[0]);
        //

        // Register items ----------------------------------------------------------------------
        ItemRegistry.registerItem("knightoforyxsword", new KnightOfOryxSword(), 20, true);

        // Register mobs ----------------------------------------------------------------------
        MobRegistry.registerMob("humantestmob", HumantestMob.class, true);
        MobRegistry.registerMob("knightoforyx", KnightOfOryxMob.class, true);
        //

        // Register buffs ----------------------------------------------------------------------

    }

    public void initResources() {
        HumantestMob.texture = fromHumans("human/humantest");
        KnightOfOryxMob.texture = fromHumans("human/knightoforyx");
        KnightOfOryxMob.texturewithsword = fromHumans("human/knightoforyxwithsword");
        DarkWaterTile.deepTexture = tileTextures.addTexture(GameTexture.fromFile("tiles/waterrough"));
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
