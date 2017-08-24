package kiet.nguyentuan.mysteriouspyramids.stage;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by nguye on 8/23/2017.
 */

public interface IMapGenerator {
    public MapBlockType[][] createMap(int width, int height);
    public MapBlockType[][] createMap(String fileName);
}
