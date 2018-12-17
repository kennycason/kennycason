---
title: Tilesheet Padder
author: Kenny Cason
tags: libgdx, game development, kotlin
---

A common problem when working with Tiled Maps + LibGDX is your maps may flicker when rendering. There are multiple reasons for this with solutions of varying levels of complexity. During my development of [Ninja Turdle](http://ninjaturdle.com) I unfortunately encountered this problem and through much frustration I finally solved it through a combination of a few solutions:

1. Smart Rounding
2. Tilesheet Padding
3. Other Factors

## Smart Rounding

My initial intuition was that the flickering was caused by rounding errors. My previous games have all used integers for map positions. However, for a platformer this results in a rough, jerky feel. I decided to instead adopt a compromise and only round floats to nearest integers *IF* the float point number is within a short range of the nearest int. Through debugging I was able to see signs of float point arithmetic issues such as values such as `15.99999995`.

```kotlin
gameContext.map.locate(maybeRound(position.x), maybeRound(position.y))

...

private fun maybeRound(f: Float): Float {
    if (Math.abs(nearestInt - f) < 0.1) {
        return Math.round(f).toFloat()
    }
    return f
}
```

I would also expect that rounding to the nearest pixel would be equally successful.

## Tilesheet Padding

The above rounding trick worked pretty well, but I still had a few random errors. I also noticed that the flickers were not totally random. The flicker color was the color of a neighboring tile on the tilesheet! Ultimately I decided to add extra padding around each of my map tiles. The padding algorithm simply extends each tile's border out one pixel. The idea being that even if the tile is offset too far to any direction, the tile color will not change as it will draw whatever color we padded the tile with.

I had one further goal; I did not want my primary tilesheet to be padded as that is difficult to work with in many image editors. It's also manual work to add padding. I instead decided to create some code to take a standard, no-padding tilesheet, and automatically add the padding. Currently my Tiled maps are the only thing not being managed by the [Texture Packer](https://github.com/libgdx/libgdx/wiki/Texture-packer). If you are already using Texture Packer with Tiled Maps, then you can set [`padding` and `duplicatePadding`](https://www.reddit.com/r/libgdx/comments/2vt9r1/flicker_problem_on_tile_borders_using/) directly in the pack json file.

If you're not using the [Texture Packer](https://github.com/libgdx/libgdx/wiki/Texture-packer), then below is helper class to add padding to your Tilesheet.

<table>
<tr><td>Before</td><td>After</td></tr>
<tr><td><img src="/images/tilepadder/tiles.png" width="192px"/></td><td><img src="/images/tilepadder/tiles_padded.png" width="216px"/></td></tr>
</table>

Usage

```kotlin
val from = ImageIO.read(Thread.currentThread()
                              .contextClassLoader
                              .getResourceAsStream("sprite/tiles.png"))
val to = TileSheetPadder.pad(from, 16)
ImageIO.write(to, "png", File("src/main/resources/sprite/tiles_padded.png"))
```

The class

```kotlin
import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureRegion
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

object TileSheetPadder {

    fun pad(from: BufferedImage, dim: Int = 16): BufferedImage {
        val cols = from.width / dim
        val rows = from.height / dim

        // width + height will increase by 2 pixels per tile
        val newWidth = from.width + cols * 2
        val newHeight = from.height + rows * 2
        val to = BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB)

        (0 until cols).forEach { col ->
            (0 until rows).forEach { row ->
                write(from, col * dim, row * dim,
                        to, 1 + col * dim + col * 2, 1 + row * dim + row * 2,
                        dim)
            }
        }

        return to
    }

    private fun write(from: BufferedImage, fromX: Int, fromY: Int, to: BufferedImage, toX: Int, toY: Int, dim: Int) {
        (0 until dim).forEach { x ->
            (0 until dim).forEach { y ->
                // copy pixel to new location
                to.setRGB(toX + x, toY + y, from.getRGB(fromX + x, fromY + y))

                // handle corner padding
                if (x == 0 && y == 0) { // bottom left
                    to.setRGB(toX + x - 1, toY + y - 1, from.getRGB(fromX + x, fromY + y))
                }
                else if (x == dim - 1 && y == 0) { // bottom right
                    to.setRGB(toX + x + 1, toY + y - 1, from.getRGB(fromX + x, fromY + y))
                }
                else if (x == dim - 1 && y == dim - 1) { // top right
                    to.setRGB(toX + x + 1, toY + y + 1, from.getRGB(fromX + x, fromY + y))
                }
                else if (x == 0 && y == dim - 1) { // top left
                    to.setRGB(toX + x - 1, toY + y + 1, from.getRGB(fromX + x, fromY + y))
                }

                // handle border padding
                if (x == 0) {       // left vertical axis
                    to.setRGB(toX + x - 1, toY + y, from.getRGB(fromX + x, fromY + y))
                }
                if (x == dim - 1) { // right vertical axis
                    to.setRGB(toX + x + 1, toY + y, from.getRGB(fromX + x, fromY + y))
                }
                if (y == 0) {       // bottom horizontal axis
                    to.setRGB(toX + x, toY + y - 1, from.getRGB(fromX + x, fromY + y))
                }
                if (y == dim - 1) { // top horizontal axis
                    to.setRGB(toX + x, toY + y + 1, from.getRGB(fromX + x, fromY + y))
                }
            }
        }
    }

}
```

After you have added padding to the Tilesheet, don't forget to update your Tileset (.tsx) file to reflect padding. The fields you'll want to add are `spacing="2"` and `margin="1"`. Also don't forget to ensure the image `width` and `height` are correct.

Example from my `tiles.tsx` file.

```xml
<tileset name="tiles" tilewidth="16" tileheight="16" spacing="2" margin="1" tilecount="800">
<image source="../sprite/tiles_padded.png" width="360" height="720"/>
```

## Other Factors

1. Use power of two sized Tilesheets. I didn't learn this until I was already 100+ maps deep. In creasing the width of a Tiled Tilesheet will result in the maps being messed up as the tile ids are changed. You can safely increase the height of a Tilesheet.

2. Enable VSync in application configuration.

```java
final LwjglApplicationConfiguration configuration = new LwjglApplicationConfiguration();
configuration.vSyncEnabled = true;
```

3. Use `GL_NEAREST` when loading Texure Atlases.
