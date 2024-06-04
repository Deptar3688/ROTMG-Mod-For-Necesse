package rotmg.level.gameTile;

import necesse.engine.util.GameRandom;
import necesse.gfx.gameTexture.GameTextureSection;
import necesse.level.gameTile.TerrainSplatterTile;
import necesse.level.maps.Level;

import java.awt.*;

public class OryxCastleTile extends TerrainSplatterTile {
    private final GameRandom drawRandom;

    public OryxCastleTile() {
        super(true, "oryxcastletile");
        this.mapColor = new Color(97, 91, 107);
        this.canBeMined = false;
        this.drawRandom = new GameRandom();
        this.toolTier = 100;
        this.alphaMaskTextureName = "borderedbasicmask.png";
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
