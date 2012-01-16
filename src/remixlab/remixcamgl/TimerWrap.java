package remixlab.remixcamgl;

import java.util.*;

import remixlab.remixcam.util.*;

public class TimerWrap implements Timable {
	Scene scene;
	Timer timer;
	TimerTask timerTask;
	Taskable caller;

	public TimerWrap(Scene scn, Taskable o) {
		scene = scn;
		caller = o;
	}

	public Taskable timerJob() {
		return caller;
	}

	@Override
	public void create() {
		stop();
		timer = new Timer();
		timerTask = new TimerTask() {
			public void run() {
				caller.execute();
			}
		};
	}

	@Override
	public void run(long period) {
		create();
		timer.scheduleAtFixedRate(timerTask, 0, period);
	}

	@Override
	public void runOnce(long period) {
		create();
		timer.schedule(timerTask, period);
	}

	@Override
	public void cancel() {
		stop();
	}

	@Override
	public void stop() {
		if (timer != null) {
			timer.cancel();
			timer.purge();
		}
	}

	@Override
	public boolean isActive() {
		return timer != null;
	}
}
