# NTGL for Fabric 1.20.1

An unofficial, modified Fabric port of [NukaTeamGunLib](https://github.com/Jetug/NukaTeamGunLib), based on upstream source revision `a9267bed290d7feed67bd7afb67474a56497a7e8`. Upstream credits Dayton and Jetug, and credits MrCrayfish; retained source and model credits include MrCrayfish and Team Galacticraft. This is not an official upstream release.

## License and notices

The upstream mod descriptor declares LGPL 2.1. The unchanged upstream `LICENSE.txt` contains LGPL 2.1 preceded by a Minecraft Forge-specific authorship and consent template; that template is not a claim that this port is Minecraft Forge. File-specific notices, including Team Galacticraft's MIT notices, remain in the source. Models retain their original attribution. No blanket MIT license is claimed for the mod.

## Build

Install JDK 21 and JDK 17, then:

```sh
export JAVA21_HOME=/path/to/jdk-21
export JAVA17_HOME=/path/to/jdk-17
./build.sh :ntgl:build
```

Gradle/Loom runs on JDK 21 and compiles for Java 17. The mod JAR is produced in `ports/ntgl/build/libs/`. Dependencies are resolved from the repositories in `build.gradle`; the generated resources needed by the mod are included in the source tree.
