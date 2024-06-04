//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package rotmg.level.maps.biomes;

import necesse.engine.GameEvents;
import necesse.engine.events.worldGeneration.GenerateIslandFeatureEvent;
import necesse.engine.events.worldGeneration.GeneratedIslandFeatureEvent;
import necesse.engine.network.server.Server;
import necesse.engine.registries.MusicRegistry;
import necesse.engine.registries.TileRegistry;
import necesse.engine.util.GameRandom;
import necesse.engine.world.WorldEntity;
import necesse.entity.mobs.PlayerMob;
import necesse.gfx.GameMusic;
import necesse.level.maps.Level;
import necesse.level.maps.biomes.ForestBiome;
import necesse.level.maps.biomes.MobSpawnTable;
import necesse.level.maps.generationModules.IslandGeneration;
import rotmg.level.presets.OryxCastlePreset;

import java.util.Collections;
import java.util.List;

public class OryxCastleBiome extends ForestBiome {
    public static MobSpawnTable defaultOryxCastleMobs = (new MobSpawnTable()).add(80, "knightoforyx");

    public OryxCastleBiome() {
    }

    public Level getNewSurfaceLevel(int islandX, int islandY, float islandSize, Server server, WorldEntity worldEntity) {
        return addCastle(getIsland(islandX, islandY, islandSize, server, worldEntity), islandSize);
    }

    public MobSpawnTable getMobSpawnTable(Level level) {
        if (level.getDimension() >= 0) {
            return defaultOryxCastleMobs;
        } else {
            return level.getDimension() == -1 ? defaultOryxCastleMobs : super.getMobSpawnTable(level);
        }
    }
    public MobSpawnTable getCritterSpawnTable(Level level) {
        if (level.getDimension() >= 0) {
            return defaultOryxCastleMobs;
        } else if (level.getDimension() == -1) {
            return defaultOryxCastleMobs;
        } else {
            return level.getDimension() == -2 ? defaultOryxCastleMobs : new MobSpawnTable();
        }
    }
    public float getSpawnRate(Level level) {
        return level.getDimension() == -100 ? super.getSpawnRate(level) * 0.9F : super.getSpawnRate(level);
    }

    public List<GameMusic> getLevelMusic(Level level, PlayerMob perspective) {
        return level.getDimension() != -100 && level.getDimension() != -101 ? super.getLevelMusic(level, perspective) : Collections.singletonList(MusicRegistry.dungeon);
    }

    private static Level addCastle(Level level, float islandSize) {
        GameEvents.triggerEvent(new GenerateIslandFeatureEvent(level, islandSize), (e) -> {
            GameRandom random = new GameRandom(level.getSeed());
            (new OryxCastlePreset(random)).applyToLevelCentered(level, level.width / 2, level.height / 2);
        });
        GameEvents.triggerEvent(new GeneratedIslandFeatureEvent(level, islandSize));
        return level;
    }

    public Level getIsland(int islandX, int islandY, float islandSize, Server server, WorldEntity worldEntity) {
        return new BiomeIslandLevel(islandX, islandY, islandSize, server, worldEntity);
    }

    private static class BiomeIslandLevel extends Level {
        public BiomeIslandLevel(int islandX, int islandY, float islandSize, Server server, WorldEntity worldEntity) {
            super(300, 300, islandX, islandY, 0, false, server, worldEntity);
            this.generateLevel(islandSize);
        }

        public void generateLevel(float islandSize) {
            int size = (int)(islandSize * 100.0F) + 20;
            IslandGeneration ig = new IslandGeneration(this, size);
            int waterTile = TileRegistry.getTileID("darkwatertile");
            int sandTile = TileRegistry.getTileID("darksandtile");
            int grassTile = TileRegistry.grassID;
            ig.generateSimpleIsland(this.width / 2, this.height / 2, waterTile, grassTile, sandTile);
        }
    }
}
