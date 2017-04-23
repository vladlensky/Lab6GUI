package classes;

/**
 * Created by Mugenor on 23.02.2017.
 */

import java.util.ArrayList;

public class NormalHuman extends Human implements Comparable<NormalHuman>{
    protected Long age=1l;
    protected ArrayList<Thinkable> thoughts;
    public NormalHuman(String name) throws KarlsonNameException{
        super(name);
        this.thoughts = new ArrayList<Thinkable>();
    }
    public NormalHuman(){
        super();
        this.thoughts = new ArrayList<Thinkable>();
    }
    public class Thoughts implements Thinkable {
        protected String thought;
        public void thinkAbout(String th){
            thought=th;
        }
        public void thinkAbout(Thinkable th){
            thought=th.toString();
        }
        public void forgetIt(){
            this.thought=null;
        }
        public boolean equals(Object th) {
            if (this == th) return true;
            if (th == null || !(th instanceof Thoughts)) return false;
            Thoughts thoughts = (Thoughts) th;
            return thought.equals(thoughts.thought);
        }
        public int hashCode() {
            return thought != null ? thought.hashCode() : 0;
        }
        public String toString(){
            return this.thought;
        }
    }
    public String getThoughts(int i){
        if(i<=thoughts.size() && i>=0){return thoughts.get(i).toString();}
        else throw new ThoughtIndexException();
    }
    public void forgetThought(int i){thoughts.remove(i);}
    public Long getAge(){
        return age;
    }
    public void setAge(long age){
        this.age=age;
    }
    public int getThoughtsCount(){
        return thoughts.size();
    }
    public void thinkAbout(String th){
        Thoughts thought = new Thoughts();
        thought.thinkAbout(th);
        thoughts.add(thought);
    }
    public void thinkAbout(String th, int i){
        if(i<=thoughts.size() && i>=0){
            Thoughts thought = new Thoughts();
            thought.thinkAbout(th);
            thoughts.add(i, thought);}
        else throw new ThoughtIndexException();
    }
    public void thinkAbout(Thinkable th,int i){
        if(i<=thoughts.size() && i>=0)thoughts.add(th);
        else throw new ThoughtIndexException();
    }
    public void thinkAbout(Thinkable th){
        thoughts.add(th);
    }
    public ArrayList getAllThoughts(){
        return thoughts;
    }
    public boolean equals(Object nh) {
        if (this == nh) return true;
        if (nh == null || !(nh instanceof NormalHuman)) return false;
        if(!super.equals(nh)) return false;
        NormalHuman normalHuman = (NormalHuman) nh;
        return thoughts.equals(normalHuman.getAllThoughts());
    }
    public int hashCode() {
        int r = super.hashCode();
        r = 31*r+ thoughts.hashCode();
        return r;
    }
    public String toString() {
        return super.toString() +
                "\nГоловные мысли: " + thoughts.toString();
    }
    public int countOfThoughts(){return thoughts.size();}
    public int compareTo(NormalHuman nh){
        return super.name.length()-nh.getName().length()+countOfThoughts()-nh.countOfThoughts()+(troublesWithTheLaw ? 10: -10) - (nh.getTroublesWithTheLaw() ? 10: -10);
    }
}