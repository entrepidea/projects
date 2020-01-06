package OCA1Z0_808;

/**
 * The answer in the material is not correct.
 *
 * */
class MissingInfoException extends Exception{}
class AgeOutOfRangeException extends Exception{}
class Candidate{
    private String name;
    private int age;
    public Candidate(String n, int a) throws Exception{
        if(n==null){
            throw new MissingInfoException();
        }
        else if(a>=150 || a <=10){
            throw new AgeOutOfRangeException();
        }
        else{
            name = n; age= a;
        }
    }
    @Override
    public String toString(){
        return name + " age:" + age;
    }
}

public class Q16 {
    public static void main(String[] args){
        try{
            Candidate c1, c2;
            c1 = new Candidate("Mike",34);
            c2 = new Candidate("John", 56);
            System.out.println(c1);
            System.out.println(c2);
        }
        catch(Exception e1){}
        //catch(MissingInfoException e2){}
        //catch(AgeOutOfRangeException e3){}
    }
}


