package org.example.uploader.report;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Objects;
import java.util.stream.Stream;

import static com.fasterxml.jackson.annotation.JsonFormat.Shape.OBJECT;

@JsonFormat(shape = OBJECT)
public enum ReportKey {
    FLAG("flag"),
    SOURCE_TABLE("sourceTable"),
    TARGET_TABLE("targetTable"),
    RESULT("result"),
    TIME_TAKEN("timeTaken");

    private final String name;

    ReportKey(String name) {
        this.name = name;
    }

    @JsonCreator
    public static ReportKey forValue(String value) {
        return
                Stream.of(values())
                        .filter(reportKey -> Objects.equals(reportKey.getName(), value))
                        .findFirst()
                        .orElseThrow(RuntimeException::new);
    }

    @JsonValue
    public String getName() {
        return name;
    }
}
