package android.example.technomedic;

class Symptom {
    private final String name;
    private final int id;
    private boolean added;

    public Symptom(String name,int id){
        this.name=name;
        this.id=id;
        this.added=false;
    }

    public String getName(){
        return name;
    }
    public int getId(){
        return id;
    }
    public boolean isAdded(){
        return added;
    }

    public void setAdded(boolean op){
        this.added=op;
    }
}
