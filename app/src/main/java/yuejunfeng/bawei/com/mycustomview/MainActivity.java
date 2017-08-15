package yuejunfeng.bawei.com.mycustomview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

import yuejunfeng.bawei.com.mycustomview.bean.NameBean;
import yuejunfeng.bawei.com.mycustomview.toast.ToastUtils;

/**
 * 1.绘制A-Z的字母列表
 2.响应触摸事件
 3.提供监听回调
 4.获取汉字的拼音，首字母（pinying4j通过汉字得到他的拼音只能一个字符一个字符的去转换成拼音）
 5.根据拼音排序
 6.根据首字母分组
 7.把监听回调和listView结合起来
 */
public class MainActivity extends AppCompatActivity {

    private QieckInderxBar qieck;
    private ListView list_view;

    private ArrayList<NameBean> persons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //取消他的ToolBar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        qieck = (QieckInderxBar) findViewById(R.id.qieck);
       /* qieck.setLetterChangeListener(new QieckInderxBar.OnLetterChangeListener() {
            @Override
            public void OnLetterChange(String letter) {
                ToastUtils.showToast(MainActivity.this,letter);
            }
        });*/
        list_view = (ListView) findViewById(R.id.list_view);
        persons = new ArrayList<>();
        fillAndSortData(persons);
        MyBase myBase = new MyBase();
        list_view.setAdapter(myBase);
        qieck.setLetterChangeListener(new QieckInderxBar.OnLetterChangeListener() {
            @Override
            public void OnLetterChange(String letter) {

                for (int i = 0; i < persons.size(); i++) {
                    ToastUtils.showToast(MainActivity.this,letter);
                    String s = persons.get(i).getPinyin().charAt(0) + "";
                    if(TextUtils.equals(s,letter)) {
                        list_view.setSelection(i);
                        break;
                    }
                }

            }
        });
    }

    private void fillAndSortData(ArrayList<NameBean> persons) {
        for (int i = 0; i < Chinese.NAMES.length; i++) {
            String name = Chinese.NAMES[i];
            persons.add(new NameBean(name));
        }
        Collections.sort(persons);
    }
    class MyBase extends BaseAdapter{
        @Override
        public int getCount() {
            return persons.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            ViewHolder holder;
            if(view == null){
                holder = new ViewHolder();
                view=View.inflate(MainActivity.this,R.layout.item,null);
                holder.names= (TextView) view.findViewById(R.id.tv_name);
                holder.index= (TextView) view.findViewById(R.id.tv_index);
                view.setTag(holder);
            }else{
                holder = (ViewHolder) view.getTag();
            }

            NameBean nameBean = persons.get(i);
            //当前首字母
            String currentStr = nameBean.getPinyin().charAt(0) + "";
            String indexStr = null;
            //如果是第一个名字，直接显示
            if(i == 0){
                indexStr = currentStr;
            }else{
                //判断当前首字母和上一个条目的首字母是否一致，不一致时候显示完整ITEM界面
                String s = persons.get(i-1 ).getPinyin().charAt(0) + "";
                //判断两个参数是否一致，不一致就执行赋值的逻辑
                if(!TextUtils.equals(s,currentStr)){
                    //不一致的时候赋值indexStr
                    indexStr = currentStr;
                }

            }
            holder.index.setVisibility(indexStr !=null ?View.VISIBLE :View.GONE );
            holder.index.setText(currentStr);
            holder.names.setText(persons.get(i).getName());
            return view;
        }
    }
    class ViewHolder{

        TextView names,index;
    }
}
