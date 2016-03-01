package com.kyte.svs;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

public class MainMenu extends ScreenAdapter {

    START game;
    OrthographicCamera guiCam;
    Rectangle soundBounds;
    Rectangle playBounds;
    Rectangle highscoresBounds;
    Rectangle helpBounds;
    Vector3 touchPoint;
    long time;
    public MainMenu (START game) {
        this.game = game;
        guiCam = new OrthographicCamera(320, 480);
        guiCam.position.set(320 / 2, 480 / 2, 0);
        soundBounds = new Rectangle(0, 0, 64, 64);
        playBounds = new Rectangle(23, 328, 274, 142);
        highscoresBounds = new Rectangle(160 - 150, 200 - 18, 300, 36);
        helpBounds = new Rectangle(160 - 150, 200 - 18 - 36, 300, 36);
        touchPoint = new Vector3();
        time = System.currentTimeMillis();
    }
    public void update () {
        if (Gdx.input.justTouched()) {
            guiCam.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));
            time = System.currentTimeMillis();
            if (playBounds.contains(touchPoint.x, touchPoint.y)) {
                game.setScreen(new Game(game));
                return;
            }
            if (highscoresBounds.contains(touchPoint.x, touchPoint.y)) {
              //  Assets.playSound(Assets.clickSound);
               // game.setScreen(new HighscoresScreen(game));
                return;
            }
            if (helpBounds.contains(touchPoint.x, touchPoint.y)) {
             //   Assets.playSound(Assets.clickSound);
             //   game.setScreen(new HelpScreen(game));
                return;
            }
            if (soundBounds.contains(touchPoint.x, touchPoint.y)) {
               // Assets.playSound(Assets.clickSound);
               // Settings.soundEnabled = !Settings.soundEnabled;
                //if (Settings.soundEnabled)
                 //   Assets.music.play();
               // else
                  //  Assets.music.pause();
            }
        }
    }
    public void draw () {
        GL20 gl = Gdx.gl;
        gl.glClearColor(1, 1, 1, 1);
        gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        guiCam.update();
        game.batcher.setProjectionMatrix(guiCam.combined);
        game.batcher.disableBlending();
        game.batcher.begin();
        //game.batcher.draw(Assets.backgroundRegion, 0, 0, 320, 480);
        game.batcher.end();
        game.batcher.enableBlending();
        game.batcher.begin();
        game.batcher.draw(new Texture(Gdx.files.internal("data/playbutton.png")), 23, 328, 274, 142);
        game.batcher.draw(new Texture(Gdx.files.internal("data/scorebutton.png")), 23, 145, 274, 142);
        explosion();
        //game.batcher.draw(Settings.soundEnabled ? Assets.soundOn : Assets.soundOff, 0, 0, 64, 64);
        game.batcher.end();
    }

    public void explosion(){
        if((System.currentTimeMillis() - time) < 500){
            game.batcher.draw(new Texture(Gdx.files.internal("data/explosion.png")),touchPoint.x - 20 ,touchPoint.y - 20,50,50 );
        }
    }
    @Override
    public void render (float delta) {
        update();
        draw();
    }
    @Override
    public void pause () {
        //Settings.save();
    }


}
