package dev.aura.bungeechat.event;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.event.ServerSwitchEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import net.md_5.bungee.event.EventPriority;

public class BungeeChatEventsListener implements Listener {
    private final List<UUID> joinedPlayers = new LinkedList<>();

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerServerSwitch(ServerSwitchEvent e) {
        ProxiedPlayer player = e.getPlayer();
        UUID uuid = player.getUniqueId();

        if (joinedPlayers.contains(uuid)) {
            ProxyServer.getInstance().getPluginManager().callEvent(new BungeeChatServerSwitchEvent(player));
        } else {
            joinedPlayers.add(uuid);

            ProxyServer.getInstance().getPluginManager().callEvent(new BungeeChatJoinEvent(player));
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerLeave(PlayerDisconnectEvent e) {
        ProxiedPlayer player = e.getPlayer();
        UUID uuid = player.getUniqueId();

        if (!joinedPlayers.contains(uuid))
            return;

        joinedPlayers.remove(uuid);

        ProxyServer.getInstance().getPluginManager().callEvent(new BungeeChatLeaveEvent(player));
    }
}
