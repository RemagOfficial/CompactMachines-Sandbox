package dev.compactmods.machines.command;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import dev.compactmods.machines.util.PlayerDepthHelper;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;

public class DebugDepthCommand {

    public static LiteralArgumentBuilder<CommandSourceStack> make() {
        final var root = Commands.literal("debug-depth")
                .requires(cs -> cs.hasPermission(Commands.LEVEL_GAMEMASTERS));

        // /cm debug-depth set <player> <depth>
        root.then(Commands.literal("set")
                .then(Commands.argument("player", EntityArgument.player())
                        .then(Commands.argument("depth", IntegerArgumentType.integer(0))
                                .executes(DebugDepthCommand::setDepth))));

        // /cm debug-depth get <player>
        root.then(Commands.literal("get")
                .then(Commands.argument("player", EntityArgument.player())
                        .executes(DebugDepthCommand::getDepth)));

        return root;
    }

    private static int setDepth(CommandContext<CommandSourceStack> ctx) throws CommandSyntaxException {
        final var source = ctx.getSource();
        final var player = EntityArgument.getPlayer(ctx, "player");
        final int depth = IntegerArgumentType.getInteger(ctx, "depth");

        PlayerDepthHelper.setMaxDepth(player, depth);

        source.sendSuccess(() ->
                        Component.translatable("commands.rooms.compactmachines.debug_depth_set",
                                player.getDisplayName(),
                                depth
                        ),
                true
        );

        return 0;
    }

    private static int getDepth(CommandContext<CommandSourceStack> ctx) throws CommandSyntaxException {
        final var source = ctx.getSource();
        final var player = EntityArgument.getPlayer(ctx, "player");
        final int depth = PlayerDepthHelper.getMaxDepth(player);

        source.sendSuccess(() ->
                        Component.translatable("commands.rooms.compactmachines.debug_depth_get",
                                player.getDisplayName(),
                                depth
                        ),
                false
        );

        return depth;
    }
}
