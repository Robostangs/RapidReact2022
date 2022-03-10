package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.music.Orchestra;

import edu.wpi.first.wpilibj.Timer;

public class Music {
    public static Music instance;
    private static Orchestra m_Orchestra;

    public Music getInstance() {
        if(instance == null) {
            instance = new Music();
        }
        return instance;
    }

    public Music() {
        m_Orchestra = new Orchestra();
    }

    public static void loadMusic(String musicFilePath, WPI_TalonFX... Falcons) {
        for (WPI_TalonFX Falcon : Falcons) {
            m_Orchestra.addInstrument(Falcon);
        }
        //Music File Path Just has to be the name of the chirp file in deploy, like dynamite.chrp
        m_Orchestra.loadMusic(musicFilePath);
        Timer.delay(2);
    }

    public static void play() {
        m_Orchestra.play();
    }

}
