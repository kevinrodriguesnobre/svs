package com.kyte.svs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.Game;

import java.sql.*;

public class START extends Game {
    // used by all screens
    public SpriteBatch batcher;
    @Override
    public void create () {

        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        String url = "jdbc:mysql://vsisls4.informatik.uni-hamburg.de/mc05?useUnicode=true&characterEncoding=UTF8";
        String username = "mc05";
        String password = "0ox2DkqP";
        Connection con;
        try {
            con = DriverManager.getConnection(url, username, password);
            String sql = "SELECT * FROM user";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()){
                int id = rs.getInt("id");
                String name = rs.getString("name");
                int points = rs.getInt("point");
                System.out.println(id + " - " + name + " - " + points);
            }
            con.close();
        } catch (Exception e) {
            System.out.println("error - " + e.getMessage());
        }

        //Music music = Gdx.audio.newMusic(Gdx.files.internal("Sounds/NeverRun.mp3"));
        //music.play();
        //music.setLooping(true);
        batcher = new SpriteBatch();
        setScreen(new MainMenu(this));
    }
    @Override
    public void render() {
        super.render();
    }
}
