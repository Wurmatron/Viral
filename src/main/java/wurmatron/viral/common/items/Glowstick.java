package wurmatron.viral.common.items;


import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import wurmatron.viral.common.potion.RepelEffect;

public class Glowstick extends Item {

  public Glowstick() {
    super(new Properties().maxStackSize(1).group(ItemGroup.MISC));
    setRegistryName("glowstick");
  }

  @Override
  public ActionResultType onItemUseFirst(ItemStack stack, ItemUseContext context) {
    CompoundNBT data = new CompoundNBT();
    data.putInt("time", 0);
    stack.setTag(data);
    return ActionResultType.SUCCESS;
  }

  @Override
  public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand hand) {
    ItemStack stack = player.getHeldItem(hand);
    if (stack.hasTag() && stack.getTag().contains("time") && stack.getTag().getInt("time") > 0) {
      stack.damageItem(1, player, (playerEntity) -> {
      });
    } else {
      CompoundNBT data = new CompoundNBT();
      data.putInt("time", 100);
      stack.setTag(data);
    }
    return super.onItemRightClick(world, player, hand);
  }

  @Override
  public void addInformation(
      ItemStack stack, @Nullable World world, List<ITextComponent> tip, ITooltipFlag flag) {
    super.addInformation(stack, world, tip, flag);
    if (stack.hasTag() && stack.getTag().contains("time")) {
      tip.add(new StringTextComponent(new TranslationTextComponent("tooltip.timeRemaining.name").getFormattedText().replaceAll("%TIME%",
          stack.getTag().getInt("time") + "")));
    }
  }

  @Override
  public void fillItemGroup(ItemGroup group, NonNullList<ItemStack> items) {
    if (group == ItemGroup.MISC) {
      items.add(create(180));
      items.add(create(300));
      items.add(create(600));
    }
    super.fillItemGroup(group, items);
  }

  public ItemStack create(int time) {
    ItemStack glowstick = new ItemStack(this, 1);
    CompoundNBT nbt = new CompoundNBT();
    nbt.putInt("time", time);
    glowstick.setTag(nbt);
    return glowstick;
  }

  @Override
  public void onUsingTick(ItemStack stack, LivingEntity player, int count) {
    if (stack.getDamage() == 1 && stack.hasTag() && stack.getTag().contains("time") & stack.getTag().getInt("time") > 0) {
      int timeLeft = stack.getTag().getInt("time");
      if (timeLeft > 0) {
        if (!player.world.isRemote && player.world.getDayTime() % 20 == 0) {
          stack.getTag().putInt("time", stack.getTag().getInt("time") - 1);
        }
        player.addPotionEffect(new EffectInstance(new RepelEffect()));
      } else if (stack.hasTag() && stack.getTag().getInt("time") <= 0) {
        PlayerEntity playerLiving = (PlayerEntity) player;
        playerLiving.inventory.deleteStack(stack);
        playerLiving.inventory.addItemStackToInventory(new ItemStack(Items.DIAMOND));
      }
    }
  }
}
