package hawkeye.ena.ba.hawkeye.gui;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Created by ena on 2/3/17.
 *
 */

public class ListElement extends ListView{
    public ListElement(Context context) {
        super(context);
    }
    public ListElement(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    public ListElement(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    private void init(){
    }
    @Override
    public void onDraw(Canvas canvas) {
        // Use the base TextView to render the text.
        super.onDraw(canvas);
    }
}
