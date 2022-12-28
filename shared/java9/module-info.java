module io.github.humbleui.skija.shared {
    exports io.github.humbleui.skija;
    exports io.github.humbleui.skija.impl;
    exports io.github.humbleui.skija.shaper;
    exports io.github.humbleui.skija.skottie;
    exports io.github.humbleui.skija.sksg;
    exports io.github.humbleui.skija.svg;
    exports io.github.humbleui.skija.paragraph;
    exports io.github.humbleui.skija.resources;

    requires static lombok;
    requires static org.jetbrains.annotations;
    requires io.github.humbleui.types;
}