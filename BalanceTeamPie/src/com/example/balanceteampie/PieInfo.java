package com.example.balanceteampie;

public class PieInfo {
   private int countLevel;
   private int resId;
   private int lv1Pic;
   private int lv2Pic;
   private int lv3Pic;
   private int lv4Pic;
   private int hotspotColor;
   
   PieInfo(int rId, int lv1, int lv2, int lv3, int lv4, int hs) {
      resId = rId;
      lv1Pic = lv1;
      lv2Pic = lv2;
      lv3Pic = lv3;
      lv4Pic = lv4;
      countLevel = 0;
      hotspotColor = hs;
   }
   
   public void setCountLevel() { countLevel++; }
   public void resetLevel() { countLevel = 0; }
   public int getCountLevel() { return countLevel; }
   public int getResId() { return resId; }
   public int getLv1Pic() { return lv1Pic; }
   public int getLv2Pic() { return lv2Pic; }
   public int getLv3Pic() { return lv3Pic; }
   public int getLv4Pic() { return lv4Pic; }
   public int getColor() { return hotspotColor; }

}
