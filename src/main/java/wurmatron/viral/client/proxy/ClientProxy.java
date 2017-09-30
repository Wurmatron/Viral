package wurmatron.viral.client.proxy;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import wurmatron.viral.Viral;
import wurmatron.viral.common.config.ConfigHandler;
import wurmatron.viral.common.items.ItemSyringe;
import wurmatron.viral.common.proxy.CommonProxy;
import wurmatron.viral.common.reference.Global;
import wurmatron.viral.common.reference.Registry;
import wurmatron.viral.common.utils.LogHandler;

public class ClientProxy extends CommonProxy {

	@Override
	public void register () {
		super.register ();
		MinecraftForge.EVENT_BUS.register (new ConfigHandler ());
	}

	@SubscribeEvent
	public void model(ModelRegistryEvent e) {
		registerItemModels();
	}

	private void registerItemModels () {
		for (int s = 0; s < ItemSyringe.EnumType.values ().length; s++)
			ModelLoader.setCustomModelResourceLocation (Viral.syringe, s, new ModelResourceLocation (Global.MODID + ":syringe" + ItemSyringe.EnumType.values ()[s].name,"inventory"));
		ModelLoader.setCustomModelResourceLocation (Registry.blockItems.get (Viral.torchInterdiction),0,new ModelResourceLocation (Global.MODID + ":torchinterdiction","inventory"));
		ModelLoader.setCustomModelResourceLocation (Registry.blockItems.get (Viral.torchInterdictionInverted),0,new ModelResourceLocation (Global.MODID + ":torchinterdictioninverted","inventory"));
	}
}
