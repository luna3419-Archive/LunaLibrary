package si.f5.luna3419.lib.command;

import java.util.Collections;
import java.util.List;

import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

public abstract class AbstractCommand implements CommandExecutor, TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        return Collections.singletonList("");
    }

    /**
     * コマンドの実行レベルがOPかどうかを確認します
     * @param sender コマンドの送信者
     * @return コマンドの実行レベルがOPかどうか
     */
    @SuppressWarnings("unused")
    private boolean requiresOperator(CommandSender sender) {
        return sender instanceof ConsoleCommandSender || sender instanceof BlockCommandSender || (sender instanceof Player player && player.isOp());
    }
}