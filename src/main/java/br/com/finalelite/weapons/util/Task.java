package br.com.finalelite.weapons.util;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

/**
 *
 * @author Willian Gois (github/willgoix)
 */
public class Task {

    private BukkitRunnable handle;
    private Consumer<Task> callback;
    private long repeat = -1;   // In ticks
    private long delay  = 0;    // In ticks
    private boolean async;

    private long started_at;

    public static Task execute(Runnable callback) {
        return execute(task -> callback.run());
    }

    public static Task execute(Consumer<Task> callback) {
        return new Task(callback);
    }

    public Task(Consumer<Task> callback) {
        this.callback = callback;
    }

    public Task async() {
        async = true;
        return this;
    }

    public Task repeat(long interval, TimeUnit unit) {
        this.repeat = unit.toMillis(interval);
        return this;
    }

    public Task repeat(long ticks) {
        this.repeat = ticks;
        return this;
    }

    public Task delay(long delay, TimeUnit unit) {
        this.delay = unit.toMillis(delay);
        return this;
    }

    public Task delay(long ticks) {
        this.delay = ticks;
        return this;
    }
    
    public Task run(Class<? extends JavaPlugin> pluginClass) {
        return run(JavaPlugin.getProvidingPlugin(pluginClass));
    }

    public Task run(Plugin plugin) {
        if (handle != null) {
            throw new IllegalStateException("Task já iniciada.");
        }
        handle = new BukkitRunnable() {
            @Override
            public void run() {
                callback.accept(Task.this);
            }
        };
        if (async) {
            if (repeat == -1) {
                handle.runTaskLaterAsynchronously(plugin, delay);
            } else {
                handle.runTaskTimerAsynchronously(plugin, delay, repeat);
            }
        } else {
            if (repeat == -1) {
                handle.runTaskLater(plugin, delay);
            } else {
                handle.runTaskTimer(plugin, delay, repeat);
            }
        }
        started_at = System.currentTimeMillis();
        return this;
    }

    public void cancel() {
        if (handle == null) {
            throw new IllegalStateException("Task ainda nao foi iniciada.");
        }
        handle.cancel();
    }

    public long getStartedAt() {
        return started_at;
    }
}
