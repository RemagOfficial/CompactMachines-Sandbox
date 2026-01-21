package dev.compactmods.machines.util;

import dev.compactmods.machines.api.CompactMachines;
import dev.compactmods.machines.api.attachment.CMDataAttachments;
import dev.compactmods.machines.common.config.CommonConfig;
import dev.compactmods.machines.network.room.SyncRoomMetadataPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.UUID;

public class PlayerDepthHelper {
    public static int getPlayerDepth(Player player) {
        return player.getExistingData(CMDataAttachments.CURRENT_ROOM_DEPTH).orElse(0);
    }

    public static void setPlayerDepth(Player player, int depth) {
        player.setData(CMDataAttachments.CURRENT_ROOM_DEPTH, depth);

        if (!player.level().isClientSide && player instanceof ServerPlayer serverPlayer) {
            String roomCode = player.getExistingData(CMDataAttachments.CURRENT_ROOM_CODE).orElse("");
            UUID owner = getRoomOwner(serverPlayer, roomCode);
            PacketDistributor.sendToPlayer(serverPlayer,new SyncRoomMetadataPacket(roomCode, depth, owner));
        }
    }

    public static void incrementPlayerDepth(Player player) {
        int current = getPlayerDepth(player);
        setPlayerDepth(player, current + 1);
    }

    public static void decrementPlayerDepth(Player player) {
        int current = getPlayerDepth(player);
        setPlayerDepth(player, Math.max(0, current - 1));
    }

    public static int getMaxDepth(Player player) {
        return player.getExistingData(CMDataAttachments.MAX_ROOM_DEPTH).orElse(0);
    }

    public static void setMaxDepth(Player player, int depth) {
        player.setData(CMDataAttachments.MAX_ROOM_DEPTH, depth);
    }

    public static void incrementMaxDepth(Player player) {
        int current = getMaxDepth(player);
        setMaxDepth(player, current + 1);
    }

    public static void decrementMaxDepth(Player player) {
        int current = getMaxDepth(player);
        setMaxDepth(player, Math.max(0, current - 1));
    }


    private static UUID getRoomOwner(ServerPlayer player, String roomCode) {
        // First try to get the room owner from the room data
        var room = CompactMachines.room(player.server, roomCode).orElse(null);
        if (room != null) {
            return room.getExistingDataOrNull(CMDataAttachments.ROOM_OWNER);
        }
        // Fall back to player's UUID if room not found
        return player.getUUID();
    }

    public static boolean canEnterRoom(Player player, int targetRoomDepth) {
        // If depth checks are disabled, always allow entry
        if (CommonConfig.DISABLE_DEPTH_CHECKS.get()) {
            return true;
        }

        int playerMaxDepth = getMaxDepth(player);
        return targetRoomDepth <= playerMaxDepth || player.isCreative();
    }
}
