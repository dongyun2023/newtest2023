package com.game.yangtechplatform.utils;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.widget.EditText;

public class MyEditextview extends EditText {
	private Context mContext;

	public MyEditextview(Context context) {
		super(context);
		mContext = context;
	}

	public MyEditextview(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		mContext = context;
	}

	public MyEditextview(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch (event.getKeyCode()) {
		case 1:

			break;

		}
		return super.onKeyDown(keyCode, event);
	}
}
