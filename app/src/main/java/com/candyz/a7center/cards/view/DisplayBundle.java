package com.candyz.a7center.cards.view;

import android.content.Context;

import com.candyz.a7center.GameActivity;
import com.candyz.a7center.base.BaseScene;

import org.andengine.engine.Engine;
import org.andengine.engine.camera.Camera;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.texture.TextureManager;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

/**
 * Created by u on 03.10.2016.
 */

public class DisplayBundle
{
    public VertexBufferObjectManager mVBOM;
    public TextureManager mTextureManager;

    public Context mContext;

    public BaseScene mScene;

    public Engine mEngine;
    public GameActivity mActivity;
    public Camera mCamera;
    public Font mGeneralTextFont;

    public boolean mHasVibratePermission = false;

    public DisplayBundle(Context c, TextureManager tm, VertexBufferObjectManager vbom,
                         BaseScene scene, Engine engine, GameActivity activity, Camera camera, Font font,
                         boolean hasVibratePermission)
    {
        mContext = c;
        mVBOM = vbom;
        mScene = scene;
        mTextureManager = tm;
        mEngine = engine;
        mActivity = activity;
        mCamera = camera;
        mGeneralTextFont = font;
        mHasVibratePermission = hasVibratePermission;
    }
}
