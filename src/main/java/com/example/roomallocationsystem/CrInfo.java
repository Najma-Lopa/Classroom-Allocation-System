package com.example.roomallocationsystem;

public class CrInfo {
    private int id;
    private String name;
    private String faculty;
    private String dept;
    private String level;
    private String semester;
    private String phn;
    private String mail;




    public CrInfo(int id, String name, String faculty, String dept, String level, String semester,String phn,String mail) {
        this.id = id;
        this.name = name;
        this.faculty = faculty;
        this.dept = dept;
        this.level = level;
        this.semester = semester;
        this.phn = phn;
        this.mail = mail;
    }

    // Getters
    public int getId() { return id; }
    public String getName() { return name; }
    public String getFaculty() { return faculty; }
    public String getDept() { return dept; }
    public String getLevel() { return level; }
    public String getSemester() { return semester; }
    public String getPhn() { return phn; }
    public String getMail() { return mail; }
}
