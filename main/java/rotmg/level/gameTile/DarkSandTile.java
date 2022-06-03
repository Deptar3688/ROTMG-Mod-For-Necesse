package rotmg.level.gameTile;

import engine.util.GameRandom;
import gfx.gameTexture.GameTexture;
import gfx.gameTexture.GameTextureSection;
import java.awt.Color;
import java.awt.Point;

import level.gameTile.TerrainSplatterTile;
import level.maps.Level;

public class DarkSandTile extends TerrainSplatterTile {
    private GameTexture texture;
    private final GameRandom drawRandom; // Used only in draw function

    public DarkSandTile() {
        super(false, "darksand");
        canBeMined = true;
        drawRandom = new GameRandom();
        roomProperties.add("outsidefloor");
        mapColor = new Color(78, 77, 113);
    }

    @Override
    protected void loadTextures() {
        super.loadTextures();
        texture = GameTexture.fromFile("resources/tiles/darksand");
    }

    @Override
    public Point getTerrainSprite(GameTextureSection gameTextureSection, Level level, int tileX, int tileY) {
        // This runs asynchronously, so if you want to randomize the tile that's
        // being drawn we have to synchronize the random call
        int tile;
        synchronized (drawRandom) {
            tile = drawRandom.seeded(getTileSeed(tileX, tileY)).nextInt(texture.getHeight() / 32);
        }
        return new Point(0, tile);
    }

    @Override
    public int getTerrainPriority() {
        return TerrainSplatterTile.PRIORITY_TERRAIN;
    }

}