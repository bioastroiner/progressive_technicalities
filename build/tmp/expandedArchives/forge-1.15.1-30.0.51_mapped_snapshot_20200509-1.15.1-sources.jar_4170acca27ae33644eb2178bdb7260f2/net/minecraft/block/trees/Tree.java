package net.minecraft.block.trees;

import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.TreeFeatureConfig;

public abstract class Tree {
   /**
    * Get a {@link net.minecraft.world.gen.feature.ConfiguredFeature} of tree
    */
   @Nullable
   protected abstract ConfiguredFeature<TreeFeatureConfig, ?> getTreeFeature(Random randomIn);

   public boolean place(IWorld worldIn, ChunkGenerator<?> chunkGeneratorIn, BlockPos blockPosIn, BlockState blockStateIn, Random randomIn) {
      ConfiguredFeature<TreeFeatureConfig, ?> configuredfeature = this.getTreeFeature(randomIn);
      if (configuredfeature == null) {
         return false;
      } else {
         worldIn.setBlockState(blockPosIn, Blocks.AIR.getDefaultState(), 4);
         ((TreeFeatureConfig)configuredfeature.config).forcePlacement();
         if (configuredfeature.place(worldIn, chunkGeneratorIn, randomIn, blockPosIn)) {
            return true;
         } else {
            worldIn.setBlockState(blockPosIn, blockStateIn, 4);
            return false;
         }
      }
   }
}