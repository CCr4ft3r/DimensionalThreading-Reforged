package wearblackallday.dimthread.util;

import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.GameRules;
import wearblackallday.dimthread.init.ModGameRules;
import wearblackallday.util.ThreadPool;

import java.util.Collections;
import java.util.ConcurrentModificationException;
import java.util.Map;
import java.util.WeakHashMap;

public class ServerManager {

	private final Map<MinecraftServer, Boolean> actives = Collections.synchronizedMap(new WeakHashMap<>());
	public final Map<MinecraftServer, ThreadPool> threadPools = Collections.synchronizedMap(new WeakHashMap<>());

	public boolean isActive(MinecraftServer server) {
		return this.actives.computeIfAbsent(server, s -> s.getGameRules().getBoolean(ModGameRules.ACTIVE.getKey()));
	}

	public void setActive(MinecraftServer server, GameRules.BooleanValue value) {
		this.actives.put(server, value.get());
	}

	public ThreadPool getThreadPool(MinecraftServer server) {
		return this.threadPools.computeIfAbsent(server, s -> new ThreadPool(s.getGameRules().getInt(ModGameRules.THREAD_COUNT.getKey())));
	}

	public void setThreadCount(MinecraftServer server, GameRules.IntegerValue value) {
		ThreadPool current = this.threadPools.get(server);

		if(current.getActiveCount() != 0) {
			throw new ConcurrentModificationException("Setting the thread count in wrong phase");
		}

		this.threadPools.put(server, new ThreadPool(value.get()));
		current.shutdown();
	}
}