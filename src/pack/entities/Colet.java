package pack.entities;


public class Colet {

    private Integer number;
    private Integer masa;

    public Colet(Integer number, Integer masa) {
        this.number = number;
        this.masa = masa;
    }


    public Integer getNumber() {
        return number;
    }

    public Integer getMasa() {
        return masa;
    }

    @Override
    public String toString() {
        return number + "," + masa + ",";
    }
}
