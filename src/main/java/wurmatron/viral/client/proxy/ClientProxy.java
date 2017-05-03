package wurmatron.viral.client.proxy;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import wurmatron.viral.Viral;
import wurmatron.viral.common.config.ConfigHandler;
import wurmatron.viral.common.items.ItemSyringe;
import wurmatron.viral.common.proxy.CommonProxy;
import wurmatron.viral.common.reference.Global;

public class ClientProxy extends CommonProxy {

	@Override
	public void register () {
		super.register ();
		MinecraftForge.EVENT_BUS.register (new ConfigHandler ());
		registerItemModels ();
	}

	private void registerItemModels () {
		for (int s = 0; s < ItemSyringe.NAMES.length; s++) {
			ModelLoader.setCustomModelResourceLocation (Viral.syringe,s,new ModelResourceLocation (Global.MODID + ":syringe" + ItemSyringe.NAMES[s],"inventory"));
			Minecraft.getMinecraft ().getRenderItem ().getItemModelMesher ().register (Viral.syringe,s,new ModelResourceLocation (Global.MODID + ":syringe" + ItemSyringe.NAMES[s],"inventory"));
		}
		Minecraft.getMinecraft ().getRenderItem ().getItemModelMesher ().register (Item.getItemFromBlock (Viral.torchInterdiction),0,new ModelResourceLocation (Global.MODID + ":torchInterdiction","inventory"));
		Minecraft.getMinecraft ().getRenderItem ().getItemModelMesher ().register (Item.getItemFromBlock (Viral.torchInterdictionInverted),0,new ModelResourceLocation (Global.MODID + ":torchInterdiction","inventory"));
	}
}
