package rotmg.level.gameTile;

import necesse.engine.util.GameRandom;
import necesse.gfx.gameTexture.GameTextureSection;
import necesse.level.gameTile.TerrainSplatterTile;
import necesse.level.maps.Level;

import java.awt.*;

public class OryxCastleDarkFloorTile extends TerrainSplatterTile {
    private final GameRandom drawRandom;

    public OryxCastleDarkFloorTile() {
        super(true, "oryxcastledarkfloor");
        this.mapColor = new Color(50, 50, 66);
        this.canBeMined = false;
        this.drawRandom = new GameRandom();
        this.toolTier = 100;
        this.alphaMaskTextureName = "basicmask.png";
    }

    public Point getTerrainSprite(GameTextureSection terrainTexture, Level level, int tileX, int tileY) {
        int tile;
        synchronized(this.drawRandom) {
            tile = this.drawRandom.seeded(this.getTileSeed(tileX, tileY)).nextInt(terrainTexture.getHeight() / 32);
        }

        return new Point(0, tile);
    }

    public int getTerrainPriority() {
        return 400;
    }
}