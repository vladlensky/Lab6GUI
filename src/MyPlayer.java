import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.AudioDevice;
import javazoom.jl.player.Player;

import java.io.InputStream;

/**
 * Created by Mugenor on 23.04.2017.
 */
public class MyPlayer extends Player {
    MyPlayer(InputStream is) throws JavaLayerException{
        super(is, (AudioDevice)null);
    }
    MyPlayer(InputStream is, AudioDevice ad) throws JavaLayerException{
        super(is, ad);
    }
}
