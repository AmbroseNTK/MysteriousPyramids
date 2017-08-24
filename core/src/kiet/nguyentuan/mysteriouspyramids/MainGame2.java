package kiet.nguyentuan.mysteriouspyramids;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.utils.AnimationController;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.graphics.g3d.utils.FirstPersonCameraController;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

import java.util.ArrayList;

import kiet.nguyentuan.mysteriouspyramids.stage.IMapGenerator;
import kiet.nguyentuan.mysteriouspyramids.stage.MapBlockType;
import kiet.nguyentuan.mysteriouspyramids.stage.SimpleMapGenerator;
import kiet.nguyentuan.support.gdx.graphic2d.BaseActor;

/**
 * Created by nguye on 8/23/2017.
 */

public class MainGame2 extends ApplicationAdapter {

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
    private FirstPersonCameraController firstPersonCameraController;
    private Stage uiStage;
    private BaseActor button1;
    private InputMultiplexer inputMultiplexer;
    @Override
    public void create() {
        modelBatch=new ModelBatch();
        camera=new PerspectiveCamera(50,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        camera.fieldOfView=50;
        camera.position.set(-10,10,10);
        camera.lookAt(10,0,10);
        camera.near=0.1f;
        camera.far=3000f;
        camera.update();
        environment=new Environment();
        environment.set(new ColorAttribute(ColorAttribute.AmbientLight,0.8f,0.8f,0.8f,1.0f));
        controller=new CameraInputController(camera);
       // Gdx.input.setInputProcessor(controller);
        assetManager=new AssetManager();
        assetManager.load("models/ground.g3db",Model.class);
        assetManager.load("models/men.g3db",Model.class);
        assetManager.load("models/EgyptBlock.g3db",Model.class);
        isLoading=true;
        mapGenerator=new SimpleMapGenerator();
        map = mapGenerator.createMap(4,3);
        instances=new ArrayList<ModelInstance>();
        firstPersonCameraController=new FirstPersonCameraController(camera);
        uiStage =new Stage(new FitViewport(Gdx.graphics.getWidth(),Gdx.graphics.getHeight()));
        button1=new BaseActor(new Texture(Gdx.files.internal("badlogic.jpg")));
        button1.setPosition(10,10);
        button1.setScale(0.2f);
        uiStage.addActor(button1);
        inputMultiplexer=new InputMultiplexer(uiStage,firstPersonCameraController);
        Gdx.input.setInputProcessor(inputMultiplexer);



    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        camera.viewportWidth=width;
        camera.viewportHeight=height;
        uiStage.getViewport().update(width,height);
        camera.update();

    }

    @Override
    public void render() {
        if(isLoading&&assetManager.update()){
            loadingAsset();
        }
        Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        if(animationController!=null){
            animationController.update(Gdx.graphics.getDeltaTime());
        }
        if(instances.size()!=0) {
            float dist=10f * Gdx.graphics.getDeltaTime();
            instances.get(instances.size() - 1).transform.translate(dist,0,0);
            //camera.position.add(dist,0,0);
            //camera.lookAt(camera.position.x+10,0,camera.position.z);
            camera.update();
        }
        uiStage.act();

        modelBatch.begin(camera);
        modelBatch.render(instances,environment);
        modelBatch.end();
        uiStage.draw();
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
                instances.get(instances.size()-1).transform.translate(i*SIZE_OF_BLOCK,0,j*SIZE_OF_BLOCK);
            }
        }
        instances.add(new ModelInstance(assetManager.get("models/men.g3db",Model.class)));
        instances.get(instances.size()-1).transform.translate(0,0,10);
        animationController=new AnimationController(instances.get(instances.size()-1));
        animationController.animate("Armature|run", -1, 5f, new AnimationController.AnimationListener() {
            @Override
            public void onEnd(AnimationController.AnimationDesc animation) {

            }

            @Override
            public void onLoop(AnimationController.AnimationDesc animation) {

            }
        },0);
        InputListener clickButton1=new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                instances.get(instances.size()-1).transform.rotate(new Quaternion(0,0.707f,0,0.707f));
                button1.addAction(Actions.sequence(Actions.fadeOut(0.5f),Actions.fadeIn(0.5f)));
                return super.touchDown(event, x, y, pointer, button);
            }
        };
        button1.addListener(clickButton1);
        isLoading=false;
    }
}
