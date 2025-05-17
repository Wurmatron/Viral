package io.wurmatron.viral.common;

import io.wurmatron.viral.Viral;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ViralItems {

    private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Viral.MODID);

    public static final RegistryObject<Item> SYRINGE_EMPTY = ITEMS.register("syringeempty", () -> new SyringeItem(new Item.Properties().tab(ItemGroup.TAB_MISC).stacksTo(8)));
    public static final RegistryObject<Item> SYRINGE_FILLED = ITEMS.register("syringefilled", () -> new SyringeItem(new Item.Properties().tab(ItemGroup.TAB_MISC).stacksTo(4)));
    public static final RegistryObject<Item> SYRINGE_CURE = ITEMS.register("syringecure", () -> new SyringeItem(new Item.Properties().tab(ItemGroup.TAB_MISC).stacksTo(4)));
    public static final RegistryObject<Item> SYRINGE_IMMUNITY = ITEMS.register("syringeimmunity", () -> new SyringeItem(new Item.Properties().tab(ItemGroup.TAB_MISC).stacksTo(4)));
    public static final RegistryObject<Item> GLOWSTICK = ITEMS.register("glowstick", () -> new Item(new Item.Properties().tab(ItemGroup.TAB_MISC)));
    public static final RegistryObject<Item> MOB_MASH = ITEMS.register("mobmash", () -> new Item(new Item.Properties().tab(ItemGroup.TAB_MISC)));

    public static void register(IEventBus bus) {
        ITEMS.register(bus);
    }
}
