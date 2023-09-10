package academy.prog;

public class TextProcessing {

    public static String getTo(String text){
        String to = "All";
        if (text.startsWith("--get") || text.startsWith("--room")){
            to = "SERVER";
        }
        if (text.startsWith("@") || text.startsWith("#")){
            to = text.substring(0,text.indexOf(" "));
        }
        return to;
    }

    public static String getText(String text){
        if (text.startsWith("@") || text.startsWith("#")){
            text = text.substring(text.indexOf(" ")+1);
        }
        return text;
    }
}
