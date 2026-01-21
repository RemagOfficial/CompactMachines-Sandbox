package dev.compactmods.machines.util;

import dev.compactmods.machines.api.attachment.CMDataAttachments;
import dev.compactmods.machines.api.component.CMDataComponents;
import net.minecraft.world.entity.player.Player;

public class PlayerDepthHelper {
    public static int getPlayerDepth(Player player) {
        return player.getExistingData(CMDataAttachments.CURRENT_ROOM_DEPTH).orElse(0);
    }

    public static void setPlayerDepth(Player player, int depth) {
        player.setData(CMDataAttachments.CURRENT_ROOM_DEPTH, depth);
    }

    public static void incrementPlayerDepth(Player player) {
        int current = getPlayerDepth(player);
        setPlayerDepth(player, current + 1);
    }

    public static void decrementPlayerDepth(Player player) {
        int current = getPlayerDepth(player);
        setPlayerDepth(player, Math.max(0, current - 1));
    }
}
