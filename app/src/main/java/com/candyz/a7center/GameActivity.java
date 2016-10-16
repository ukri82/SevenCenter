package com.candyz.a7center;

import android.util.DisplayMetrics;

import com.candyz.a7center.manager.ResourcesManager;
import com.candyz.a7center.manager.SceneManager;

import org.andengine.engine.camera.BoundCamera;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.FillResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.ui.activity.SimpleBaseGameActivity;

import java.io.IOException;

public class GameActivity extends SimpleBaseGameActivity
{

    private Camera camera;
    private static int CAMERA_WIDTH = 800;
    private static int CAMERA_HEIGHT = 480;

    private ResourcesManager resourcesManager;

    /*@Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }*/

    /*public void onCreateScene(OnCreateSceneCallback pOnCreateSceneCallback) throws IOException
    {
        //SceneManager.getInstance().createSplashScene(pOnCreateSceneCallback);
        //SceneManager.getInstance().createGameScene(mEngine, pOnCreateSceneCallback);
    }*/

    @Override
    protected void onCreateResources()
    {

    }
    public void onCreateResources(OnCreateResourcesCallback pOnCreateResourcesCallback) throws IOException
    {
        ResourcesManager.prepareManager(mEngine, this, camera, getVertexBufferObjectManager());
        resourcesManager = ResourcesManager.getInstance();
        pOnCreateResourcesCallback.onCreateResourcesFinished();
        resourcesManager.loadGameResources();
    }

    public void onPopulateScene(Scene pScene, OnPopulateSceneCallback pOnPopulateSceneCallback) throws IOException
    {
        /*mEngine.registerUpdateHandler(new TimerHandler(2f, new ITimerCallback()
        {
            public void onTimePassed(final TimerHandler pTimerHandler)
            {
                mEngine.unregisterUpdateHandler(pTimerHandler);
                SceneManager.getInstance().createMenuScene();
            }
        }));*/
        pOnPopulateSceneCallback.onPopulateSceneFinished();
    }

    @Override
    protected Scene onCreateScene()
    {
        /*Scene scene = new Scene();
        scene.setBackground(new Background(0.09804f, 0.6274f, 0.8784f));
        return scene;*/
        return SceneManager.getInstance().createGameScene(mEngine);
        //return null;
    }

    @Override
    public EngineOptions onCreateEngineOptions()
    {
        //camera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        CAMERA_WIDTH = metrics.widthPixels;
        CAMERA_HEIGHT = metrics.heightPixels;

        camera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);

        EngineOptions engineOptions = new EngineOptions(true, ScreenOrientation.LANDSCAPE_FIXED,
                new FillResolutionPolicy(), camera);
        return engineOptions;
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        System.exit(0);
    }
}
