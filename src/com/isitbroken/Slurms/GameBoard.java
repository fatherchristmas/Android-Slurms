package com.isitbroken.Slurms;

import java.io.IOException;
import java.io.InputStream;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Handler;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

public class GameBoard extends SurfaceView implements SurfaceHolder.Callback{

	private boolean surfaceCreated;
	private GameThread thread;
	private Bitmap background;
	private SlurmsActivity activity;
	Handler handler;

	public GameBoard(Context context, SlurmsActivity slurmsActivity) {
		super(context, null);
		surfaceCreated = false;
		activity = slurmsActivity;
		getHolder().addCallback(this);
		AssetManager mgr = activity.getAssets();
		InputStream temp;
		try {
			temp = mgr.open("BackGrounds/l0jV8.png");
			background = BitmapFactory.decodeStream(temp);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		if (surfaceCreated == false) {
			createThread(holder);
			surfaceCreated = true;
		}
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		surfaceCreated = false;
	}

	public void terminateThread() {
		thread.run = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
		}
	}

	public void createThread(SurfaceHolder holder) {
		thread = new GameThread(this, holder);
		thread.run = true;
		thread.start();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		if (background != null) {
			canvas.drawBitmap(background, null, new Rect(0, 0, getWidth(),getHeight()), null);
		}
	}

	private void UpdateUI() {
		handler.post(new Runnable() {
			@Override
			public void run() {
				activity.UPdateUI();
			}
		});
	}

}
