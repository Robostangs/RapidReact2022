package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.music.Orchestra;

import edu.wpi.first.wpilibj.Timer;

public class Music {

    private static Music instance;

    private final static Orchestra mOrchestra = new Orchestra();

    public Music getInstance() {
        if (instance == null) {
            instance = new Music();
        }
        return instance;
    }

    private Music() {}

    public static void loadMusic(String musicFilePath, WPI_TalonFX... Falcons) {
        for (WPI_TalonFX Falcon : Falcons) {
            mOrchestra.addInstrument(Falcon);
        }
        // Music File Path Just has to be the name of the chirp file in deploy, like
        // dynamite.chrp
        mOrchestra.loadMusic(musicFilePath);
        Timer.delay(2);
    }

    public static void play() {
        mOrchestra.play();
    }
}
