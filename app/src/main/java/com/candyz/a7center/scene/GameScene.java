package com.candyz.a7center.scene;


import com.candyz.a7center.base.BaseScene;
import com.candyz.a7center.cards.game.DeckFactory;
import com.candyz.a7center.cards.game.PlayerFactory;
import com.candyz.a7center.cards.game.SevenCenter;
import com.candyz.a7center.cards.game.SevenCenterView;

import com.candyz.a7center.cards.view.DisplayBundle;
import com.candyz.a7center.manager.OptionsManager;
import com.candyz.a7center.manager.SceneManager;


public class GameScene extends BaseScene
{
	SevenCenterView mSevenCenterView;
	SevenCenter mSevenCenter;
	
	@Override
	public void createScene()
	{

        DisplayBundle dBundle = new DisplayBundle(activity, resourcesManager.activity.getTextureManager(), vbom,
                this, resourcesManager.engine, resourcesManager.activity, resourcesManager.camera, resourcesManager.mGeneralTextFont, resourcesManager.mHasVibratePermission);
		mSevenCenter = new SevenCenter(new PlayerFactory(resourcesManager.activity), new DeckFactory());
		int numPlayers = Integer.parseInt(OptionsManager.getInstance().get("number_of_players"));
		mSevenCenter.startGame(numPlayers);
		mSevenCenterView = new SevenCenterView(mSevenCenter, dBundle);
    }

	@Override
	public void onBackKeyPressed()
	{


	}



	@Override
	public void updateScene()
	{
		mSevenCenter.updateInteractivePlayer();
	}

	@Override
	public SceneManager.SceneType getSceneType()
	{
		return SceneManager.SceneType.SCENE_GAME;
	}

	@Override
	public void disposeScene()
	{
		camera.setHUD(null);
		camera.setChaseEntity(null); //TODO
		//camera.setCenter(400, 240);

		// TODO code responsible for disposing scene
		// removing all game scene objects.
	}



}