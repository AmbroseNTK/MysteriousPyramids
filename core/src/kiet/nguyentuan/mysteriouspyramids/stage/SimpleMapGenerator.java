package kiet.nguyentuan.mysteriouspyramids.stage;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by nguye on 8/23/2017.
 */

public class SimpleMapGenerator implements IMapGenerator {

    public SimpleMapGenerator(){

    }
    @Override
    public MapBlockType[][] createMap(int width, int height) {
        MapBlockType[][] map=new MapBlockType[width][height];
        map[0][0]=MapBlockType.WALL;
        map[0][1]=MapBlockType.GROUND;
        map[0][2]=MapBlockType.WALL;
        map[1][0]= MapBlockType.WALL;
        map[1][1]=MapBlockType.GROUND;
        map[1][2]=MapBlockType.WALL;
        map[2][0]=MapBlockType.GROUND;
        map[2][1]=MapBlockType.GROUND;
        map[2][2]=MapBlockType.GROUND;
        map[3][1]=MapBlockType.GROUND;
        map[3][0]=MapBlockType.WALL;
        map[3][2]=MapBlockType.WALL;
        return map;
    }

    @Override
    public MapBlockType[][] createMap(String fileName) {
        return new MapBlockType[0][];
    }

}
