package ru.d78boga.dreammobs.entity.render;

import javax.annotation.Nonnull;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import ru.d78boga.dreammobs.entity.EntityGoha;
import ru.d78boga.dreammobs.util.ResourceLocator;

public class RenderGoha extends RenderLiving<EntityGoha>
{
	private ResourceLocation texture = ResourceLocator.getResourceLocation("textures/entities/goha.png");

	public RenderGoha(RenderManager manager)
	{
		super(manager, new ModelBiped(0.0F, 0.0F, 64, 64), 0.5F);
	}

	public static Factory FACTORY = new Factory();

	@Override
	@Nonnull
	protected ResourceLocation getEntityTexture(@Nonnull EntityGoha entity)
	{
		return texture;
	}

	public static class Factory implements IRenderFactory<EntityGoha>
	{
		@Override
		public Render<? super EntityGoha> createRenderFor(RenderManager manager)
		{
			return new RenderGoha(manager);
		}
	}
}
