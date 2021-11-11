package sk.tuke.kpi.oop.game;

public enum Direction {
    NORTH (0, 1),
    EAST (1, 0),
    SOUTH (0, -1),
    WEST (-1, 0),
    NORTHEAST (1, 1),
    NORTHWEST (-1,1),
    SOUTHEAST(1, -1),
    SOUTHWEST(-1, -1),
    NONE(0, 0);


    private final int dx;
    private final int dy;

    Direction(int dx, int dy){
        this.dx = dx;
        this.dy = dy;
    }

    public int getDx(){
        return this.dx;
    }

    public int getDy(){
        return this.dy;
    }

    public float getAngle(){
        if(this.dx == 0 && this.dy == 1) return 0;
        else if(this.dx == 1 && this.dy == 0) return 270;
        else if(this.dx == 0 && this.dy == -1) return 180;
        else if(this.dx == -1 && this.dy == 0) return 90;
        else if(this.dx == 1 && this.dy == 1) return 315;
        else if(this.dx == -1 && this.dy == 1) return 45;
        else if(this.dx == 1 && this.dy == -1) return 225;
        else if(this.dx == -1 && this.dy == -1) return 135;
        else return 0;
    }
}