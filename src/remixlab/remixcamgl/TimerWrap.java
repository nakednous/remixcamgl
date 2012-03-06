package remixlab.remixcamgl;

import java.util.*;

import remixlab.remixcam.util.*;

public class TimerWrap implements Timable {
	Scene scene;
	Timer timer;
	TimerTask timerTask;
	Taskable caller;
	boolean runOnlyOnce;
	long prd;

	public TimerWrap(Scene scn, Taskable o) {
		this(scn, o, false);
	}

	public TimerWrap(Scene scn, Taskable o, boolean singleShot) {
		scene = scn;
		runOnlyOnce = singleShot;
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
		prd = period;
		run();
	}

	@Override
	public void run() {
		create();
		if(isSingleShot())
			timer.schedule(timerTask, prd);
		else
			timer.scheduleAtFixedRate(timerTask, 0, prd);		
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
			/**
			 * prd = 0; runOnlyOnce = false;
			 */
		}
	}

	@Override
	public boolean isActive() {
		return timer != null;
	}

	@Override
	public long period() {
		return prd;
	}

	@Override
	public void setPeriod(long period) {
		prd = period;
	}

	@Override
	public boolean isSingleShot() {
		return runOnlyOnce;
	}

	@Override
	public void setSingleShot(boolean singleShot) {
		runOnlyOnce = singleShot;
	}
}
