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

import net.minecraft.world.level.GameRules;
import wearblackallday.dimthread.DimThread;

public abstract class GameRule<T extends GameRules.Value<T>> {

	private final GameRules.Key<T> key;
	private final GameRules.Type<T> rule;

	public GameRule(String name, GameRules.Category category, GameRules.Type<T> rule) {
		this.key = GameRules.register(DimThread.MOD_ID + "_" + name, category, rule);
		this.rule = rule;
	}

	public GameRules.Key<T> getKey() {
		return this.key;
	}

	@SuppressWarnings("unused")
	public GameRules.Type<T> getRule() {
		return this.rule;
	}

}