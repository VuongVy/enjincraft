package com.enjin.enjincraft.spigot.cmd;

import com.enjin.enjincraft.spigot.SpigotBootstrap;
import com.enjin.enjincraft.spigot.enums.Permission;
import com.enjin.enjincraft.spigot.i18n.Translation;
import com.enjin.enjincraft.spigot.player.EnjPlayer;
import com.enjin.enjincraft.spigot.util.TokenUtils;
import com.enjin.sdk.model.service.identities.DeleteIdentity;
import com.enjin.java_commons.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.io.IOException;

public class CmdUnlink extends EnjCommand {

    public CmdUnlink(SpigotBootstrap bootstrap, EnjCommand parent) {
        super(bootstrap, parent);
        this.aliases.add("unlink");
        this.requirements = CommandRequirements.builder()
                .withAllowedSenderTypes(SenderType.PLAYER)
                .withPermission(Permission.CMD_UNLINK)
                .build();
    }

    @Override
    public void execute(CommandContext context) {
        Player sender = context.player;
        EnjPlayer enjPlayer = context.enjPlayer;

        if (!enjPlayer.isLoaded()) {
            Translation.IDENTITY_NOTLOADED.send(sender);
            return;
        }

        if (!enjPlayer.isLinked()) {
            Translation.WALLET_NOTLINKED_SELF.send(sender);
            return;
        }

        Bukkit.getScheduler().runTaskAsynchronously(bootstrap.plugin(), () -> {
            try {
                enjPlayer.unlink();
            } catch (Exception ex) {
                bootstrap.log(ex);
                Translation.ERRORS_EXCEPTION.send(sender, ex.getMessage());
            }
        });
    }
    @Override
    public Translation getUsageTranslation() {
        return Translation.COMMAND_UNLINK_DESCRIPTION;
    }

}
