package com.nukateam.ntgl.mixin.ntgl.client;

import com.nukateam.ntgl.common.util.interfaces.IModelAccessor;
import net.minecraft.client.model.QuadrupedModel;
import net.minecraft.client.model.geom.ModelPart;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;

import java.util.List;

@Mixin(QuadrupedModel.class)
public class QuadrupedModelAccessor implements IModelAccessor {
    @Shadow @Final protected ModelPart head;
    @Shadow @Final protected ModelPart body;
    @Shadow @Final protected ModelPart rightHindLeg;
    @Shadow @Final protected ModelPart leftHindLeg;
    @Shadow @Final protected ModelPart rightFrontLeg;
    @Shadow @Final protected ModelPart leftFrontLeg;

    public List<ModelPart> getModelParts(){
        return List.of(head, body, rightHindLeg, leftHindLeg, rightFrontLeg, leftFrontLeg);
    }
}
