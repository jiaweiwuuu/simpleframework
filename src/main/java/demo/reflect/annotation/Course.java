package demo.reflect.annotation;
@CourseInfoAnnotation(courseName = "courseName",courseTag = "interview",courseProfile = "db")


public class Course {
    @PersonInfoAnnotation(name = "jiawei",language = {"java","go"})
    private String author;

    @CourseInfoAnnotation(courseName = "name",courseTag = "love",courseProfile = "girlfriend",courseIndex = 18)
    public void getCourseInfo(){

    }
}
