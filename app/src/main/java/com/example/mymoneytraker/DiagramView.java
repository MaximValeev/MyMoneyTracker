package com.example.mymoneytraker;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;


public class DiagramView extends View {
    private long expense;
    private long income;

    private Paint expensesPaint = new Paint();
    private Paint incomePaint = new Paint();

    public DiagramView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        expensesPaint.setColor(getResources().getColor(R.color.balance_expense_color));
        incomePaint.setColor(getResources().getColor(R.color.balance_income_color));

        if(isInEditMode()){
            expense = 4500;
            income = 19000;
        }
    }

    public void update (long expense, long income){
        this.expense = expense;
        this.income = income;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            drawPieDiagram(canvas);
        } else drawRectDiagram(canvas);
    }

    public void drawRectDiagram(Canvas canvas){
        if (expense + income == 0){
            return;
        }

        long max = Math.max(expense, income);
        long expensesHeight = getHeight() * expense / max;
        long incomeHeight = getHeight() * income / max;
        int w = getWidth() / 4;

        canvas.drawRect(w / 2, getHeight() - expensesHeight, w * 3 / 2, getHeight(), expensesPaint);
        canvas.drawRect(5 * w / 2, getHeight() - incomeHeight, w * 7 / 2, getHeight(), incomePaint);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void drawPieDiagram(Canvas canvas){
        if (expense + income == 0){
            return;
        }

        float expensesAngle = 360.f * expense / (expense + income);
        float incomeAngle = 360.f * income / (income + expense);
        int space = 10;
        int size = Math.min(getWidth(), getHeight()) - space / 2;
        final int xMargin = (getWidth() - size) / 2;
        final  int yMargin = (getHeight() - size) / 2;

        canvas.drawArc(xMargin - space, yMargin, getWidth() - xMargin - space, getHeight() - yMargin, 180 - expensesAngle / 2, expensesAngle, true, expensesPaint);
        canvas.drawArc(xMargin + space, yMargin, getWidth() - xMargin + space, getHeight() - yMargin, 360 - incomeAngle / 2, incomeAngle, true, incomePaint );
    }
}
