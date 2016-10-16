package com.candyz.a7center.cards.game;

import com.candyz.a7center.cards.model.Deck;
import com.candyz.a7center.cards.model.Tray;

/**
 * Created by u on 16.10.2016.
 */

public class SevenCenterTray extends Tray
{
    @Override
    public boolean isAllowed(int suit, int number)
    {
        int[] endValues = getEndValues(suit);
        if(can7BePlayed(suit, number, endValues))
        {
            return true;
        }

        if (isEmpty(endValues))
            return false;

        if(number - 2 == endValues[0] - 1 || number - 2 == endValues[1] + 1)
        {
            return true;
        }
        return false;
    }

    private int[] getEndValues(int suit)
    {
        int firstCard = 14;
        int lastCard = -1;

        for(int i = 0; i < mSortedHand.get(suit - 1).size(); i++)
        {
            if(mSortedHand.get(suit - 1).get(i) == 1)
            {
                if(i < firstCard)
                {
                    firstCard = i;
                }
                if(i > lastCard)
                {
                    lastCard = i;
                }
            }
        }

        return new int[] {firstCard, lastCard};
    }

    private boolean isEmpty(int[] endValues)
    {
        if (endValues[0] == 14 && endValues[1] == -1)
            return true;

        return false;
    }

    private boolean can7BePlayed(int suit, int number, int[] endValues)
    {
        if(number == 7)
        {
            if (isEmpty(endValues))
            {
                //  first card in the suit is not yet played
                if (suit == Deck.getSpadeSuit())
                {
                    return true;    //  spade. don't wait
                }
                else
                {
                    int[] spadeEndValues = getEndValues(Deck.getSpadeSuit());
                    if (isEmpty(spadeEndValues))
                    {
                        return false;
                    }
                    return true;
                }
            }
        }
        return false;
    }
}
