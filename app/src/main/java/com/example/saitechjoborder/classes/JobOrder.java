package com.example.saitechjoborder.classes;

public class JobOrder {
    private int jobOrderNo;
    private int formalQuotationNo;

    public JobOrder(int jobOrderNo, int formalQuotationNo){
        this.jobOrderNo = jobOrderNo;
        this.formalQuotationNo = formalQuotationNo;
    }

    public void setJobOrderNo(int jobOrderNo){
        this.jobOrderNo = jobOrderNo;
    }

    public int getJobOrderNo(){
        return jobOrderNo;
    }

    public void setFormalQuotationNo(int formalQuotationNo){
        this.formalQuotationNo = formalQuotationNo;
    }

    public int getFormalQuotationNo(){
        return formalQuotationNo;
    }
}
