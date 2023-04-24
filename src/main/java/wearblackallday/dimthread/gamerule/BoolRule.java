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
package wearblackallday.dimthread.gamerule;

import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.GameRules;

import java.util.function.BiConsumer;

public class BoolRule extends GameRule<GameRules.BooleanValue> {

    protected BoolRule(String name, GameRules.Category category, GameRules.Type<GameRules.BooleanValue> rule) {
        super(name, category, rule);
    }

    public static Builder builder(String name, GameRules.Category category) {
        return new Builder(name, category);
    }

    public static class Builder {
        private final String name;
        private final GameRules.Category category;

        private boolean initialValue = false;
        private BiConsumer<MinecraftServer, GameRules.BooleanValue> callback = (server, rule) -> {
        };

        private Builder(String name, GameRules.Category category) {
            this.name = name;
            this.category = category;
        }

        public Builder setInitial(boolean initial) {
            this.initialValue = initial;
            return this;
        }

        public Builder setCallback(BiConsumer<MinecraftServer, GameRules.BooleanValue> callback) {
            this.callback = callback;
            return this;
        }

        public BoolRule build() {
            return new BoolRule(this.name, this.category, GameRules.BooleanValue.create(this.initialValue, this.callback));
        }
    }
}