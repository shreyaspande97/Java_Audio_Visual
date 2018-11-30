package iface;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.embed.swing.JFXPanel;
import javafx.event.*;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.media.*;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.*;

public class VideoPlayer {
	
	JFrame frame;
	Thread t1, t2;
	Scene scene;
	String file;
	SceneGenerator sg;
	JFXPanel panel;
	VideoPlayer(String f) 
	{
		file = f;
		SwingUtilities.invokeLater(
			new Runnable() 
			{
				public void run() 
				{
					initAndShowGUI();
				}
		    }
		);
	}
	private void initAndShowGUI()
	{
		frame = new JFrame("Video Player");
		panel = new JFXPanel();
		frame.add(panel);
		frame.setBounds(10, 10, 1900, 1000);
		frame.setVisible(true);
		frame.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent we) {
				close();
			}
		});
	    Platform.runLater(
	    	new Runnable() 
	    	{
	    		public void run() 
	    		{
	    			initFX(panel);        
	    		}
	    	}
	    );
	}
	private void initFX(JFXPanel fxPanel) 
	{
		panel = fxPanel;
		sg = new SceneGenerator();
		scene = sg.createScene(file);
		panel.setScene(scene);
	}
	public void perform(int ch)
	{
		sg.pr(ch);
	}
	public void close()
	{
		System.exit(0);
	}
}

class SceneGenerator {   
	Thread t3, t4;
	MediaPlayer player;
	Button play, stop, mute;
	MediaView mediaView;
	ProgressBar prg;
	int videoState[] = new int[2];
	public Scene createScene(String file) 
	{
	    videoState[0] = videoState[1] = 1;
		BorderPane layout = new BorderPane();
		GridPane control = new GridPane();
		layout.setBackground(new Background(new BackgroundFill(Color.web("#FFFFFF"), CornerRadii.EMPTY, Insets.EMPTY)));
		control.setBackground(new Background(new BackgroundFill(Color.web("#E3E3E3"), CornerRadii.EMPTY, Insets.EMPTY)));
		layout.setPadding(new Insets(20,20,20,20));
	    mediaView = new MediaView();
	    play = new Button("Pause");
	    stop = new Button("Stop");
	    mute = new Button("Mute");
	    prg = new ProgressBar();
	    player = createPlayer("file:///C:/Users/Shreyas%20Deshpande/workspace/Project/vid/"+ file +".mp4");
	    play.setOnAction(
	    	new EventHandler<ActionEvent>() 
	    	{
	    		public void handle(ActionEvent actionEvent) 
	    		{
	    			if ("Pause".equals(play.getText())) 
	    			{
	    				mediaView.getMediaPlayer().pause();
	    				play.setText("Play");
	    				videoState[0] = 0;
	    			}
	    			else
	    			{
						mediaView.getMediaPlayer().play();
						play.setText("Pause");
	    				videoState[0] = 1;
	    			}
	    		}
	    	}
	    );
	    stop.setOnAction(
	    	new EventHandler<ActionEvent>() 
	    	{
	    		public void handle(ActionEvent actionEvent) 
	    		{
					mediaView.getMediaPlayer().stop();
    				play.setText("  Play  ");
    				videoState[0] = 0;
	    		}
	    	}
	    );
	    mute.setOnAction(
	    	new EventHandler<ActionEvent>() 
	    	{
	    		public void handle(ActionEvent actionEvent) 
	    		{
	    			if ("Mute".equals(mute.getText())) 
	    			{
	    				mediaView.getMediaPlayer().setMute(true);
	    				mute.setText("Unmute");
	    				videoState[1] = 0;
	    			}
	    			else
	    			{
						mediaView.getMediaPlayer().setMute(false);
						mute.setText("Mute");
	    				videoState[1] = 1;
	    			}
	    		}
	    	}
	    );
		mediaView.setMediaPlayer(player);
		mediaView.getMediaPlayer().play();
		prg.setProgress(0);
		ChangeListener<Duration> pcl = new ChangeListener<Duration>() 
		{
			public void changed(ObservableValue<? extends Duration> observableValue, Duration oldValue, Duration newValue) 
			{
				prg.setProgress(1.0 * player.getCurrentTime().toMillis() / player.getTotalDuration().toMillis());
			}
	    };
	    player.currentTimeProperty().addListener(pcl);
		layout.setCenter(mediaView);
		play.setMinWidth(100);
		stop.setMinWidth(100);
		mute.setMinWidth(100);
		prg.setMinWidth(1350);
		control.add(play,1,1);
		control.add(stop,2,1);
		control.add(mute,3,1);
		control.add(prg,4,1);
		control.setMargin(play, new Insets(20, 20, 20, 20));
		control.setMargin(stop, new Insets(20, 20, 20, 20));
		control.setMargin(mute, new Insets(20, 20, 20, 20));
		control.setMargin(prg, new Insets(20, 20, 20, 20));
		layout.setBottom(control);
		return new Scene(layout, 958, 305);
	}
	
	private MediaPlayer createPlayer(String aMediaSrc) 
	{
		System.out.println("Playing: " + aMediaSrc);
		final MediaPlayer player = new MediaPlayer(new Media(aMediaSrc));
		player.setOnError(
			new Runnable() 
			{
				public void run() 
				{
					System.out.println("Media error occurred: " + player.getError());
				}
			}
		);
		return player;
	}
	
	public void pr(int a)
	{
		if(a == 2)
		{
			if(videoState[0] == 0)
			{
				mediaView.getMediaPlayer().play();
				videoState[0] = 1;
			}
		}
		else if(a == 3)
		{
			if(videoState[0] == 1)
			{
				mediaView.getMediaPlayer().pause();
				videoState[0] = 0;
			}
		}
		else if(a == 4)
		{
			player.pause();
			videoState[0] = 0;
		}
		else if(a == 5)
		{
			if (videoState[1] == 0) 
			{
				mediaView.getMediaPlayer().setMute(false);
				videoState[1] = 1;
			}
		}
		else if(a == 6)
		{
			if (videoState[1] == 1) 
			{
				mediaView.getMediaPlayer().setMute(true);
				videoState[1] = 0;
			}
		}
	}
}