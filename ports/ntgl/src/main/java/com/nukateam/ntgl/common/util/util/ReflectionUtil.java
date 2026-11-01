package com.nukateam.ntgl.common.util.util;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.TargetBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ReflectionUtil {
    private static final Method UPDATE_REDSTONE_OUTPUT = findUpdateRedstoneOutput();

    private static Method findUpdateRedstoneOutput() {
        String name = FabricLoader.getInstance().getMappingResolver().mapMethodName("intermediary",
                "net.minecraft.class_4850", "method_24858",
                "(Lnet/minecraft/class_1936;Lnet/minecraft/class_2680;Lnet/minecraft/class_3965;Lnet/minecraft/class_1297;)I");
        try {
            Method method = TargetBlock.class.getDeclaredMethod(name, LevelAccessor.class, BlockState.class,
                    BlockHitResult.class, Entity.class);
            method.setAccessible(true);
            return method;
        } catch (ReflectiveOperationException e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    public static int updateTargetBlock(TargetBlock block, LevelAccessor accessor, BlockState state, BlockHitResult result, Entity entity) {
        try {
            return (int) UPDATE_REDSTONE_OUTPUT.invoke(null, accessor, state, result, entity);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new IllegalStateException("Failed to update target block", e);
        }
    }
}
