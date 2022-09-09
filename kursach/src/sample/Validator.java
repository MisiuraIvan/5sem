package sample;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class Validator {
    private Pattern pattern;
    private Matcher matcher;

    public Validator(String str){
        pattern=Pattern.compile(str);
    }

    public boolean validate(String str){
        matcher=pattern.matcher(str);
        System.out.println(matcher.matches());
        return matcher.matches();
    }
}
