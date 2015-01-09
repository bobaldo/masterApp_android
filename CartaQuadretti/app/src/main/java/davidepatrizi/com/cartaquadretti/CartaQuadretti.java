package davidepatrizi.com.cartaquadretti;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 * Created by Bobaldo on 09/01/2015.
 */

public class CartaQuadretti extends EditText {
    int dimquad;
    Paint righini = new Paint();

    public CartaQuadretti(Context context) {
        super(context);
        init(context, null, 0);
    }

    public CartaQuadretti(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs, defStyle);
    }

    public CartaQuadretti(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    private void init(Context context, AttributeSet attrs, int defStyle) {
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.CartaQuadretti,
                defStyle, 0);
        try {
            dimquad = a.getInteger(R.styleable.CartaQuadretti_dimquad, 20);
          int  r = a.getColor(R.styleable.CartaQuadretti_righino, 2);

        } finally {
            a.recycle();
        }
    }

    protected void onDraw(Canvas canvas) {
        int w = getWidth(), h = getHeight();
        for (int x = 0; x < w; x += dimquad)
            canvas.drawLine(x, 0, x, h, righini);
        for (int y = 0; y < h; y += dimquad)
            canvas.drawLine(0, y, w, y, righini);
        super.onDraw(canvas);
    }

    public void setDimQuad(int n) {
        if (n > 0) {
            dimquad = n;
            invalidate();
        }
    }
}