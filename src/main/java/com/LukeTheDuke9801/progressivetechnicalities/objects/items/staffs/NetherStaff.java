package com.LukeTheDuke9801.progressivetechnicalities.objects.items.staffs;

import java.util.List;

import com.LukeTheDuke9801.progressivetechnicalities.util.helpers.KeyboardHelper;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.BlockState;
import net.minecraft.block.ILiquidContainer;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.BucketItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;

public class NetherStaff extends Item{
	public NetherStaff(Properties properties) {
		super(properties);
	}
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
		if (KeyboardHelper.isHoldingShift()) {
			tooltip.add(new StringTextComponent("Summons lava"));
			tooltip.add(new StringTextComponent("Shift rightclick toggles between nether and overworld (spawns a portal)"));
		}
		
		super.addInformation(stack, worldIn, tooltip, flagIn);
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn){
		if (KeyboardHelper.isHoldingShift()) {
			if (worldIn.dimension.getType() == DimensionType.OVERWORLD) {
				playerIn.changeDimension(DimensionType.THE_NETHER);
			} else {
				playerIn.changeDimension(DimensionType.OVERWORLD);
			}
		} else {
			return placeLava(worldIn, playerIn, handIn);
		}
		
		return super.onItemRightClick(worldIn, playerIn, handIn);
		
	}
	
	private ActionResult placeLava(World worldIn, PlayerEntity playerIn, Hand handIn){
		// Stolen from https://github.com/Vazkii/Botania/blob/master/src/main/java/vazkii/botania/common/item/rod/ItemWaterRod.java
		
		ItemStack itemstack = playerIn.getHeldItem(handIn);
		RayTraceResult raytraceresult = rayTrace(worldIn, playerIn, RayTraceContext.FluidMode.NONE);
		ActionResult<ItemStack> ret = net.minecraftforge.event.ForgeEventFactory.onBucketUse(playerIn, worldIn, itemstack, raytraceresult);
		if (ret != null) {
			return ret;
		}
		if (raytraceresult.getType() == RayTraceResult.Type.MISS) {
			return new ActionResult<>(ActionResultType.PASS, itemstack);
		} else if (raytraceresult.getType() != RayTraceResult.Type.BLOCK) {
			return new ActionResult<>(ActionResultType.PASS, itemstack);
		} else {
			BlockRayTraceResult blockraytraceresult = (BlockRayTraceResult) raytraceresult;
			BlockPos blockpos = blockraytraceresult.getPos();
			if (worldIn.isBlockModifiable(playerIn, blockpos) && playerIn.canPlayerEdit(blockpos, blockraytraceresult.getFace(), itemstack)) {
				BlockState blockstate = worldIn.getBlockState(blockpos);
				BlockPos blockpos1 = blockstate.getBlock() instanceof ILiquidContainer ? blockpos : blockraytraceresult.getPos().offset(blockraytraceresult.getFace());
				if (((BucketItem) Items.LAVA_BUCKET).tryPlaceContainedLiquid(playerIn, worldIn, blockpos1, blockraytraceresult)) {
					if (playerIn instanceof ServerPlayerEntity) {
						CriteriaTriggers.PLACED_BLOCK.trigger((ServerPlayerEntity) playerIn, blockpos1, itemstack);
					}

					playerIn.addStat(Stats.ITEM_USED.get(this));
					return new ActionResult<>(ActionResultType.SUCCESS, itemstack);
				} else {
					return new ActionResult<>(ActionResultType.FAIL, itemstack);
				}
			} 
		}
		
		return new ActionResult<>(ActionResultType.FAIL, itemstack);
	}
}
