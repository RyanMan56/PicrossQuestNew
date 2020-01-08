package uk.co.ryanbrianjones.picrossquest.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import uk.co.ryanbrianjones.picrossquest.PicrossQuest;
import uk.co.ryanbrianjones.picrossquest.entity.Entity;
import uk.co.ryanbrianjones.picrossquest.entity.Player;


public class GameScreen implements Screen {
    private PicrossQuest game;
    private AssetManager assetManager;
    private SpriteBatch batch;
    protected ShapeRenderer shapeRenderer;
    protected OrthographicCamera camera;
    private Viewport viewport;

    protected Player player;
    protected TiledMap tiledMap;
    private OrthogonalTiledMapRenderer mapRenderer;
    private float screenMoveSpeed = 2;
    private float mapScale = 2;

    private float mapWidth, mapHeight, tileWidth, tileHeight, mapPixelWidth, mapPixelHeight;
    protected int[] backgroundLayers;
    protected int[] foregroundLayers;
    protected float camX, camY;

    public GameScreen(PicrossQuest game, AssetManager assetManager, String mapFile) {
        this.game = game;
        this.assetManager = assetManager;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 854, 480);
        viewport = new FitViewport(854, 480, camera);
        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();

        player = new Player(assetManager, 600, 200);
        camera.position.set(player.getX()+player.getWidth()/2, player.getY()+player.getHeight()/2, 0);
        tiledMap = assetManager.get(mapFile, TiledMap.class);
        mapRenderer = new OrthogonalTiledMapRenderer(tiledMap, mapScale);
        mapRenderer.setView(camera);

        mapWidth = tiledMap.getProperties().get("width", Integer.class);
        mapHeight = tiledMap.getProperties().get("height", Integer.class);
        tileWidth = tiledMap.getProperties().get("tilewidth", Integer.class);
        tileHeight = tiledMap.getProperties().get("tileheight", Integer.class);

        mapPixelWidth = mapWidth * tileWidth * mapScale;
        mapPixelHeight = mapHeight * tileHeight * mapScale;
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        update();

        shapeRender();

        mapRenderer.setView(camera);
        mapRenderer.render(backgroundLayers);
        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        player.render(batch);
        batch.end();
        mapRenderer.render(foregroundLayers);
    }

    protected void shapeRender(){

    }

    private void update() {
        camX = camera.position.x - camera.viewportWidth / 2;
        camY = camera.position.y - camera.viewportHeight / 2;
        if (Gdx.input.isKeyPressed(Keys.D))
            if (camX + camera.viewportWidth < mapPixelWidth)
                if (player.getX() > viewport.unproject(new Vector3((Gdx.graphics.getWidth() /2f) - player.getWidth()/2, 0, 0)).x)
                    camera.translate(screenMoveSpeed, 0);
        if (Gdx.input.isKeyPressed(Keys.A))
            if (camX > 0)
                if (player.getX() < viewport.unproject(new Vector3((Gdx.graphics.getWidth() / 2f) - player.getWidth()/2, 0, 0)).x)
                    camera.translate(-screenMoveSpeed, 0);
        if (Gdx.input.isKeyPressed(Keys.W))
            if (camY + camera.viewportHeight < mapPixelHeight)
                if (player.getY() > viewport.unproject(new Vector3(0, (Gdx.graphics.getHeight() / 2f) + player.getHeight()/2, 0)).y)
                    camera.translate(0, screenMoveSpeed);
        if (Gdx.input.isKeyPressed(Keys.S))
            if (camY > 0)
                if (player.getY() < viewport.unproject(new Vector3(0, (Gdx.graphics.getHeight() / 2f) + player.getHeight()/2, 0)).y)
                    camera.translate(0, -screenMoveSpeed);
        camera.update();
    }

    protected void checkCollisions(Entity e, Rectangle[] rects) {
        Rectangle eBounds = e.getBounds();
        float eX = e.getX();
        float eY = e.getY();
        float eOldX = e.getOldX();
        float eOldY = e.getOldY();
        float eW = e.getWidth();
        float eH = e.getHeight();
        float eS = e.getSpeed();

        Rectangle rightBound = new Rectangle(eX + eW - eS, eY + eS, eS, eH - eS * 2);
        Rectangle leftBound = new Rectangle(eX, eY + eS, eS, eH - eS * 2);
        Rectangle topBound = new Rectangle(eX + eS, eY + eH - eS, eW - eS * 2, eS); // top assuming that (0, 0) is bottom left
        Rectangle bottomBound = new Rectangle(eX + eS, eY, eW - eS * 2, eS);

        for (Rectangle rect : rects) {
            if (rect.overlaps(eBounds)) {
                if(rightBound.overlaps(rect) || leftBound.overlaps(rect))
                    e.setX(eOldX);
                if(topBound.overlaps(rect) || bottomBound.overlaps(rect))
                    e.setY(eOldY);
            }
        }
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
    }

}

