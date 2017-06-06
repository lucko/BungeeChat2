package dev.aura.bungeechat.lib.sponge;

import java.io.File;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GamePreInitializationEvent;
import org.spongepowered.api.plugin.Plugin;

import dev.aura.bungeechat.api.BungeeChatApi;
import dev.aura.bungeechat.api.account.BungeeChatAccount;
import dev.aura.bungeechat.api.enums.ChannelType;
import dev.aura.bungeechat.api.enums.Permission;
import dev.aura.bungeechat.api.enums.ServerType;
import dev.aura.bungeechat.api.placeholder.BungeeChatContext;
import dev.aura.bungeechat.api.placeholder.InvalidContextError;
import dev.aura.bungeechat.api.utils.BungeeChatInstaceHolder;
import lombok.Getter;

@Plugin(id = BungeeChatApi.ID_LIB, name = BungeeChatApi.NAME_LIB, version = BungeeChatApi.VERSION, description = BungeeChatApi.DESCRIPTION_LIB, url = BungeeChatApi.URL, authors = {
        BungeeChatApi.AUTHOR_SHAWN, BungeeChatApi.AUTHOR_BRAINSTONE })
public class BungeeChatLibSponge implements BungeeChatApi {
    @Getter
    private static BungeeChatLibSponge instance;

    private File configDir;

    @Override
    public ServerType getServerType() {
        return ServerType.SPONGE;
    }

    @Override
    public File getConfigFolder() {
        if (configDir == null) {
            configDir = new File(Sponge.getConfigManager().getSharedConfig(this).getDirectory().toFile(), "BungeeChat");
            configDir.mkdirs();
        }

        return configDir;
    }

    @Override
    public boolean hasPermission(BungeeChatAccount account, Permission permission) {
        // TODO: Get via channel messages!
        throw new UnsupportedOperationException("Not implemented!");
    }

    @Listener
    public void preInit(GamePreInitializationEvent event) {
        instance = this;
        BungeeChatInstaceHolder.setInstance(instance);
    }

    @Override
    public void sendPrivateMessage(BungeeChatContext context) throws InvalidContextError {
        // TODO: Send via channel messages!
        throw new UnsupportedOperationException("Not implemented!");
    }

    @Override
    public void sendChannelMessage(BungeeChatContext context, ChannelType chanel) throws InvalidContextError {
        // TODO: Send via channel messages!
        throw new UnsupportedOperationException("Not implemented!");
    }
}
