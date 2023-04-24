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

import com.mojang.logging.LogUtils;
import net.minecraft.world.level.GameRules;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import wearblackallday.dimthread.mixin.IntegerValueAccessor;

public class RestrictedIntegerValue extends GameRules.IntegerValue {

    private static final Logger LOGGER = LogUtils.getLogger();

    private final int min;
    private final int max;

    public RestrictedIntegerValue(GameRules.Type<GameRules.IntegerValue> type, int def, int min, int max) {
        super(type, def);
        this.min = min;
        this.max = max;
    }

    @Override
    protected void deserialize(@NotNull String value) {
        final int i = safeParse(value);

        if (i < this.min || i > this.max) {
            return;
        }
        ((IntegerValueAccessor) this).setValue(i);
    }

    @Override
    public boolean tryDeserialize(@NotNull String input) {
        try {
            int value = Integer.parseInt(input);

            if (this.min > value || this.max < value) {
                return false;
            }

            ((IntegerValueAccessor) this).setValue(value);
            return true;
        } catch (NumberFormatException var3) {
            return false;
        }
    }

    @Override
    protected GameRules.@NotNull IntegerValue copy() {
        return new RestrictedIntegerValue(this.type, ((IntegerValueAccessor) this).getValue(), this.min, this.max);
    }

    private static int safeParse(String input) {
        if (!input.isEmpty()) {
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException var2) {
                LOGGER.warn("Failed to parse integer {}", input);
            }
        }

        return 0;
    }
}