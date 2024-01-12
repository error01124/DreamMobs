package ru.d78boga.dreammobs.entity;

import java.util.UUID;

import javax.annotation.Nullable;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAITempt;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemMonsterPlacer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ru.d78boga.dreammobs.entity.ai.EntityAIMateExtended;
import ru.d78boga.dreammobs.entity.interfaces.IAgeableMob;
import ru.d78boga.dreammobs.entity.interfaces.ILoveableMob;
import ru.d78boga.dreammobs.entity.interfaces.IMusicMob;
import ru.d78boga.dreammobs.init.LootTables;
import ru.d78boga.dreammobs.init.Sounds;

public class EntityDanya extends EntityMob implements IMusicMob, ILoveableMob<EntityDanya>, IAgeableMob
{
	private static final DataParameter<Boolean> BABY = EntityDataManager.<Boolean>createKey(EntityDanya.class, DataSerializers.BOOLEAN);
	protected int growingAge;
	protected int forcedAge;
	protected int forcedAgeTimer;
	private float ageWidth = -1.0F;
	private float ageHeight;
	private int inLove;
	private UUID playerInLove;

	public EntityDanya(World world)
	{
		super(world);
		setSize(0.6F, 1.98F);
	}

	@Override
	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(30D);
		this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(35.0D);
		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.3D);
		this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(6.0D);
		this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(2.0D);
		this.getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).setBaseValue(2.0D);
	}

	@Override
	protected void initEntityAI()
	{
		this.tasks.addTask(0, new EntityAISwimming(this));
		this.tasks.addTask(1, new EntityAIMateExtended<EntityDanya>(this, 1.0D));
		this.tasks.addTask(2, new EntityAITempt(this, 1.1D, Items.WHEAT, false));
		this.tasks.addTask(2, new EntityAIAttackMelee(this, 1.0D, false));
		this.tasks.addTask(3, new EntityAIWander(this, 1.0D));
		this.tasks.addTask(4, new EntityAILookIdle(this));
		this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true, new Class[0]));
	}

	public boolean isBreedingItem(ItemStack stack)
	{
		return stack.getItem() == Items.WHEAT;
	}

	@Override
	public boolean attackEntityAsMob(Entity entityIn)
	{
		if (super.attackEntityAsMob(entityIn))
		{
			if (entityIn instanceof EntityLivingBase)
			{
				((EntityLivingBase) entityIn).attackEntityAsMob(this);
				entityIn.attackEntityFrom(((EntityLivingBase) entityIn).getLastDamageSource(), 2);
			}

			return true;
		} else
		{
			return false;
		}
	}

	@Override
	protected void playHurtSound(DamageSource source)
	{
		SoundEvent soundevent = this.getHurtSound(source);

		if (soundevent != null)
		{
			this.playSound(soundevent, this.getSoundVolume(), this.getSoundPitch());
		}
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource p_184601_1_)
	{
		return Sounds.ENTITY_GOHA_HURT;
	}

	@Override
	protected float getSoundPitch()
	{
		return 1.0F;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void handleStatusUpdate(byte id)
	{
		boolean flag = id == 33;
		boolean flag1 = id == 36;
		boolean flag2 = id == 37;

		if (id == 18)
		{
			for (int i = 0; i < 7; ++i)
			{
				double d0 = this.rand.nextGaussian() * 0.02D;
				double d1 = this.rand.nextGaussian() * 0.02D;
				double d2 = this.rand.nextGaussian() * 0.02D;
				this.world.spawnParticle(EnumParticleTypes.HEART, this.posX + this.rand.nextFloat() * this.width * 2.0F - this.width, this.posY + 0.5D + this.rand.nextFloat() * this.height, this.posZ + this.rand.nextFloat() * this.width * 2.0F - this.width, d0, d1, d2);
			}
		} else if (id != 2 && !flag && !flag1 && !flag2)
		{
			if (id == 3)
			{
				SoundEvent soundevent1 = this.getDeathSound();

				if (soundevent1 != null)
				{
					this.playSound(soundevent1, this.getSoundVolume(), this.getSoundPitch());
				}

				this.setHealth(0.0F);
				this.onDeath(DamageSource.GENERIC);
			} else if (id == 30)
			{
				this.playSound(SoundEvents.ITEM_SHIELD_BREAK, 0.8F, 0.8F + this.world.rand.nextFloat() * 0.4F);
			} else if (id == 29)
			{
				this.playSound(SoundEvents.ITEM_SHIELD_BLOCK, 1.0F, 0.8F + this.world.rand.nextFloat() * 0.4F);
			} else
			{
				super.handleStatusUpdate(id);
			}
		} else
		{
			this.limbSwingAmount = 1.5F;
			this.hurtResistantTime = this.maxHurtResistantTime;
			this.maxHurtTime = 10;
			this.hurtTime = this.maxHurtTime;
			this.attackedAtYaw = 0.0F;

			if (flag)
			{
				this.playSound(SoundEvents.ENCHANT_THORNS_HIT, this.getSoundVolume(), (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
			}

			DamageSource damagesource;

			if (flag2)
			{
				damagesource = DamageSource.ON_FIRE;
			} else if (flag1)
			{
				damagesource = DamageSource.DROWN;
			} else
			{
				damagesource = DamageSource.GENERIC;
			}

			SoundEvent soundevent = this.getHurtSound(damagesource);

			if (soundevent != null)
			{
				this.playSound(soundevent, this.getSoundVolume(), this.getSoundPitch());
			}

			this.attackEntityFrom(DamageSource.GENERIC, 0.0F);
		}
	}

	@Override
	public boolean processInteract(EntityPlayer player, EnumHand hand)
	{
		ItemStack itemstack = player.getHeldItem(hand);

		if (!itemstack.isEmpty())
		{
			System.out.print(String.format("%s %d %s |", Boolean.toString(this.isBreedingItem(itemstack)), this.getGrowingAge(), Boolean.toString(this.inLove <= 0)));

			if (this.isBreedingItem(itemstack) && this.getGrowingAge() == 0 && this.inLove <= 0)
			{
				this.consumeItemFromStack(player, itemstack);
				this.setInLove(player);
				return true;
			}

			if (this.isChild() && this.isBreedingItem(itemstack))
			{
				this.consumeItemFromStack(player, itemstack);
				this.ageUp((int) (-this.getGrowingAge() / 20 * 0.1F), true);
				return true;
			}
		}

		if (itemstack.getItem() == Items.SPAWN_EGG)
		{
			if (!this.world.isRemote)
			{
				Class<? extends Entity> oclass = EntityList.getClass(ItemMonsterPlacer.getNamedIdFrom(itemstack));

				if (oclass != null && this.getClass() == oclass)
				{
					EntityDanya entityageable = this.createChild(this);

					if (entityageable != null)
					{
						entityageable.setGrowingAge(-24000);
						entityageable.setLocationAndAngles(this.posX, this.posY, this.posZ, 0.0F, 0.0F);
						this.world.spawnEntity(entityageable);

						if (itemstack.hasDisplayName())
						{
							entityageable.setCustomNameTag(itemstack.getDisplayName());
						}

						if (!player.capabilities.isCreativeMode)
						{
							itemstack.shrink(1);
						}
					}
				}
			}

			return true;
		} else
		{
			return false;
		}
	}

	protected boolean holdingSpawnEggOfClass(ItemStack p_190669_1_, Class<? extends Entity> p_190669_2_)
	{
		if (p_190669_1_.getItem() != Items.SPAWN_EGG)
		{
			return false;
		} else
		{
			Class<? extends Entity> oclass = EntityList.getClass(ItemMonsterPlacer.getNamedIdFrom(p_190669_1_));
			return oclass != null && p_190669_2_ == oclass;
		}
	}

	@Override
	protected void entityInit()
	{
		super.entityInit();
		this.dataManager.register(BABY, Boolean.valueOf(false));
	}

	@Override
	public int getGrowingAge()
	{
		if (this.world.isRemote)
		{
			return this.dataManager.get(BABY).booleanValue() ? -1 : 1;
		} else
		{
			return this.growingAge;
		}
	}

	@Override
	public void ageUp(int p_175501_1_, boolean p_175501_2_)
	{
		int i = this.getGrowingAge();
		int j = i;
		i = i + p_175501_1_ * 20;

		if (i > 0)
		{
			i = 0;

			if (j < 0)
			{
				this.onGrowingAdult();
			}
		}

		int k = i - j;
		this.setGrowingAge(i);

		if (p_175501_2_)
		{
			this.forcedAge += k;

			if (this.forcedAgeTimer == 0)
			{
				this.forcedAgeTimer = 40;
			}
		}

		if (this.getGrowingAge() == 0)
		{
			this.setGrowingAge(this.forcedAge);
		}
	}

	@Override
	public void addGrowth(int growth)
	{
		this.ageUp(growth, false);
	}

	@Override
	public void setGrowingAge(int age)
	{
		this.dataManager.set(BABY, Boolean.valueOf(age < 0));
		this.growingAge = age;
		this.setScaleForAge(this.isChild());
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound compound)
	{
		super.writeEntityToNBT(compound);
		compound.setInteger("Age", this.getGrowingAge());
		compound.setInteger("ForcedAge", this.forcedAge);
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound compound)
	{
		super.readEntityFromNBT(compound);
		this.setGrowingAge(compound.getInteger("Age"));
		this.forcedAge = compound.getInteger("ForcedAge");
	}

	@Override
	public void notifyDataManagerChange(DataParameter<?> key)
	{
		if (BABY.equals(key))
		{
			this.setScaleForAge(this.isChild());
		}

		super.notifyDataManagerChange(key);
	}

	protected void onGrowingAdult()
	{
	}

	@Override
	public boolean isChild()
	{
		return this.getGrowingAge() < 0;
	}

	public void setScaleForAge(boolean child)
	{
		this.setScale(child ? 0.5F : 1.0F);
	}

	@Override
	protected final void setSize(float width, float height)
	{
		boolean flag = this.ageWidth > 0.0F;
		this.ageWidth = width;
		this.ageHeight = height;

		if (!flag)
		{
			this.setScale(1.0F);
		}
	}

	protected final void setScale(float scale)
	{
		super.setSize(this.ageWidth * scale, this.ageHeight * scale);
	}

	@Override
	public void onLivingUpdate()
	{
		super.onLivingUpdate();

		if (this.world.isRemote)
		{
			if (this.forcedAgeTimer > 0)
			{
				if (this.forcedAgeTimer % 4 == 0)
				{
					this.world.spawnParticle(EnumParticleTypes.VILLAGER_HAPPY, this.posX + this.rand.nextFloat() * this.width * 2.0F - this.width, this.posY + 0.5D + this.rand.nextFloat() * this.height, this.posZ + this.rand.nextFloat() * this.width * 2.0F - this.width, 0.0D, 0.0D, 0.0D);
				}

				--this.forcedAgeTimer;
			}
		} else
		{
			int i = this.getGrowingAge();

			if (i < 0)
			{
				++i;
				this.setGrowingAge(i);

				if (i == 0)
				{
					this.onGrowingAdult();
				}
			} else if (i > 0)
			{
				--i;
				this.setGrowingAge(i);
			}
		}

		if (this.getGrowingAge() != 0)
		{
			this.inLove = 0;
		}

		if (this.inLove > 0)
		{
			--this.inLove;

			if (this.inLove % 10 == 0)
			{
				double d0 = this.rand.nextGaussian() * 0.02D;
				double d1 = this.rand.nextGaussian() * 0.02D;
				double d2 = this.rand.nextGaussian() * 0.02D;
				this.world.spawnParticle(EnumParticleTypes.HEART, this.posX + this.rand.nextFloat() * this.width * 2.0F - this.width, this.posY + 0.5D + this.rand.nextFloat() * this.height, this.posZ + this.rand.nextFloat() * this.width * 2.0F - this.width, d0, d1, d2);
			}
		}
	}

	protected void consumeItemFromStack(EntityPlayer player, ItemStack stack)
	{
		if (!player.capabilities.isCreativeMode)
		{
			stack.shrink(1);
		}
	}

	@Override
	public void setInLove(@Nullable EntityPlayer player)
	{
		this.inLove = 600;

		if (player != null)
		{
			this.playerInLove = player.getUniqueID();
		}

		this.world.setEntityState(this, (byte) 18);
	}

	@Override
	@Nullable
	public EntityPlayerMP getLoveCause()
	{
		if (this.playerInLove == null)
		{
			return null;
		} else
		{
			EntityPlayer entityplayer = this.world.getPlayerEntityByUUID(this.playerInLove);
			return entityplayer instanceof EntityPlayerMP ? (EntityPlayerMP) entityplayer : null;
		}
	}

	@Override
	public SoundEvent getMusic()
	{
		return Sounds.MUSIC_DANYA;
	}

	@Override
	@Nullable
	protected ResourceLocation getLootTable()
	{
		return LootTables.ENTITIES_DANYA;
	}

	public boolean isArmRaised()
	{
		return true;
	}

	@Override
	public void resetInLove()
	{
		this.inLove = 0;
	}

	@Override
	public boolean canMateWith(EntityDanya otherAnimal)
	{
		if (otherAnimal == this)
		{
			return false;
		} else if (otherAnimal.getClass() != this.getClass())
		{
			return false;
		} else
		{
			return this.isInLove() && otherAnimal.isInLove();
		}
	}

	@Override
	public boolean isInLove()
	{
		return this.inLove > 0;
	}

	@Override
	public EntityDanya createChild(EntityDanya entity)
	{
		return new EntityDanya(this.world);
	}
}
