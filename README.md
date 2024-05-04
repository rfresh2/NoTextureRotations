# NoTextureRotations

<p align="center">
  <a href="https://discord.gg/nJZrSaRKtb">
  <img alt="Discord" src="https://dcbadge.vercel.app/api/server/nJZrSaRKtb">
  </a>
</p>

<p align="center">
  <img src="https://img.shields.io/badge/MC-1.20.6-brightgreen.svg" alt="Minecraft"/>
  <img src="https://img.shields.io/badge/MC-1.20.4-brightgreen.svg" alt="Minecraft"/>
  <img src="https://img.shields.io/badge/MC-1.20.2-brightgreen.svg" alt="Minecraft"/>
  <img src="https://img.shields.io/badge/MC-1.20.1-brightgreen.svg" alt="Minecraft"/>
</p>


A Fabric mod that prevents Minecraft coordinate exploits based on texture rotation and position offsets.

Compatible with Vanilla MC, Sodium, and custom texture/resource packs

## What's the exploit?

Many blocks like grass, dirt, and stone have *variations* that rotate or change their textures slightly.

Texture variants are not inherently bad, they make the game visually more interesting as they prevent blocks from looking "same-y" when many are next to each other.

<p align="center">
  <img src=".github/example-texture-rotation.png" alt="Example"/>
</p>

The problem is that the "random" number used to select the variant is seeded by the block's position in-game. No world seed is required.

Offsets works similarly, some blocks like flowers offset their position from the center of the block with a "random" offset.

Any screenshots or videos that show examples of these blocks are susceptible to an attacker cracking the block coordinates.

On anarchy servers, this can be particularly powerful - leading to bases with images or videos shared being found.

This is not a new discovery, and the method has been known since at least before 2018 and is still regularly used today
in reversing panorma seeds. Example: https://youtu.be/gE1dMNCyofs?t=57 

There are multiple public tools to perform this:
 * https://github.com/19MisterX98/TextureRotations
 * https://github.com/coolmann24/TextureFinderJava

# How the mod works

This mod can either:
1. Disable the texture rotations completely
2. Replace the random function with a secure implementation

I generally prefer the secure implementation as it will retain the normal visual feel of the game. 

It is also possible to prevent rotations using a resource pack like this: https://modrinth.com/resourcepack/no-more-alternate-blocks

