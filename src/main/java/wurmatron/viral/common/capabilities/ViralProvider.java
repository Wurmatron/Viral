package wurmatron.viral.common.capabilities;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

public class ViralProvider implements ICapabilitySerializable<INBT> {

  @CapabilityInject(IViral.class)
  public static final Capability<IViral> VIRAL = null;

  @Nonnull
  @Override
  public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
    return null;
  }

  @Override
  public INBT serializeNBT() {
    return null;
  }

  @Override
  public void deserializeNBT(INBT nbt) {

  }

//
//  private final LazyOptional<IViral> instance = LazyOptional.of(new ViralData());
//
//  @Nonnull
//  @Override
//  public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap) {
//    return cap == VIRAL ? LazyOptional.of(VIRAL.ca) : LazyOptional.empty();
//  }
//
//  @Override
//  public INBT serializeNBT() {
//    return VIRAL.getStorage().writeNBT(VIRAL, instance, null);
//  }
//
//  @Override
//  public void deserializeNBT(INBT nbt) {
//    VIRAL.getStorage().readNBT(VIRAL, instance, null, nbt);
//  }
}
