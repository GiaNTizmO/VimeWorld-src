package net.minecraft.client.renderer;

import java.awt.image.BufferedImage;

public class LegacyCapeTransformer implements IImageBuffer
{
    public BufferedImage parseUserSkin(BufferedImage image)
    {
        if (image != null && image.getWidth() != 64 && image.getHeight() != 32)
        {
            BufferedImage bufferedimage = new BufferedImage(64, 32, 2);
            int i = image.getWidth();
            int j = image.getHeight();
            int[] aint = image.getRGB(0, 0, i, j, (int[])null, 0, i);
            bufferedimage.setRGB(0, 0, i, j, aint, 0, i);
            image = bufferedimage;
        }

        return image;
    }

    public void skinAvailable()
    {
    }
}
