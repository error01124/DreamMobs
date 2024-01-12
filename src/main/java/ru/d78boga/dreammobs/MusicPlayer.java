package ru.d78boga.dreammobs;

import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import ru.d78boga.dreammobs.entity.MusicEntry;
import ru.d78boga.dreammobs.entity.interfaces.IMusicMob;

@Mod.EventBusSubscriber(modid = DreamMobs.MODID)
public class MusicPlayer
{
	private static MusicEntry playingMusic = null;

	public static void playMusic(SoundEvent soundEvent)
	{
		if (soundEvent != null)
		{
			SoundHandler soundHandler = Minecraft.getMinecraft().getSoundHandler();
			ISound sound = PositionedSoundRecord.getMusicRecord(soundEvent);

			if (playingMusic == null)
			{
				playingMusic = new MusicEntry(soundEvent, sound);
				soundHandler.playSound(playingMusic.getSound());
			}
		}
	}

	public static void changeMusic(SoundEvent soundEvent)
	{
		if (soundEvent != null)
		{
			SoundEvent playingSoundEvent = playingMusic == null ? null : playingMusic.getSoundEvent();

			if (playingSoundEvent != soundEvent)
			{
				stopMusic();
				playMusic(soundEvent);
			}

		}
	}

	@SubscribeEvent
	public static void playerTick(TickEvent.PlayerTickEvent event)
	{
		SoundEvent music = getMusic(event.player);

		if (music != null)
		{
			changeMusic(music);
		} else
		{
			stopMusic();
		}
	}

	private static SoundEvent getMusic(Entity entity)
	{
		List<EntityLivingBase> entities = entity.world.getEntitiesWithinAABB(EntityLivingBase.class, getTargetableArea(getTargetDistance(), entity));

		for (int i = 0; i < entities.size(); i++)
		{
			if (entities.get(i) instanceof IMusicMob)
			{
				return ((IMusicMob) entities.get(i)).getMusic();
			}
		}

		return null;
	}

	private static AxisAlignedBB getTargetableArea(double targetDistance, Entity entity)
	{
		return entity.getEntityBoundingBox().grow(targetDistance, 4.0D, targetDistance);
	}

	private static double getTargetDistance()
	{
		return 20.0D;
	}

	public static void stopMusic()
	{
		if (playingMusic != null)
		{
			SoundHandler soundHandler = Minecraft.getMinecraft().getSoundHandler();

			if (soundHandler.isSoundPlaying(playingMusic.getSound()))
			{
				soundHandler.stopSound(playingMusic.getSound());
			}

			playingMusic = null;
		}
	}
}