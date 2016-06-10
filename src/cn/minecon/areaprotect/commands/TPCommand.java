package cn.minecon.areaprotect.commands;

import cn.minecon.areaprotect.AreaProtect;
import cn.minecon.areaprotect.Config;
import cn.minecon.areaprotect.FlagManager;
import cn.minecon.areaprotect.TimedTeleport;
import cn.minecon.areaprotect.area.Area;
import com.earth2me.essentials.utils.LocationUtil;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;

public class TPCommand extends CommandSub {
    public TPCommand(AreaProtect plugin) {
        super(plugin, "tp", "areaprotect.tp", "Commands.TP");
    }

    @Override
    public boolean onCommand(CommandSender sender, String[] args) {
        // tp <area>
        if (args.length == 2) {
            final Player player = (Player) sender;
            final Area area = checkAndGetArea(player, args[1], false);
            if (!area.allowAction(player, FlagManager.TELEPORT)) {
                alert(Config.getMessage("FlagDeny", FlagManager.TELEPORT.getDescription()));
            }

            final Location loc = area.getTeleportLocation();

            final String completeMessage = Config.getMessage("TPComplete", area.getName());
            if (loc == null) {
                alert(Config.getMessage("AreaNotSetTPLocation", area.getName()));
                Location temp = new Location(area.getWorld(), (area.getHighX() + area.getLowX()) / 2,
                        64, (area.getHighZ() + area.getLowZ()) / 2);
                try {
                    temp = LocationUtil.getSafeDestination(temp);
                } catch (Exception ignored) {
                }
                player.teleport(temp, TeleportCause.COMMAND);
                player.sendMessage(completeMessage + ", ���������ô��͵�");
            }

            if (Config.getTeleportDelay() > 0 && !AreaProtect.getInstance().isAdminMode(player)) {
                player.sendMessage(Config.getMessage("TPDelay", area.getName(), Config.getTeleportDelay()));
                new TimedTeleport(plugin, player, Config.getTeleportDelay() * 1000, loc, completeMessage);
            } else {
                player.teleport(loc, TeleportCause.COMMAND);
                player.sendMessage(completeMessage);
            }
            return true;
        }
        return false;
    }
}
