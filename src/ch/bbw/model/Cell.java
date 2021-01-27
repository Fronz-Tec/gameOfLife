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
        this.isAlive = this.newState;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
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

    public void setOldState(boolean oldState) {
        this.oldState = oldState;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
