package wurmatron.viral.client.gui;

import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.config.DummyConfigElement;
import net.minecraftforge.fml.client.config.GuiConfig;
import net.minecraftforge.fml.client.config.IConfigElement;
import wurmatron.viral.common.config.ConfigHandler;
import wurmatron.viral.common.refrence.Global;

import java.util.ArrayList;
import java.util.List;

public class ConfigGui extends GuiConfig {

		public ConfigGui (GuiScreen parent) {
				super(parent, getConfigElements(), Global.MODID, false, false, "Viral Configuration");
		}


		private static List<IConfigElement> getConfigElements () {
				List<IConfigElement> list = new ArrayList<>();
				list.add(categoryElement(Configuration.CATEGORY_GENERAL, "General", "config.category.general"));
				return list;
		}

		private static IConfigElement categoryElement (String category, String name, String tooltip_key) {
				return new DummyConfigElement.DummyCategoryElement(name, tooltip_key, new ConfigElement(ConfigHandler.config.getCategory(category)).getChildElements());
		}
}
