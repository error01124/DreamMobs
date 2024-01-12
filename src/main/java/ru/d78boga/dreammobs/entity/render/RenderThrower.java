package ru.d78boga.dreammobs.entity.render;

import javax.annotation.Nonnull;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import ru.d78boga.dreammobs.entity.EntityThrower;
import ru.d78boga.dreammobs.entity.render.model.ModelThrower;
import ru.d78boga.dreammobs.util.ResourceLocator;

public class RenderThrower extends RenderLiving<EntityThrower>
{
	private ResourceLocation texture = ResourceLocator.getResourceLocation("textures/entities/thrower.png");

	public RenderThrower(RenderManager manager)
	{
		super(manager, new ModelThrower(), 0.5F);
	}

	public static Factory FACTORY = new Factory();

	@Override
	@Nonnull
	protected ResourceLocation getEntityTexture(@Nonnull EntityThrower entity)
	{
		return texture;
	}

	public static class Factory implements IRenderFactory<EntityThrower>
	{
		@Override
		public Render<? super EntityThrower> createRenderFor(RenderManager manager)
		{
			return new RenderThrower(manager);
		}
	}
}
