package com.nukateam.ntgl.common.debug.screen.widget;

import com.nukateam.ntgl.common.debug.IDebugWidget;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractSliderButton;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;
import java.util.function.Consumer;

public class DebugSlider extends AbstractSliderButton implements IDebugWidget {
    private final Consumer<Double> callback;
    private final double min;
    private final double max;
    private final double step;
    private final int precision;

    public DebugSlider(double minValue, double maxValue, double currentValue, double stepSize, int precision, Consumer<Double> callback) {
        super(0, 0, 0, 14, Component.empty(), maxValue == minValue ? 0 : Mth.clamp((currentValue - minValue) / (maxValue - minValue), 0, 1));
        this.min = minValue;
        this.max = maxValue;
        this.step = stepSize;
        this.precision = precision;
        this.callback = callback;
    }

    @Override
    protected void updateMessage() {
        // This widget renders only the slider handle; the editor supplies the label.
    }

    @Override
    protected void applyValue() {
        double actual = min + value * (max - min);
        if (step > 0) actual = min + Math.round((actual - min) / step) * step;
        double scale = Math.pow(10, precision);
        actual = Mth.clamp(Math.round(actual * scale) / scale, min, max);
        value = max == min ? 0 : (actual - min) / (max - min);
        callback.accept(actual);
    }

    @Override
    public void renderWidget(@NotNull GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        graphics.setColor(1.0F, 1.0F, 1.0F, 1.0F);
        int i = (this.isHoveredOrFocused() ? 2 : 1) * 20;
        graphics.blit(WIDGETS_LOCATION, this.getX() + (int) (this.value * (double) (this.width - 8)), this.getY(), 0, 46 + i, 4, this.height);
        graphics.blit(WIDGETS_LOCATION, this.getX() + (int) (this.value * (double) (this.width - 8)) + 4, this.getY(), 196, 46 + i, 4, this.height);
    }
}
