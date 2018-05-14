package wurmatron.viral.common.blocks;

import net.minecraft.block.BlockTorch;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import wurmatron.viral.common.capabilities.IViral;
import wurmatron.viral.common.capabilities.ViralProvider;
import wurmatron.viral.common.config.ConfigHandler;

import java.util.List;
import java.util.Random;

public class ViralShield extends BlockTorch {

	public ViralShield () {
		setCreativeTab (CreativeTabs.DECORATIONS);
		setUnlocalizedName ("shield");
		setHardness (1);
		setResistance (5);
		setLightLevel (12);
	}

	@Override
	public void updateTick (World world,BlockPos pos,IBlockState state,Random random) {
		super.updateTick (world,pos,state,random);
		world.scheduleBlockUpdate (pos,this,1,1);
		if (!world.isRemote) {
			world.setBlockState (pos.north (ConfigHandler.radius).east (ConfigHandler.radius),Blocks.BEACON.getDefaultState ());
			world.setBlockState (pos.south (ConfigHandler.radius).west (ConfigHandler.radius),Blocks.BEACON.getDefaultState ());
			List <Entity> entities = world.getEntitiesWithinAABB (Entity.class,new AxisAlignedBB (pos.getX () - ConfigHandler.radius,pos.getY () - ConfigHandler.radius,pos.getZ () - ConfigHandler.radius,pos.getX () + ConfigHandler.radius,pos.getY () + ConfigHandler.radius,pos.getZ () + ConfigHandler.radius));
			for (Entity e : entities) {
				if (!(e instanceof EntityPlayer)) {
					IViral status = e.getCapability (ViralProvider.VIRAL,null);
					if (status != null && status.status () != -1 && status.status () == 1) {
						e.setDead ();
					}
				}
			}
		}
	}

	public int tickRate (World worldIn) {
		return 5;
	}

	@Override
	public void randomDisplayTick (IBlockState state,World world,BlockPos pos,Random rand) {
		EnumFacing facing =  state.getValue (FACING);
		double d0 = (double) pos.getX () + 0.5D;
		double d1 = (double) pos.getY () + 0.7D;
		double d2 = (double) pos.getZ () + 0.5D;
		if (facing.getAxis ().isHorizontal ()) {
			EnumFacing enumfacing1 = facing.getOpposite ();
			world.spawnParticle (EnumParticleTypes.SMOKE_NORMAL,d0 + 0.27D * (double) enumfacing1.getFrontOffsetX (),d1 + 0.22D,d2 + 0.27D * (double) enumfacing1.getFrontOffsetZ (),0.0D,0.0D,0.0D);
			world.spawnParticle (EnumParticleTypes.DRAGON_BREATH,d0 + 0.27D * (double) enumfacing1.getFrontOffsetX (),d1 + 0.22D,d2 + 0.27D * (double) enumfacing1.getFrontOffsetZ (),0.0D,0.0D,0.0D);
		} else {
			world.spawnParticle (EnumParticleTypes.SMOKE_NORMAL,d0,d1,d2,0.0D,0.0D,0.0D);
			world.spawnParticle (EnumParticleTypes.DRAGON_BREATH,d0,d1,d2,0.0D,0.0D,0.0D);
		}
	}
}
