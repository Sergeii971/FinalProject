package by.balashevich.finalproject.controller.command;

public enum PageName {
    INDEX("/index.jsp"),
    LOGIN("/jsp/login.jsp"),
    REGISTER("/jsp/register.jsp"),
    ERROR("/jsp/error.jsp"),
    HOME("/jsp/home.jsp");

    private String path;

    PageName(String path){
        this.path = path;
    }

    public String getPath(){
        return path;
    }
}