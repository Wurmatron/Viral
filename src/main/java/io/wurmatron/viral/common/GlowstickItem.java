package io.wurmatron.viral.common;

import io.wurmatron.viral.Viral;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class GlowstickItem extends Item {
    public GlowstickItem(Properties prop) {
        super(prop);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int x, boolean bol) {
        super.inventoryTick(stack, world, entity, x, bol);
        if (entity instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) entity;
            if (!player.hasEffect(Viral.REPEL_EFFECT.get()) || (player.hasEffect(Viral.REPEL_EFFECT.get()) && player.getEffect(Viral.REPEL_EFFECT.get()).getDuration() <= 20)) {
                if (stack.getDamageValue() > 0 && stack.getDamageValue() < stack.getMaxDamage()) {
                    player.addEffect(new EffectInstance(Viral.REPEL_EFFECT.get(), 120));
                }
            }
        }
        if (stack.getDamageValue() > 0 && stack.getDamageValue() < stack.getMaxDamage()) {
            stack.setDamageValue(stack.getDamageValue() + 1);
        }
    }

    @Override
    public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        if(player.inventory.getItem(player.inventory.selected).getDamageValue() == 0 && hand == Hand.MAIN_HAND) {
            player.inventory.getItem(player.inventory.selected).setDamageValue(1);
        }
        return super.use(world, player, hand);
    }
}
