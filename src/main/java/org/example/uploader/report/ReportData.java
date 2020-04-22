package org.example.uploader.report;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"flag", "sourceTable", "targetTable", "result", "timeTaken"})
public class ReportData {
    private String flag;
    private String sourceTable;
    private String targetTable;
    private String result;
    private String timeTaken;

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getSourceTable() {
        return sourceTable;
    }

    public void setSourceTable(String sourceTable) {
        this.sourceTable = sourceTable;
    }

    public String getTargetTable() {
        return targetTable;
    }

    public void setTargetTable(String targetTable) {
        this.targetTable = targetTable;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getTimeTaken() {
        return timeTaken;
    }

    public void setTimeTaken(String timeTaken) {
        this.timeTaken = timeTaken;
    }

    @Override
    public String toString() {
        return "ReportData{" +
                "flag='" + flag + '\'' +
                ", sourceTable='" + sourceTable + '\'' +
                ", targetTable='" + targetTable + '\'' +
                ", result='" + result + '\'' +
                ", timeTaken='" + timeTaken + '\'' +
                '}';
    }
}
