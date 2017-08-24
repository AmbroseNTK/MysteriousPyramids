package kiet.nguyentuan.mysteriouspyramids.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import kiet.nguyentuan.mysteriouspyramids.MainGame;
import kiet.nguyentuan.mysteriouspyramids.MainGame2;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		new LwjglApplication(new MainGame2(), config);
	}
}
