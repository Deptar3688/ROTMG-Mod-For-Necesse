package rotmg.level.presets;

import necesse.engine.registries.ObjectRegistry;
import necesse.engine.registries.TileRegistry;
import necesse.engine.util.GameRandom;
import necesse.level.maps.presets.Preset;

import java.awt.*;

public class OryxCastlePreset extends Preset {
    public OryxCastlePreset(GameRandom random) {
        this(300, random);
    }
    private OryxCastlePreset(int size, GameRandom random) {
        super(size, size);
//        int mid = size / 2;
//        int maxDistance = size / 2 * 32;

//        int dungeonFloor = TileRegistry.getTileID("dungeonfloor");
//        int dungeonWall = ObjectRegistry.getObjectID("dungeonwall");

        PresetMap map = new PresetMap("sizzeresources/map/oryxcastlemap.png");
        Color[][] mapColors = map.getMap();

//        for(int x = 0; x < this.width; ++x) {
//            for(int y = 0; y < this.height; ++y) {
//                float distance = (float)(new Point(mid * 32 + 16, mid * 32 + 16)).distance((double)(x * 32 + 16), (double)(y * 32 + 16));
//                float distancePerc = distance / (float)maxDistance;
//                if (distancePerc < 0.4F) {
//                    this.setTile(x, y, castleFloor);
//                    this.setObject(x, y, 0);
//                } else if (distancePerc <= 1.0F) {
//                    float chance = Math.abs((distancePerc - 0.5F) * 2.0F - 1.0F) * 2.0F;
//                    if (random.getChance(chance)) {
//                        this.setTile(x, y, castleFloor);
//                        this.setObject(x, y, 0);
//                    }
//                }
//
//                if (distance < (float)maxDistance && distance >= (float)(maxDistance - 40) && random.getChance(0.4F)) {
//                    this.setObject(x, y, castleWall);
//                }
//            }
//        }
//
//        int chaliceOffset = 3;
//        this.setFireChalice(mid - chaliceOffset - 1, mid - chaliceOffset - 1);
//        this.setFireChalice(mid - chaliceOffset - 1, mid + chaliceOffset);
//        this.setFireChalice(mid + chaliceOffset, mid - chaliceOffset - 1);
//        this.setFireChalice(mid + chaliceOffset, mid + chaliceOffset);
//        this.setObject(mid, mid, ObjectRegistry.getObjectID("dungeonentrance"));
//        int mobOffset = 5;
//        this.addMob("knightoforyx", mid - mobOffset, mid - mobOffset, false);
//        this.addMob("knightoforyx", mid + mobOffset, mid - mobOffset, false);
//        this.addMob("knightoforyx", mid - mobOffset, mid + mobOffset, false);
//        this.addMob("knightoforyx", mid + mobOffset, mid + mobOffset, false);


        for (int x=0; x<mapColors.length; x++){
            for (int y=0; y< mapColors[0].length; y++){
                placeTile(x, y, mapColors[x][y]);
            }
        }
    }

//    private void setFireChalice(int x, int y) {
//        this.setObject(x, y, ObjectRegistry.getObjectID("firechalice"));
//        this.setObject(x + 1, y, ObjectRegistry.getObjectID("firechalice2"));
//        this.setObject(x, y + 1, ObjectRegistry.getObjectID("firechalice3"));
//        this.setObject(x + 1, y + 1, ObjectRegistry.getObjectID("firechalice4"));
//    }

    private void placeTile(int x, int y, Color color){
        // Possible Tiles/Objects
        int darkGrass = TileRegistry.getTileID("darkgrasstile");
        int corruptGrass = TileRegistry.getTileID("corruptgrasstile");
        int darkSand = TileRegistry.getTileID("darksandtile");
        int darkWater = TileRegistry.getTileID("darkwatertile");

        int castleTile = TileRegistry.getTileID("oryxcastletile");
        int castleFloor = TileRegistry.getTileID("oryxcastlefloor");
        int castleFloorDark = TileRegistry.getTileID("oryxcastledarkfloor");
        int castleStoneTile = TileRegistry.getTileID("oryxcastlestonetile");
        int castleStoneTileS = TileRegistry.getTileID("oryxcastlestonetilesquare");
        int castleStoneTileC = TileRegistry.getTileID("oryxcastlestonetilecircle");
        int castleStonePath = TileRegistry.getTileID("oryxcastlestonepath");

        int castleCarpet = ObjectRegistry.getObjectID("redcarpet");
        int castleWall = ObjectRegistry.getObjectID("oryxcastlewall");
        int interiorWall = ObjectRegistry.getObjectID("oryxinteriorwall");

        // Possible Colors
        Color darkGrassColor = new Color(42, 73, 27);
        Color corruptGrassColor = new Color(30, 36, 50);
        Color darkSandColor = new Color(78, 77, 113);
        Color darkWaterColor = new Color(42, 40,63);

        Color castleTileColor = new Color(97, 91, 107);
        Color castleFloorColor = new Color(69, 69, 91);
        Color castleFloorDarkColor = new Color(50, 50, 66);
        Color castleStoneTileColor = new Color(84, 84, 84);
        Color castleStoneTileSColor = new Color(67, 67, 67);
        Color castleStoneTileCColor = new Color(54, 54, 54);
        Color castleStonePathColor = new Color(66, 66, 66);

        Color castleCarpetColor = new Color(96, 23, 15);
        Color castleWallColor = new Color(0, 0,0);
        Color interiorWallColor = new Color(94, 95, 109);

        // Place tiles/objects corresponding to color
        if (color.equals(darkGrassColor)){
            this.setTile(x, y, darkGrass);
        } else if (color.equals(corruptGrassColor)) {
            this.setTile(x, y, corruptGrass);
        } else if (color.equals(darkSandColor)) {
            this.setTile(x, y, darkSand);
        } else if (color.equals(darkWaterColor)) {
            this.setTile(x, y, darkWater);
        } else if (color.equals(castleTileColor)) {
            this.setTile(x, y, castleTile);
        } else if (color.equals(castleFloorColor)) {
            this.setTile(x, y, castleFloor);
        } else if (color.equals(castleFloorDarkColor)) {
            this.setTile(x, y, castleFloorDark);
        } else if (color.equals(castleStoneTileColor)) {
            this.setTile(x, y, castleStoneTile);
        } else if (color.equals(castleStoneTileSColor)) {
            this.setTile(x, y, castleStoneTileS);
        } else if (color.equals(castleStoneTileCColor)) {
            this.setTile(x, y, castleStoneTileC);
        } else if (color.equals(castleStonePathColor)) {
            this.setTile(x, y, castleStonePath);
        } else if (color.equals(castleCarpetColor)) {
            this.setTile(x, y, castleFloor);
            this.setObject(x, y, castleCarpet);
        } else if (color.equals(castleWallColor)) {
            this.setTile(x, y, castleFloor);
            this.setObject(x, y, castleWall);
        } else if (color.equals(interiorWallColor)) {
            this.setTile(x, y, castleFloor);
            this.setObject(x, y, interiorWall);
        }
    }
}

