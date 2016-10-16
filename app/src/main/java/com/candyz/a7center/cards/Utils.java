package com.candyz.a7center.cards;

import com.candyz.a7center.cards.model.Card;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by u on 03.10.2016.
 */

public class Utils
{
    /*public ArrayList<Integer> getShuffleIndices(int numEntries)
    {
        Random ran = new Random();
        ArrayList<Integer> shuffledEntries = new ArrayList<>();
        for(int i = 0; i < numEntries; i++)
        {
            shuffledEntries.add(i);
        }
        for(int i = shuffledEntries.size() - 1; i > 0; i--)
        {
            int nextIndex = ran.nextInt(i);
            int temp = shuffledEntries.get(i);
            shuffledEntries.set(i, shuffledEntries.get(nextIndex));
            shuffledEntries.set(nextIndex, temp);
        }
        return shuffledEntries;
    }*/

    public static <T> void shuffle(ArrayList<T> entries)
    {
        Random ran = new Random();
        for(int i = entries.size() - 1; i > 0; i--)
        {
            int nextIndex = ran.nextInt(i);
            T temp = entries.get(i);
            entries.set(i, entries.get(nextIndex));
            entries.set(nextIndex, temp);
        }
    }
}
