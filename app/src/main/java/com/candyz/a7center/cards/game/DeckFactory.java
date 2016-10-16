package com.candyz.a7center.cards.game;

import com.candyz.a7center.cards.Utils;
import com.candyz.a7center.cards.model.Card;
import com.candyz.a7center.cards.model.Deck;
import com.candyz.a7center.cards.model.IDeckFactory;

import java.util.ArrayList;

/**
 * Created by u on 03.10.2016.
 */

public class DeckFactory implements IDeckFactory
{
    @Override
    public Deck create()
    {
        Deck d = new Deck();

        d.set(getCards());

        return d;
    }

    private ArrayList<Card> getCards()
    {
        ArrayList<Card> cards = new ArrayList<>();

        for(int i = 1; i < 5; i++)
        {
            for(int j = 14; j >= 2; j--)
            {
                cards.add(new Card(i, j));
            }
        }
        Utils.shuffle(cards);
        return cards;
    }

}
