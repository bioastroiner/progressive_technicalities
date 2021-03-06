package com.LukeTheDuke9801.progressivetechnicalities.objects.items.tools;

import java.util.List;

import com.LukeTheDuke9801.progressivetechnicalities.entities.projectiles.ShulkerArrowEntity;
import com.LukeTheDuke9801.progressivetechnicalities.util.helpers.KeyboardHelper;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.ArrowItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;

public class ShulkerBowItem extends ModBow {
	public ShulkerBowItem(Item.Properties builder) {
	 	super(builder);
	}
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
		if (KeyboardHelper.isHoldingShift()) {
			tooltip.add(new StringTextComponent("Send your foes to the sky (for 2 seconds)"));
		}
		
		super.addInformation(stack, worldIn, tooltip, flagIn);
	}
	
	@Override
	protected AbstractArrowEntity getArrowEntity(World world, PlayerEntity player, ItemStack bowStack, ItemStack ammoStack){
		ShulkerArrowEntity arrowentity = new ShulkerArrowEntity(world, player);
        arrowentity.setPotionEffect(ammoStack);
        return arrowentity;
	}
	
}
