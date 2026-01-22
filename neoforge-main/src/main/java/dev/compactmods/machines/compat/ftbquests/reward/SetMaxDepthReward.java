package dev.compactmods.machines.compat.ftbquests.reward;

import dev.compactmods.machines.util.PlayerDepthHelper;
import dev.ftb.mods.ftblibrary.config.ConfigGroup;
import dev.ftb.mods.ftbquests.quest.Quest;
import dev.ftb.mods.ftbquests.quest.reward.Reward;
import dev.ftb.mods.ftbquests.quest.reward.RewardType;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

public class SetMaxDepthReward extends Reward {

    private int depth = 1;
    
    public SetMaxDepthReward(long id, Quest quest) {
        super(id, quest);
    }

    public SetMaxDepthReward(long id, Quest quest, int depth) {
        super(id, quest);
        this.depth = depth;
    }

    @Override
    public RewardType getType() {
        return ModRewardTypes.SET_MAX_DEPTH;
    }

    @Override
    public void writeData(CompoundTag nbt, HolderLookup.Provider provider) {
        super.writeData(nbt, provider);
        nbt.putInt("depth", depth);
    }
    
    @Override
    public void readData(CompoundTag nbt, HolderLookup.Provider provider) {
        super.readData(nbt, provider);
        depth = nbt.getInt("depth");
    }
    
    @Override
    public void writeNetData(RegistryFriendlyByteBuf buffer) {
        super.writeNetData(buffer);
        buffer.writeVarInt(depth);
    }
    
    @Override
    public void readNetData(RegistryFriendlyByteBuf buffer) {
        super.readNetData(buffer);
        depth = buffer.readVarInt();
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void fillConfigGroup(ConfigGroup config)  {
        super.fillConfigGroup(config);
        config.addInt("depth", depth, v -> depth = v, 1, 0, Integer.MAX_VALUE)
                .setNameKey("compactmachines.ftbquests.reward.set_max_depth.depth");
    }
    
    @Override
    public void claim(ServerPlayer player, boolean notify) {
        PlayerDepthHelper.setMaxDepth(player, depth);
        if (notify) {
            player.sendSystemMessage(Component.translatable(
                    "compactmachines.ftbquests.reward.set_max_depth.message",
                    player.getDisplayName(),
                    depth
            ));
        }
    }
    
    @Override
    public MutableComponent getAltTitle() {
        return Component.translatable("compactmachines.ftbquests.reward.set_max_depth.title", depth);
    }
    
    @Override
    public String getButtonText() {
        return "+" + depth;
    }
}
