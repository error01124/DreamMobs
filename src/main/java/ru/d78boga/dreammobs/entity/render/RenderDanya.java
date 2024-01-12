package ru.d78boga.dreammobs.entity.render;

import javax.annotation.Nonnull;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import ru.d78boga.dreammobs.entity.EntityDanya;
import ru.d78boga.dreammobs.util.ResourceLocator;

public class RenderDanya extends RenderLiving<EntityDanya>
{
	private ResourceLocation texture = ResourceLocator.getResourceLocation("textures/entities/danya.png");

	public RenderDanya(RenderManager manager)
	{
		super(manager, new ModelBiped(0.0F, 0.0F, 64, 64), 0.5F);
	}

	public static Factory FACTORY = new Factory();

	@Override
	protected ResourceLocation getEntityTexture(@Nonnull EntityDanya entity)
	{
		return texture;
	}

	public static class Factory implements IRenderFactory<EntityDanya>
	{
		@Override
		public Render<? super EntityDanya> createRenderFor(RenderManager manager)
		{
			return new RenderDanya(manager);
		}
	}

}
