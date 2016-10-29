package com.candyz.a7center.cards.game;

import com.candyz.a7center.cards.model.Brain;
import com.candyz.a7center.cards.model.Card;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * Created by u on 07.10.2016.
 */

public class SimulatedBrain extends Brain
{
    class CardScore
    {
        Card mCard;
        int mScore = 0;
        public CardScore(Card c, int score)
        {
            mCard = c;
            mScore = score;
        }

        public Card getCard()
        {
            return mCard;
        }

        public int getScore()
        {
            return mScore;
        }
    }

    ArrayList<Card> getCardsOfSuit(ArrayList<Card> possibleCards, Card c)
    {
        ArrayList<Card> cards = new ArrayList<>();

        for(int i = 0; i < possibleCards.size(); i++)
        {
            if(possibleCards.get(i).getSuit() == c.getSuit())
            {
                cards.add(possibleCards.get(i));
            }

        }

        return cards;
    }

    int computeScore(ArrayList<Card> cardsOfSameSuit, Card c)
    {
        int score = 0;
        if(c.getNumber() == 14 || c.getNumber() == 2)
        {
            score = 25;
            return score;
        }
        if(cardsOfSameSuit.size() > 0)
        {
            for(int i = 0; i < cardsOfSameSuit.size(); i++)
            {
                int cardNumDiff = cardsOfSameSuit.get(i).getNumber() - c.getNumber();
                if(cardNumDiff == 1 || cardNumDiff == -1)
                {
                    score = 25;
                    return score;
                }
            }

            if(c.getNumber() >= 7)
            {
                if(cardsOfSameSuit.get(cardsOfSameSuit.size() - 1).getNumber() >= 7)
                    score = 3 * (cardsOfSameSuit.get(cardsOfSameSuit.size() - 1).getNumber() - c.getNumber());
            }
            else
            {
                if(cardsOfSameSuit.get(0).getNumber() < 7)
                    score = c.getNumber() - cardsOfSameSuit.get(0).getNumber();
            }
        }

        return score;

    }

    ArrayList<CardScore> getCardScores()
    {
        ArrayList<CardScore> cardScores = new ArrayList<>();
        ArrayList<Card> possibleCards = getPlayeableCards();
        for(int i = 0; i < possibleCards.size(); i++)
        {
            ArrayList<Card> cardsOfSameSuit = getCardsOfSuit(possibleCards, possibleCards.get(i));
            Collections.sort(cardsOfSameSuit, new Brain.CardComparator());
            int score = computeScore(cardsOfSameSuit, possibleCards.get(i));
            cardScores.add(new CardScore(possibleCards.get(i), score));
        }
        return cardScores;
    }

    Card getBrilliantCard()
    {
        int maxScore = -1;
        Card maxCard = null;
        ArrayList<CardScore> cardScores = getCardScores();
        for(int i = 0; i < cardScores.size(); i++)
        {
            if(cardScores.get(i).getScore() > maxScore)
            {
                maxScore = cardScores.get(i).getScore();
                maxCard = cardScores.get(i).getCard();
            }
        }

        return maxCard;

    }

    Card getDumbCard()
    {
        ArrayList<Card> possibleCards = getPlayeableCards();

        Card nextCard = null;
        if(possibleCards.size() != 0)
        {
            Random ran = new Random();
            int nextIndex = ran.nextInt(possibleCards.size());
            nextCard = possibleCards.get(nextIndex);
        }

        return nextCard;
    }

    Card getSafeCard()
    {
        Card nextCard = null;
        ArrayList<Card> possibleCards = getPlayeableCards();

        int maxNumber = 0;

        for(int i = 0; i < possibleCards.size(); i++)
        {
            if(possibleCards.get(i).getNumber() > maxNumber)
            {
                maxNumber = possibleCards.get(i).getNumber();
                nextCard = possibleCards.get(i);
            }
        }

        return nextCard;

    }

    @Override
    public Card getNextCard()
    {
        try
        {
            Thread.sleep(1000);
        } catch (InterruptedException e)
        {
            e.printStackTrace();
        }

        Card nextCard = null;
        if(mBrilliancy == 3)
        {
            nextCard = getBrilliantCard();
        }
        else if(mBrilliancy == 2)
        {
            nextCard = getSafeCard();
        }
        else
        {
            nextCard = getDumbCard();
        }

        return nextCard;
    }


    public Card getNextCard1()
    {
        try
        {
            Thread.sleep(2000);
        } catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        int nextSuit = -1;
        for(int i = 0; i < mSortedHand.size(); i++)
        {
            if(mSortedHand.get(i).size() > 0)
            {
                nextSuit = i;
                if (Math.random() < 0.5)
                    break;
            }
        }
        int nextCard = -1;
        if(nextSuit != -1)
        {
            for(int i = 0; i < mSortedHand.get(nextSuit).size(); i++)
            {
                nextCard = i;
                if(Math.random() < 0.5)
                {
                    break;
                }
            }
        }

        Card c = null;
        if(nextSuit != -1 && nextCard != -1)
        {
            c = mSortedHand.get(nextSuit).get(nextCard);
            mSortedHand.get(nextSuit).remove(nextCard);
        }
        return c;
    }
}
