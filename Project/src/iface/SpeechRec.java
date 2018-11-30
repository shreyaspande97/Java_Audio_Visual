package iface;

import java.io.IOException;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Port;

import edu.cmu.sphinx.api.Configuration;
import edu.cmu.sphinx.api.LiveSpeechRecognizer;
import edu.cmu.sphinx.api.SpeechResult;

public class SpeechRec{
	Thread	speechThread;
	int ib = 0, is=0, ex=0, vd = 0;
	private LiveSpeechRecognizer recognizer;
	private volatile boolean recognizerStopped = true;
	public SpeechRec() {
		Configuration configuration = new Configuration();
		configuration.setAcousticModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us");
		configuration.setDictionaryPath("resource:/edu/cmu/sphinx/models/en-us/cmudict-en-us.dict");
		configuration.setGrammarPath("resource:/grammars");
		configuration.setGrammarName("grammar");
		configuration.setUseGrammar(true);
		try {
			recognizer = new LiveSpeechRecognizer(configuration);
		} catch (IOException ex) {}
		recognizer.startRecognition(true);
	}
	public void startSpeechThread(Moniter b) {
		if (speechThread != null && speechThread.isAlive())
			return;
		speechThread = new Thread(() -> {
			System.out.println("You can start to speak...");
			try {
				recognizerStopped = false;
				while (!recognizerStopped) {
					SpeechResult speechResult = recognizer.getResult();
					if (speechResult != null) {
						String result = speechResult.getHypothesis();
						System.out.println("You said : " + result);
						makeDecision(result, b);
					} 
				}
			} catch (Exception ex) {
				recognizerStopped = true;
			}
		});
		speechThread.start();

	}
	public void stopSpeechThread() {
		if (speechThread != null && speechThread.isAlive()) {
			recognizerStopped = true;
			speechThread = null;
		}
	}
	
	void makeDecision(String result, Moniter b)
	{
		ex = vd = 0;
		if(result.contains("select branch"))
		{
			if(result.contains("computer"))
				ib = 1;
			else if(result.contains("information"))
				ib = 2;
			else if(result.contains("mechanical"))
				ib = 3;
			else if(result.contains("civil"))
				ib = 4;
			is = 0;
		}
		if(result.contains("select subject"))
		{
			if(result.contains("intelligence"))
				is = 1;
			else if(result.contains("development") || result.contains("programming"))
				is = 2;
			else if(result.contains("logic") || result.contains("machine"))
				is = 3;
		}
		if(result.contains("close application"))
			ex = 1;
		else if(result.contains("open video"))
			ex = 2;
		if(result.contains("play video"))
			vd = 2;
		else if(result.contains("pause video"))
			vd = 3;
		else if(result.contains("stop video"))
			vd = 4;
		else if(result.contains("mute off"))
			vd = 5;
		else if(result.contains("mute video"))
			vd = 6;
		b.put(ib, is, ex, vd);
	}
}
