package com.stream.tour.global.config;

import com.p6spy.engine.common.P6Util;
import com.p6spy.engine.spy.appender.MessageFormattingStrategy;

public class P6spySqlFormatConfig implements MessageFormattingStrategy {
    @Override
    public String formatMessage(int connectionId, String now, long elapsed, String category, String prepared, String sql, String url) {
        return "took " + elapsed + "ms|" + category + "|connection " + connectionId + "|singleLineSql -> " + P6Util.singleLine(sql);
    }
}