package yuejunfeng.bawei.com.mycustomview.util;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

/**
 * Created by admin on 2017/8/15.
 */

public class PinyingUtil {
    public static String getpinyin(String string){
        HanyuPinyinOutputFormat hpof=new HanyuPinyinOutputFormat();

        hpof.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        //设置转换出大写字母
        hpof.setCaseType(HanyuPinyinCaseType.UPPERCASE);



        //返回数组
        char[] chars = string.toCharArray();
        //效率高
        StringBuilder stringBuilder = new StringBuilder();


        for (int i = 0; i < chars.length; i++) {
            char c= chars[i];
            //如果是空格，跳过当前循环
            if(Character.isWhitespace(c)){
                continue;
            }
            //是不是汉字，如果不是汉字，直接拼写
            if(c > -128 && c<127){
                stringBuilder.append(c);
            }else{
                try {
                    String s = PinyinHelper.toHanyuPinyinStringArray(c, hpof)[0];
                    stringBuilder.append(s);
                } catch (BadHanyuPinyinOutputFormatCombination badHanyuPinyinOutputFormatCombination) {
                    badHanyuPinyinOutputFormatCombination.printStackTrace();
                }

            }
        }


        return stringBuilder.toString();
    }


}
