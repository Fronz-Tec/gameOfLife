package ch.bbw.model;



public class Cell {

    boolean isAlive;
    boolean newState;
    boolean oldState;
    int age;

    public Cell() {
        this.isAlive = false;
        this.newState = false;
        this.oldState = false;
        this.age = 0;
    }

    public void updateState(){
        this.oldState = this.isAlive;
        this.isAlive = this.newState;

        if(isAlive){
            age++;
        }else{
            age = 0;
        }

    }

    public boolean isAlive() {
        return isAlive;
    }

    public boolean isNewState() {
        return newState;
    }

    public void setNewState(boolean newState) {
        this.newState = newState;
    }

    public boolean isOldState() {
        return oldState;
    }


    public int getAge() {
        return age;
    }

    public void resetAge(){
        age=0;
    }
}
