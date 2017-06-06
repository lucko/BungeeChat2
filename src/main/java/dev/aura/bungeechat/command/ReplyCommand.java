package dev.aura.bungeechat.command;

import java.util.HashMap;
import java.util.Optional;

import dev.aura.bungeechat.account.BungeecordAccountManager;
import dev.aura.bungeechat.api.account.BungeeChatAccount;
import dev.aura.bungeechat.api.enums.Permission;
import dev.aura.bungeechat.message.Message;
import dev.aura.bungeechat.message.MessagesService;
import dev.aura.bungeechat.module.MessengerModule;
import dev.aura.bungeechat.permission.PermissionManager;
import net.md_5.bungee.api.CommandSender;

public class ReplyCommand extends BaseCommand {
    private static HashMap<CommandSender, CommandSender> replies;

    public ReplyCommand(MessengerModule messengerModule) {
        super("reply", messengerModule.getModuleSection().getStringList("aliases.reply"));
        replies = new HashMap<>();
    }

    public static void setReply(CommandSender sender, CommandSender target) {
        replies.put(sender, target);
        replies.put(target, sender);
    }

    public static CommandSender getReplier(CommandSender player) {
        return replies.getOrDefault(player, null);
    }

    @Override
    @SuppressWarnings("deprecation")
    public void execute(CommandSender sender, String[] args) {
        if (PermissionManager.hasPermission(sender, Permission.COMMAND_MESSAGE)) {
            if (args.length < 1) {
                sender.sendMessage(Message.INCORRECT_USAGE.get(sender, "/reply <message>"));
            } else {
                Optional<BungeeChatAccount> targetAccount = BungeecordAccountManager.getAccount(getReplier(sender));

                if (!targetAccount.isPresent() || (targetAccount.get().isVanished()
                        && !PermissionManager.hasPermission(sender, Permission.COMMAND_VANISH_SEE))) {
                    sender.sendMessage(Message.NO_REPLY.get());
                    return;
                }

                CommandSender target = BungeecordAccountManager.getCommandSender(targetAccount.get()).get();

                if (!targetAccount.get().hasMessangerEnabled()
                        && !PermissionManager.hasPermission(sender, Permission.COMMAND_TOGGLE_MESSAGE_BYPASS)) {
                    sender.sendMessage(Message.HAS_MESSAGER_DISABLED.get(target));
                    return;
                }
                if (targetAccount.get().hasIgnored(BungeecordAccountManager.getAccount(sender).get())
                        && !PermissionManager.hasPermission(sender, Permission.COMMAND_IGNORE_BYPASS)) {
                    sender.sendMessage(Message.HAS_INGORED.get());
                    return;
                }

                StringBuilder stringBuilder = new StringBuilder();

                for (String arg : args) {
                    stringBuilder.append(arg).append(" ");
                }

                MessagesService.sendPrivateMessage(sender, target, stringBuilder.toString().trim());
                ReplyCommand.setReply(sender, target);
            }
        }
    }
}
