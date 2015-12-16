package org.csproject.model.actors;

/**
 * @author Maike Keune-Staab on 12.09.2015.
 *
 * Npc is just a random actor, with random sentences. For example an arms dealer
 */
public class Npc extends Actor {

    private String message;
    private String image;
    private int blockX;
    private int blockY;

    public Npc(String name, String image, int blockX, int blockY, String message, int x, int y) {
        super(name, "npc", x, y);
        this.image = image;
        this.blockX = blockX;
        this.blockY = blockY;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public String getImage() {
        return image;
    }

    public int getBlockX() {
        return blockX;
    }

    public int getBlockY() {
        return blockY;
    }
}

