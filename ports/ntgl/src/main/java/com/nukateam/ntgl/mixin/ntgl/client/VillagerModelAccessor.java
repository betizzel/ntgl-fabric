package com.nukateam.ntgl.mixin.ntgl.client;

import com.nukateam.ntgl.common.util.interfaces.IModelAccessor;
import net.minecraft.client.model.VillagerModel;
import net.minecraft.client.model.geom.ModelPart;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;

import java.util.List;

@Mixin(VillagerModel.class)
public class VillagerModelAccessor implements IModelAccessor {
    @Shadow @Final private ModelPart head;
    @Shadow @Final private ModelPart hat;
    @Shadow @Final private ModelPart hatRim;
    @Shadow @Final private ModelPart rightLeg;
    @Shadow @Final private ModelPart leftLeg;
    @Shadow @Final protected ModelPart nose;

    public List<ModelPart> getModelParts(){
        return List.of(head, hat, hatRim, rightLeg, leftLeg, nose);
    }
}
