package com.candyz.a7center.cards.view;

import com.candyz.a7center.cards.model.Card;

import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.IEntityModifier;
import org.andengine.entity.modifier.MoveModifier;
import org.andengine.entity.modifier.MoveYModifier;
import org.andengine.entity.modifier.ParallelEntityModifier;
import org.andengine.entity.modifier.RotationModifier;
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
import org.andengine.util.modifier.IModifier;
import org.andengine.util.modifier.ease.EaseBounceOut;
import org.andengine.util.modifier.ease.EaseCubicIn;
import org.andengine.util.modifier.ease.EaseElasticIn;

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

    static final int mOrigCardWidth = 72;
    static final int mOrigCardHeight = 96;

    private ArrayList<Line> mBorderLines;

    public CardView(Card card, DisplayBundle displayBundle)
    {
        super(card.getBackImageURL(), mOrigCardWidth, mOrigCardHeight, displayBundle);

        mCard = card;

        //mTextureAtlas = cardAtlas;
        loadBgGraphics();

        show();

        //load();
    }

    private void loadBgGraphics()
    {
        mFgTextureAtlas = new BuildableBitmapTextureAtlas(mDispBundle.mTextureManager, mOrigCardWidth, mOrigCardHeight, TextureOptions.BILINEAR);
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

    /*private void load()
    {
        //mFgTextureAtlas = new BuildableBitmapTextureAtlas(mDispBundle.mTextureManager, 1024, 1024, TextureOptions.BILINEAR);

        mCardFgRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mTextureAtlas, mDispBundle.mContext, mCard.getImageURL());

        loadGraphics();

        mCardFgSprite = createSprite(mCardFgRegion, mWidth, mHeight);
    }*/

    private void createBorder()
    {
        if(mBorderLines == null)
        {
            mBorderLines = new ArrayList<>();
            mBorderLines.add(new Line(0, 0, 0, 0, mDispBundle.mVBOM));
            mBorderLines.add(new Line(0, 0, 0, 0, mDispBundle.mVBOM));
            mBorderLines.add(new Line(0, 0, 0, 0, mDispBundle.mVBOM));
            mBorderLines.add(new Line(0, 0, 0, 0, mDispBundle.mVBOM));

            for (int i = 0; i < mBorderLines.size(); i++)
            {
                mBorderLines.get(i).setLineWidth(4f);
            }
        }
    }

    private void setBorderPosition()
    {
        if(mBorderLines != null)
        {
            float left = mSprite.getX() - 1;
            float top = mSprite.getY() - 1;
            float right = mSprite.getX() + mSprite.getWidth() + 1;
            float bottom = mSprite.getY() + mSprite.getHeight() + 1;

            mBorderLines.get(0).setPosition(left, top, right, top);
            mBorderLines.get(1).setPosition(right, top, right, bottom);
            mBorderLines.get(2).setPosition(right, bottom, left, bottom);
            mBorderLines.get(3).setPosition(left, bottom, left, top);
        }
    }


    public void highlight()
    {
        createBorder();
        setBorderPosition();

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
        setBorderPosition();
    }

    public void setWidth(float w)
    {
        super.setWidth(w);
        mCardFgSprite.setWidth(w);
        setBorderPosition();
    }
    public void setHeight(float h)
    {
        super.setHeight(h);
        mCardFgSprite.setHeight(h);
        setBorderPosition();
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
        if(mIsOpen == true)
        {
            mDispBundle.mScene.detachChild(mCardFgSprite);
            show();
            mIsOpen = false;
        }
    }

    public void open()
    {
        if(mIsOpen == false)
        {
            hide();
            mDispBundle.mScene.attachChild(mCardFgSprite);
            mIsOpen = true;
        }
    }

    public void reset()
    {
        removeHighlight();

        setWidth(mOrigCardWidth);
        setHeight(mOrigCardHeight);
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

    public void animateMove(float toX, float toY)
    {
        mCardFgSprite.registerEntityModifier(
                new ParallelEntityModifier(
                        new MoveModifier(1, getX(), toX, getY(), toY, EaseCubicIn.getInstance()),
                        new RotationModifier(1, 0, 360)
                )
                );
    }
}
