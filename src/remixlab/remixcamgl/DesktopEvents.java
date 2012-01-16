/**
 *                     ProScene (version 1.2.0)      
 *    Copyright (c) 2010-2011 by National University of Colombia
 *                 @author Jean Pierre Charalambos      
 *           http://www.disi.unal.edu.co/grupos/remixlab/
 *                           
 * This java package provides classes to ease the creation of interactive 3D
 * scenes in Processing.
 * 
 * This source file is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the Free
 * Software Foundation; either version 3 of the License, or (at your option)
 * any later version.
 * 
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for
 * more details.
 * 
 * A copy of the GNU General Public License is available on the World Wide Web
 * at <http://www.gnu.org/copyleft/gpl.html>. You can also obtain it by
 * writing to the Free Software Foundation, 51 Franklin Street, Suite 500
 * Boston, MA 02110-1335, USA.
 */

package remixlab.remixcamgl;

import java.awt.event.*;

public class DesktopEvents extends AWTDesktopEvents implements MouseListener, MouseMotionListener, KeyListener, MouseWheelListener {	
	public DesktopEvents(Scene s) {
		super(s);
	}

	/**
	 * Implementation of {@link java.awt.event.MouseWheelListener#mouseWheelMoved(MouseWheelEvent)}.
	 * <p>
	 * The action generated when the user start rotating the mouse wheel is handled by the
	 * {@link remixlab.proscene.Scene#mouseGrabber()} (if any), or the
	 * {@link remixlab.proscene.Scene#interactiveFrame()}
	 * (if @link remixlab.proscene.Scene#interactiveFrameIsDrawn()), or the
	 * {@link remixlab.proscene.Scene#camera()} (checks are performed in that order).
	 * <p>
	 * Mouse wheel rotation is interpreted according to the
	 * {@link remixlab.proscene.Scene#currentCameraProfile()} mouse wheel bindings.
	 * 
	 * @see #awtMousePressed(MouseEvent)
	 */
	@Override	
	public void mouseWheelMoved(MouseWheelEvent event) {
		awtMouseWheelMoved(event);
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub		
	}

	@Override
	public void keyReleased(KeyEvent event) {
		awtKeyReleased(event);
	}

	@Override
	public void keyTyped(KeyEvent event) {
		awtKeyTyped(event);
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		awtMouseDragged(e);
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		awtMouseMoved(e);	
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		awtMouseClicked(e);	
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		awtMousePressed(e);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		awtMouseReleased(e);
	}
}
