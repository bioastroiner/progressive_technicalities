package com.LukeTheDuke9801.progressivetechnicalities.entities;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ProjectileItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ItemParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ThrowableFireballEntity extends ProjectileItemEntity {
   private int explosionPower = 1;
   public ThrowableFireballEntity(EntityType<? extends ThrowableFireballEntity> p_i50159_1_, World p_i50159_2_) {
      super(p_i50159_1_, p_i50159_2_);
   }

   public ThrowableFireballEntity(World worldIn, LivingEntity throwerIn) {
      super(EntityType.SNOWBALL, throwerIn, worldIn);
   }

   protected Item getDefaultItem() {
      return Items.FIRE_CHARGE;
   }

   @OnlyIn(Dist.CLIENT)
   private IParticleData makeParticle() {
      ItemStack itemstack = this.func_213882_k();
      return (IParticleData)(itemstack.isEmpty() ? ParticleTypes.ITEM_SNOWBALL : new ItemParticleData(ParticleTypes.ITEM, itemstack));
   }

   /**
    * Handler for {@link World#setEntityState}
    */
   @OnlyIn(Dist.CLIENT)
   public void handleStatusUpdate(byte id) {
      if (id == 3) {
         IParticleData iparticledata = this.makeParticle();

         for(int i = 0; i < 8; ++i) {
            this.world.addParticle(iparticledata, this.getPosX(), this.getPosY(), this.getPosZ(), 0.0D, 0.0D, 0.0D);
         }
      }

   }

   /**
    * Called when this EntityThrowable hits a block or entity.
    */
   protected void onImpact(RayTraceResult result) {
	   if (!this.world.isRemote) {
	      if (result.getType() == RayTraceResult.Type.ENTITY) {
	         Entity entity = ((EntityRayTraceResult)result).getEntity();
	         entity.attackEntityFrom(DamageSource.IN_FIRE, 6.0F);
	         this.applyEnchantments(this.getThrower(), entity);
	      }

	      boolean flag = net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.world, this.getThrower());
	      this.world.createExplosion((Entity)null, this.getPosX(), this.getPosY(), this.getPosZ(), (float)this.explosionPower, flag, flag ? Explosion.Mode.DESTROY : Explosion.Mode.NONE);
	      this.remove();
	   }

   }

   
}