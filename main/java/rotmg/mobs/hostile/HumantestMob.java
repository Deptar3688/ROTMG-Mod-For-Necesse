////
//// Source code recreated from a .class file by IntelliJ IDEA
//// (powered by FernFlower decompiler)
////
//
//package examples;
//
//import engine.Screen;
//import engine.modifiers.ModifierValue;
//import engine.registries.ProjectileRegistry;
//import engine.registries.MobRegistry.Textures;
//import engine.save.LoadData;
//import engine.save.SaveData;
//import engine.sound.SoundEffect;
//import engine.tickManager.TickManager;
//import engine.util.GameRandom;
//import entity.mobs.GameDamage;
//import entity.mobs.HumanTexture;
//import entity.mobs.Mob;
//import entity.mobs.MobDrawable;
//import entity.mobs.MobHitEvent;
//import entity.mobs.MobSpawnLocation;
//import entity.mobs.PathDoorOption;
//import entity.mobs.PlayerMob;
//import entity.mobs.ability.CoordinateMobAbility;
//import entity.mobs.ability.TargetedMobAbility;
//import entity.mobs.ai.behaviourTree.BehaviourTreeAI;
//import entity.mobs.ai.behaviourTree.trees.PirateAITree;
//import entity.mobs.buffs.BuffModifiers;
//import entity.mobs.hostile.HostileMob;
//import entity.mobs.hostile.bosses.BossMob;
//import entity.particle.FleshParticle;
//import entity.particle.Particle.GType;
//import entity.projectile.Projectile;
//import gfx.GameResources;
//import gfx.camera.GameCamera;
//import gfx.drawOptions.DrawOptions;
//import gfx.drawOptions.human.HumanDrawOptions;
//import gfx.drawables.OrderableDrawables;
//import gfx.gameTexture.GameTexture;
//import inventory.InventoryItem;
//import inventory.lootTable.LootItemInterface;
//import inventory.lootTable.LootTable;
//import inventory.lootTable.lootItem.ChanceLootItemList;
//import inventory.lootTable.lootItem.LootItem;
//import inventory.lootTable.lootItem.LootItemList;
//import inventory.lootTable.lootItem.OneOfLootItems;
//import java.awt.Point;
//import java.awt.Rectangle;
//import java.awt.geom.Line2D;
//import java.util.List;
//import level.maps.CollisionFilter;
//import level.maps.Level;
//import level.maps.light.GameLight;
//
//public abstract class HumantestMob extends HostileMob {
//    public static HumanTexture texture;
//    public static LootTable lootTable = new LootTable(new LootItemInterface[]{new ChanceLootItemList(0.05F, new LootItemInterface[]{new OneOfLootItems(new LootItem("cutlass"), new LootItemInterface[]{new LootItemList(new LootItemInterface[]{new LootItem("flintlock"), LootItem.between("simplebullet", 40, 80)})})}), LootItem.between("coin", 10, 50)});
//    private boolean rangedAttack;
//    private long shootTime;
//    private Mob shootTarget;
//    private int normalAnimSpeed;
//    public int meleeDamage = 30;
//    public int shootDamage = 40;
//    public Point baseTile;
//    public final TargetedMobAbility startShootingAbility;
//    public final CoordinateMobAbility shootAbility;
//
//    public HumantestMob() {
//        super(100);
//        this.attackCooldown = 500L;
//        this.normalAnimSpeed = 400;
//        this.attackAnimSpeed = this.normalAnimSpeed;
//        this.setSpeed(35.0F);
//        this.setSwimSpeed(1.0F);
//        this.setFriction(3.0F);
//        this.setArmor(20);
//        this.collision = new Rectangle(-10, -7, 20, 14);
//        this.hitBox = new Rectangle(-14, -12, 28, 24);
//        this.selectBox = new Rectangle(-14, -41, 28, 48);
//        this.canDespawn = false;
//        this.baseTile = null;
//        this.startShootingAbility = (TargetedMobAbility)this.registerAbility(new TargetedMobAbility() {
//            protected void run(Mob target) {
//                int chargeTime = 1500;
//                HumantestMob.this.attackAnimSpeed = chargeTime + 500;
//                HumantestMob.this.attackCooldown = (long)chargeTime;
//                HumantestMob.this.shootTime = HumantestMob.this.getWorldEntity().getTime() + (long)chargeTime;
//                HumantestMob.this.rangedAttack = true;
//                HumantestMob.this.shootTarget = target;
//                HumantestMob.this.startAttackCooldown();
//                if (target != null) {
//                    HumantestMob.this.showAttack(target.getX(), target.getY(), false);
//                } else {
//                    HumantestMob.this.showAttack(HumantestMob.this.getX() + 100, HumantestMob.this.getY(), false);
//                }
//
//            }
//        });
//        this.shootAbility = (CoordinateMobAbility)this.registerAbility(new CoordinateMobAbility() {
//            protected void run(int x, int y) {
//                HumantestMob.this.rangedAttack = true;
//                HumantestMob.this.attackAnimSpeed = HumantestMob.this.normalAnimSpeed;
//                HumantestMob.this.shootAbilityProjectile(x, y);
//            }
//        });
//    }
//
//    @Override
//    public void addSaveData(SaveData save) {
//        super.addSaveData(save);
//        if (this.baseTile != null) {
//            save.addPoint("baseTile", this.baseTile);
//        }
//
//    }
//
//    @Override
//    public void applyLoadData(LoadData save) {
//        super.applyLoadData(save);
//        if (save.hasLoadDataByName("baseTile")) {
//            this.baseTile = save.getPoint("baseTile", (Point)null);
//        }
//
//    }
//
//    @Override
//    public void init() {
//        super.init();
//        this.setupAI();
//    }
//
//    public void setupAI() {
//        if (this.baseTile == null || this.baseTile.x == 0 && this.baseTile.y == 0) {
//            this.baseTile = new Point(this.getX() / 32, this.getY() / 32);
//        }
//
//        this.ai = new BehaviourTreeAI(this, new PirateAITree(544, 5000, 40, 640, 60000));
//    }
//
//    @Override
//    public PathDoorOption getPathDoorOption() {
//        return this.getLevel() != null ? this.getLevel().regionManager.CAN_OPEN_DOORS_OPTIONS : null;
//    }
//
//    @Override
//    public void clientTick() {
//        super.clientTick();
//        if (this.isAttacking) {
//            this.getAttackAnimProgress();
//        }
//
//    }
//
//    @Override
//    public void serverTick() {
//        super.serverTick();
//        this.tickShooting();
//        if (this.isAttacking) {
//            this.getAttackAnimProgress();
//        }
//
//    }
//
//    private void tickShooting() {
//        if (this.shootTime != 0L && this.getWorldEntity().getTime() > this.shootTime) {
//            if (this.getLevel().isServerLevel() && this.shootTarget != null && this.isSamePlace(this.shootTarget) && !this.getLevel().collides(new Line2D.Float(this.x, this.y, this.shootTarget.x, this.shootTarget.y), (new CollisionFilter()).projectileCollision())) {
//                this.shootAbility.runAndSend(this.shootTarget.getX(), this.shootTarget.getY());
//            }
//
//            this.shootTime = 0L;
//        }
//
//    }
//
//    @Override
//    public LootTable getLootTable() {
//        return this.isSummoned ? new LootTable() : lootTable;
//    }
//
//    @Override
//    public void spawnDeathParticles(float knockbackX, float knockbackY) {
//        for(int i = 0; i < 4; ++i) {
//            this.getLevel().entityManager.addParticle(new FleshParticle(this.getLevel(), this.getHumantestTexture().body, GameRandom.globalRandom.nextInt(5), 8, 32, this.x, this.y, 20.0F, knockbackX, knockbackY), GType.IMPORTANT_COSMETIC);
//        }
//
//    }
//
//    public void superAddDrawables(List<MobDrawable> list, OrderableDrawables tileList, OrderableDrawables topList, Level level, int x, int y, TickManager tickManager, GameCamera camera, PlayerMob perspective) {
//        super.addDrawables(list, tileList, topList, level, x, y, tickManager, camera, perspective);
//    }
//
//    @Override
//    public void addDrawables(List<MobDrawable> list, OrderableDrawables tileList, OrderableDrawables topList, Level level, int x, int y, TickManager tickManager, GameCamera camera, PlayerMob perspective) {
//        super.addDrawables(list, tileList, topList, level, x, y, tickManager, camera, perspective);
//        GameLight light = level.getLightLevel(x / 32, y / 32);
//        int drawX = camera.getDrawX(x) - 22 - 10;
//        int drawY = camera.getDrawY(y) - 44 - 7;
//        Point sprite = this.getAnimSprite(x, y, this.dir);
//        drawY += this.getBobbing(x, y);
//        drawY += this.getLevel().getTile(x / 32, y / 32).getMobSinkingAmount(this);
//        HumanDrawOptions humanDrawOptions = (new HumanDrawOptions(this.getHumantestTexture())).sprite(sprite).dir(this.dir).light(light);
//        if (this.inLiquid(x, y)) {
//            drawY -= 10;
//            humanDrawOptions.mask(Textures.mountmask);
//        }
//
//        float attackProgress = this.getAttackAnimProgress();
//        if (this.isAttacking) {
//            this.addAttackDraw(humanDrawOptions, attackProgress);
//        }
//
//        final DrawOptions drawOptions = humanDrawOptions.pos(drawX, drawY);
//        final DrawOptions boat = this.inLiquid(x, y) ? this.getBoatTexture().initDraw().sprite(this.dir % 4, 1, 64).light(light).pos(drawX, drawY + 10) : null;
//        list.add(new MobDrawable() {
//            public void draw(TickManager tickManager) {
//                drawOptions.draw();
//                if (boat != null) {
//                    boat.draw();
//                }
//
//            }
//        });
//        this.addShadowDrawables(tileList, x, y, light, camera);
//    }
//
//    protected abstract HumanTexture getHumantestTexture();
//
//    protected GameTexture getBoatTexture() {
//        return Textures.woodBoat;
//    }
//
//    protected void addAttackDraw(HumanDrawOptions drawOptions, float attackProgress) {
//        if (this.rangedAttack) {
//            this.addRangedAttackDraw(drawOptions, attackProgress);
//        } else {
//            this.addMeleeAttackDraw(drawOptions, attackProgress);
//        }
//
//    }
//
//    protected void addRangedAttackDraw(HumanDrawOptions drawOptions, float attackProgress) {
//        drawOptions.itemAttack(new InventoryItem("flintlock"), (PlayerMob)null, attackProgress, this.attackDir.x, this.attackDir.y);
//    }
//
//    protected void addMeleeAttackDraw(HumanDrawOptions drawOptions, float attackProgress) {
//        drawOptions.itemAttack(new InventoryItem("cutlass"), (PlayerMob)null, attackProgress, this.attackDir.x, this.attackDir.y);
//    }
//
//    @Override
//    public float getAttackAnimProgress() {
//        float out = super.getAttackAnimProgress();
//        if (!this.isAttacking) {
//            this.attackAnimSpeed = this.normalAnimSpeed;
//            this.attackCooldown = 500L;
//            this.rangedAttack = false;
//        }
//
//        return out;
//    }
//
//    @Override
//    public void doHitLogic(MobHitEvent hitEvent) {
//        super.doHitLogic(hitEvent);
//    }
//
//    @Override
//    public int getRockSpeed() {
//        return 20;
//    }
//
//    public void shootAbilityProjectile(int x, int y) {
//        if (this.getLevel().isServerLevel()) {
//            Projectile p = ProjectileRegistry.getProjectile("handgunbullet", this.getLevel(), this.x, this.y, (float)x, (float)y, 500.0F, 800, new GameDamage((float)this.shootDamage), 50, this);
//            p.resetUniqueID(new GameRandom((long)(x + y)));
//            this.getLevel().entityManager.projectiles.add(p);
//        }
//
//        this.showAttack(x, y, false);
//        if (this.getLevel().isClientLevel()) {
//            Screen.playSound(GameResources.handgun, SoundEffect.effect(this));
//        }
//
//    }
//
//    public void setSummoned() {
//        this.isSummoned = true;
//        this.spawnLightThreshold = (new ModifierValue(BuffModifiers.MOB_SPAWN_LIGHT_THRESHOLD, 0)).min(150, Integer.MAX_VALUE);
//        this.setHealthHidden(75);
//        this.setMaxHealth(75);
//    }
//
//    @Override
//    public MobSpawnLocation checkSpawnLocation(MobSpawnLocation location) {
//        return location.checkNotSolidTile().checkNotLevelCollides();
//    }
//
//    @Override
//    public boolean shouldSave() {
//        return !this.isSummoned;
//    }
//
//    @Override
//    public int getRespawnTime() {
//        return !this.isSummoned ? super.getRespawnTime() : BossMob.getBossRespawnTime(this);
//    }
//}

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package rotmg.mobs.hostile;

import necesse.engine.Screen;
import necesse.engine.modifiers.ModifierValue;
import necesse.engine.seasons.GameSeasons;
import necesse.engine.seasons.SeasonalHat;
import necesse.engine.sound.SoundEffect;
import necesse.engine.tickManager.TickManager;
import necesse.engine.util.GameRandom;
import necesse.entity.mobs.*;
import necesse.entity.mobs.ai.behaviourTree.BehaviourTreeAI;
import necesse.entity.mobs.buffs.BuffModifiers;
import necesse.entity.mobs.hostile.HostileMob;
import necesse.entity.particle.FleshParticle;
import necesse.entity.particle.Particle.GType;
import necesse.gfx.GameResources;
import necesse.gfx.camera.GameCamera;
import necesse.gfx.drawOptions.DrawOptions;
import necesse.gfx.drawOptions.human.HumanDrawOptions;
import necesse.gfx.drawables.OrderableDrawables;
import necesse.gfx.gameTexture.GameTexture;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.armorItem.ArmorItem.HairDrawMode;
import necesse.inventory.lootTable.LootItemInterface;
import necesse.inventory.lootTable.LootTable;
import necesse.inventory.lootTable.lootItem.LootItem;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.List;
import necesse.level.maps.Level;
import necesse.level.maps.light.GameLight;
import rotmg.mobs.ai.MeleeSwordChaserAI;

public class HumantestMob extends HostileMob {
    public static HumanTexture texture;
    public static LootTable lootTable = new LootTable(new LootItemInterface[]{LootItem.between("bone", 1, 3)});
    protected SeasonalHat hat;
    public int meleeDamage = 50;
    public Point baseTile;

    public HumantestMob() {
        super(300);
        this.attackAnimSpeed = 200;
        this.setSpeed(30.0F);
        this.setSwimSpeed(0.4F);
        this.setFriction(3.0F);
        this.setKnockbackModifier(0.8F);
        this.setArmor(50);
        this.collision = new Rectangle(-10, -7, 20, 14);
        this.hitBox = new Rectangle(-14, -12, 28, 24);
        this.selectBox = new Rectangle(-14, -41, 28, 48);
        this.canDespawn = false;
        this.baseTile = null;
    }

    public void init() {
        if (this.baseTile == null || this.baseTile.x == 0 && this.baseTile.y == 0) {
            this.baseTile = new Point(this.getX() / 32, this.getY() / 32);
        }

        this.ai = new BehaviourTreeAI(this, new MeleeSwordChaserAI(40, meleeDamage, 3000, 30000, baseTile));
        this.hat = GameSeasons.getHat(new GameRandom(this.getUniqueID()));
    }


    public void clientTick() {
        super.clientTick();
        if (this.isAttacking) {
            this.getAttackAnimProgress();
        }

    }

    public void serverTick() {
        super.serverTick();
        if (this.isAttacking) {
            this.getAttackAnimProgress();
        }

    }

    protected void doHitLogic(MobHitEvent hitEvent) {
        super.doHitLogic(hitEvent);
        this.startAttackCooldown();
    }

    public LootTable getLootTable() {
        return this.hat != null ? this.hat.getLootTable(lootTable) : lootTable;
    }

    public DeathMessageTable getDeathMessages() {
        return this.getDeathMessages("humantestmob", 3);
    }

    public void playHitSound() {
        float pitch = GameRandom.globalRandom.getOneOf(new Float[]{0.95F, 1.0F, 1.05F});
        Screen.playSound(GameResources.crack, SoundEffect.effect(this).volume(1.6F).pitch(pitch));
    }

    protected void playDeathSound() {
        float pitch = GameRandom.globalRandom.getOneOf(new Float[]{0.95F, 1.0F, 1.05F});
        Screen.playSound(GameResources.crackdeath, SoundEffect.effect(this).volume(0.8F).pitch(pitch));
    }

    public void spawnDeathParticles(float knockbackX, float knockbackY) {
        for(int i = 0; i < 4; ++i) {
            this.getLevel().entityManager.addParticle(new FleshParticle(this.getLevel(), texture.body, GameRandom.globalRandom.nextInt(5), 8, 32, this.x, this.y, 20.0F, knockbackX, knockbackY), GType.IMPORTANT_COSMETIC);
        }

    }

    public void addDrawables(List<MobDrawable> list, OrderableDrawables tileList, OrderableDrawables topList, Level level, int x, int y, TickManager tickManager, GameCamera camera, PlayerMob perspective) {
        super.addDrawables(list, tileList, topList, level, x, y, tickManager, camera, perspective);
        GameLight light = level.getLightLevel(x / 32, y / 32);
        int drawX = camera.getDrawX(x) - 22 - 10;
        int drawY = camera.getDrawY(y) - 44 - 7;
        Point sprite = this.getAnimSprite(x, y, this.dir);
        drawY += this.getBobbing(x, y);
        drawY += this.getLevel().getTile(x / 32, y / 32).getMobSinkingAmount(this);
        HumanDrawOptions humanDrawOptions = (new HumanDrawOptions(texture)).sprite(sprite).dir(this.dir).light(light);

        float attackProgress = this.getAttackAnimProgress();
        if (this.isAttacking) {
            this.addAttackDraw(humanDrawOptions, attackProgress);
        }

        if (this.hat != null) {
            humanDrawOptions.hatTexture(this.hat.getDrawOptions(), HairDrawMode.NO_HAIR);
        }

        final DrawOptions drawOptions = humanDrawOptions.pos(drawX, drawY);
        list.add(new MobDrawable() {
            public void draw(TickManager tickManager) {
                drawOptions.draw();
            }
        });
        this.addShadowDrawables(tileList, x, y, light, camera);
    }

    public int getRockSpeed() {
        return 20;
    }

    public float getAttackAnimProgress() {
        float out = super.getAttackAnimProgress();
        if (!this.isAttacking) {
            this.attackAnimSpeed = 400;
            this.attackCooldown = 500L;
        }

        return out;
    }

    // Melee Attack
    protected void addAttackDraw(HumanDrawOptions drawOptions, float attackProgress) {
        drawOptions.itemAttack(new InventoryItem("cutlass"), null, attackProgress, this.attackDir.x, this.attackDir.y);
    }

    public ModifierValue<?>[] getDefaultModifiers() {
        return new ModifierValue[]{(new ModifierValue(BuffModifiers.FRICTION, 0.0F)).min(0.75F)};
    }
}
