import classes.NormalHuman;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import javax.swing.*;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        NormalHuman nh = new NormalHuman();
        nh.thinkAbout("dadad");
        nh.forgetThought(1);
    }
}
