package classes;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;

import androidx.core.content.ContextCompat;

import com.example.activities.R;

public class CustomThumbDrawable extends Drawable {
    private Paint backgroundPaint;
    private Paint textPaint;
    private String text;
    private Rect textBounds = new Rect();
    private Context context;
    private int width;
    private int height;

    public CustomThumbDrawable(Context context, String initialText, int initialBackgroundColor) {
        this.context = context;
        this.text = initialText;

        // Инициализируем кисть для фона
        backgroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        backgroundPaint.setColor(initialBackgroundColor); // Устанавливаем начальный цвет фона
        backgroundPaint.setStyle(Paint.Style.FILL);

        // Инициализируем кисть для текста
        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setColor(Color.WHITE);
        textPaint.setTextSize(14 * context.getResources().getDisplayMetrics().scaledDensity);
        textPaint.setTextAlign(Paint.Align.CENTER);

        // Получаем размеры из ресурсов
        height = (int) context.getResources().getDimension(R.dimen.seekbar_thumb_height);
        width = (int) context.getResources().getDimension(R.dimen.seekbar_thumb_width);
    }

    // Метод для обновления текста на ползунке
    public void setText(String newText) {
        this.text = newText;
        invalidateSelf(); // Перерисовываем Drawable с новым текстом
    }

    // Метод для обновления цвета фона
    public void setBackgroundColor(int color) {
        backgroundPaint.setColor(color);
        invalidateSelf(); // Перерисовываем Drawable с новым цветом
    }

    @Override
    public void draw(Canvas canvas) {
        // Получаем границы Drawable
        Rect bounds = getBounds();

        // Рисуем прямоугольник с закругленными углами
        float radius = height / 2f;
        RectF rectF = new RectF(bounds.left, bounds.top, bounds.right, bounds.bottom);
        canvas.drawRoundRect(rectF, radius, radius, backgroundPaint);

        // Рисуем текст в центре
        float centerX = bounds.centerX();
        float centerY = bounds.centerY() - (textPaint.descent() + textPaint.ascent()) / 2;
        canvas.drawText(text, centerX, centerY, textPaint);
    }

    @Override
    public void setAlpha(int alpha) {
        backgroundPaint.setAlpha(alpha);
        textPaint.setAlpha(alpha);
    }

    @Override
    public void setColorFilter(android.graphics.ColorFilter colorFilter) {
        backgroundPaint.setColorFilter(colorFilter);
        textPaint.setColorFilter(colorFilter);
    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }

    // Переопределяем размеры по умолчанию
    @Override
    public int getIntrinsicWidth() {
        return width;
    }

    @Override
    public int getIntrinsicHeight() {
        return height;
    }

    private int dpToPx(int dp) {
        float density = context.getResources().getDisplayMetrics().density;
        return Math.round((float) dp * density);
    }
}
