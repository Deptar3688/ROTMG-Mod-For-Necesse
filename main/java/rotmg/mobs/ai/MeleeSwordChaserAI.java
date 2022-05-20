//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package rotmg.mobs.ai;

import engine.util.gameAreaSearch.GameAreaStream;
import entity.mobs.GameDamage;
import entity.mobs.Mob;
import entity.mobs.ai.behaviourTree.composites.SequenceAINode;
import entity.mobs.ai.behaviourTree.leaves.ChaserAINode;
import entity.mobs.ai.behaviourTree.leaves.TargetFinderAINode;
import entity.mobs.ai.behaviourTree.leaves.WandererAINode;
import entity.mobs.ai.behaviourTree.util.TargetFinderDistance;
import entity.mobs.hostile.pirates.PirateMob;
import java.awt.Point;

public class MeleeSwordChaserAI<T extends Mob> extends SequenceAINode<T> {
    public final TargetFinderAINode<T> targetFinderNode;
    public final ChaserAINode<T> chaserNode;


    public MeleeSwordChaserAI(int meleeDistance, int meleeDamage, int searchDistance, int wanderFrequency, Point baseTile) {
        TargetFinderDistance<T> targetFinder = new TargetFinderDistance(searchDistance);
        targetFinder.targetLostAddedDistance = searchDistance * 2;
        this.addChild(this.targetFinderNode = new TargetFinderAINode<T>(targetFinder) {
            public GameAreaStream<? extends Mob> streamPossibleTargets(T mob, Point base, TargetFinderDistance<T> distance) {
                return TargetFinderAINode.streamPlayersAndHumans(mob, base, distance);
            }
        });
        this.addChild(this.chaserNode = new ChaserAINode<T>(meleeDistance, false, true) {
            public boolean attackTarget(T mob, Mob target) {
                if (mob.canAttack()) {
                    mob.attack(target.getX(), target.getY(), false);
                    target.isServerHit(new GameDamage((float) meleeDamage), target.x - mob.x, target.y - mob.y, 100.0F, mob);
                    return true;
                } else {
                    return false;
                }
            }
        });
        WandererAINode<T> wanderer = new WandererAINode<T>(wanderFrequency) {
            public Point getBase(T mob) {
                return baseTile;
            }
        };
        wanderer.searchRadius = 5;
        this.addChild(wanderer);
    }
}
