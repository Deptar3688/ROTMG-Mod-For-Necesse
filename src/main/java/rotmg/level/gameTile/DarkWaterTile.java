//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package rotmg.level.gameTile;

import necesse.engine.registries.BiomeRegistry;
import necesse.engine.tickManager.TickManager;
import necesse.engine.util.GameRandom;
import necesse.gfx.camera.GameCamera;
import necesse.gfx.drawables.LevelSortedDrawable;
import necesse.gfx.drawables.LevelTileDrawOptions;
import necesse.gfx.gameTexture.GameTexture;
import necesse.gfx.gameTexture.GameTextureSection;
import java.awt.Color;
import java.util.List;

import necesse.level.gameTile.LiquidTile;
import necesse.level.maps.Level;

public class DarkWaterTile extends LiquidTile {
    public static GameTextureSection deepTexture;
    public GameTextureSection shallowTexture;
    protected final GameRandom drawRandom = new GameRandom();

    public DarkWaterTile() {
        super(false, true, new Color(42, 40, 63));
    }

    protected void loadTextures() {
        super.loadTextures();
        this.shallowTexture = tileTextures.addTexture(GameTexture.fromFile("tiles/watershallow"));
    }

    public Color getLiquidColor(Level level, int x, int y) {
        return this.getLiquidColor(5);
    }

    public Color getMapColor(Level level, int tileX, int tileY) {
        return this.getLiquidColor(level, tileX, tileY);
    }

    protected void addLiquidTopDrawables(LevelTileDrawOptions list, List<LevelSortedDrawable> sortedList, Level level, int tileX, int tileY, GameCamera camera, TickManager tickManager) {
        boolean addBobbing;
        synchronized(this.drawRandom) {
            addBobbing = this.drawRandom.seeded(this.getTileSeed(tileX, tileY)).getChance(0.9F);
        }

        if (addBobbing) {
            int drawX = camera.getTileDrawX(tileX);
            int drawY = camera.getTileDrawY(tileY);
            int offset = this.getLiquidBobbing(level, tileX, tileY);
            int xOffset;
            int yOffset;
            GameTextureSection bobbingTexture;
            if (level.liquidManager.getHeight(tileX, tileY) <= -2) {
                xOffset = 0;
                yOffset = offset;
                bobbingTexture = this.deepTexture;
            } else {
                xOffset = offset;
                yOffset = 0;
                bobbingTexture = this.shallowTexture;
            }

            int tile;
            synchronized(this.drawRandom) {
                tile = this.drawRandom.seeded(this.getTileSeed(tileX, tileY)).nextInt(bobbingTexture.getHeight() / 32);
            }

            list.add(bobbingTexture.sprite(0, tile, 32)).color(this.getLiquidColor(level, tileX, tileY).brighter()).pos(drawX + xOffset, drawY + yOffset - 2);
        }

    }
}
