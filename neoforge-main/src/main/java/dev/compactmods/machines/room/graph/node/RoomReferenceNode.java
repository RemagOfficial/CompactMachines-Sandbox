package dev.compactmods.machines.room.graph.node;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.compactmods.feather.node.Node;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

/**
 * Represents the inside of a Compact Machine.
 */
public record RoomReferenceNode(UUID id, String code, int depth) implements Node<String> {

    public static final Codec<RoomReferenceNode> CODEC = RecordCodecBuilder.create(i -> i.group(
            Codec.STRING.fieldOf("code").forGetter(RoomReferenceNode::code),
            Codec.INT.fieldOf("depth").forGetter(RoomReferenceNode::depth)
    ).apply(i, RoomReferenceNode::new));

    public RoomReferenceNode(String code, int roomDepth) {
        this(UUID.randomUUID(), code, roomDepth);
    }

    public @NotNull Codec<RoomReferenceNode> codec() {
        return CODEC;
    }

    @Override
    public String data() {
        return code;
    }
}
