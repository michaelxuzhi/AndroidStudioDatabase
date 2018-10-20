package com.example.michaelxuzhi.atry;


public class Student {
    public int ID=-1;
    public String Name;
    public String Sex;
    public int Totalcredits;
    public String toString(){
        String results = " ";
        results +="ID: "+this.ID;
        results +="姓名: "+this.Name;
        results +="性别: "+this.Sex;
        results +="总学分: "+this.Totalcredits;
        return results;

    }

}
