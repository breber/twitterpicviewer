package org.reber.twitpicviewer;

import android.graphics.Matrix;
import android.graphics.PointF;
import android.util.FloatMath;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;

public class Touch implements OnTouchListener {
	// These matrices will be used to move and zoom image
	private Matrix matrix = new Matrix();
	private Matrix savedMatrix = new Matrix();
	
	// We can be in one of these 3 states
	private static final int NONE = 0;
	private static final int DRAG = 1;
	private static final int ZOOM = 2;
	
	/**
	 * Our current mode
	 */
	private int mode = NONE;

	// Remember some things for zooming
	private PointF start = new PointF();
	private PointF mid = new PointF();
	private float oldDist = 1f;

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		ImageView view = (ImageView) v;

		// Handle touch events here...
		switch (event.getAction() & MotionEvent.ACTION_MASK) {
			// First finger touched
			case MotionEvent.ACTION_DOWN:
				savedMatrix.set(matrix);
				start.set(event.getX(), event.getY());
				mode = DRAG;
				
				break;
			// Second finger touched
			case MotionEvent.ACTION_POINTER_DOWN:
				oldDist = spacing(event);
				
				// If the distance between the two fingers
				// is greater than 10f, we know we are zooming
				if (oldDist > 10f) {
					savedMatrix.set(matrix);
					midPoint(mid, event);
					mode = ZOOM;
				}
				
				break;
			// Releasing one or both fingers
			case MotionEvent.ACTION_UP:
			case MotionEvent.ACTION_POINTER_UP:
				mode = NONE;
				
				break;
			// Moving one or more fingers
			case MotionEvent.ACTION_MOVE:
				if (mode == DRAG) {
					// Just have one finger down, so just move the matrix
					matrix.set(savedMatrix);
					matrix.postTranslate(event.getX() - start.x, event.getY() - start.y);
				} else if (mode == ZOOM) {
					// There are two fingers down, perform a scale
					float newDist = spacing(event);
					if (newDist > 10f) {
						matrix.set(savedMatrix);
						float scale = newDist / oldDist;
						matrix.postScale(scale, scale, mid.x, mid.y);
					}
				}
				
				break;
		}

		view.setImageMatrix(matrix);
		return true; // indicate event was handled
	}

	/** 
	 * Determine the space between the first two fingers 
	 * 
	 * @return the distance between the first two points
	 */
	private float spacing(MotionEvent event) {
		float x = event.getX(0) - event.getX(1);
		float y = event.getY(0) - event.getY(1);
		
		return FloatMath.sqrt(x * x + y * y);
	}

	/** 
	 * Calculate the mid point of the first two fingers 
	 */
	private void midPoint(PointF point, MotionEvent event) {
		float x = event.getX(0) + event.getX(1);
		float y = event.getY(0) + event.getY(1);
		
		point.set(x / 2, y / 2);
	}
}
