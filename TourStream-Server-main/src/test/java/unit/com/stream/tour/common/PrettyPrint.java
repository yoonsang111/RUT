package com.stream.tour.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.lang.Nullable;
import org.springframework.test.web.servlet.ResultHandler;
import org.springframework.test.web.servlet.result.PrintingResultHandler;
import org.springframework.util.CollectionUtils;

import java.io.OutputStream;
import java.io.PrintWriter;

public class PrettyPrint extends PrintingResultHandler {

    public static ResultHandler print() {
        return print(System.out);
    }

    public static ResultHandler print(OutputStream stream) {
        return new PrettyPrint(new PrintWriter(stream, true));
    }

    public PrettyPrint(PrintWriter writer) {
        super(new ResultValuePrinter() {
            @Override
            public void printHeading(String heading) {
                writer.println();
                writer.println(String.format("%s:", heading));
            }

            @Override
            public void printValue(String label, @Nullable Object value) {
                if (value != null && value.getClass().isArray()) {
                    printArrayValue(label, value);
                } else if (value != null && value.getClass().isAssignableFrom(String.class) && !"".equals(value) && isJson(value.toString())) {
                    printJsonValue(label, value);
                } else {
                    printSimpleValue(label, value);
                }
            }

            private boolean isJson(String value) {
                return value.startsWith("{") && value.endsWith("}");
            }

            private void printArrayValue(String label, Object value) {
                value = CollectionUtils.arrayToList(value);
                writer.println(String.format("%17s = %s", label, value));
            }

            private void printJsonValue(String label, Object value) {
                try {
                    ObjectMapper objectMapper = new ObjectMapper();
                    Object json = objectMapper.readValue(value.toString(), Object.class);
                    value = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(json);
                    writer.println(String.format("%17s = {", label));

                    String[] split = ((String) value).split("\n");
                    for (String s : split) {
                        if (s.startsWith("{")) {
                            continue;
                        }
                        writer.println(String.format("%-20s%-100s", " ", s));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            private void printSimpleValue(String label, Object value) {
                writer.println(String.format("%17s = %s", label, value));
            }
        });
    }
}

