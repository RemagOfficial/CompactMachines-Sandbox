package dev.compactmods.machines.compat.ftbquests.reward;

import dev.ftb.mods.ftblibrary.icon.Icon;
import dev.ftb.mods.ftbquests.api.FTBQuestsAPI;
import dev.ftb.mods.ftbquests.quest.reward.RewardType;
import dev.ftb.mods.ftbquests.quest.reward.RewardTypes;
import net.neoforged.fml.ModList;

public class ModRewardTypes {
    private ModRewardTypes() {}

    public static RewardType SET_MAX_DEPTH;

    public static void init() {
        // check if FTB quests is loaded if so register the reward type
        if (ModList.get().isLoaded("ftbquests")) {
            SET_MAX_DEPTH = RewardTypes.register(
                    FTBQuestsAPI.rl("set_max_depth"),
                    SetMaxDepthReward::new,
                    () -> Icon.getIcon("ftblibrary:icons/up")
            );
        }
    }
}
