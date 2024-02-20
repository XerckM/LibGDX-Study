package com.xmdev.sfs;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.xmdev.sfs.objects.Fighter;
import com.xmdev.sfs.objects.FighterChoice;
import com.xmdev.sfs.resources.Assets;
import com.xmdev.sfs.resources.AudioManager;
import com.xmdev.sfs.screens.GameScreen;
import com.xmdev.sfs.screens.MainMenuScreen;

import java.util.ArrayList;

public class SFS extends Game {
	public SpriteBatch batch;
	public ShapeRenderer shapeRenderer;
	public Assets assets;
	public AudioManager audioManager;

	// screens
	public GameScreen gameScreen;
	public MainMenuScreen mainMenuScreen;

	// fighters
	public Fighter player, opponent;
	public final ArrayList<FighterChoice> fighterChoiceList = new ArrayList<>();
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		shapeRenderer = new ShapeRenderer();
		assets = new Assets();

		// Load all assets
		assets.load();
		assets.manager.finishLoading();

		// initialize audio manager
		audioManager = new AudioManager(assets.manager);
		audioManager.playMusic();

		// load the fighter choice list
		loadFighterChoiceList();

		// initialize the fighters
		player = new Fighter(this, fighterChoiceList.get(0).getName(), fighterChoiceList.get(0).getColor());
		opponent = new Fighter(this, fighterChoiceList.get(1).getName(), fighterChoiceList.get(1).getColor());

		// initialize game screen
		gameScreen = new GameScreen(this);

		// initialize main menu screen and switch to it
		mainMenuScreen = new MainMenuScreen(this);
		setScreen(mainMenuScreen);
	}

	private void loadFighterChoiceList() {
		// load the fighter choice list from the assets
		Json json = new Json();
		JsonValue fighterChoices = new JsonReader().parse(Gdx.files.internal("data/fighter_choices.json"));
		for (int i = 0; i < fighterChoices.size; i++) {
			fighterChoiceList.add(json.fromJson(FighterChoice.class, String.valueOf(fighterChoices.get(i))));
		}
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		shapeRenderer.dispose();
		assets.dispose();
	}
}
