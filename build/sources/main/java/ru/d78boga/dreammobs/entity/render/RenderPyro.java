package ru.d78boga.dreammobs.entity.render;

import javax.annotation.Nonnull;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import ru.d78boga.dreammobs.entity.EntityPyro;
import ru.d78boga.dreammobs.util.ResourceLocator;

public class RenderPyro extends RenderLiving<EntityPyro>
{
	private ResourceLocation texture = ResourceLocator.getResourceLocation("textures/entity/pyro.png");

	public RenderPyro(RenderManager manager)
	{
		super(manager, new ModelBiped(), 0.5F);
	}

	public static Factory FACTORY = new Factory();

	@Override
	@Nonnull
	protected ResourceLocation getEntityTexture(@Nonnull EntityPyro entity)
	{
		return texture;
	}

	public static class Factory implements IRenderFactory<EntityPyro>
	{
		@Override
		public Render<? super EntityPyro> createRenderFor(RenderManager manager)
		{
			return new RenderPyro(manager);
		}
	}
}
