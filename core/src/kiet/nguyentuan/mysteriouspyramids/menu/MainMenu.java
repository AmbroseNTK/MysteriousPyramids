package kiet.nguyentuan.mysteriouspyramids.menu;

import com.badlogic.gdx.Game;

import kiet.nguyentuan.mysteriouspyramids.stage.MainRound;
import kiet.nguyentuan.support.gdx.graphic2d.BaseScreen;

/**
 * Created by nguye on 8/23/2017.
 */

public class MainMenu extends BaseScreen {

    public MainMenu(Game g){
        super(g);
    }
    @Override
    protected void create() {

        //Event start game stage
        game.setScreen(new MainRound(game));
    }

    @Override
    protected void update(float v) {

    }
}
