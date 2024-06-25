package datamodel;

import java.util.Map;

public class AddData extends CommonData {
   private String to;
   private String updatedBy;
   private String delInd;
   private String dated;
   private String orderDate;
   private Map goodsValues;
   private int compCount;

   public String getTo() {
      return this.to;
   }

   public void setTo(String to) {
      this.to = to;
   }

   public String getUpdatedBy() {
      return this.updatedBy;
   }

   public void setUpdatedBy(String updatedBy) {
      this.updatedBy = updatedBy;
   }

   public String getDelInd() {
      return this.delInd;
   }

   public void setDelInd(String delInd) {
      this.delInd = delInd;
   }

   public String getDated() {
      return this.dated;
   }

   public void setDated(String dated) {
      this.dated = dated;
   }

   public String getOrderDate() {
      return this.orderDate;
   }

   public void setOrderDate(String orderDate) {
      this.orderDate = orderDate;
   }

   public Map getGoodsValues() {
      return this.goodsValues;
   }

   public void setGoodsValues(Map goodsValues) {
      this.goodsValues = goodsValues;
   }

   public int getCompCount() {
      return this.compCount;
   }

   public void setCompCount(int compCount) {
      this.compCount = compCount;
   }
}
