package yuejunfeng.bawei.com.mycustomview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by admin on 2017/8/14.
 */

public class QieckInderxBar extends View{

    private Paint paint;
    private static final String[] LETTERS = new String[]{
            "A", "B", "C", "D", "E", "F",
            "G", "H", "I", "J", "K", "L",
            "M", "N", "O", "P", "Q", "R",
            "S", "T", "U", "V", "W", "X",
            "Y", "Z"
    };
    private float mwidth;
    private float callheight;
    private String letter;
    private float mY;
    private int currentIndex;

    public QieckInderxBar(Context context) {
        this(context,null);
    }

    public QieckInderxBar(Context context, AttributeSet attrs) {
       this(context, attrs,0);
    }

    public QieckInderxBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //初始化画笔
        initPaint();
    }

    private void initPaint() {
        //创建一个抗锯齿的画笔
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        //画笔加粗
        paint.setTypeface(Typeface.DEFAULT_BOLD);
        //颜色
        paint.setColor(Color.WHITE);

        paint.setTextSize(25);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        for (int i = 0; i < LETTERS.length; i++) {
            //从数组，根据I取出字母5
            letter = LETTERS[i];
            float x = mwidth * 0.5f - paint.measureText(letter) * 0.5f;
            float y = callheight * 0.5f + paint.measureText(letter) * 0.5f + i * callheight;
            canvas.drawText(letter,x,y,paint);
        }

    }
    //记录用户上一次按下的位置，以便进行判断这一次所按住的位置是否还是上一次的位置，如果是的话，不做任何处理
    private int lastIndex = -1;
    //重写触摸事件，返回值为true,方起效果
      @Override
          public boolean onTouchEvent(MotionEvent event) {
              switch (event.getAction()){
                  //计算用户按到那个字母的范围，主要是Y轴
                  case MotionEvent.ACTION_DOWN://按下,自动调用
                  case MotionEvent.ACTION_MOVE://移动,自动调用
                      //获取被点击的字母索引
                      mY = event.getY();
                      //获取用户点击的位置
                      currentIndex = (int) (mY / callheight);
                      //为了防止一个字母按住，不停的重复调用，进行判断，判断是否还是按着上一个字母，是的话，就不做任何处理，提高程序的性能
                      if(currentIndex != lastIndex){
                        //  为了防止角标越界，我们只在用户按住的Y轴值大于0，小于数组长度方执行
                          if(currentIndex >= 0 && currentIndex < LETTERS.length){
                              String letter = LETTERS[currentIndex];
                              if(mLetterChangeListener !=null){
                                  mLetterChangeListener.OnLetterChange(letter);
                              }
                              lastIndex=currentIndex;
                          }
                      }
                      break;
                  //松开
                  case MotionEvent.ACTION_UP:
                      break;
              }


              return true;
          }
    //完成侧拉索引的测量，得到单元的宽高
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //获取控件的宽高
        int mheight = getMeasuredHeight();
        mwidth = getMeasuredWidth();
        //获取单元格的高度,由自定义控件总高度，除以所有字母所占有的高度
        callheight = mheight * 1.0f / LETTERS.length;


    }
    //定义接口
    public interface OnLetterChangeListener{
        void OnLetterChange(String letter);
    }
    // 定义接口对象
    private OnLetterChangeListener mLetterChangeListener;

    public OnLetterChangeListener getLetterChangeListener() {
        return mLetterChangeListener;
    }
    //暴露接口，让外界传过来一个实现接口的类对象
    public void setLetterChangeListener(OnLetterChangeListener mLetterChangeListener) {
        this.mLetterChangeListener = mLetterChangeListener;
    }

}
