package kiet.nguyentuan.mysteriouspyramids.stage;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.utils.AnimationController;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;

import java.util.ArrayList;

import kiet.nguyentuan.support.gdx.graphic2d.BaseScreen;

/**
 * Created by nguye on 8/23/2017.
 */

public class MainRound extends BaseScreen {

    final float SIZE_OF_BLOCK=10f;
    final int MAP_WIDTH = 4;
    final int MAP_HEIGHT=3;
    private ModelBatch modelBatch;
    private ArrayList<ModelInstance> instances;
    private CameraInputController controller;
    private PerspectiveCamera camera;
    private AssetManager assetManager;
    private boolean isLoading;
    private AnimationController animationController;
    private Environment environment;
    private IMapGenerator mapGenerator;
    private MapBlockType[][] map;

    public MainRound(Game g){
        super(g);
    }
    @Override
    protected void create() {
        modelBatch=new ModelBatch();
        camera=new PerspectiveCamera();
        camera.fieldOfView=50;
        camera.position.set(0,10,0);
        camera.lookAt(0,10,0);
        camera.near=10f;
        camera.far=3000f;
        environment=new Environment();
        environment.set(new ColorAttribute(ColorAttribute.AmbientLight,0.8f,0.8f,0.8f,1.0f));
        controller=new CameraInputController(camera);
        Gdx.input.setInputProcessor(controller);
        assetManager=new AssetManager();
        assetManager.load("models/ground.g3db",Model.class);
        assetManager.load("models/men.g3db",Model.class);
        assetManager.load("models/EgyptBlock.g3db",Model.class);
        isLoading=true;
        mapGenerator=new SimpleMapGenerator();
        map = mapGenerator.createMap(4,3);
        instances=new ArrayList<ModelInstance>();
    }

    @Override
    protected void update(float v) {

        Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

    }

    @Override
    public void render(float delta) {
        super.render(delta);
        if(isLoading&&assetManager.update()){
            loadingAsset();
        }
        modelBatch.begin(camera);
        modelBatch.render(instances,environment);
        modelBatch.end();
    }

    private void loadingAsset(){
        isLoading=false;
        for(int i=0;i<MAP_WIDTH;i++){
            for(int j=0;j<MAP_HEIGHT;j++){
                String modelName="";
                switch (map[i][j]){
                    case WALL:
                        modelName="models/EgyptBlock.g3db";
                        break;
                    case GROUND:
                        modelName="models/ground.g3db";
                        break;
                }
                if(modelName.equals(""))
                    continue;
                instances.add(new ModelInstance(assetManager.get(modelName,Model.class)));
                instances.get(instances.size()-1).transform.translate(i*SIZE_OF_BLOCK,j*SIZE_OF_BLOCK,0);
            }
        }
        isLoading=false;
    }
}
