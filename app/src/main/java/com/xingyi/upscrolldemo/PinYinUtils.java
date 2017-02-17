package com.xingyi.upscrolldemo;

import android.text.TextUtils;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

/**
 * Created by feilong.guo on 17/2/17.
 */

public class PinYinUtils {
    private static final HanyuPinyinOutputFormat OUTPUT_FORMAT = new HanyuPinyinOutputFormat();
    private static final String EMPTY = "";

    static {
        OUTPUT_FORMAT.setCaseType(HanyuPinyinCaseType.UPPERCASE);
        OUTPUT_FORMAT.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
    }

    private PinYinUtils() {
    }

    public static PinYinUtils getInstance() {
        return SingletonInstance.INSTANCE;
    }

    private static class SingletonInstance {
        private static final PinYinUtils INSTANCE = new PinYinUtils();
    }

    /**
     * 汉字转拼音的方法
     *
     * @param chineseSource 汉字
     * @return 拼音
     */
    public String getPinYin(String chineseSource) {
        String pinYin = "";
        if (TextUtils.isEmpty(chineseSource)) {
            return pinYin;
        }
        try {
            pinYin = PinyinHelper.toHanYuPinyinString(chineseSource, OUTPUT_FORMAT, EMPTY, true);
        } catch (BadHanyuPinyinOutputFormatCombination badHanyuPinyinOutputFormatCombination) {
            badHanyuPinyinOutputFormatCombination.printStackTrace();
        }
        return pinYin;
    }


}
