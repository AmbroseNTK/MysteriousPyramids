package kiet.nguyentuan.mysteriouspyramids;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import kiet.nguyentuan.mysteriouspyramids.menu.MainMenu;
import kiet.nguyentuan.support.gdx.*;

public class MainGame extends Game {

	@Override
	public void create() {
		setScreen(new MainMenu(this));
	}
}
