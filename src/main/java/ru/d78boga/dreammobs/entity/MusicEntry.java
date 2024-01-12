package ru.d78boga.dreammobs.entity;

import net.minecraft.client.audio.ISound;
import net.minecraft.util.SoundEvent;

public class MusicEntry
{
	private SoundEvent soundEvent;
	private ISound sound;

	public MusicEntry(SoundEvent soundEvent, ISound sound)
	{
		this.soundEvent = soundEvent;
		this.sound = sound;
	}

	public SoundEvent getSoundEvent()
	{
		return this.soundEvent;
	}

	public ISound getSound()
	{
		return this.sound;
	}
}
