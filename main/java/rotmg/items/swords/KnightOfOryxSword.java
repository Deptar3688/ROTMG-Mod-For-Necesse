package rotmg.items.swords;

import necesse.inventory.item.toolItem.swordToolItem.CustomSwordToolItem;

// Extends CustomSwordToolItem
public class KnightOfOryxSword extends CustomSwordToolItem {

    // Weapon attack textures are loaded from resources/player/weapons/<itemStringID>

    public KnightOfOryxSword() {
        super(Rarity.UNCOMMON, 300, 200, 65, 100, 400);
        this.attackXOffset = 8;
        this.attackYOffset = 8;
    }

}
