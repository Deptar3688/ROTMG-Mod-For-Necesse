package rotmg.mobs.hostile;
//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import necesse.engine.Screen;
import necesse.engine.modifiers.ModifierValue;
import necesse.engine.registries.MobRegistry;
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

public class KnightOfOryxMob extends HostileMob {
    public static HumanTexture texture;
    public static HumanTexture texturewithsword;
    public static LootTable lootTable = new LootTable(new LootItemInterface[]{LootItem.between("bone", 1, 3)});
    protected SeasonalHat hat;
    public int meleeDamage = 50;
    public Point baseTile;

    public KnightOfOryxMob() {
        super(300);
        this.attackAnimSpeed = 200;
        this.setSpeed(30.0F);
        this.setSwimSpeed(0.4F);
        this.setFriction(3.0F);
        this.setKnockbackModifier(0.8F);
        this.setArmor(30);
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
        return this.getDeathMessages("Knight Of Oryx", 3);
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

//        HumanDrawOptions humanDrawOptions = (new HumanDrawOptions(texture)).sprite(sprite).dir(this.dir).light(light);
        HumanDrawOptions humanDrawOptions = (new HumanDrawOptions(this.isAttacking ? texture : texturewithsword)).sprite(sprite).dir(this.dir).light(light);

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
        drawOptions.itemAttack(new InventoryItem("knightoforyxsword"), null, attackProgress, this.attackDir.x, this.attackDir.y);
    }

    public ModifierValue<?>[] getDefaultModifiers() {
        return new ModifierValue[]{(new ModifierValue(BuffModifiers.FRICTION, 0.0F)).min(0.75F)};
    }
}