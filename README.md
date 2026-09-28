# NoTextureRotations

<p align="center">
  <a href="https://discord.gg/nJZrSaRKtb">
  <img alt="Discord" src="https://dcbadge.limes.pink/api/server/nJZrSaRKtb">
  </a>
</p>

<p align="center">
  <a href=https://modrinth.com/mod/notexturerotations ><img alt="Modrinth Downloads" src="https://img.shields.io/modrinth/dt/h4ktIYQ8?style=for-the-badge&logo=modrinth&label=Modrinth&color=00AF5C"></a>
  <a href=https://legacy.curseforge.com/minecraft/mc-mods/notexturerotations ><img alt="CurseForge Downloads" src="https://cf.way2muchnoise.eu/1013466.svg?badge_style=for_the_badge"></a>
</p>

<p align="center">
  <img src="https://img.shields.io/badge/MC-26.3-brightgreen.svg" alt="Minecraft"/>
  <img src="https://img.shields.io/badge/MC-26.2-brightgreen.svg" alt="Minecraft"/>
  <img src="https://img.shields.io/badge/MC-26.1-brightgreen.svg" alt="Minecraft"/>
  <img src="https://img.shields.io/badge/MC-1.21.11-brightgreen.svg" alt="Minecraft"/>
  <img src="https://img.shields.io/badge/MC-1.21.10-brightgreen.svg" alt="Minecraft"/>
  <img src="https://img.shields.io/badge/MC-1.21.8-brightgreen.svg" alt="Minecraft"/>
  <img src="https://img.shields.io/badge/MC-1.21.5-brightgreen.svg" alt="Minecraft"/>
  <img src="https://img.shields.io/badge/MC-1.21.4-brightgreen.svg" alt="Minecraft"/>
  <img src="https://img.shields.io/badge/MC-1.21.3-brightgreen.svg" alt="Minecraft"/>
  <img src="https://img.shields.io/badge/MC-1.21.2-brightgreen.svg" alt="Minecraft"/>
  <img src="https://img.shields.io/badge/MC-1.21.1-brightgreen.svg" alt="Minecraft"/>
  <img src="https://img.shields.io/badge/MC-1.21-brightgreen.svg" alt="Minecraft"/>
  <img src="https://img.shields.io/badge/MC-1.20.6-brightgreen.svg" alt="Minecraft"/>
  <img src="https://img.shields.io/badge/MC-1.20.4-brightgreen.svg" alt="Minecraft"/>
  <img src="https://img.shields.io/badge/MC-1.20.2-brightgreen.svg" alt="Minecraft"/>
  <img src="https://img.shields.io/badge/MC-1.20.1-brightgreen.svg" alt="Minecraft"/>
  <img src="https://img.shields.io/badge/MC-1.19.4-brightgreen.svg" alt="Minecraft"/>
  <img src="https://img.shields.io/badge/MC-1.19.2-brightgreen.svg" alt="Minecraft"/>
</p>


A Fabric mod to prevent coordinate exploits based on texture rotation and position offsets. Or if you prefer how it looks!

Compatible with Vanilla MC, Sodium, and custom texture/resource packs

# How the mod works

This mod provides multiple modes to select from:

* No Rotations: All blocks have the same rotation and offset
* Random: A new random seed is generated for each block position. However, rotations/offsets will shuffle if reloaded. 
* Stable Random: Generates and remembers a random offset from the 0,0 chunk for each chunk. Offsets are re-generated each game session.
* Repeating Chunk: Use the same rotations and offsets as the chunk section at 0,0,0 for each chunk

note: a chunk section is a 16x16x16 part of a chunk. no modes are vulnerable to absolute x or z coords being cracked.

|                 | visual variation | deterministic    | security issues/notes                                                  |
|-----------------|------------------|------------------|------------------------------------------------------------------------|
| no rotations    | none             | yes              | none                                                                   |
| random          | high             | no               | none                                                                   |
| stable random   | high             | per game session | y coord and xz section coords can be cracked if many examples provided |
| repeating chunk | medium           | yes              | xyz section coords can be cracked                                      |


## Configuration

There are two ways to configure the mod in-game:
1. Install [YetAnotherConfigLib](https://modrinth.com/mod/yacl) and [ModMenu](https://modrinth.com/mod/modmenu)
2. Install [Sodium](https://modrinth.com/mod/sodium). A page for NoTextureRotation settings will be added to the Video Settings.

The configuration file is located at: `.minecraft/config/no-texture-rotations.json`

## What's the exploit?

Many blocks like grass, dirt, and stone have *variations* that rotate or change their textures slightly.

Texture variants are not inherently bad, they make the game visually more interesting as they prevent blocks from looking "same-y" when many are next to each other.

<p align="center">
  <img src=".github/example-texture-rotation.png" alt="Example"/>
</p>

The problem is that the "random" number used to select the variant is seeded by the block's position in-game. No world seed is required.

Offsets work similarly, some blocks like flowers offset their position from the center of the block with a "random" offset.

Any screenshots or videos that show examples of these blocks are susceptible to an attacker cracking the block coordinates.

On anarchy servers, this can be particularly powerful - leading to bases with images or videos shared being found.

This is not a new discovery, the method has been known since at least before 2018 and is still regularly used today
for finding panorma seeds. Example: https://youtu.be/gE1dMNCyofs?t=57

more detailed explanation: https://gitea.com/ChromeCrusher/Texploit-Guide

some examples of public coord cracking tools:
* https://github.com/19MisterX98/TextureRotations
* https://github.com/coolmann24/TextureFinderJava
* https://github.com/ALaggyDev/CoordsFinder

