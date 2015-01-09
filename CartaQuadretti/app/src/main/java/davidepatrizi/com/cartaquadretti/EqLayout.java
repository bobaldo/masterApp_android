package davidepatrizi.com.cartaquadretti;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Bobaldo on 09/01/2015.
 */
public class EqLayout {
    public EqLayout(Context context) {
        super(context);
    }

    public EqLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public EqLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        int lato = getLato();
        int w = (right-left)/lato;
        int h = (bottom-top)/lato;
        for (int i = 0; i < this.getChildCount(); i++) {
            View v = getChildAt(i);
            int x = i%lato, y = i/lato;
            v.layout(x*w, y*h, (x+1)*w, (y+1)*h);
        }
    }
    private int getLato() {
        return (int)Math.ceil(Math.sqrt(getChildCount()));
    }
}
