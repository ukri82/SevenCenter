package com.candyz.a7center.cards.view;

import com.candyz.a7center.cards.model.Card;

import org.andengine.entity.primitive.Line;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.sprite.ButtonSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.buildable.builder.BlackPawnTextureAtlasBuilder;
import org.andengine.opengl.texture.atlas.buildable.builder.ITextureAtlasBuilder;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.util.debug.Debug;

import java.util.ArrayList;

/**
 * Created by u on 02.10.2016.
 */

public class CardView extends BaseView
{
    ITextureRegion mCardFgRegion;
    protected BuildableBitmapTextureAtlas mFgTextureAtlas;
    ButtonSprite mCardFgSprite;

    Card mCard;
    boolean mIsOpen = false;

    public CardView(Card card, DisplayBundle displayBundle)
    {
        super(card.getBackImageURL(), displayBundle);

        mCard = card;

        loadBgGraphics();
    }

    private void loadBgGraphics()
    {
        mFgTextureAtlas = new BuildableBitmapTextureAtlas(mDispBundle.mTextureManager, 1024, 1024, TextureOptions.BILINEAR);
        mCardFgRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mFgTextureAtlas, mDispBundle.mContext, mCard.getImageURL());
        try
        {
            this.mFgTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 1, 0));
            this.mFgTextureAtlas.load();
        }
        catch (final ITextureAtlasBuilder.TextureAtlasBuilderException e)
        {
            Debug.e(e);
        }

        mCardFgSprite = createSprite(mCardFgRegion, mWidth, mHeight);
    }

    private void createBorder()
    {
        if(mBorderLines == null)
        {
            mBorderLines = new ArrayList<>();
            float left = mSprite.getX() - 1;
            float top = mSprite.getY() - 1;
            float right = mSprite.getX() + mSprite.getWidth() + 1;
            float bottom = mSprite.getY() + mSprite.getHeight() + 1;
            mBorderLines.add(new Line(left, top, right, top, mDispBundle.mVBOM));
            mBorderLines.add(new Line(right, top, right, bottom, mDispBundle.mVBOM));
            mBorderLines.add(new Line(right, bottom, left, bottom, mDispBundle.mVBOM));
            mBorderLines.add(new Line(left, bottom, left, top, mDispBundle.mVBOM));

            for (int i = 0; i < mBorderLines.size(); i++)
            {
                mBorderLines.get(i).setLineWidth(2f);
            }
        }
    }

    private ArrayList<Line> mBorderLines;
    public void highlight()
    {
        createBorder();

        for (int i = 0; i < mBorderLines.size(); i++)
        {
            mBorderLines.get(i).setColor(0.0f, 0.0f, 1.0f);
            mDispBundle.mScene.attachChild(mBorderLines.get(i));
        }
    }

    public void removeHighlight()
    {
        if(mBorderLines != null)
        {
            for (int i = 0; i < mBorderLines.size(); i++)
            {
                mDispBundle.mScene.detachChild(mBorderLines.get(i));
            }
        }
    }


    public void setPosition(float x, float y)
    {
        super.setPosition(x, y);
        mCardFgSprite.setPosition(x, y);
    }

    public void setWidth(float w)
    {
        super.setWidth(w);
        mCardFgSprite.setWidth(w);
    }
    public void setHeight(float h)
    {
        super.setHeight(h);
        mCardFgSprite.setHeight(h);
    }

    public Card getCard()
    {
        return mCard;
    }

    public boolean isOpen()
    {
        return mIsOpen;
    }

    public void close()
    {
        mDispBundle.mScene.detachChild(mCardFgSprite);
        show();
        mIsOpen = false;
    }

    public void open()
    {
        hide();
        mDispBundle.mScene.attachChild(mCardFgSprite);
        mIsOpen = true;
    }



    public void setClickable(boolean flag, final ICardClickHandler cardClickHandler)
    {
        if(flag)
        {
            final CardView thisView = this;
            mDispBundle.mScene.registerTouchArea(mCardFgSprite);
            mCardFgSprite.setOnClickListener(new ButtonSprite.OnClickListener()
            {
                @Override
                public void onClick(ButtonSprite pButtonSprite, float pTouchAreaLocalX, float pTouchAreaLocalY)
                {
                    cardClickHandler.onClick(thisView);
                }
            });
        }
        else
        {
            mDispBundle.mScene.unregisterTouchArea(mCardFgSprite);
            mCardFgSprite.setOnClickListener(null);
        }
    }
}
