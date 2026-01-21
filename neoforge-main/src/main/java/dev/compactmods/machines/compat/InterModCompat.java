package dev.compactmods.machines.compat;

import dev.compactmods.machines.compat.curios.CuriosCompat;
import dev.compactmods.machines.compat.ftbquests.reward.ModRewardTypes;
import net.neoforged.fml.ModList;
import net.neoforged.fml.event.lifecycle.InterModEnqueueEvent;

public class InterModCompat {

    public static void enqueueCompatMessages(final InterModEnqueueEvent ignoredEvt) {
//        if(ModList.get().isLoaded("theoneprobe"))
//            TheOneProbeCompat.sendIMC();
//
//        if(ModList.get().isLoaded("carryon"))
//            CarryOnCompat.sendIMC();

        if(ModList.get().isLoaded("curios"))
            CuriosCompat.register();
            
        // Initialize FTB Quests integration if the mod is loaded
        if(ModList.get().isLoaded("ftbquests"))
            ModRewardTypes.init();
    }
}
