package wurmatron.viral.common.reference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.Item.Properties;
import net.minecraft.potion.Potion;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class Registry {

  public static List<Item> items = new ArrayList<>();
  public static List<Block> blocks = new ArrayList<>();
  public static HashMap<Block, Item> blockItems = new HashMap<>();
  public static List<Potion> potions = new ArrayList<>();

  public static void registerItem(Item item, String registryName) {
    item.setRegistryName(registryName);
    items.add(item);
  }

  public static void registerBlock(Block block, String registryName) {
    block.setRegistryName(registryName);
    BlockItem itemBlock = new BlockItem(block, new Properties());
    itemBlock.setRegistryName(registryName);
    blocks.add(block);
    blockItems.put(block, itemBlock);
  }

  public static void registerPotion(Potion potion) {
    potions.add(potion);
  }

  @SubscribeEvent
  public void registerBlocks(RegistryEvent.Register<Block> e) {
    e.getRegistry().registerAll(blocks.toArray(new Block[0]));
  }

  @SubscribeEvent
  public void registerItems(RegistryEvent.Register<Item> e) {
    e.getRegistry().registerAll(items.toArray(new Item[0]));
    e.getRegistry().registerAll(blockItems.values().toArray(new Item[0]));
  }

  @SubscribeEvent
  public static void registerPotions(RegistryEvent.Register<Potion> e) {
    e.getRegistry().registerAll(potions.toArray(new Potion[0]));
  }
}
