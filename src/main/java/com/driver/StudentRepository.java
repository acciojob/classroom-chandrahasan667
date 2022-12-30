package com.driver;

import java.util.*;

import org.springframework.stereotype.Repository;

@Repository
public class StudentRepository {

  private  HashMap<String, Student> studentMap;

  private  HashMap<String, Teacher> teacherMap;

  private HashMap<String, List<String>> teacherStudentMapping;


    public StudentRepository() {
        this.studentMap = new HashMap<String, Student>();
        this.teacherMap = new HashMap<String, Teacher>();
        this.teacherStudentMapping = new HashMap<String, List<String>>();
    }

    public void saveStudent(Student student){
        studentMap.put(student.getName(),student);
    }

    public void saveTeacher(Teacher teacher){
        teacherMap.put(teacher.getName(), teacher);
    }

    public void saveStudentTeacherPair(String student, String teacher){

        //1. Add the movie into Datbase ---> WRONG bcz I dont have te movie object

        if(studentMap.containsKey(student)&&teacherMap.containsKey(teacher)){


            List<String> currentStudentsByTeacher = new ArrayList<>();

            if(teacherStudentMapping.containsKey(teacher))
                currentStudentsByTeacher = teacherStudentMapping.get(teacher);

            currentStudentsByTeacher.add(student);

            teacherStudentMapping.put(teacher,currentStudentsByTeacher);

        }
    }

    public Student findStudent(String student){

        return studentMap.get(student);
    }

    public Teacher findTeacher(String teacher){
        return teacherMap.get(teacher);
    }

    public List<String> findStudentsFromTeacher(String teacher){
        List<String> studentsList = new ArrayList<String>();
        if(teacherStudentMapping.containsKey(teacher)) studentsList = teacherStudentMapping.get(teacher);
        return studentsList;
    }

    public List<String> findAllStudents(){

        return new ArrayList<>(studentMap.keySet());
    }

    public void deleteTeacher(String teacher){

        List<String> students = new ArrayList<String>();
        if(teacherStudentMapping.containsKey(teacher)){
            //1. Find the movie names by director from the pair
            students = teacherStudentMapping.get(teacher);

            //2. Deleting all the movies from movieDb by using movieName
            for(String student: students){
                if(studentMap.containsKey(student)){
                    studentMap.remove(student);
                }
            }

            //3. Deleteing the pair
            teacherStudentMapping.remove(teacher);
        }

        //4. Delete the director from directorDb.
        if(teacherMap.containsKey(teacher)){
            teacherMap.remove(teacher);
        }
    }

    public void deleteAllTeacher(){

        HashSet<String> studentsSet = new HashSet<String>();

        //Deleting the director's map
        teacherMap = new HashMap<>();

        //Finding out all the movies by all the directors combined
        for(String teacher: teacherStudentMapping.keySet()){

            //Iterating in the list of movies by a director.
            for(String student: teacherStudentMapping.get(teacher)){
                studentsSet.add(student);
            }
        }

        //Deleting the movie from the movieDb.
        for(String student: studentsSet){
            if(studentMap.containsKey(student)){
                studentMap.remove(student);
            }
        }
        //clearing the pair.
        teacherStudentMapping = new HashMap<>();
    }




}
