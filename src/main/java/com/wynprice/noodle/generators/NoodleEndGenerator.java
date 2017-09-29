package com.wynprice.noodle.generators;

import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;

import com.wynprice.noodle.NoodleUtils;

import net.minecraft.block.BlockChorusFlower;
import net.minecraft.block.BlockFalling;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityEndGateway;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.ChunkGeneratorEnd;
import net.minecraft.world.gen.MapGenBase;
import net.minecraft.world.gen.NoiseGeneratorOctaves;
import net.minecraft.world.gen.NoiseGeneratorSimplex;
import net.minecraft.world.gen.feature.WorldGenEndGateway;
import net.minecraft.world.gen.structure.MapGenEndCity;

public class NoodleEndGenerator extends ChunkGeneratorEnd
{
    /** RNG. */
    private final Random rand;
    private final World world;
    private final boolean mapFeaturesEnabled;
    private final BlockPos spawnPoint;
    private MapGenEndCity endCityGen = new MapGenEndCity(this);
    private NoiseGeneratorSimplex islandNoise;
    private Biome[] biomesForGeneration;

    public NoodleEndGenerator(World p_i47241_1_, boolean p_i47241_2_, long p_i47241_3_, BlockPos p_i47241_5_)
    {
    	super(p_i47241_1_, p_i47241_2_, p_i47241_3_, p_i47241_5_);
        this.world = p_i47241_1_;
        this.mapFeaturesEnabled = p_i47241_2_;
        this.spawnPoint = p_i47241_5_;
        this.rand = new Random(p_i47241_3_);
        this.islandNoise = new NoiseGeneratorSimplex(this.rand);

        net.minecraftforge.event.terraingen.InitNoiseGensEvent.ContextEnd ctx =
                new net.minecraftforge.event.terraingen.InitNoiseGensEvent.ContextEnd(new NoiseGeneratorOctaves(this.rand, 16), new NoiseGeneratorOctaves(this.rand, 16),
                		new NoiseGeneratorOctaves(this.rand, 8), new NoiseGeneratorOctaves(this.rand, 10), new NoiseGeneratorOctaves(this.rand, 16), islandNoise);
        ctx = net.minecraftforge.event.terraingen.TerrainGen.getModdedNoiseGenerators(p_i47241_1_, this.rand, ctx);
        this.islandNoise = ctx.getIsland();
        this.endCityGen = (MapGenEndCity) net.minecraftforge.event.terraingen.TerrainGen.getModdedMapGen(this.endCityGen, net.minecraftforge.event.terraingen.InitMapGenEvent.EventType.END_CITY);
    }
    /**
     * Generates the chunk at the specified position, from scratch
     */
    public Chunk generateChunk(int x, int z)
    {
        this.rand.setSeed((long)x * 341873128712L + (long)z * 132897987541L);
        ChunkPrimer chunkprimer = new ChunkPrimer();
        this.biomesForGeneration = this.world.getBiomeProvider().getBiomes(this.biomesForGeneration, x * 16, z * 16, 16, 16);
        if (this.mapFeaturesEnabled)
            this.endCityGen.generate(this.world, x, z, chunkprimer);
        
        NoodleUtils.TYPE.getWorldGenerator(Blocks.END_STONE).generate(this.world, x, z, chunkprimer);


        Chunk chunk = new Chunk(this.world, chunkprimer, x, z);
        byte[] abyte = chunk.getBiomeArray();

        for (int i = 0; i < abyte.length; ++i)
        {
            abyte[i] = (byte)Biome.getIdForBiome(this.biomesForGeneration[i]);
        }

        chunk.generateSkylightMap();
        return chunk;
    }

    private float getIslandHeightValue(int p_185960_1_, int p_185960_2_, int p_185960_3_, int p_185960_4_)
    {
        float f = (float)(p_185960_1_ * 2 + p_185960_3_);
        float f1 = (float)(p_185960_2_ * 2 + p_185960_4_);
        float f2 = 100.0F - MathHelper.sqrt(f * f + f1 * f1) * 8.0F;

        if (f2 > 80.0F)
        {
            f2 = 80.0F;
        }

        if (f2 < -100.0F)
        {
            f2 = -100.0F;
        }

        for (int i = -12; i <= 12; ++i)
        {
            for (int j = -12; j <= 12; ++j)
            {
                long k = (long)(p_185960_1_ + i);
                long l = (long)(p_185960_2_ + j);

                if (k * k + l * l > 4096L && this.islandNoise.getValue((double)k, (double)l) < -0.8999999761581421D)
                {
                    float f3 = (MathHelper.abs((float)k) * 3439.0F + MathHelper.abs((float)l) * 147.0F) % 13.0F + 9.0F;
                    f = (float)(p_185960_3_ - i * 2);
                    f1 = (float)(p_185960_4_ - j * 2);
                    float f4 = 100.0F - MathHelper.sqrt(f * f + f1 * f1) * f3;

                    if (f4 > 80.0F)
                    {
                        f4 = 80.0F;
                    }

                    if (f4 < -100.0F)
                    {
                        f4 = -100.0F;
                    }

                    if (f4 > f2)
                    {
                        f2 = f4;
                    }
                }
            }
        }

        return f2;
    }

    public boolean isIslandChunk(int p_185961_1_, int p_185961_2_)
    {
        return (long)p_185961_1_ * (long)p_185961_1_ + (long)p_185961_2_ * (long)p_185961_2_ > 4096L && this.getIslandHeightValue(p_185961_1_, p_185961_2_, 1, 1) >= 0.0F;
    }

    /**
     * Generate initial structures in this chunk, e.g. mineshafts, temples, lakes, and dungeons
     */
    public void populate(int x, int z)
    {
        BlockFalling.fallInstantly = true;
        net.minecraftforge.event.ForgeEventFactory.onChunkPopulate(true, this, this.world, this.rand, x, z, false);
        BlockPos blockpos = new BlockPos(x * 16, 0, z * 16);

        if (this.mapFeaturesEnabled)
            this.endCityGen.generateStructure(this.world, this.rand, new ChunkPos(x, z));


        this.world.getBiome(blockpos.add(16, 0, 16)).decorate(this.world, this.world.rand, blockpos);
        long i = (long)x * (long)x + (long)z * (long)z;

        if (i > 4096L)
        {
            if (this.getIslandHeightValue(x, z, 1, 1) > 40.0F)
            {
                int j = this.rand.nextInt(5);

                for (int k = 0; k < j; ++k)
                {
                    int l = this.rand.nextInt(16) + 8;
                    int i1 = this.rand.nextInt(16) + 8;
                    int j1 = this.world.getHeight(blockpos.add(l, 0, i1)).getY();

                    if (j1 > 0)
                    {
                        int k1 = j1 - 1;

                        if (this.world.isAirBlock(blockpos.add(l, k1 + 1, i1)) && this.world.getBlockState(blockpos.add(l, k1, i1)).getBlock() == Blocks.END_STONE)
                        {
                            BlockChorusFlower.generatePlant(this.world, blockpos.add(l, k1 + 1, i1), this.rand, 8);
                        }
                    }
                }

                if (this.rand.nextInt(700) == 0)
                {
                    int l1 = this.rand.nextInt(16) + 8;
                    int i2 = this.rand.nextInt(16) + 8;
                    int j2 = this.world.getHeight(blockpos.add(l1, 0, i2)).getY();

                    if (j2 > 0)
                    {
                        int k2 = j2 + 3 + this.rand.nextInt(7);
                        BlockPos blockpos1 = blockpos.add(l1, k2, i2);
                        (new WorldGenEndGateway()).generate(this.world, this.rand, blockpos1);
                        TileEntity tileentity = this.world.getTileEntity(blockpos1);

                        if (tileentity instanceof TileEntityEndGateway)
                        {
                            TileEntityEndGateway tileentityendgateway = (TileEntityEndGateway)tileentity;
                            tileentityendgateway.setExactPosition(this.spawnPoint);
                        }
                    }
                }
            }
        }

        net.minecraftforge.event.ForgeEventFactory.onChunkPopulate(false, this, this.world, this.rand, x, z, false);
        BlockFalling.fallInstantly = false;
    }

    /**
     * Called to generate additional structures after initial worldgen, used by ocean monuments
     */
    public boolean generateStructures(Chunk chunkIn, int x, int z)
    {
        return false;
    }

    public List<Biome.SpawnListEntry> getPossibleCreatures(EnumCreatureType creatureType, BlockPos pos)
    {
        return this.world.getBiome(pos).getSpawnableList(creatureType);
    }

    @Nullable
    public BlockPos getNearestStructurePos(World worldIn, String structureName, BlockPos position, boolean findUnexplored)
    {
        return "EndCity".equals(structureName) && this.endCityGen != null ? this.endCityGen.getNearestStructurePos(worldIn, position, findUnexplored) : null;
    }

    public boolean isInsideStructure(World worldIn, String structureName, BlockPos pos)
    {
        return "EndCity".equals(structureName) && this.endCityGen != null ? this.endCityGen.isInsideStructure(pos) : false;
    }

    /**
     * Recreates data about structures intersecting given chunk (used for example by getPossibleCreatures), without
     * placing any blocks. When called for the first time before any chunk is generated - also initializes the internal
     * state needed by getPossibleCreatures.
     */
    public void recreateStructures(Chunk chunkIn, int x, int z)
    {
    }
}