package com.LukeTheDuke9801.progressivetechnicalities.objects.blocks.machines.auto_spawner;

import com.LukeTheDuke9801.progressivetechnicalities.init.ModTileEntityTypes;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;

public class AutoSpawnerTileEntity extends TileEntity implements ITickableTileEntity{
	int tick = 0;

	String mobType;
	
	public AutoSpawnerTileEntity(final TileEntityType<?> tileEntityTypeIn) {
		super(tileEntityTypeIn);
		this.mobType = null;
	}
	
	public AutoSpawnerTileEntity() {
		this(ModTileEntityTypes.AUTO_SPAWNER.get());
	}
	
	@Override
	public void tick() {
		tick++;
		if (tick == 100) {
			tick = 0;
			execute();
		}
	}
	
	private void execute(){
		if (world.isRemote) return;
		
		if (this.mobType != null) {
			EntityType e = EntityType.byKey(this.mobType).get();
			e.spawn(world, ItemStack.EMPTY, null, pos.up(), SpawnReason.SPAWNER, false, false);
		}
	}
	
	public void setMobType(EntityType typeIn) {
		String key = EntityType.getKey(typeIn).toString();
		this.mobType = key;
	}
	
	@Override
	public CompoundNBT write(CompoundNBT compound) {
		compound.putString("type", this.mobType);
		return super.write(compound);
	}
	
	@Override
	public void read(CompoundNBT compound) {
		super.read(compound);
		this.mobType = compound.getString("type");
		
	}
}
