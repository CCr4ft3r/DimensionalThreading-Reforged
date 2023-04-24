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
package wearblackallday.dimthread.init;

import net.minecraft.world.level.GameRules;
import wearblackallday.dimthread.DimThread;
import wearblackallday.dimthread.gamerule.BoolRule;
import wearblackallday.dimthread.gamerule.IntRule;

public class ModGameRules {

	public static BoolRule ACTIVE;
	public static IntRule THREAD_COUNT;

	public static void registerGameRules() {
		ACTIVE = BoolRule.builder("active", GameRules.Category.UPDATES).setInitial(true)
				.setCallback(DimThread.MANAGER::setActive).build();

		THREAD_COUNT = IntRule.builder("thread_count", GameRules.Category.UPDATES).setInitial(3)
				.setBounds(1, Runtime.getRuntime().availableProcessors()).setCallback(DimThread.MANAGER::setThreadCount).build();
	}

}