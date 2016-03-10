package com.kyte.svs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Created by X303 on 08.03.2016.
 */
public class Score extends ScreenAdapter {

    START game;
    OrthographicCamera guiCam;
    Rectangle backBounds;
    Vector3 touchPoint;
    long time;
    Label _usernameLabel;
    Label _pointsLabel;
    Stage _stage;
    Table _table;


    public Score (START game)
    {
        this.game = game;
        guiCam = new OrthographicCamera(1080, 1920);
        guiCam.position.set(1080 / 2,  1920 / 2, 0);
        backBounds = new Rectangle(0,0, 290, 150);
        touchPoint = new Vector3();
        time = System.currentTimeMillis();

        _stage = new Stage();
        Skin skin = new Skin(Gdx.files.internal("data/uiskin.json"));

        _usernameLabel = new Label("Username", skin);
        _usernameLabel.setFontScale(Gdx.graphics.getWidth() / 450);
        _usernameLabel.setColor(0f, 0f, 0f, 1.0f);
        _pointsLabel = new Label("Points", skin);
        _pointsLabel.setFontScale(Gdx.graphics.getWidth() / 450);
        _pointsLabel.setColor(0f, 0f, 0f, 1.0f);

        _table =  new Table();
        _table.setFillParent(true);
        _table.add(_usernameLabel);
        _table.add(_pointsLabel);
        _table.row();
        for(int i = 0; i < 10; ++i)
        {
            _table.add(new TextField("",skin)).width(100).padBottom(Gdx.graphics.getHeight()/100).expandX();
            _table.add(new TextField("",skin)).width(100).padBottom(Gdx.graphics.getHeight()/100).expandX();
            _table.row();
        }

        _stage.addActor(_table);
        Gdx.input.setInputProcessor(_stage);
    }

    public void update () {
        if (Gdx.input.justTouched()) {
            guiCam.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));
            time = System.currentTimeMillis();
            if (backBounds.contains(touchPoint.x, touchPoint.y)) {
                game.setScreen(new MainMenu(game));
                return;
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
        game.batcher.enableBlending();
        game.batcher.begin();
        game.batcher.draw(new Texture(Gdx.files.internal("data/background1.png")), 0, 0, 1080, 1920);
        game.batcher.draw(new Texture(Gdx.files.internal("data/backbutton.png")), 0, 0, 290, 150);
        game.batcher.draw(new Texture(Gdx.files.internal("data/backbutton.png")), 0, 0, 290, 150);
        _stage.act(Gdx.graphics.getDeltaTime());
        _stage.draw();
        explosion();
        //game.batcher.draw(Settings.soundEnabled ? Assets.soundOn : Assets.soundOff, 0, 0, 64, 64);
        game.batcher.end();
    }

    public void explosion(){
        if((System.currentTimeMillis() - time) < 500){
            game.batcher.draw(new Texture(Gdx.files.internal("data/hand.png")),touchPoint.x - 100 ,touchPoint.y - 100,200,200);
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
/**
    public void getScoreOutDb() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        String url = "jdbc:mysql://vsisls4.informatik.uni-hamburg.de/mc05?useUnicode=true&characterEncoding=UTF8";
        String username = "mc05";
        String password = "0ox2DkqP";
        Connection con;
        try {
            con = DriverManager.getConnection(url, username, password);
            String sql = "SELECT username, points " + "FROM user " + "ORDER BY points DESC " + "LIMIT 10";
            System.out.println(sql);
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);

            int i = 0;
            while(!rs.isAfterLast()) {
                System.out.println(st.getResultSet().getString(i));
                ++i;
            }
            while(rs.next()){
                String name = rs.getString("name");
                int points = rs.getInt("point");
                System.out.println(name + " - " + points);
            }
            con.close();
        } catch (Exception e) {
            System.out.println("ALARM! Error: " + e.getMessage());
        }
    }
 **/

}

