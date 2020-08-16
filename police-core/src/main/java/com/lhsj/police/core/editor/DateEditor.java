package com.lhsj.police.core.editor;

import com.lhsj.police.core.log.ReLogs;
import com.lhsj.police.core.time.ReDateFormats;
import com.google.common.base.Strings;

import java.beans.PropertyEditorSupport;
import java.util.regex.Pattern;

import static com.lhsj.police.core.time.ReDateFormats.PATTERN_DEFAULT;
import static com.lhsj.police.core.time.ReDateFormats.PATTERN_DEFAULT_ON_SECOND;
import static com.lhsj.police.core.time.ReDateFormats.PATTERN_ISO_ON_DATE;

/**
 * date editor
 */
public class DateEditor extends PropertyEditorSupport {

    private static final Pattern DATE_REG             = Pattern.compile("\\d{4}-\\d{2}-\\d{2}");
    private static final Pattern DATE_TIME_REG        = Pattern.compile("\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}");
    private static final Pattern DATE_TIME_MILLIS_REG = Pattern.compile("\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}.\\d{1,3}");

    @Override
    public void setAsText(String text) {
        if (Strings.isNullOrEmpty(text)) {
            setValue(null);
            return;
        }
        try {
            if (DATE_TIME_REG.matcher(text).matches()) {
                setValue(ReDateFormats.parseDate(PATTERN_DEFAULT_ON_SECOND, text));
                return;
            }
            if (DATE_TIME_MILLIS_REG.matcher(text).matches()) {
                setValue(ReDateFormats.parseDate(PATTERN_DEFAULT, text));
                return;
            }
            if (DATE_REG.matcher(text).matches()) {
                setValue(ReDateFormats.parseDate(PATTERN_ISO_ON_DATE, text));
            }
        } catch (Throwable e) {
            ReLogs.info("date parse error: ", e);
        }
    }

}