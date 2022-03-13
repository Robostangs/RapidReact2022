package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.TalonFX;
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

    public static void loadMusic(TalonFX Falcon1, TalonFX Falcon2,TalonFX Falcon3,TalonFX Falcon4,TalonFX Falcon5,TalonFX Falcon6,TalonFX Falcon7,TalonFX Falcon8,TalonFX Falcon9,TalonFX Falcon10,TalonFX Falcon11,TalonFX Falcon12,TalonFX Falcon13,TalonFX Falcon14,TalonFX Falcon15,TalonFX Falcon16,TalonFX Falcon17, String musicFilePath) {
        m_Orchestra.addInstrument(Falcon1);
        m_Orchestra.addInstrument(Falcon2);
        m_Orchestra.addInstrument(Falcon3);
        m_Orchestra.addInstrument(Falcon4);
        m_Orchestra.addInstrument(Falcon5);
        m_Orchestra.addInstrument(Falcon6);
        m_Orchestra.addInstrument(Falcon7);
        m_Orchestra.addInstrument(Falcon8);
        m_Orchestra.addInstrument(Falcon9);
        m_Orchestra.addInstrument(Falcon10);
        m_Orchestra.addInstrument(Falcon11);
        m_Orchestra.addInstrument(Falcon12);
        m_Orchestra.addInstrument(Falcon13);
        m_Orchestra.addInstrument(Falcon14);
        m_Orchestra.addInstrument(Falcon15);
        m_Orchestra.addInstrument(Falcon16);
        m_Orchestra.addInstrument(Falcon17);
//Music File Path Just has to be the name of the chirp file in deploy, like dynamite.chrp
        m_Orchestra.loadMusic(musicFilePath);

        Timer.delay(2);
    }

    public static void play() {
        m_Orchestra.play();
    }

}
