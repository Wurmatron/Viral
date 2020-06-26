package io.wurmatron.viral.api.capabilities;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.common.util.NonNullSupplier;

public class ViralProvider implements ICapabilitySerializable<INBT> {

  @CapabilityInject(IViral.class)
  public static final Capability<IViral> VIRAL = null;

  private final LazyOptional<IViral> holder = LazyOptional.of(Viral::new);

  @Nonnull
  @Override
  public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap,
      @Nullable Direction side) {
    return cap == VIRAL ? holder.cast() : LazyOptional.empty();
  }
  @Override
  public INBT serializeNBT() {
    NonNullSupplier<IViral> nonNullSupplier = new NonNullSupplier<IViral>() {
      @Nonnull
      @Override
      public IViral get() {
        return null;
      }
    };
    return VIRAL.getStorage().writeNBT(VIRAL, holder.orElseGet(nonNullSupplier), null);
  }

  @Override
  public void deserializeNBT(INBT nbt) {
    NonNullSupplier<IViral> nonNullSupplier = new NonNullSupplier<IViral>() {
      @Nonnull
      @Override
      public IViral get() {
        return null;
      }
    };
    VIRAL.getStorage().readNBT(VIRAL, holder.orElseGet(nonNullSupplier), null, nbt);
  }
}
