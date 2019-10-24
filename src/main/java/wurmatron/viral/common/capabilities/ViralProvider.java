//package wurmatron.viral.common.capabilities;
//
//import net.minecraft.nbt.NBTBase;
//import net.minecraft.util.EnumFacing;
//import net.minecraftforge.common.capabilities.Capability;
//import net.minecraftforge.common.capabilities.CapabilityInject;
//import net.minecraftforge.common.capabilities.ICapabilitySerializable;
//
//public class ViralProvider implements ICapabilitySerializable<NBTBase> {
//
//  @CapabilityInject(IViral.class)
//  public static final Capability<IViral> VIRAL = null;
//
//  private IViral instance = VIRAL.getDefaultInstance();
//
//  @Override
//  public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
//    return capability == VIRAL;
//  }
//
//  @Override
//  public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
//    return capability == VIRAL ? VIRAL.cast(instance) : null;
//  }
//
//  @Override
//  public NBTBase serializeNBT() {
//    return VIRAL.getStorage().writeNBT(VIRAL, instance, null);
//  }
//
//  @Override
//  public void deserializeNBT(NBTBase nbt) {
//    VIRAL.getStorage().readNBT(VIRAL, instance, null, nbt);
//  }
//}
