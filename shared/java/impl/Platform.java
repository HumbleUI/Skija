package io.github.humbleui.skija.impl;

import lombok.*;
import org.jetbrains.annotations.*;

import java.util.*;
import java.util.regex.*;

public final class Platform {

    public static final Platform CURRENT;

    static {
        OperatingSystem operatingSystem;
        Architecture architecture;

        String os = System.getProperty("os.name").toLowerCase(Locale.ROOT);
        if (os.contains("mac") || os.contains("darwin")) {
            operatingSystem = OperatingSystem.MACOS;
        } else if (os.contains("windows")) {
            operatingSystem = OperatingSystem.WINDOWS;
        } else if (os.contains("nux") || os.contains("nix")) {
            operatingSystem = OperatingSystem.LINUX;
        } else {
            operatingSystem = OperatingSystem.UNKNOWN;
        }

        String arch = System.getProperty("os.arch").toLowerCase(Locale.ROOT);
        boolean littleEndian = "little".equalsIgnoreCase(System.getProperty("sun.cpu.endian"));
        switch (arch) {
            case "x8664":
            case "x86-64":
            case "x86_64":
            case "amd64":
            case "ia32e":
            case "em64t":
            case "x64":
                architecture = Architecture.X64;
                break;
            case "x8632":
            case "x86-32":
            case "x86_32":
            case "x86":
            case "i86pc":
            case "i386":
            case "i486":
            case "i586":
            case "i686":
            case "ia32":
            case "x32":
                architecture = Architecture.X86;
                break;
            case "arm64":
            case "aarch64":
                architecture = Architecture.ARM64;
                break;
            case "arm":
            case "arm32":
                architecture = Architecture.ARM32;
                break;
            case "mips64":
                architecture = littleEndian ? Architecture.MIPS64EL : Architecture.MIPS64;
                break;
            case "mips64el":
                architecture = Architecture.MIPS64EL;
                break;
            case "mips":
            case "mips32":
                architecture = littleEndian ? Architecture.MIPSEL : Architecture.MIPS;
                break;
            case "mipsel":
            case "mips32el":
                architecture = Architecture.MIPSEL;
                break;
            case "riscv":
            case "risc-v":
            case "riscv64":
                architecture = Architecture.RISCV64;
                break;
            case "ia64":
            case "ia64w":
            case "itanium64":
                architecture = Architecture.IA64;
                break;
            case "sparcv9":
            case "sparc64":
                architecture = Architecture.SPARCV9;
                break;
            case "sparc":
            case "sparc32":
                architecture = Architecture.SPARC;
                break;
            case "ppc64":
            case "powerpc64":
                architecture = littleEndian ? Architecture.PPC64LE : Architecture.PPC64;
                break;
            case "ppc64le":
            case "powerpc64le":
                architecture = Architecture.PPC64LE;
                break;
            case "ppc":
            case "ppc32":
            case "powerpc":
            case "powerpc32":
                architecture = littleEndian ? Architecture.PPCLE : Architecture.PPC;
                break;
            case "ppcle":
            case "ppc32le":
            case "powerpcle":
            case "powerpc32le":
                architecture = Architecture.PPCLE;
                break;
            case "s390":
                architecture = Architecture.S390;
                break;
            case "s390x":
                architecture = Architecture.S390X;
                break;
            case "loong32":
            case "loongarch32":
                architecture = Architecture.LOONGARCH32;
                break;
            case "loong64":
            case "loongarch64":
                boolean oldWorld = false;
                if (operatingSystem == OperatingSystem.LINUX) {
                    String osVersion = System.getProperty("os.version");
                    if (osVersion.startsWith("4.")) {
                        oldWorld = true;
                    } else if (osVersion.startsWith("5.")) {
                        Matcher matcher = Pattern.compile("^5\\.(?<minor>[0-9]+)").matcher(osVersion);
                        if (matcher.find() && Integer.parseInt(matcher.group("minor")) < 19) {
                            oldWorld = true;
                        }
                    }
                }
                architecture = oldWorld ? Architecture.LOONGARCH64_OW : Architecture.LOONGARCH64;
                break;
            default:
                if (arch.startsWith("armv7")) {
                    architecture = Architecture.ARM32;
                } else if (arch.startsWith("armv8") || arch.startsWith("armv9")) {
                    architecture = Architecture.ARM64;
                } else {
                    architecture = Architecture.UNKNOWN;
                }
        }
        CURRENT = new Platform(operatingSystem, architecture);
    }

    @Getter
    public final OperatingSystem _operatingSystem;
    @Getter
    public final Architecture _architecture;

    public Platform(@NotNull OperatingSystem operatingSystem, @NotNull Architecture architecture) {
        assert operatingSystem != null : "OperatingSystem == null";
        assert architecture != null : "Architecture == null";

        this._operatingSystem = operatingSystem;
        this._architecture = architecture;
    }

    public enum OperatingSystem {
        WINDOWS,
        LINUX,
        MACOS,
        UNKNOWN
    }

    public enum Architecture {
        X86,
        X64,
        IA64,
        ARM32,
        ARM64,
        SPARC,
        SPARCV9,
        MIPS,
        MIPS64,
        MIPSEL,
        MIPS64EL,
        PPC,
        PPC64,
        PPCLE,
        PPC64LE,
        S390,
        S390X,
        RISCV32,
        RISCV64,
        LOONGARCH32,
        LOONGARCH64,
        LOONGARCH64_OW,
        UNKNOWN
    }
}
