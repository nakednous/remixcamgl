package remixlab.remixcamgl;

import java.lang.reflect.Method;

import remixlab.remixcam.core.AbstractScene;
import remixlab.remixcam.devices.*;

public class Device extends AbstractDevice {
	protected Method handlerMethod;
	
	public Device(AbstractScene scn) {
		super(scn);
	}
	
	public Device(AbstractScene scn, Mode m) {
		super(scn, m);
	}	
	
	/**
	 * Overriding of
	 * {@link remixlab.remixcam.devices.AbstractHIDevice#addHandler(Object, String)}.
	 */
	@Override
	public void addHandler(Object obj, String methodName) {
		try {
			handlerMethod = obj.getClass().getMethod(methodName, new Class[] { Device.class });
			handlerObject = obj;
			handlerMethodName = methodName;
		} catch (Exception e) {
			  System.out.println("Something went wrong when registering your " + methodName + " method");
			  e.printStackTrace();
		}
	}
	
	/**
	 * Overriding of
	 * {@link remixlab.remixcam.devices.AbstractHIDevice#removeHandler()}.
	 */
	@Override
	public void removeHandler() {
		handlerMethod = null;
		handlerObject = null;
		handlerMethodName = null;
	}
	
	/**
	 * Overriding of
	 * {@link remixlab.remixcam.devices.AbstractHIDevice#invoke()}.
	 */
	@Override
	public boolean invoke() {
		boolean result = false;
		if (handlerObject != null) {
			try {
				handlerMethod.invoke(handlerObject, new Object[] { this });
				result = true;
			} catch (Exception e) {
				System.out.println("Something went wrong when invoking your "	+ handlerMethodName + " method");
				e.printStackTrace();
			}
		}
		return result;
	}
	
	@Override
	public void nextCameraMode() {
		switch (camMode) {		
		case GOOGLE_EARTH:
			if (Device.class == this.getClass())
				setCameraMode(CameraMode.FIRST_PERSON);
			else
				setCameraMode(CameraMode.CUSTOM);
			break;
		default:
			super.nextCameraMode();
			break;
		}
	}
	
	@Override
  public void previousCameraMode() {  	
  	switch (camMode) {
  	case FIRST_PERSON:
			if (Device.class == this.getClass())
				setCameraMode(CameraMode.GOOGLE_EARTH);
			else
				setCameraMode(CameraMode.CUSTOM);
			break;
		default:
			super.previousCameraMode();
		break;
		}
  }
  
	@Override
  public void nextIFrameMode() {  	
  	switch (iFrameMode) {		
		case WORLD:
			if (Device.class == this.getClass())
				setIFrameMode(IFrameMode.FRAME);
			else
				setIFrameMode(IFrameMode.CUSTOM);
			break;
		default:
			super.nextIFrameMode();
			break;
		}
  }
  
	@Override
  public void previousIFrameMode() {  	
  	switch (iFrameMode) {
		case FRAME:
			if (Device.class == this.getClass())
				setIFrameMode(IFrameMode.WORLD);
			else
				setIFrameMode(IFrameMode.CUSTOM);
			break;
		default:
			super.previousIFrameMode();
			break;
		}
  }	
}
