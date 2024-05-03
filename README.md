# NoTextureRotations

A Fabric mod that prevents Minecraft coordinate exploits based on texture rotation and position offsets.

Compatible with Sodium and vanilla

## What's the exploit?

Many blocks like grass, dirt, and stone have *variations* that rotate or change their textures slightly.

Texture variants are not inherently bad, they make the game visually more interesting as they prevent blocks from looking "same-y" when many are next to each other.

The problem is that the "random" number used to select the variant is seeded by the block's position in-game. No world seed is required.

Offsets works similarly, some blocks like flowers offset their position from the center of the block with a "random" offset.

Any screenshots or videos that show examples of these blocks are susceptible to an attacker cracking the block coordinates.

On anarchy servers, this can be particularly powerful - leading to bases with images or videos shared being found.

This is not a new discovery, and the method has been known since at least before 2018 and is still regularly used today
in reversing panorma seeds. Example: https://www.youtube.com/watch?v=gw9oLFTHXqw 

There are multiple public tools to perform this:
 * https://github.com/19MisterX98/TextureRotations
 * https://github.com/coolmann24/TextureFinderJava

# How the mod works

This mod can either:
1. Disable the texture rotations completely
2. Replace the random function with a secure implementation

I generally prefer the secure implementation as it will retain the normal visual feel of the game. 

