package dmcjj.rmitpp.toiletlocator.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by A on 9/09/2017.
 */

public class CustomView extends View
{
    Paint paint = new Paint();

    Rect rect = new Rect();

    public CustomView(Context context) {
        super(context);


    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {


        setMeasuredDimension(500, 500);

    }

    @Override
    protected void onDraw(Canvas canvas) {

        canvas.drawColor(Color.RED);

        rect.bottom = 200;
        rect.right = 200;



        //canvas.drawRect(rect, paint);



        canvas.drawText("hello world", 50,50, paint);





    }



}
