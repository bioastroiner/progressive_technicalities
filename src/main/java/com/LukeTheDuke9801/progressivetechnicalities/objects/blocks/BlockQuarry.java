package com.LukeTheDuke9801.progressivetechnicalities.objects.blocks;

import com.LukeTheDuke9801.progressivetechnicalities.init.ModTileEntityTypes;

import com.LukeTheDuke9801.progressivetechnicalities.util.helpers.KeyboardHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;

public class BlockQuarry extends Block{
	
	public BlockQuarry(Properties propertises) {
		super(propertises);
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void addInformation(ItemStack stack, @Nullable IBlockReader worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
		if (KeyboardHelper.isHoldingShift()) {
			tooltip.add(new StringTextComponent("Mines a 3x3 tunnel down to bedrock"));
		}
		super.addInformation(stack, worldIn, tooltip, flagIn);
	}

	@Override
	public boolean hasTileEntity(BlockState state) {
		return true;
	}
	
	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world) {
		return ModTileEntityTypes.QUARRY.get().create();
	}

}
