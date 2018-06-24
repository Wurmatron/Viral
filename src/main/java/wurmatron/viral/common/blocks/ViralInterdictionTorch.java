package wurmatron.viral.common.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockTorch;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import wurmatron.viral.common.capabilities.IViral;
import wurmatron.viral.common.capabilities.ViralProvider;
import wurmatron.viral.common.config.ConfigHandler;

import java.util.List;
import java.util.Random;

public class ViralInterdictionTorch extends BlockTorch {

	public ViralInterdictionTorch () {
		setCreativeTab (CreativeTabs.DECORATIONS);
		setUnlocalizedName ("torchInterdiction");
		setHardness (1);
		setResistance (5);
		setLightLevel (12);
		setTickRandomly(true);
	}

	@Override
	public void updateTick (World world,BlockPos pos,IBlockState state,Random random) {
		super.updateTick (world,pos,state,random);
		if (!world.isRemote) {
			List <Entity> entities = world.getEntitiesWithinAABB (Entity.class,new AxisAlignedBB (pos.getX () - ConfigHandler.radius,pos.getY () - ConfigHandler.radius,pos.getZ () - ConfigHandler.radius,pos.getX () + ConfigHandler.radius,pos.getY () + ConfigHandler.radius,pos.getZ () + ConfigHandler.radius));
			for (Entity e : entities) {
				if (!(e instanceof EntityPlayer)) {
					IViral status = e.getCapability (ViralProvider.VIRAL,null);
					if (status != null && status.status () == 1) {
						double distance = e.getDistanceSqToCenter (pos);
						double knockbackSpeed = 1 + (1 / distance);
						Vec3d angle = new Vec3d (e.posX - (pos.getX () + 0.5),e.posY - pos.getY (),e.posZ - (pos.getZ () + 0.5));
						e.motionX += angle.x * knockbackSpeed;
						e.motionY += angle.y * knockbackSpeed;
						e.motionZ += angle.z * knockbackSpeed;
					}
				}
			}
		}
	}

	@Override
	public int tickRate (World worldIn) {
		return 10;
	}
}
