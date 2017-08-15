package yuejunfeng.bawei.com.mycustomview.bean;

import yuejunfeng.bawei.com.mycustomview.util.PinyingUtil;

/**
 * Created by admin on 2017/8/15.
 */

public class NameBean implements Comparable<NameBean>{
    private String name;
    private String pinyin;

    public NameBean(String name) {
        this.name = name;
        //使用工具类，根据汉字拿到拼音
        this.pinyin= PinyingUtil.getpinyin(name);
    }

    public String getName() {
        return name;
    }

    public String getPinyin() {
        return pinyin;
    }

    @Override
    public int compareTo(NameBean nameBean) {
        return this.pinyin.compareTo(nameBean.pinyin);
    }
}
