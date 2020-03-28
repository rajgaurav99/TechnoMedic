package android.example.technomedic;

class Disease {
    private final String name;
    private final String pro_name;
    private final double accuracy;
    private final String desc;
    private final String spec;

    public Disease(String name,String pro_name,double accuracy,String desc,String spec){
        this.name=name;
        this.pro_name=pro_name;
        this.accuracy=accuracy;
        this.desc=desc;
        this.spec=spec;
    }

    public String getName(){
        return name;
    }
    public String getProName(){
        return pro_name;
    }
    public double getAccuracy(){
        return accuracy;
    }
    public String getDesc(){
        return desc;
    }
    public String getSpec() {
        return spec;
    }
}
