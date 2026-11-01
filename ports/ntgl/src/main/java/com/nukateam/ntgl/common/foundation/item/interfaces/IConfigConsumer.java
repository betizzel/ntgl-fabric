package com.nukateam.ntgl.common.foundation.item.interfaces;

import com.nukateam.ntgl.modules.datapack.ConfigSupplier;

public interface IConfigConsumer<T> {
    void setConfig(ConfigSupplier<T> supplier);
}
