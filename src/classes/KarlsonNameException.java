package classes;

/**
 * Created by Mugenor on 23.02.2017.
 */
public class KarlsonNameException extends Exception {
    KarlsonNameException(){super("У Карлсона только одно имя!");}
    KarlsonNameException(String s){super(s);}
}
