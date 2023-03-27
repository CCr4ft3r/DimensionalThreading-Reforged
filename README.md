# Dimensional Threading Reforged (Unofficial)

This project is a free and open-source Minecraft mod which optimizes the processing of multiple Dimensions, by assigning them independent threads.
It works on both the **client and server**, and **does not** require the mod to be installed on both sides.

### Important notes:
- This is an unofficial port of the fabric mod Dimensional Threading. The original copyright owner is WearBlackAllDay.
- This port is not going to fix the issues that the fabric version has (unless they are being fixed it beforehand in the fabric version)
- This mod might probably have some incompatibilities with other mods. Most of the time, it can only be fixed by the specific mods (making their code and used data structures thread-safe).

### Requests:
- Please do not report issues with this port to the original owner or to their GitHub repo.
- Please do not join their discord or post a comment on their mod page in order to get support for my port.

### Changes:
This port currently does not change any of the original implementations. On code side the only differences are the following ones:

- I adapted the class references and mixin injection points to match it to SRG and Forge

This port should be fully compatible with Rubidium, Immersive Portals and Oculus (at least no crashes/issues occured while testing it). 
Optifine is not going to be officially supported.

---

### Configuration

##### DimThread comes with 2 native Gamerules:
- `/gamerule dimthread_active <true/false>` enables/disables the mod
- `/gamerule dimthread_thread_count <count>`changes the amount of threads used

### FAQ

##### Does the mod change Vanilla behaviour?
DimThread aims to conserve vanilla-parity in all points. At the moment there are no known deviations in behaviour from Mojangs`server.jar`. If you notice any, feel free to [open an issue.](https://github.com/CCr4ft3r/DimensionalThreading-Reforged/issues)

##### Can my dimensions get de-synchronized?
As stated above this is **NOT** the case. DimThread will always synchronize the dimensions it threads with each other, setting the overall MSPT to the slowest individual dimension.

##### Are dimension counts above 3 supported?
Yes, but you will have to adjust the GameRule accordingly.

##### Will the server run faster if assign more than 3 threads to DimThread?
No, the mod can only assign one dimension to one thread.

##### Can i use DimThread if i have less threads on my CPU than dimensions?
The mod will not crash if you do not have enough threads available, but it will make the game **slower**. You should always have atleast`DimensionCount + 1`threads available.

##### How is the compatibility with other mods?
Compatibility with [JellySquids](https://github.com/jellysquid3) performance mods and [Carpet](https://github.com/gnembon/fabric-carpet) will be ensured and issues concerning them are accepted. If you plan on using a different mod compatibility cannot be guaranteed, since not every author writes their mod threadsafe.

##### What about older versions of Minecraft?
I'm not going to backport this mod to versions before 1.18.2. But every other developer is permitted to do so under the conditions of the license.

---