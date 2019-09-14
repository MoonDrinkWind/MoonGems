package moon.atbar.egems;

import java.util.List;

public class GemsConfig {

    List<Integer> can;
    String Enchant;
    Integer level;
    Integer Successrate;

    public List<Integer> getCan() {
        return can;
    }

    public void setCan(List<Integer> can) {
        this.can = can;
    }

    public String getEnchant() {
        return Enchant;
    }

    public void setEnchant(String enchant) {
        Enchant = enchant;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getSuccessrate() {
        return Successrate;
    }

    public void setSuccessrate(Integer successrate) {
        Successrate = successrate;
    }

}
