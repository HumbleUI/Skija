package io.github.humbleui.skija.impl;

import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    UNKNOWN;

    public static final Architecture CURRENT;

    static {
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
                CURRENT = X64;
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
                CURRENT = X86;
                break;
            case "arm64":
            case "aarch64":
                CURRENT = ARM64;
                break;
            case "arm":
            case "arm32":
                CURRENT = ARM32;
                break;
            case "mips64":
                CURRENT = littleEndian ? MIPS64EL : MIPS64;
                break;
            case "mips64el":
                CURRENT = MIPS64EL;
                break;
            case "mips":
            case "mips32":
                CURRENT = littleEndian ? MIPSEL : MIPS;
                break;
            case "mipsel":
            case "mips32el":
                CURRENT = MIPSEL;
                break;
            case "riscv":
            case "risc-v":
            case "riscv64":
                CURRENT = RISCV64;
                break;
            case "ia64":
            case "ia64w":
            case "itanium64":
                CURRENT = IA64;
                break;
            case "sparcv9":
            case "sparc64":
                CURRENT = SPARCV9;
                break;
            case "sparc":
            case "sparc32":
                CURRENT = SPARC;
                break;
            case "ppc64":
            case "powerpc64":
                CURRENT = littleEndian ? PPC64LE : PPC64;
                break;
            case "ppc64le":
            case "powerpc64le":
                CURRENT = PPC64LE;
                break;
            case "ppc":
            case "ppc32":
            case "powerpc":
            case "powerpc32":
                CURRENT = littleEndian ? PPCLE : PPC;
                break;
            case "ppcle":
            case "ppc32le":
            case "powerpcle":
            case "powerpc32le":
                CURRENT = PPCLE;
                break;
            case "s390":
                CURRENT = S390;
                break;
            case "s390x":
                CURRENT = S390X;
                break;
            case "loong32":
            case "loongarch32":
                CURRENT = LOONGARCH32;
                break;
            case "loong64":
            case "loongarch64":
                boolean oldWorld = false;
                if (OperatingSystem.CURRENT == OperatingSystem.LINUX) {
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
                CURRENT = oldWorld ? LOONGARCH64_OW : LOONGARCH64;
                break;
            default:
                if (arch.startsWith("armv7")) {
                    CURRENT = ARM32;
                } else if (arch.startsWith("armv8") || arch.startsWith("armv9")) {
                    CURRENT = ARM64;
                } else {
                    CURRENT = UNKNOWN;
                }
        }
    }
}
