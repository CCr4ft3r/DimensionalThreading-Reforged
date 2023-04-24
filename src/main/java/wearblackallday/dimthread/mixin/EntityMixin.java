/*
 * This file is part of Dimensional Threading Reforged - https://github.com/CCr4ft3r/DimensionalThreading-Reforged
 * Copyright (C) WearBlackAllDay and contributors: https://github.com/WearBlackAllDay/DimensionalThreading
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package wearblackallday.dimthread.mixin;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.common.util.ITeleporter;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import wearblackallday.dimthread.DimThread;

@Mixin(Entity.class)
public abstract class EntityMixin {

    @Shadow
    public abstract Entity changeDimension(ServerLevel destination);

    /**
     * Schedules moving entities between dimensions to the server thread. Once all the world finish ticking,
     * {@code moveToWorld()} is processed in a safe manner avoiding concurrent modification exceptions.
     * <p>
     * For example, the entity list is not thread-safe and modifying it from multiple threads will cause
     * a crash. Additionally, loading chunks from another thread will cause a deadlock in the server chunk manager.
     */
    @Inject(method = "changeDimension(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraftforge/common/util/ITeleporter;)Lnet/minecraft/world/entity/Entity;", at = @At("HEAD"), cancellable = true, remap = false)
    public void moveToWorld(ServerLevel destination, ITeleporter teleporter, CallbackInfoReturnable<Entity> cir) {
        if (!DimThread.MANAGER.isActive(destination.getServer())) return;

        if (DimThread.owns(Thread.currentThread())) {
            destination.getServer().execute(() -> this.changeDimension(destination));
            cir.setReturnValue(null);
        }
    }
}