package com.candyz.a7center.cards.view;

import android.content.Context;
import android.util.Log;

import com.candyz.a7center.base.BaseScene;
import com.candyz.a7center.cards.model.Card;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.sprite.ButtonSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.TextureManager;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.buildable.builder.BlackPawnTextureAtlasBuilder;
import org.andengine.opengl.texture.atlas.buildable.builder.ITextureAtlasBuilder;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.util.GLState;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.debug.Debug;

/**
 * Created by u on 03.10.2016.
 */

public class BaseView
{
    protected ITextureRegion mRegion;
    protected BuildableBitmapTextureAtlas mTextureAtlas;
    protected ButtonSprite mSprite;

    protected float mWidth = 72;
    protected float mHeight = 96;
    protected String mImageURL;

    int mAtlasWidth = 1024;
    int mAtlasHeight = 1024;

    protected DisplayBundle mDispBundle;

    public BaseView()
    {

    }

    public BaseView(String imageURL, int atlasWidth, int atlasHeight, DisplayBundle dispBundle)
    {
        mDispBundle = dispBundle;

        mImageURL = imageURL;

        mAtlasWidth = atlasWidth;
        mAtlasHeight = atlasHeight;

        loadGraphics();
    }

    public void show()
    {
        mDispBundle.mScene.attachChild(mSprite);
    }

    public void hide()
    {
        mDispBundle.mScene.detachChild(mSprite);
    }

    public void bringToTop()
    {
        mSprite.setZIndex(1000);
    }

    public ButtonSprite getSprite()
    {
        return mSprite;
    }

    public float getWidth()
    {
        return mWidth;
    }
    public float getHeight()
    {
        return mHeight;
    }

    public void setWidth(float w)
    {
        mWidth = w;
        mSprite.setWidth(mWidth);
    }
    public void setHeight(float h)
    {
        mHeight = h;
        mSprite.setHeight(mHeight);
    }

    public void setPosition(float x, float y)
    {
        //Log.d("", "CardView.setPosition (" + x + ", " + y + ")");
        mSprite.setPosition(x, y);
    }
    public float getX()
    {
        return mSprite.getX();
    }
    public float getY()
    {
        return mSprite.getY();
    }


    protected void loadGraphics()
    {
        if(mImageURL.equals(""))
            return;

        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
        mTextureAtlas = new BuildableBitmapTextureAtlas(mDispBundle.mTextureManager, mAtlasWidth, mAtlasHeight, TextureOptions.BILINEAR);
        mRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mTextureAtlas, mDispBundle.mContext, mImageURL);

        try
        {
            this.mTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 0, 0));
            this.mTextureAtlas.load();
        }
        catch (final ITextureAtlasBuilder.TextureAtlasBuilderException e)
        {
            Debug.e(e);
        }

        mSprite = createSprite(mRegion, mWidth, mHeight);
    }

    protected ButtonSprite createSprite(ITextureRegion textureRegion, float width, float height)
    {
        ButtonSprite sprite = new ButtonSprite(0, 0, textureRegion, mDispBundle.mVBOM);

        setSpriteProperties(sprite, width, height);
        return sprite;
    }

    private void setSpriteProperties(Sprite sprite, float width, float height)
    {
        sprite.setHeight(height);
        sprite.setWidth(width);
    }
}
