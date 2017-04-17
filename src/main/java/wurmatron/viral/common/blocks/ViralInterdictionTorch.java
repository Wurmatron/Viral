package wurmatron.viral.common.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockTorch;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import wurmatron.viral.common.capabilities.IViral;
import wurmatron.viral.common.capabilities.ViralProvider;
import wurmatron.viral.common.config.Settings;
import wurmatron.viral.common.utils.LogHandler;

import java.util.List;
import java.util.Random;

public class ViralInterdictionTorch extends BlockTorch {

    public ViralInterdictionTorch() {
        setCreativeTab(CreativeTabs.DECORATIONS);
        setUnlocalizedName("torchInterdiction");
        setRegistryName("torchInterdiction");
        setHardness(1);
        setResistance(5);
        setLightLevel(12);
    }

    @Override
    public void updateTick(World world, BlockPos pos, IBlockState state, Random random) {
        super.updateTick(world, pos, state, random);
        world.scheduleBlockUpdate(pos, this, 1, 1);
        if (!world.isRemote) {
            List<Entity> entities = world.getEntitiesWithinAABB(Entity.class, new AxisAlignedBB(pos.getX() - Settings.radius, pos.getY() - Settings.radius, pos.getZ() - Settings.radius, pos.getX() + Settings.radius, pos.getY() + Settings.radius, pos.getZ() + Settings.radius));
            for (Entity e : entities) {
                if (!(e instanceof EntityPlayer)) {
                    IViral status = e.getCapability(ViralProvider.VIRAL, null);
                    if (status != null && status.status() != -1 && status.status() == 1) {
                        double distance = e.getDistance(pos.getX(), pos.getY(), pos.getZ());
                        if (distance >= Settings.radius || distance == 0)
                            continue;
                        if (distance < 1)
                            distance = 1;
                        double knockback = 1 + (1 / distance);
                        Vec3d angle = new Vec3d(e.posX - (pos.getX() + 0.5), e.posY - pos.getY(), e.posZ - (pos.getZ() + 0.5));
                        e.motionX += angle.xCoord * knockback * 0.05;
                        e.motionY += angle.yCoord * knockback * 0.05;
                        e.motionZ += angle.zCoord * knockback * 0.05;
                    }
                }
            }
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(IBlockState state, World world, BlockPos pos, Random random) {
        
    }
}
