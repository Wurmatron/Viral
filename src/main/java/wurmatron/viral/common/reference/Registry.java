package wurmatron.viral.common.reference;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import wurmatron.viral.Viral;
import wurmatron.viral.common.items.ItemSyringe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Registry {

	public static List <Item> items = new ArrayList <> ();
	public static List <Block> blocks = new ArrayList <> ();
	public static HashMap <Block, Item> blockItems = new HashMap <> ();

	public static void registerItem (Item item,String registryName) {
		item.setRegistryName (registryName);
		item.setUnlocalizedName (item.getRegistryName ().toString ());
		items.add (item);
	}

	public static void registerBlock (Block block,String registryName) {
		block.setRegistryName (registryName);
		block.setUnlocalizedName (block.getRegistryName ().toString ());
		ItemBlock itemBlock = new ItemBlock (block);
		itemBlock.setRegistryName (registryName);
		blocks.add (block);
		blockItems.put (block,itemBlock);
	}

	@SubscribeEvent
	public void registerBlocks (RegistryEvent.Register <Block> e) {
		e.getRegistry ().registerAll (blocks.toArray (new Block[0]));
	}

	@SubscribeEvent
	public void registerItems (RegistryEvent.Register <Item> e) {
		e.getRegistry ().registerAll (items.toArray (new Item[0]));
		e.getRegistry ().registerAll (blockItems.values ().toArray (new Item[0]));
	}

//	@SubscribeEvent
//	public void registerItemModels (ModelBakeEvent e) {
//		for (Item item : Registry.items)
//			if (!(item instanceof ItemSyringe))
//				ModelLoader.setCustomModelResourceLocation (item,0,new ModelResourceLocation (item.getRegistryName ().toString (),"inventory"));
//
//		ModelLoader.setCustomModelResourceLocation (Viral.syringe,3,new ModelResourceLocation (Global.MODID +  ":test", "inventory"));
//		for(int index = 0; index < ItemSyringe.EnumType.values ().length; index++)
//			ModelLoader.setCustomModelResourceLocation (Viral.syringe,ItemSyringe.EnumType.values ()[index].meta, new ModelResourceLocation (Viral.syringe.getRegistryName (), "syringe" + "=" + ItemSyringe.EnumType.values ()[index]));
//		//		for (int s = 0; s < ItemSyringe.NAMES.length; s++)
////			ModelLoader.setCustomModelResourceLocation (Viral.syringe,s,new ModelResourceLocation (Global.MODID + ":syringe" + ItemSyringe.NAMES[s],"inventory"));
//		ModelLoader.setCustomModelResourceLocation (Item.getItemFromBlock (Viral.torchInterdiction),0,new ModelResourceLocation (Global.MODID + ":torchInterdiction","inventory"));
//		ModelLoader.setCustomModelResourceLocation (Item.getItemFromBlock (Viral.torchInterdictionInverted),0,new ModelResourceLocation (Global.MODID + ":torchInterdiction","inventory"));
//	}
}
