package datamodel;

import java.sql.Date;

public class CommonData {
   private String invoiceNo;
   private String orderNo;
   private String updatedBy;
   private Date updatedDate;

   public String getInvoiceNo() {
      return this.invoiceNo;
   }

   public void setInvoiceNo(String invoiceNo) {
      this.invoiceNo = invoiceNo;
   }

   public String getUpdatedBy() {
      return this.updatedBy;
   }

   public void setUpdatedBy(String updatedBy) {
      this.updatedBy = updatedBy;
   }

   public Date getUpdatedDate() {
      return this.updatedDate;
   }

   public void setUpdatedDate(Date updatedDate) {
      this.updatedDate = updatedDate;
   }

   public String getOrderNo() {
      return this.orderNo;
   }

   public void setOrderNo(String orderNo) {
      this.orderNo = orderNo;
   }
}
