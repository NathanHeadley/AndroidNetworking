package zelphinstudios.courseworkapp.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class BitmapDecoder {

	// Variables
	private Context context;


	// Constructor
	public BitmapDecoder(Context context_) {
		context = context_;
	}


	// Methods
	public Bitmap decode(String bitmap_) {
		return BitmapFactory.decodeResource(context.getResources(),
				context.getResources().getIdentifier(bitmap_, "drawable", context.getPackageName()));
	}
}
