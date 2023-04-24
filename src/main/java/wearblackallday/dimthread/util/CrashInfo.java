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
package wearblackallday.dimthread.util;

import net.minecraft.CrashReport;
import net.minecraft.ReportedException;
import net.minecraft.server.level.ServerLevel;

public record CrashInfo(ServerLevel world, Throwable throwable) {

    public void crash(String title) {
        CrashReport report = CrashReport.forThrowable(this.throwable, title);
        this.world.fillReportDetails(report);
        throw new ReportedException(report);
    }
}